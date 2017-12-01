package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhd.object.order.Order;
import com.yhd.object.order.OrderInfo;
import com.yhd.response.order.OrderDetailGetResponse;
import com.yhd.response.order.OrdersDetailGetResponse;
import com.yhd.response.order.OrdersGetResponse;

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
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncOrdersService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.OrderDateType;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsordTTranslator;

@Service
public class YhdApiSyncOrdersServiceImpl implements YhdApiSyncOrdersService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private YhdApiService yhdApiService;

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
    return syncOrdersData(taskScheduleConfig, aomsshopT, 
        OrderDateType.UPDATE_TIME.getDateType());
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    return syncOrdersData(startDate, endDate, storeId, 
        YhdCommonTool.ORDER_STATUS, OrderDateType.UPDATE_TIME.getDateType());
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    return getYhdOrderCount(appKey, appSecret, accessToken, 
        startDate, endDate, YhdCommonTool.ORDER_STATUS, OrderDateType.UPDATE_TIME.getDateType());
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return syncOrdersData(taskScheduleConfig, aomsshopT, 
        OrderDateType.CREATE_TIME.getDateType());
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    return syncOrdersData(startDate, endDate, storeId, 
        YhdCommonTool.ORDER_STATUS, OrderDateType.CREATE_TIME.getDateType());
  }

  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws Exception {
    return getYhdOrderCount(appKey, appSecret, accessToken, 
        startDate, endDate, YhdCommonTool.ORDER_STATUS, OrderDateType.CREATE_TIME.getDateType());
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
 // 參數設定
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    OrderDetailGetResponse orderDetailGetResponse = 
        yhdApiService.yhdOrderDetailGet(
            appKey, appSecret, accessToken, 
            orderId);

    loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
        "yhd.order.detail.get 获取订单详情", JsonUtil.format(orderDetailGetResponse),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    List<AomsordT> aomsordTs = 
        new YhdAomsordTTranslator(orderDetailGetResponse.getOrderInfo(), storeId).doTranslate();
    
    taskService.newTransaction4Save(aomsordTs);
    
    return true;
  }

  private Boolean syncOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      Integer dateType) throws Exception {
    // 參數設定
    Date lastUpdateDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // add by mowj 20151224 start
    OrdersGetResponse responseTmp = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    // 取得時間區間內總資料筆數
    do {
      responseTmp = yhdApiService.yhdOrdersGet(
          appKey, appSecret, accessToken, YhdCommonTool.ORDER_STATUS, dateType, 
          sDate, eDate, 
          ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue());
      // add by mowj 20151204
      if (responseTmp.getErrorCount() > 0 || responseTmp.getErrInfoList() != null) {
        // mark by mowj 20160728
//        ErrDetailInfo errorDetailInfo = responseTmp.getErrInfoList().getErrDetailInfo().get(0);
//        String errorCode = errorDetailInfo.getErrorCode();
//        String errorDescription = errorDetailInfo.getErrorDes();
//        throw new GenericBusinessException(errorCode, errorDescription, errorDescription);
      } else {
        totalSize = responseTmp.getTotalCount();
      }

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程. add by xavier on
      // 20150829
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
    // add by mowj 20151224 end

    // // 取得時間區間內總資料筆數
    // int totalSize = digiwinOrdersGet(aomsshopT, dateType, sDate, eDate, 1, 1).getTotalCount();
    //
    // //如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程. add by xavier on
    // 20150829
    // if (taskScheduleConfig.isReCycle()) {
    // // process empty, 主要是為好閱讀
    //
    // } else if (totalSize == 0) {
    // // 區間內沒有資料
    // if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
    // // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
    // taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
    // return;
    // }
    // }
    //
    // while (totalSize > taskScheduleConfig.getMaxReadRow()) {
    // // eDate變為sDate與eDate的中間時間
    // eDate = DateTimeTool.format(new Date((DateTimeTool.parse(sDate).getTime() +
    // DateTimeTool.parse(eDate).getTime()) / 2));
    // // System.out.println("eDate" + eDate);
    // totalSize = digiwinOrdersGet(aomsshopT, dateType, sDate, eDate, 1, 1).getTotalCount();
    // }


    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      // 取得订单列表
      OrdersGetResponse response = yhdApiService.yhdOrdersGet(
          appKey, appSecret, accessToken, YhdCommonTool.ORDER_STATUS, dateType, 
          sDate, eDate, i, pageSize);

      // add by mowj 20150818
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, YhdCommonTool.STORE_TYPE,
          "[" + (dateType == OrderDateType.CREATE_TIME.getDateType() ? "CREATE_TIME"
              : "UPDATE_TIME") + "]|yhd.orders.get 获取订单列表",
          JsonUtil.format(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, taskScheduleConfig.getScheduleType());

      List<String> orderCodeList = generateOrderCodeList(response);
      for (String orderCodes : orderCodeList) {
        // 取得批量訂單詳情
        OrdersDetailGetResponse ordersDetail = yhdApiService.yhdOrdersDetailGet(
            appKey, appSecret, accessToken, orderCodes);

        // add by mowj 20150818
        // modi by mowj 20150825 增加biztype
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.orders.detail.get 批量获取订单详情", JsonUtil.format(ordersDetail),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        // 針對每一筆訂單詳情新增
        for (OrderInfo orderInfo : ordersDetail.getOrderInfoList().getOrderInfo()) {
          List<AomsordT> list =
              new YhdAomsordTTranslator(orderInfo, storeId).doTranslate();
          aomsordTs.addAll(list);

          // mark by mowj 20150807 start
          // for (AomsordT aomsordT : list) {
          // if (DateTimeTool.parse(aomsordT.getModified()).after(lastUpdateDate)) {
          // lastUpdateDate = DateTimeTool.parse(aomsordT.getModified());
          // }
          // }
          // mark by mowj 20150807 end

          // add by mowj 20150807
          if (DateTimeTool.parse(orderInfo.getOrderDetail().getUpdateTime())
              .after(lastUpdateDate)) {
            lastUpdateDate = DateTimeTool.parse(orderInfo.getOrderDetail().getUpdateTime());
          }
        }
      }
      taskService.newTransaction4Save(aomsordTs);
    }
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程. add by xavier on
    // 20150829
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      logger.info("更新{}的lastUpdateDate为{}...", taskScheduleConfig.getScheduleType(), lastUpdateDate);
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateDate));
    }

    return true;
  }
  
  private Long syncOrdersData(String startDate, String endDate, String storeId, 
      String orderStatus, Integer dateType) throws Exception {
    int pageSize = ApiPageParam.DEFAULT_PAGE_SIZE.getValue();

    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    // 取得時間區間內總資料筆數
    OrdersGetResponse responseTmp = null;
    int totalSize = 0;
    responseTmp = yhdApiService.yhdOrdersGet(
        appKey, appSecret, accessToken, orderStatus, dateType, 
        startDate, endDate, 
        ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue());
    // add by mowj 20151204
    if (responseTmp.getErrorCount() > 0 || responseTmp.getErrInfoList() != null) {
      // mark by mowj 20160728
//      ErrDetailInfo errorDetailInfo = responseTmp.getErrInfoList().getErrDetailInfo().get(0);
//      String errorCode = errorDetailInfo.getErrorCode();
//      String errorDescription = errorDetailInfo.getErrorDes();
//      throw new GenericBusinessException(errorCode, errorDescription, errorDescription);
    } else {
      totalSize = responseTmp.getTotalCount();
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      // 取得订单列表
      OrdersGetResponse response = yhdApiService.yhdOrdersGet(
          appKey, appSecret, accessToken, YhdCommonTool.ORDER_STATUS, dateType, 
          startDate, endDate, i, pageSize);

      // add by mowj 20150818
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, YhdCommonTool.STORE_TYPE,
          "[" + (dateType == OrderDateType.CREATE_TIME.getDateType() ? "CREATE_TIME"
              : "UPDATE_TIME") + "]|yhd.orders.get 获取订单列表",
          JsonUtil.format(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, null);

      List<String> orderCodeList = generateOrderCodeList(response);
      for (String orderCodes : orderCodeList) {
        // 取得批量訂單詳情
        OrdersDetailGetResponse ordersDetail = yhdApiService.yhdOrdersDetailGet(
            appKey, appSecret, accessToken, orderCodes);

        // add by mowj 20150818
        // modi by mowj 20150825 增加biztype
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.orders.detail.get 批量获取订单详情", JsonUtil.format(ordersDetail),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            null);

        // 針對每一筆訂單詳情新增
        for (OrderInfo orderInfo : ordersDetail.getOrderInfoList().getOrderInfo()) {
          List<AomsordT> list =
              new YhdAomsordTTranslator(orderInfo, storeId).doTranslate();
          aomsordTs.addAll(list);

        }
      }
      taskService.newTransaction4Save(aomsordTs);
    }

    return (long)totalSize;
  }
  
  /**
   * 將訂單列表中的所有orderCode拼成一個字串
   * 
   * @param ordersGetResponse
   * @return
   */
  private List<String> generateOrderCodeList(OrdersGetResponse ordersGetResponse) {
    List<String> orderCodeList = new ArrayList<String>();
    int ordersSize = ordersGetResponse.getOrderList().getOrder().size();
    // String str = null;
    // for (int i = 0; i < ordersSize; i++) {
    // if (i == 0 || i == 50) {
    // if (str != null)
    // list.add(str);
    // str = new String();
    // }
    // Order order = ordersGetResponse.getOrderList().getOrder().get(i);
    // str += order.getOrderCode();
    // if (i < ordersSize - 1) {
    // str += ",";
    // }
    // }

    String str = "";
    for (int i = 1; i <= ordersSize; i++) {
      Order order = ordersGetResponse.getOrderList().getOrder().get(i - 1);
      str += order.getOrderCode() + YhdCommonTool.LIST_DELIMITER;
      if (i % YhdCommonTool.MAX_ORDER_CODE_LENGTH == 0) {
        orderCodeList.add(str.substring(0, str.length() - 1));
        str = "";
      }
    }
    // 如果无法被除尽，则最后一组（肯定小于length）不会在上方的循环中被添加进list，所以在这里单独添加
    if (ordersSize % YhdCommonTool.MAX_ORDER_CODE_LENGTH != 0) {
      orderCodeList.add(str.substring(0, str.length() - 1));
    }

    return orderCodeList;
  }
  
  private Long getYhdOrderCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate, String status, Integer dateType) throws Exception {
    OrdersGetResponse responseTmp = null;
    Long totalSize = 0L;
    responseTmp = yhdApiService.yhdOrdersGet(
        appKey, appSecret, accessToken, 
        status, dateType, 
        startDate, endDate, 
        ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue());
    if (responseTmp.getErrorCount() > 0 || responseTmp.getErrInfoList() != null) {
      // mark by mowj 20160728
//      ErrDetailInfo errorDetailInfo = responseTmp.getErrInfoList().getErrDetailInfo().get(0);
//      String errorCode = errorDetailInfo.getErrorCode();
//      String errorDescription = errorDetailInfo.getErrorDes();
//      throw new GenericBusinessException(errorCode, errorDescription, errorDescription);
    } else {
      totalSize = responseTmp.getTotalCount().longValue();
    }
    
    return totalSize;
  }
}
