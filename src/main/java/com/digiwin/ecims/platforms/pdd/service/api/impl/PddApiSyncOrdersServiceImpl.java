package com.digiwin.ecims.platforms.pdd.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.digiwin.ecims.platforms.pdd.bean.domain.order.OrderSearchResult;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiService;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncOrdersService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool.OrderStatus;
import com.digiwin.ecims.platforms.pdd.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class PddApiSyncOrdersServiceImpl implements PddApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(PddApiSyncOrdersServiceImpl.class);
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private PddApiService pddApiService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByCreated(taskScheduleConfig, aomsshopT);
  
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
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
    for (long i = pageNum - 1; i >= 0; i--) {
      OrderSearchResponse response = pddApiService.pddOrderSearch(
          appKey, appSecret, accessToken, 
          OrderStatus.PAIED.getValue(), 
          sDate, eDate, 
          (int)i, pageSize); 

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, PddCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|mOrderSearch 订单列表查询接口",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (OrderSearchResult orderSearchResult : response.getOrderList()) {
        String orderSN = orderSearchResult.getOrderSN();
        OrderGetResponse orderGetResponse = pddApiService.pddOrderGet(
            appKey, appSecret, accessToken, orderSN);
        
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, PddCommonTool.STORE_TYPE,
            "[" + UseTimeEnum.CREATE_TIME + "]|mGetOrder 订单详细信息接口",
            JsonUtil.formatByMilliSecond(orderGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        
        List<AomsordT> list =
            new AomsordTTranslator(orderGetResponse).doTranslate(storeId);
        
        if (DateTimeTool.parse(orderGetResponse.getCreatedTime()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(orderGetResponse.getCreatedTime());
        }
        
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
      throws Exception {
    // 参数设定
    int pageSize = PddCommonTool.DEFAULT_PAGE_SIZE;
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
    for (long i = pageNum - 1; i >= 0; i--) {
      OrderSearchResponse response = pddApiService.pddOrderSearch(
          appKey, appSecret, accessToken, 
          OrderStatus.PAIED.getValue(), 
          startDate, endDate, 
          (int)i, pageSize); 

      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, PddCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|mOrderSearch 订单列表查询接口",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          scheduleType);

      // 防止在第一次统计订单总数后，翻页过程中订单数量有变化，导致翻页超过实际数量
      if (response.getOrderCount() == 0) {
        break;
      }
      
      for (OrderSearchResult orderSearchResult : response.getOrderList()) {
        String orderSN = orderSearchResult.getOrderSN();
        OrderGetResponse orderGetResponse = pddApiService.pddOrderGet(
            appKey, appSecret, accessToken, orderSN);
        
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", PddCommonTool.STORE_TYPE,
            "[" + UseTimeEnum.CREATE_TIME + "]|mGetOrder 订单详细信息接口",
            JsonUtil.formatByMilliSecond(orderGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            scheduleType);
        
        List<AomsordT> list =
            new AomsordTTranslator(orderGetResponse).doTranslate(storeId);
        
        aomsordTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsordTs);
      // 清空列表，为下一页资料作准备
      aomsordTs.clear();
    }

    return totalSize;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.core.api.EcImsApiSyncOrdersService#getCreatedOrdersCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   * 拼多多目前统计时间段内订单数量，只能一页一页去请求，然后做加法来统计。
   * 另外，在第一次统计完后，订单数量可能会改变。
   * 所以在后面翻页请求时，需要添加对数量为0的判断，如果为0，表示页数大于实际数量，就应该退出循环。
   */
  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    
    OrderSearchResponse orderSearchResponse = null;
    int i = PddCommonTool.MIN_PAGE_NO;
    long totalSize = 0L;
    long size = 0L;
    do {
      orderSearchResponse =
          pddApiService.pddOrderSearch(
              appKey, appSecret, accessToken, 
              OrderStatus.PAIED.getValue(), 
              startDate, endDate, i, PddCommonTool.DEFAULT_PAGE_SIZE);
      
      if (orderSearchResponse == null
          || (orderSearchResponse.getResult() == PddCommonTool.RESPONSE_FAILURE_CODE)) {
        loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
            "PddApiSyncOrdersServiceImpl#getCreatedOrdersCount", 
            LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
            new Date(), "获取数据异常", orderSearchResponse.getCause(), "", "");
        return 0L;
      }
      size = (long)orderSearchResponse.getOrderCount();
      totalSize += size;
      i++;
      
    } while (size > 0);
    

    return totalSize;
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
    // 參數設定
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    OrderGetResponse response = pddApiService.pddOrderGet(
        appKey, appSecret, accessToken, orderId);

    loginfoOperateService.newTransaction4SaveSource(
        "N/A", "N/A", 
        PddCommonTool.STORE_TYPE,
        "mGetOrder 订单详细信息接口",
        JsonUtil.format(response),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    List<AomsordT> aomsordTs = new AomsordTTranslator(response).doTranslate(storeId);
    
    taskService.newTransaction4Save(aomsordTs);
    
    return true;
  }

}
