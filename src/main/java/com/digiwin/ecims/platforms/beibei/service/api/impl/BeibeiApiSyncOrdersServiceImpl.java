package com.digiwin.ecims.platforms.beibei.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.OrdersGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderGetResponse;
import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiService;
import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiSyncOrdersService;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool.OrderTimeRange;
import com.digiwin.ecims.platforms.beibei.util.translator.AomsordTTranslator;
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

@Service
public class BeibeiApiSyncOrdersServiceImpl implements BeibeiApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(BeibeiApiSyncOrdersServiceImpl.class);
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private BeibeiApiService beibeiApiService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取得时间区间内总资料笔数
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
    } else if (totalSize == BeibeiCommonTool.NO_COUNT_RETURNED) {
      // API调用异常，则把sdate+1天回写到排程
      String tomorrow = DateTimeTool.getAfterDays(sDate, 1);
      if (DateTimeTool.isBefore(tomorrow, taskScheduleConfig.getSysDate())) {
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), tomorrow);
        return false;
      }
    }
    
 // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OuterTradeOrderGetResponse response = 
          beibeiApiService.beibeiOuterTradeOrderGet(
              appKey, appSecret, accessToken, 
              null, OrderTimeRange.UPDATE_TIME.getValue(), 
              sDate, eDate, 
              i, (long)pageSize); 
      
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BeibeiCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|beibei.outer.trade.order.get 订单列表",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (OrdersGetDto orderGetDto : response.getData()) {
//        Long oid = orderGetDto.getOid();
//        OuterTradeOrderDetailGetResponse orderDetailGetResponse = 
//              beibeiApiService.beibeiOuterTradeOrderDetailGet(
//            appKey, appSecret, accessToken, oid.toString());
//        
//        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BeibeiCommonTool.STORE_TYPE,
//            "beibei.outer.trade.order.detail.get 订单详情",
//            JsonUtil.formatByMilliSecond(orderDetailGetResponse),
//            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
//            taskScheduleConfig.getScheduleType());
        
        List<AomsordT> list =
            new AomsordTTranslator(orderGetDto).doTranslate(storeId);
        
//        List<AomsordT> list =
//            new AomsordTTranslator(orderDetailGetResponse).doTranslate(storeId);
        
        if (DateTimeTool.parse(orderGetDto.getModifiedTime()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(orderGetDto.getModifiedTime());
        }
        
        aomsordTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
      
      // add on 20161111 API返回网络繁忙错误
      Thread.sleep(1000);
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      // 更新updateTime 成為下次的sDate
      logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId,
      String scheduleType) throws Exception {
    // 参数设定
    Long pageSize = BeibeiCommonTool.DEFAULT_PAGE_SIZE;
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esv.getAppKey();
    String appSecret = esv.getAppSecret();
    String accessToken = esv.getAccessToken();
    
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = getCreatedOrdersCount(appKey, appSecret, accessToken, startDate, endDate);

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OuterTradeOrderGetResponse response = 
          beibeiApiService.beibeiOuterTradeOrderGet(
              appKey, appSecret, accessToken, 
              null, OrderTimeRange.UPDATE_TIME.getValue(), 
              startDate, endDate, 
              i, (long)pageSize); 
      
      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, BeibeiCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|beibei.outer.trade.order.get 订单列表",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      for (OrdersGetDto orderGetDto : response.getData()) {
        Long oid = orderGetDto.getOid();
        OuterTradeOrderDetailGetResponse orderDetailGetResponse = 
              beibeiApiService.beibeiOuterTradeOrderDetailGet(
            appKey, appSecret, accessToken, oid.toString());
        
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", BeibeiCommonTool.STORE_TYPE,
            "beibei.outer.trade.order.detail.get 订单详情",
            JsonUtil.formatByMilliSecond(orderDetailGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            null);
        
        List<AomsordT> list =
            new AomsordTTranslator(orderDetailGetResponse).doTranslate(storeId);
        
        aomsordTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
    }

    return totalSize;
    
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    OuterTradeOrderGetResponse response = 
        beibeiApiService.beibeiOuterTradeOrderGet(appKey, appSecret, accessToken, 
          null, OrderTimeRange.UPDATE_TIME.getValue(), 
          startDate, endDate, BeibeiCommonTool.MIN_PAGE_NO, BeibeiCommonTool.MIN_PAGE_SIZE);
    if (response == null
        || (response.getSuccess() == Boolean.FALSE)) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "BeibeiApiSyncOrdersServiceImpl#getIncrementalOrdersCount", 
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), JsonUtil.format(response), response.getMessage(), "", "");
      return BeibeiCommonTool.NO_COUNT_RETURNED;
    }
    Long count = response.getCount();
    
    return count;
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId,
      String scheduleType) throws Exception {
    // 参数设定
    Long pageSize = BeibeiCommonTool.DEFAULT_PAGE_SIZE;
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esv.getAppKey();
    String appSecret = esv.getAppSecret();
    String accessToken = esv.getAccessToken();
    
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = getCreatedOrdersCount(appKey, appSecret, accessToken, startDate, endDate);

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OuterTradeOrderGetResponse response = 
          beibeiApiService.beibeiOuterTradeOrderGet(
              appKey, appSecret, accessToken, 
              null, OrderTimeRange.PAY_TIME.getValue(), 
              startDate, endDate, 
              i, (long)pageSize); 
      
      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, BeibeiCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.PAY_TIME + "]|beibei.outer.trade.order.get 订单列表",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      for (OrdersGetDto orderGetDto : response.getData()) {
        Long oid = orderGetDto.getOid();
        OuterTradeOrderDetailGetResponse orderDetailGetResponse = 
              beibeiApiService.beibeiOuterTradeOrderDetailGet(
            appKey, appSecret, accessToken, oid.toString());
        
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", BeibeiCommonTool.STORE_TYPE,
            "beibei.outer.trade.order.detail.get 订单详情",
            JsonUtil.formatByMilliSecond(orderDetailGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            null);
        
        List<AomsordT> list =
            new AomsordTTranslator(orderDetailGetResponse).doTranslate(storeId);
        
        aomsordTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
    }

    return totalSize;
    
  }

  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    return getPaiedOrdersCount(appKey, appSecret, accessToken, startDate, endDate);
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  private Long getPaiedOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    OuterTradeOrderGetResponse response = 
        beibeiApiService.beibeiOuterTradeOrderGet(appKey, appSecret, accessToken, 
          null, OrderTimeRange.PAY_TIME.getValue(), 
          startDate, endDate, BeibeiCommonTool.MIN_PAGE_NO, BeibeiCommonTool.MIN_PAGE_SIZE);
    if (response == null
        || (response.getSuccess() == Boolean.FALSE)) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "BeibeiApiSyncOrdersServiceImpl#getPaiedOrdersCount", 
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), JsonUtil.format(response), response.getMessage(), "", "");
      return BeibeiCommonTool.NO_COUNT_RETURNED;
    }
    Long count = response.getCount();
    
    return count;
  }
}
