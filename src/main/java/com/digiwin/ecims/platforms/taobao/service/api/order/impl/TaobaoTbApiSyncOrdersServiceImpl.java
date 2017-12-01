package com.digiwin.ecims.platforms.taobao.service.api.order.impl;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.taobao.bean.jst.JstParam;
import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.api.order.TaobaoTbApiSyncOrdersService;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncTbOrdersService;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpTbTradeService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Trade;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaobaoTbApiSyncOrdersServiceImpl implements TaobaoTbApiSyncOrdersService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private TaobaoJdpTbTradeService taobaoJdpTbTrade;

  @Autowired
  private TaobaoApiService taobaoApiService;
  
  @Autowired
  private TaobaoJstSyncTbOrdersService taobaoJstSyncTbOrdersService;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws ApiException, IOException {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws ApiException, IOException {
    // 參數設定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    final String sDate = taskScheduleConfig.getLastUpdateTime();
    final String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 由于淘宝增量API只允许时间跨度（enddate-start <= 1 day），所以这里需要计算请求次数，并循环调用
    int periodDay = DateTimeTool.getDiffDays(sDate, eDate) + 1;
    String tmpSdate = sDate;
    String tmpEdate = "";
    long totalSize = 0L;
    for (int i = 0; i < periodDay; i++) {
      if (i + 1 == periodDay) {
        tmpEdate = eDate;
      } else {
        tmpEdate = DateTimeTool.getAfterDays(tmpSdate, 1);
      }
      
   // 取得時間區間內總資料筆數
      long size = 0L;
      boolean sizeMoreThanSetting = false;
      do {
        size = getIncrementalOrdersCount(appKey, appSecret, accessToken, tmpSdate, tmpEdate);
        
        sizeMoreThanSetting = size > taskScheduleConfig.getMaxReadRow();
        if (sizeMoreThanSetting) {
          // tmpEdate變為sDate與tmpEdate的中間時間
          tmpEdate = DateTimeTool.getDivisionEndTime(tmpSdate, tmpEdate);
        }
      } while (sizeMoreThanSetting);

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀

      } else if (size == 0L) {
        // 区间内没有资料，且同步的是一天前到目前为止的资料，才更新排程时间
        if (DateTimeTool.parse(tmpEdate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))
            && (i + 1 == periodDay)) {
          // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), tmpEdate);
          return true;
        }
      }
      
      totalSize += size;
      // 區間內有資料， 計算頁數
      long pageNum = (size / pageSize) + (size % pageSize == 0 ? 0 : 1);

      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      // 針對每一頁(倒序)的所有資料新增
      for (long j = pageNum; j > 0; j--) {
        TradesSoldIncrementGetResponse response =
            taobaoApiService.taobaoTradesSoldIncrementGet(
                appKey, appSecret, accessToken, 
                TaobaoCommonTool.API_ORDER_MIN_FIELDS, 
                DateTimeTool.parse(tmpSdate), DateTimeTool.parse(tmpEdate), 
                (long)j, (long)pageSize, Boolean.FALSE);

        loginfoOperateService.newTransaction4SaveSource(tmpSdate, tmpEdate, TaobaoCommonTool.STORE_TYPE,
            "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.trades.sold.increment.get 查询卖家已卖出的增量交易数据（根据修改时间）",
            JsonUtil.format(response),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        for (Trade trade : response.getTrades()) {
          TradeFullinfoGetResponse fullinfoGetResponse = 
              taobaoApiService.taobaoTradeFullinfoGet(appKey, appSecret, accessToken, 
                  TaobaoCommonTool.API_ORDER_FIELDS, trade.getTid());

          loginfoOperateService.newTransaction4SaveSource(
              "N/A", "N/A", 
              TaobaoCommonTool.STORE_TYPE,
              "taobao.trade.fullinfo.get 获取单笔交易的详细信息",
              JsonUtil.format(fullinfoGetResponse),
              SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
              taskScheduleConfig.getScheduleType());
          
          if (fullinfoGetResponse.isSuccess()) {
            List<AomsordT> list = taobaoJdpTbTrade
                .parseTradeFullinfoGetResponseToAomsordT(fullinfoGetResponse, storeId);
            aomsordTs.addAll(list);
            
            // 比較區間資料時間，取最大時間
            if (trade.getModified().after(lastUpdateTime)) {
              lastUpdateTime = trade.getModified();
            }
          }
        }
        taskService.newTransaction4Save(aomsordTs);
        // 清空列表，为下一页资料作准备
        aomsordTs.clear();
      }

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
      // 排程. add by xavier on 20150829
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀

      } else {
        // 更新updateTime 成為下次的sDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
            DateTimeTool.format(lastUpdateTime));
      }
      
      // 将本次的结束时间作为下一次的开始时间
      tmpSdate = tmpEdate;
    }
    
    return true;
  }

  @Override
  public Long syncOrdersByIncremental(final String startDate, final String endDate, String storeId, String scheduleType)
      throws ApiException, IOException {
    long pageSize = TaobaoCommonTool.DEFAULT_PAGE_SIZE;
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

 // 由于淘宝增量API只允许时间跨度（enddate-start <= 1 day），所以这里需要计算请求次数，并循环调用
    int periodDay = DateTimeTool.getDiffDays(startDate, endDate) + 1;
    String tmpSdate = startDate;
    String tmpEdate = "";
    long totalSize = 0L;
    for (int i = 0; i < periodDay; i++) {
      if (i + 1 == periodDay) {
        tmpEdate = endDate;
      } else {
        tmpEdate = DateTimeTool.getAfterDays(tmpSdate, 1);
      }
   // 取得時間區間內總資料筆數
      long size = getIncrementalOrdersCount(appKey, appSecret, accessToken, tmpSdate, tmpEdate);
      
      totalSize += size;
      // 區間內有資料， 計算頁數
      long pageNum = (size / pageSize) + (size % pageSize == 0 ? 0 : 1);

      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      // 針對每一頁(倒序)的所有資料新增
      for (long j = pageNum; j > 0; j--) {
        TradesSoldIncrementGetResponse response =
            taobaoApiService.taobaoTradesSoldIncrementGet(
                appKey, appSecret, accessToken, 
                TaobaoCommonTool.API_ORDER_MIN_FIELDS, 
                DateTimeTool.parse(tmpSdate), DateTimeTool.parse(tmpEdate), 
                (long)j, (long)pageSize, Boolean.TRUE);

        loginfoOperateService.newTransaction4SaveSource(tmpSdate, tmpEdate, TaobaoCommonTool.STORE_TYPE,
            "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.trades.sold.increment.get 查询卖家已卖出的增量交易数据（根据修改时间）",
            JsonUtil.format(response),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            null);

        for (Trade trade : response.getTrades()) {
          TradeFullinfoGetResponse fullinfoGetResponse = 
              taobaoApiService.taobaoTradeFullinfoGet(appKey, appSecret, accessToken, 
                  TaobaoCommonTool.API_ORDER_FIELDS, trade.getTid());

          loginfoOperateService.newTransaction4SaveSource(
              "N/A", "N/A", 
              TaobaoCommonTool.STORE_TYPE,
              "taobao.trade.fullinfo.get 获取单笔交易的详细信息",
              JsonUtil.format(fullinfoGetResponse),
              SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
              null);
          
          if (fullinfoGetResponse.isSuccess()) {
            List<AomsordT> list = taobaoJdpTbTrade
                .parseTradeFullinfoGetResponseToAomsordT(fullinfoGetResponse, storeId);
            aomsordTs.addAll(list);
          }
        }
        taskService.newTransaction4Save(aomsordTs);
        // 清空列表，为下一页资料作准备
        aomsordTs.clear();
      }

      
      // 将本次的结束时间作为下一次的开始时间
      tmpSdate = tmpEdate;
    }
    return totalSize;
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws ApiException, IOException {
    // 取得時間區間內總資料筆數
    TradesSoldIncrementGetResponse tradesSoldIncreGetResponse = null;
    long totalSize = 0L;
    tradesSoldIncreGetResponse =
        taobaoApiService.taobaoTradesSoldIncrementGet(
            appKey, appSecret, accessToken, 
            TaobaoCommonTool.API_ORDER_MIN_FIELDS, 
            DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
            TaobaoCommonTool.MIN_PAGE_NO, TaobaoCommonTool.MIN_PAGE_SIZE, Boolean.FALSE); 

    if (tradesSoldIncreGetResponse == null
        || tradesSoldIncreGetResponse.getSubCode() != null) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoTbApiSyncOrdersServiceImpl#getTaobaoIncrementalOrdersCount", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), "获取数据异常", tradesSoldIncreGetResponse.getSubMsg(), "", "");
      return null;
    } else {
      totalSize = tradesSoldIncreGetResponse.getTotalResults();
    }
    
    return totalSize;
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws ApiException, IOException {
    // 參數設定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    // 取得時間區間內總資料筆數
    long totalSize = 0L;
    boolean sizeMoreThanSetting = false;
    do {
      totalSize = getCreatedOrdersCount(appKey, appSecret, accessToken, sDate, eDate);
      
      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);
     
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else if (totalSize == 0L) {
      // 区间内没有资料 
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return true;
      }
    }

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      TradesSoldGetResponse response =
          taobaoApiService.taobaoTradesSoldGet(
              appKey, appSecret, accessToken, 
              TaobaoCommonTool.API_ORDER_MIN_FIELDS, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              (long)i, (long)pageSize, Boolean.TRUE);
      
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, TaobaoCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|taobao.trades.sold.get (查询卖家已卖出的交易数据（根据创建时间）)",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (Trade trade : response.getTrades()) {
        TradeFullinfoGetResponse fullinfoGetResponse = 
            taobaoApiService.taobaoTradeFullinfoGet(appKey, appSecret, accessToken, 
                TaobaoCommonTool.API_ORDER_FIELDS, trade.getTid());

        loginfoOperateService.newTransaction4SaveSource(
            sDate, eDate, 
            TaobaoCommonTool.STORE_TYPE,
            "taobao.trade.fullinfo.get 获取单笔交易的详细信息",
            JsonUtil.format(fullinfoGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        
        List<AomsordT> list = taobaoJdpTbTrade
            .parseTradeFullinfoGetResponseToAomsordT(fullinfoGetResponse, storeId);
        aomsordTs.addAll(list);

        // 比較區間資料時間，取最大時間
        if (trade.getModified().after(lastUpdateTime)) {
          lastUpdateTime = trade.getModified();
        }
      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      // 更新updateTime 成為下次的sDate
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    return true;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws ApiException, IOException {
    // 參數設定
    long pageSize = TaobaoCommonTool.DEFAULT_PAGE_SIZE;
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    // 取得時間區間內總資料筆數
    long totalSize = 0L;
    totalSize = getCreatedOrdersCount(appKey, appSecret, accessToken, startDate, endDate);

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

//    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      TradesSoldGetResponse response =
          taobaoApiService.taobaoTradesSoldGet(
              appKey, appSecret, accessToken, 
              TaobaoCommonTool.API_ORDER_MIN_FIELDS, 
              DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
              (long)i, (long)pageSize, Boolean.TRUE);
      
      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, TaobaoCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|taobao.trades.sold.get (查询卖家已卖出的交易数据（根据创建时间）)",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      for (Trade trade : response.getTrades()) {
//        TradeFullinfoGetResponse fullinfoGetResponse = 
//            taobaoApiService.taobaoTradeFullinfoGet(appKey, appSecret, accessToken, 
//                TaobaoCommonTool.API_ORDER_FIELDS, trade.getTid());
//
//        loginfoOperateService.newTransaction4SaveSource(
//            startDate, endDate, 
//            TaobaoCommonTool.STORE_TYPE,
//            "taobao.trade.fullinfo.get 获取单笔交易的详细信息",
//            JsonUtil.format(fullinfoGetResponse),
//            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
//            null);
        JstParam jstParam = new JstParam();
        jstParam.setId(trade.getTid() + "");
        // 记录原始数据到日志时会使用
        jstParam.setScheduleType(scheduleType);
        jstParam.setStoreId(storeId);

        PostBean postBean = new PostBean(
            TaobaoJstSyncTbOrdersService.GET_DETAIL_API.getApi(), jstParam);
        
        taobaoJstSyncTbOrdersService.saveWithGetDetail(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.JST_RDS), postBean, AomsordT.class);
//        List<AomsordT> list = taobaoJdpTbTrade
//            .parseTradeFullinfoGetResponseToAomsordT(fullinfoGetResponse, storeId);
//        aomsordTs.addAll(list);

      }
//      taskService.newTransaction4Save(aomsordTs);
//      // 清空列表，为下一页资料作准备
//      aomsordTs.clear();
    }
    return totalSize;
  }

  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws ApiException, IOException {
    // 取得時間區間內總資料筆數
    TradesSoldGetResponse tradesSoldGetResponse = null;
    long totalSize = 0L;
    tradesSoldGetResponse =
        taobaoApiService.taobaoTradesSoldGet(
            appKey, appSecret, accessToken, 
            TaobaoCommonTool.API_ORDER_MIN_FIELDS, 
            DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
            TaobaoCommonTool.MIN_PAGE_NO, TaobaoCommonTool.MIN_PAGE_SIZE, Boolean.FALSE); 

    if (tradesSoldGetResponse == null
        || tradesSoldGetResponse.getSubCode() != null) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoTbApiSyncOrdersServiceImpl#getTaobaoCreatedOrdersCount", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), "获取数据异常", tradesSoldGetResponse.getSubMsg(), "", "");
      return null;
    } else {
      totalSize = tradesSoldGetResponse.getTotalResults();
    }
    
    return totalSize;
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) 
      throws ApiException, IOException {
 // 參數設定
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    TradeFullinfoGetResponse fullinfoGetResponse = 
        taobaoApiService.taobaoTradeFullinfoGet(
            appKey, appSecret, accessToken, 
            TaobaoCommonTool.API_ORDER_FIELDS, Long.parseLong(orderId));

    loginfoOperateService.newTransaction4SaveSource(
        "N/A", "N/A", 
        TaobaoCommonTool.STORE_TYPE,
        "taobao.trade.fullinfo.get 获取单笔交易的详细信息",
        JsonUtil.format(fullinfoGetResponse),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    List<AomsordT> aomsordTs = taobaoJdpTbTrade
        .parseTradeFullinfoGetResponseToAomsordT(fullinfoGetResponse, storeId);
    taskService.newTransaction4Save(aomsordTs);
    
    return true;
  }

}
