package com.digiwin.ecims.platforms.baidu.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.QueryTimeType;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.Order;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.OrderDetail;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder.FindOrderResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiService;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiSyncOrdersService;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.platforms.baidu.util.translator.AomsordTTranslator;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class BaiduApiSyncOrdersServiceImpl implements BaiduApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(BaiduApiSyncOrdersServiceImpl.class);

  @Autowired
  private BaiduApiService baiduApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

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
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取得区间内资料总笔数
    Response<FindOrderResponse> findOrderResponse = null;
    long totalSize = 0;
    boolean sizeMoreThanSetting = false;

    do {
      totalSize = getIncrementalOrdersCount(sDate, eDate, appKey, appSecret, accessToken);

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀
      } else if (totalSize == 0) {
        // 區間內沒有資料
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return true;
        }
      }

      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);

    // 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);


    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

      findOrderResponse = baiduApiService.baiduMallOrdersFind(aomsshopT,
          BaiduCommonTool.getOrderStatus(), QueryTimeType.UPDATE, sDate, eDate, (int)i, pageSize);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BaiduCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|baidumall.orders.findOrder 获取订单列表",
          JsonUtil.format(findOrderResponse),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      List<Order> orders = findOrderResponse.getBody().getData().get(0).getOrderList();

      for (Order order : orders) {
        // 取得订单详情
        Response<GetDetailResponse> detailResponse =
            baiduApiService.baiduMallOrderDetailGet(aomsshopT, order.getOrderNo());
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", BaiduCommonTool.STORE_TYPE,
            "baidumall.order.detail.get 获取订单详情", JsonUtil.format(detailResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        OrderDetail orderDetail = detailResponse.getBody().getData().get(0).getOrderDetail();
        List<AomsordT> list =
            new AomsordTTranslator(orderDetail).doTranslate(storeId);

        aomsordTs.addAll(list);

        if (DateTimeTool.parse(orderDetail.getUpdateTime()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(orderDetail.getUpdateTime());
        }
      } // end for one order

      taskService.newTransaction4Save(aomsordTs);
    } // end for one page

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    return syncOrders(startDate, endDate, storeId, scheduleType, QueryTimeType.UPDATE, UseTimeEnum.UPDATE_TIME);
  }

  @Override
  public Long getIncrementalOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    return getOrdersCount(startDate, endDate, appKey, appSecret, accessToken, QueryTimeType.UPDATE);
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    return syncOrders(startDate, endDate, storeId, scheduleType, QueryTimeType.CREATE, UseTimeEnum.CREATE_TIME);
  }

  @Override
  public Long getCreatedOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    return getOrdersCount(startDate, endDate, appKey, appSecret, accessToken, QueryTimeType.CREATE);
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
    // 參數設定
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    // 取得订单详情
    Response<GetDetailResponse> detailResponse =
        baiduApiService.baiduMallOrderDetailGet(
            appKey, appSecret, accessToken,
            Long.parseLong(orderId));
    loginfoOperateService.newTransaction4SaveSource(
        "N/A", "N/A", 
        BaiduCommonTool.STORE_TYPE,
        "baidumall.order.detail.get 获取订单详情", JsonUtil.format(detailResponse),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    OrderDetail orderDetail = detailResponse.getBody().getData().get(0).getOrderDetail();
    List<AomsordT> list =
        new AomsordTTranslator(orderDetail).doTranslate(storeId);

    taskService.newTransaction4Save(list);

    return true;
  }

  private Long syncOrders(String startDate, String endDate, String storeId, String scheduleType, 
      QueryTimeType queryTimeType, UseTimeEnum useTimeEnum) throws Exception {
    int pageSize = BaiduCommonTool.DEFAULT_PAGE_SIZE;
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    // 取得区间内资料总笔数
    Response<FindOrderResponse> findOrderResponse = null;
    long totalSize = 0;

    totalSize = getOrdersCount(startDate, endDate, appKey, appSecret, accessToken, queryTimeType);

    // 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

      findOrderResponse = baiduApiService.baiduMallOrdersFind(
          appKey, appSecret, accessToken,
          BaiduCommonTool.getOrderStatus(), queryTimeType, startDate, endDate, 
          (int)i, pageSize);
      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, 
          BaiduCommonTool.STORE_TYPE,
          "[" + useTimeEnum + "]|baidumall.orders.findOrder 获取订单列表",
          JsonUtil.format(findOrderResponse),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
          scheduleType);

      List<Order> orders = findOrderResponse.getBody().getData().get(0).getOrderList();

      for (Order order : orders) {
        // 取得订单详情
        Response<GetDetailResponse> detailResponse =
            baiduApiService.baiduMallOrderDetailGet(
                appKey, appSecret, accessToken,
                order.getOrderNo());
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", BaiduCommonTool.STORE_TYPE,
            "baidumall.order.detail.get 获取订单详情", JsonUtil.format(detailResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            scheduleType);
        OrderDetail orderDetail = detailResponse.getBody().getData().get(0).getOrderDetail();
        List<AomsordT> list =
            new AomsordTTranslator(orderDetail).doTranslate(storeId);

        aomsordTs.addAll(list);

      } // end for one order

      taskService.newTransaction4Save(aomsordTs);
    } // end for one page

    return totalSize;
  }
  
  private Long getOrdersCount(String startDate, String endDate, String appKey, String appSecret, String accessToken,
      QueryTimeType queryTimeType) throws Exception {
    Response<FindOrderResponse> findOrderResponse = null;
    int totalSize = 0;

    findOrderResponse =
        baiduApiService.baiduMallOrdersFind(appKey, appSecret, accessToken, 
            BaiduCommonTool.getOrderStatus(),
            queryTimeType, 
            startDate, endDate, 
            BaiduCommonTool.MIN_PAGE_NO, BaiduCommonTool.MIN_PAGE_SIZE);

    if (findOrderResponse == null
        || findOrderResponse.getHeader().getStatus() != BaiduCommonTool.RESPONSE_SUCCESS_CODE) {
      return BaiduCommonTool.NO_COUNT_RETURNED;
    } else {
      totalSize = findOrderResponse.getBody().getData().get(0).getTotalCount();
    }
      
    return (long)totalSize;
  }
}
