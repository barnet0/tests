package com.digiwin.ecims.platforms.aliexpress.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.OrderItemVO;
import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderByIdRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderListQueryResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.sync.SyncDataResult;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiHelperService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiSyncOrdersService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool.OrderStatusEnum;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressDateTimeTool;
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
public class AliexpressApiSyncOrdersServiceImpl implements AliexpressApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(AliexpressApiSyncOrdersServiceImpl.class);

  @Autowired
  private AliexpressApiService aliexpressApiService;

  @Autowired
  private AliexpressApiHelperService aliexpressApiHelperServie;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
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
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Long getIncrementalOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = AliexpressDateTimeTool
        .turnDateStringToAeReqDateString(taskScheduleConfig.getLastUpdateTime());
    String eDate =
        AliexpressDateTimeTool.turnDateStringToAeReqDateString(taskScheduleConfig.getEndDate());
    int pageSize = taskScheduleConfig.getMaxPageSize();
    String scheduleType = taskScheduleConfig.getScheduleType();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    FindOrderListQueryRequest listRequest = new FindOrderListQueryRequest();
    listRequest.setPage(AliexpressCommonTool.MIN_PAGE_NO);
    listRequest.setPageSize(AliexpressCommonTool.DEFAULT_PAGE_SIZE);

    // 已完成的订单需要单独查询，所以这里循环两种情形查询
    for (OrderStatusEnum orderStatus : AliexpressCommonTool.OrderStatusEnum.values()) {
      listRequest.setOrderStatus(orderStatus.getOrderStatus());

      FindOrderListQueryResponse listResponse = null;
      int totalSize = 0;
      boolean sizeMoreThanSetting = false;
      do {
        listRequest.setCreateDateStart(sDate);
        listRequest.setCreateDateEnd(eDate);

        listResponse =
            aliexpressApiService.ApiFindOrderListQuery(listRequest, appKey, appSecret, accessToken);
        if (listResponse == null) {
          return false;
        } else {
          totalSize = listResponse.getTotalItem();
        }

        // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
        if (taskScheduleConfig.isReCycle()) {
          // process empty, 主要是為好閱讀
        } else if (totalSize == 0) {
          // 區間內沒有資料
          Date aeEdate = AliexpressDateTimeTool.parseToAeRequestDate(eDate);
          if (aeEdate.before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
            // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
            taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(),
                DateTimeTool.format(aeEdate));
            return true;
          }
        }

        sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
        if (sizeMoreThanSetting) {
          // eDate變為sDate與eDate的中間時間
          eDate = AliexpressDateTimeTool.getRequestMiddleDateString(sDate, eDate);
        }
      } while (sizeMoreThanSetting);

      // 計算頁數
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

      // for (int i = pageNum; i > 0; i--) {
      // List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      //
      // listRequest.setPage(i);
      // listResponse =
      // aliexpressApiService.ApiFindOrderListQuery(listRequest, appKey, appSecret, accessToken);
      // loginfoOperateService.newTransaction4SaveSource(
      // AliexpressDateTimeTool.turnAeReqDateStringToDateString(sDate),
      // AliexpressDateTimeTool.turnAeReqDateStringToDateString(eDate),
      // AliexpressCommonTool.STORE_TYPE,
      // "[" + OrderUseTimeEnum.CREATE_TIME + "]|api.findOrderListQuery -- version: 1 交易订单列表查询",
      // JsonUtil.objectToJsonString(listResponse),
      // SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
      // taskScheduleConfig.getScheduleType());
      //
      // FindOrderByIdRequest detailRequest = new FindOrderByIdRequest();
      // FindOrderByIdResponse detailResponse = null;
      // for (OrderItemVO orderItemVO : listResponse.getOrderList()) {
      // Long orderId = orderItemVO.getOrderId();
      //
      // detailRequest.setOrderId(orderId);
      // detailResponse =
      // aliexpressApiService.ApiFindOrderById(detailRequest, appKey, appSecret, accessToken);
      // loginfoOperateService.newTransaction4SaveSource(
      // AliexpressDateTimeTool.turnAeReqDateStringToDateString(sDate),
      // AliexpressDateTimeTool.turnAeReqDateStringToDateString(eDate),
      // AliexpressCommonTool.STORE_TYPE, "api.findOrderById -- version: 1 交易订单详情查询",
      // JsonUtil.objectToJsonString(detailResponse),
      // SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
      // taskScheduleConfig.getScheduleType());
      //
      // List<AomsordT> list = new AomsordTTranslator(detailResponse).doTranslate(storeId);
      //
      // aomsordTs.addAll(list);
      //
      // if (AliexpressDateTimeTool.after(orderItemVO.getGmtCreate(), lastUpdateTime)) {
      // lastUpdateTime = AliexpressDateTimeTool.parseToAeResponseDate(orderItemVO.getGmtCreate());
      // }
      // }
      // taskService.newTransaction4Save(aomsordTs);
      // } // end-for one page

      SyncDataResult syncDataResult =
          syncOrderDataLogic(appKey, appSecret, accessToken, listRequest, listResponse,
              taskScheduleConfig.getScheduleType(), storeId, lastUpdateTime, sDate, eDate, pageNum);
      // TODO 应该判断所有状态的最新创建时间，而不是一个状态更新一次lastUpdateTime。
      // TODO 这样会导致应该提到循环外面 mark by mowj 20160718 出现订单状态未成功更新到DB
      /*
      还有速卖通的订单   76426976359149  平台状态是等待卖家发货   不过在ord表里是订购成功（PLACE_ORDER_SUCCESS）
      */
      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀

      } else {
        taskService.updateLastUpdateTime(scheduleType,
            AliexpressDateTimeTool.turnAeReqDateStringToDateString(sDate),
            DateTimeTool.format(syncDataResult.getSyncLastUpdateTime()));
      }
    }

    // TODO 是否应该拿出来单独做排程。类似京东的服务单更新
    aliexpressApiHelperServie.updateOrderStatus(taskScheduleConfig, aomsshopT);
    
    return true;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);

    int pageSize = AliexpressCommonTool.DEFAULT_PAGE_SIZE;

    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    FindOrderListQueryRequest listRequest = new FindOrderListQueryRequest();
    listRequest.setPage(AliexpressCommonTool.MIN_PAGE_NO);
    listRequest.setPageSize(pageSize);

    int totalSize = 0;

    // 已完成的订单需要单独查询，所以这里循环两种情形查询
    for (OrderStatusEnum orderStatus : AliexpressCommonTool.OrderStatusEnum.values()) {
      if (orderStatus.getOrderStatus().length() > 0) {
        listRequest.setOrderStatus(orderStatus.getOrderStatus());
      }

      FindOrderListQueryResponse listResponse = null;
      int dataSize = 0;
      listRequest.setCreateDateStart(startDate);
      listRequest.setCreateDateEnd(endDate);

      listResponse =
          aliexpressApiService.ApiFindOrderListQuery(listRequest, appKey, appSecret, accessToken);
      if (listResponse == null) {
        totalSize = 0;
      } else {
        dataSize = listResponse.getTotalItem();
        totalSize += dataSize;
      }

      // 計算頁數
      int pageNum = (dataSize / pageSize) + (dataSize % pageSize == 0 ? 0 : 1);

      syncOrderDataLogic(appKey, appSecret, accessToken, listRequest, listResponse, "", storeId,
          DateTimeTool.parse(startDate), startDate, endDate, pageNum);
    }

    return (long) totalSize;
  }

  @Override
  public Long getCreatedOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  private SyncDataResult syncOrderDataLogic(final String appKey, final String appSecret,
      final String accessToken, final FindOrderListQueryRequest listRequest,
      FindOrderListQueryResponse listResponse, final String scheduleType, final String storeId,
      final Date lastUpdateTime, final String sDate, final String eDate, final int pageNum)
          throws Exception {

    long dataCount = 0L;
    Date lastUpdateTimeInner = lastUpdateTime;

    for (int i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

      listRequest.setPage(i);
      listResponse =
          aliexpressApiService.ApiFindOrderListQuery(listRequest, appKey, appSecret, accessToken);
      loginfoOperateService.newTransaction4SaveSource(
          AliexpressDateTimeTool.turnAeReqDateStringToDateString(sDate),
          AliexpressDateTimeTool.turnAeReqDateStringToDateString(eDate),
          AliexpressCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|" + "[" + listRequest.getOrderStatus() + "]|"
              + "api.findOrderListQuery -- version: 1 交易订单列表查询",
          JsonUtil.format(listResponse), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, scheduleType);

      dataCount += listResponse.getTotalItem();

      FindOrderByIdRequest detailRequest = new FindOrderByIdRequest();
      FindOrderByIdResponse detailResponse = null;
      for (OrderItemVO orderItemVO : listResponse.getOrderList()) {
        Long orderId = orderItemVO.getOrderId();

        detailRequest.setOrderId(orderId);
        detailResponse =
            aliexpressApiService.ApiFindOrderById(detailRequest, appKey, appSecret, accessToken);
        loginfoOperateService.newTransaction4SaveSource(
            AliexpressDateTimeTool.turnAeReqDateStringToDateString(sDate),
            AliexpressDateTimeTool.turnAeReqDateStringToDateString(eDate),
            AliexpressCommonTool.STORE_TYPE, "api.findOrderById -- version: 1 交易订单详情查询",
            JsonUtil.format(detailResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId, scheduleType);

        List<AomsordT> list = new AomsordTTranslator(detailResponse).doTranslate(storeId);

        aomsordTs.addAll(list);

        if (AliexpressDateTimeTool.after(orderItemVO.getGmtCreate(), lastUpdateTimeInner)) {
          lastUpdateTimeInner =
              AliexpressDateTimeTool.parseToAeResponseDate(orderItemVO.getGmtCreate());
        }
      }
      taskService.newTransaction4Save(aomsordTs);
    } // end-for one page

    return new SyncDataResult(dataCount, lastUpdateTimeInner);

  }
  
}
