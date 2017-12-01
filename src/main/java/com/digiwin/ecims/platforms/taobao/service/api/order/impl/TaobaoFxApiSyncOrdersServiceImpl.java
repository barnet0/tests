package com.digiwin.ecims.platforms.taobao.service.api.order.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.response.FenxiaoOrdersGetResponse;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.api.order.TaobaoFxApiSyncOrdersService;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpFxTradeService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool.FxTradesGetTimeType;

@Service
public class TaobaoFxApiSyncOrdersServiceImpl implements TaobaoFxApiSyncOrdersService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private TaobaoJdpFxTradeService taobaoJdpFxTrade;
  
  @Autowired
  private TaobaoApiService taobaoApiService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
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
      totalSize = getIncrementalOrdersCount(appKey, appSecret, accessToken, sDate, eDate);
      
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
      FenxiaoOrdersGetResponse response =
          taobaoApiService.taobaoFenxiaoOrdersGet(
              appKey, appSecret, accessToken, null, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              FxTradesGetTimeType.UPDATE_TIME.getType(), 
              i, (long)pageSize, 
        null, TaobaoCommonTool.API_FX_ORDER_FIELDS);

      loginfoOperateService.newTransaction4SaveSource(
          sDate, eDate, 
          TaobaoCommonTool.STORE_TYPE_FX,
          "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.fenxiao.orders.get 查询采购单信息",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (PurchaseOrder purchaseOrder : response.getPurchaseOrders()) {
        List<AomsordT> list = taobaoJdpFxTrade.parseResponseToAomsordT(response, storeId);
        aomsordTs.addAll(list);

        // 比較區間資料時間，取最大時間
        if (purchaseOrder.getModified().after(lastUpdateTime)) {
          lastUpdateTime = purchaseOrder.getModified();
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
    
    return true;
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws ApiException, IOException {
    // 參數設定
    long pageSize = TaobaoCommonTool.DEFAULT_PAGE_SIZE;
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    // 取得時間區間內總資料筆數
    long totalSize = 0L;
    totalSize = getIncrementalOrdersCount(startDate, endDate, 
        appKey, appSecret, accessToken);
    
    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      FenxiaoOrdersGetResponse response =
          taobaoApiService.taobaoFenxiaoOrdersGet(
              appKey, appSecret, accessToken, null, 
              DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
              FxTradesGetTimeType.UPDATE_TIME.getType(), 
              i, (long)pageSize, 
        null, TaobaoCommonTool.API_FX_ORDER_FIELDS);

      loginfoOperateService.newTransaction4SaveSource(
          startDate, endDate, 
          TaobaoCommonTool.STORE_TYPE_FX,
          "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.fenxiao.orders.get 查询采购单信息",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      List<AomsordT> list = taobaoJdpFxTrade.parseResponseToAomsordT(response, storeId);
      aomsordTs.addAll(list);
      
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
    }
    return totalSize;
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws ApiException, IOException {
    // 取得時間區間內總資料筆數
    long totalSize = 0L;
    FenxiaoOrdersGetResponse fxOrdersGetResponse = 
        taobaoApiService.taobaoFenxiaoOrdersGet(
        appKey, appSecret, accessToken, null, 
        DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
        FxTradesGetTimeType.UPDATE_TIME.getType(), 
        TaobaoCommonTool.MIN_PAGE_NO, TaobaoCommonTool.MIN_PAGE_SIZE, 
        null, TaobaoCommonTool.API_FX_ORDER_MIN_FIELDS);

    if (fxOrdersGetResponse == null
        || fxOrdersGetResponse.getSubCode() != null) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoFxApiSyncOrdersServiceImpl#getTaobaoIncrementalOrdersCount", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), "获取数据异常", fxOrdersGetResponse.getSubMsg(), "", "");
      return null;
    } else {
      totalSize = fxOrdersGetResponse.getTotalResults();
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
      FenxiaoOrdersGetResponse response =
          taobaoApiService.taobaoFenxiaoOrdersGet(
              appKey, appSecret, accessToken, null, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              FxTradesGetTimeType.CREATE_TIME.getType(), 
              i, (long)pageSize, 
        null, TaobaoCommonTool.API_FX_ORDER_FIELDS);

      loginfoOperateService.newTransaction4SaveSource(
          sDate, eDate, 
          TaobaoCommonTool.STORE_TYPE_FX,
          "[" + UseTimeEnum.CREATE_TIME + "]|taobao.fenxiao.orders.get 查询采购单信息",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (PurchaseOrder purchaseOrder : response.getPurchaseOrders()) {
        List<AomsordT> list = taobaoJdpFxTrade.parseResponseToAomsordT(response, storeId);
        aomsordTs.addAll(list);

        // 比較區間資料時間，取最大時間
        if (purchaseOrder.getCreated().after(lastUpdateTime)) {
          lastUpdateTime = purchaseOrder.getCreated();
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

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      FenxiaoOrdersGetResponse response =
          taobaoApiService.taobaoFenxiaoOrdersGet(
              appKey, appSecret, accessToken, null, 
              DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
              FxTradesGetTimeType.CREATE_TIME.getType(), 
              i, (long)pageSize, 
        null, TaobaoCommonTool.API_FX_ORDER_FIELDS);

      loginfoOperateService.newTransaction4SaveSource(
          startDate, endDate, 
          TaobaoCommonTool.STORE_TYPE_FX,
          "[" + UseTimeEnum.CREATE_TIME + "]|taobao.fenxiao.orders.get 查询采购单信息",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      List<AomsordT> list = taobaoJdpFxTrade.parseResponseToAomsordT(response, storeId);
      aomsordTs.addAll(list);
      
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
    }

    return totalSize;
  }

  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws ApiException, IOException {
    // 取得時間區間內總資料筆數
    long totalSize = 0L;
    FenxiaoOrdersGetResponse fxOrdersGetResponse = 
        taobaoApiService.taobaoFenxiaoOrdersGet(
        appKey, appSecret, accessToken, null, 
        DateTimeTool.parse(startDate), DateTimeTool.parse(endDate), 
        FxTradesGetTimeType.CREATE_TIME.getType(), 
        TaobaoCommonTool.MIN_PAGE_NO, TaobaoCommonTool.MIN_PAGE_SIZE, 
        null, TaobaoCommonTool.API_FX_ORDER_MIN_FIELDS);

    if (fxOrdersGetResponse == null
        || fxOrdersGetResponse.getSubCode() != null) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "TaobaoFxApiSyncOrdersServiceImpl#getTaobaoFenxiaoCreatedOrdersCount", 
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), "获取数据异常", fxOrdersGetResponse.getSubMsg(), "", "");
      return null;
    } else {
      totalSize = fxOrdersGetResponse.getTotalResults();
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
    
    FenxiaoOrdersGetResponse response =
        taobaoApiService.taobaoFenxiaoOrdersGet(
            appKey, appSecret, accessToken, null, 
            null, null, 
            FxTradesGetTimeType.UPDATE_TIME.getType(), 
            TaobaoCommonTool.MIN_PAGE_NO, TaobaoCommonTool.MIN_PAGE_SIZE, 
      Long.parseLong(orderId), TaobaoCommonTool.API_FX_ORDER_FIELDS);

    loginfoOperateService.newTransaction4SaveSource(
        "N/A", "N/A", 
        TaobaoCommonTool.STORE_TYPE_FX,
        "taobao.fenxiao.orders.get 查询采购单信息",
        JsonUtil.format(response),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    List<AomsordT> aomsordTs = taobaoJdpFxTrade.parseResponseToAomsordT(response, storeId);
    taskService.newTransaction4Save(aomsordTs);
    
    return true;
  }

}
