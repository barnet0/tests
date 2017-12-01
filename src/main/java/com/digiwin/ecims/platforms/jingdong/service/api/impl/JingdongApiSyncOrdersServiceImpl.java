package com.digiwin.ecims.platforms.jingdong.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncOrdersService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsordTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class JingdongApiSyncOrdersServiceImpl implements JingdongApiSyncOrdersService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private JingdongApiService jingdongApiService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) throws JdException, IOException {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
   
    Map<String,String> jdmap=new HashMap<String,String>();
    jdmap.put("appKey", appKey);
    jdmap.put("accessToken", accessToken);
    
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
    }

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OrderSearchResponse response =
          jingdongApiService.jingdongOrderSearch(
              appKey, appSecret, accessToken, 
              sDate, eDate, JingdongCommonTool.ORDER_STATUS, 
              (int)i, pageSize, 
              JingdongCommonTool.ORDER_FIELDS, JingdongCommonTool.ORDER_SEARCH_DESCEND_SORT, 
              JingdongCommonTool.UPDATE_TIME); 

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, JingdongCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|360buy.order.search 订单检索",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (OrderSearchInfo orderSearchInfo : response.getOrderInfoResult().getOrderInfoList()) {
    	  
        List<AomsordT> list =
            new AomsordTTranslator(orderSearchInfo).doTranslate(aomsshopT.getAomsshop001(),jdmap);
        aomsordTs.addAll(list);

        // 比較區間資料時間，取最大時間
        if (DateTimeTool.parse(orderSearchInfo.getModified()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(orderSearchInfo.getModified());
        }
      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
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
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws JdException, IOException {
    int pageSize = JingdongCommonTool.DEFAULT_PAGE_SIZE;
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    Map<String,String> jdmap=new HashMap<String,String>();
    jdmap.put("appKey", appKey);
    jdmap.put("accessToken", accessToken);
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = getIncrementalOrdersCount(appKey, appSecret, accessToken, startDate, endDate);

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OrderSearchResponse response =
          jingdongApiService.jingdongOrderSearch(
              appKey, appSecret, accessToken, 
              startDate, endDate, JingdongCommonTool.ORDER_STATUS, 
              (int)i, pageSize, 
              JingdongCommonTool.ORDER_FIELDS, JingdongCommonTool.ORDER_SEARCH_DESCEND_SORT, 
              JingdongCommonTool.UPDATE_TIME); 

      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, 
          JingdongCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|360buy.order.search 订单检索",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      for (OrderSearchInfo orderSearchInfo : response.getOrderInfoResult().getOrderInfoList()) {
    	  
        List<AomsordT> list =
            new AomsordTTranslator(orderSearchInfo).doTranslate(storeId,jdmap);
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
      String startDate, String endDate) 
          throws JdException, IOException {
    return getJingdongOrderCount(appKey, appSecret, accessToken, 
        startDate, endDate, JingdongCommonTool.ORDER_STATUS, JingdongCommonTool.UPDATE_TIME);
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) throws JdException, IOException {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    Map<String,String> jdmap=new HashMap<String,String>();
    jdmap.put("appKey", appKey);
    jdmap.put("accessToken", accessToken);
    // 取得时间区间内总资料笔数
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
      OrderSearchResponse response =
          jingdongApiService.jingdongOrderSearch(
              appKey, appSecret, accessToken, 
              sDate, eDate, JingdongCommonTool.ORDER_STATUS, 
              (int)i, pageSize, 
              JingdongCommonTool.ORDER_FIELDS, JingdongCommonTool.ORDER_SEARCH_DESCEND_SORT, 
              JingdongCommonTool.CREATE_TIME); 

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, JingdongCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|360buy.order.search 订单检索",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (OrderSearchInfo orderSearchInfo : response.getOrderInfoResult().getOrderInfoList()) {
        List<AomsordT> list =
            new AomsordTTranslator(orderSearchInfo).doTranslate(aomsshopT.getAomsshop001(),jdmap);
        aomsordTs.addAll(list);

      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
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
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws JdException, IOException {
    int pageSize = JingdongCommonTool.DEFAULT_PAGE_SIZE;
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    Map<String,String> jdmap=new HashMap<String,String>();
    jdmap.put("appKey", appKey);
    jdmap.put("accessToken", accessToken);
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = getCreatedOrdersCount(appKey, appSecret, accessToken, startDate, endDate);

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OrderSearchResponse response =
          jingdongApiService.jingdongOrderSearch(
              appKey, appSecret, accessToken, 
              startDate, endDate, JingdongCommonTool.ORDER_STATUS, 
              (int)i, pageSize, 
              JingdongCommonTool.ORDER_FIELDS, JingdongCommonTool.ORDER_SEARCH_DESCEND_SORT, 
              JingdongCommonTool.CREATE_TIME); 

      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, 
          JingdongCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|360buy.order.search 订单检索",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          null);

      for (OrderSearchInfo orderSearchInfo : response.getOrderInfoResult().getOrderInfoList()) {
        List<AomsordT> list =
            new AomsordTTranslator(orderSearchInfo).doTranslate(storeId,jdmap);
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
      String startDate, String endDate) 
          throws JdException, IOException {
    return getJingdongOrderCount(appKey, appSecret, accessToken, 
        startDate, endDate, JingdongCommonTool.ORDER_STATUS, JingdongCommonTool.CREATE_TIME);
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId)
      throws JdException, IOException {
    // 參數設定
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    Map<String,String> jdmap=new HashMap<String,String>();
    jdmap.put("appKey", appKey);
    jdmap.put("accessToken", accessToken);
    OrderGetResponse response = jingdongApiService.jingdongOrderGet(
        appKey, appSecret, accessToken, 
        orderId, JingdongCommonTool.ORDER_FIELDS, null);

    loginfoOperateService.newTransaction4SaveSource(
        "N/A", "N/A", 
        JingdongCommonTool.STORE_TYPE,
        "360buy.order.get 获取单个订单",
        JsonUtil.format(response),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    List<AomsordT> aomsordTs = new AomsordTTranslator(response.getOrderDetailInfo().getOrderInfo())
        .doTranslate(storeId,jdmap);
    taskService.newTransaction4Save(aomsordTs);
    
    return true;
  }

  private Long getJingdongOrderCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate, String status, String dateType) 
          throws JdException, IOException {
    OrderSearchResponse orderSearchResponse = null;
    long totalSize = 0L;
    orderSearchResponse =
        jingdongApiService.jingdongOrderSearch(
            appKey, appSecret, accessToken, 
            startDate, endDate, status, 
            JingdongCommonTool.MIN_PAGE_NO, JingdongCommonTool.MIN_PAGE_SIZE, 
            null, JingdongCommonTool.ORDER_SEARCH_DESCEND_SORT, dateType); 

    if (orderSearchResponse == null
        || !orderSearchResponse.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE)) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "JingdongApiSyncOrdersServiceImpl#getJingdongOrderCount", 
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), "获取数据异常", orderSearchResponse.getZhDesc(), "", "");
      return null;
    } else {
      totalSize = orderSearchResponse.getOrderInfoResult().getOrderTotal();
    }
    
    return totalSize;
  }
  
  public static void main(String[] args) {
	
}
  
}
