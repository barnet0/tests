package com.digiwin.ecims.platforms.suning.service.api.impl;

import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.exception.SyncResponseErrorException;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.suning.bean.AlreadyProcessStore;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiHelperService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiSyncOrdersService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.*;
import com.digiwin.ecims.platforms.suning.util.SuningTranslatorTool;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.suning.api.*;
import com.suning.api.entity.transaction.OrdQueryRequest;
import com.suning.api.entity.transaction.OrderGetResponse;
import com.suning.api.entity.transaction.OrderQueryRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SuningApiSyncOrdersServiceImpl implements SuningApiSyncOrdersService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private SuningApiService suningApiService;

  @Autowired
  private SuningApiHelperService suningApiHelperService;
  
  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersData(taskScheduleConfig, aomsshopT, 
        OrderUseTime.UPDATE_TIME, OrderOperateType.IS_SCHEDULE);
    return true;
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    return (long) syncOrdersData(startDate, endDate, storeId, 
        OrderUseTime.UPDATE_TIME, 
        scheduleType == null ? OrderOperateType.IS_MANUALLY : OrderOperateType.IS_CHECK_SCHEDULE, scheduleType);
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    return findOrderCountFromEc(OrderUseTime.UPDATE_TIME, appKey, appSecret, startDate, endDate);
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersData(taskScheduleConfig, aomsshopT, OrderUseTime.CREATE_TIME, OrderOperateType.IS_CHECK_SCHEDULE);
    return true;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    return (long) syncOrdersData(startDate, endDate, storeId, 
        OrderUseTime.CREATE_TIME, 
        scheduleType == null ? OrderOperateType.IS_MANUALLY : OrderOperateType.IS_CHECK_SCHEDULE, scheduleType);
  }

  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    return findOrderCountFromEc(OrderUseTime.CREATE_TIME, appKey, appSecret, startDate, endDate);
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
 // 參數設定
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    OrderGetResponse response = suningApiService.suningCustomOrderGet(
        appKey, appSecret, accessToken, orderId);
    
    loginfoOperateService.newTransaction4SaveSource(
        "N/A", "N/A", 
        SuningCommonTool.STORE_TYPE,
        "suning.custom.order.get 单笔订单查询 ",
        JsonUtil.format(response),
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
        null);
    
    List<AomsordT> aomsordTs = SuningTranslatorTool.getInstance()
        .transOrderGetToAomsordTBean(storeId, response, StringTool.EMPTY);
    
    taskService.newTransaction4Save(aomsordTs);
    
    return true;
  }

  private int syncOrdersData(String startDate, String endDate, String storeId, 
      OrderUseTime orderUseTime, OrderOperateType orderOperateType, String scheduleType) 
          throws SyncResponseErrorException, Exception {
    // 基本資料
    String inScheduleType = SuningCommonTool.API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX 
        + "#" + storeId + "#" + SuningCommonTool.STORE_TYPE;
    TaskScheduleConfig oldTaskScheduleConfig = 
        taskService.getTaskScheduleConfigInfo(inScheduleType);

    /*
     * 做一份新的 taskSchedule, 因不明 原因, taskScheduleConfig 查出來後, 只要有修改, 
     * 再經過任何一個Table 的存 檔動作, 新的值就會被回寫到DB（沒有對 taskScheduleConfig存檔）
     */
    TaskScheduleConfig newTaskScheduleConfig = new TaskScheduleConfig();
    BeanUtils.copyProperties(newTaskScheduleConfig, oldTaskScheduleConfig);
    
    //設定拉取時間
    newTaskScheduleConfig.setLastUpdateTime(startDate);
    newTaskScheduleConfig.setEndDate(DateTimeTool.parse(endDate));
    
    //執行拉取
    int returnTotalRows = syncOrdersData(
            newTaskScheduleConfig, aomsShopService.getStoreByStoreId(storeId), 
            orderUseTime, orderOperateType);
    
    return returnTotalRows;
  }
  
  private int syncOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      OrderUseTime outType, OrderOperateType operateType)
      throws SyncResponseErrorException, Exception {
 // 取得 api 認証的 key.
    final String storeId = aomsshopT.getAomsshop001();
    final String appKey = aomsshopT.getAomsshop004();
    final String appSecret = aomsshopT.getAomsshop005();

    // 基本資料
    Date modiDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    final int pageSize = taskScheduleConfig.getMaxPageSize();
    final int maxReadRows =
        taskScheduleConfig.getMaxReadRow() == null ? 2000 : taskScheduleConfig.getMaxReadRow(); // 每次可存檔的筆數
 // 目前處理筆數
    final AlreadyProcessStore alreadyProcessStore =
        new AlreadyProcessStore(maxReadRows, 0, operateType); 

    /*
     * 計算時間 因為會直接傳入, 要查詢的日期區間, 且要配合每個 api 的特性去調整.
     * 
     * taskScheduleConfig.getLastUpdateTime(); ==> start time （last time）
     * taskScheduleConfig.getEndDate(); ==> end time (current time)
     */
    Date startDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    final Date endDate = DateTimeTool.parse(taskScheduleConfig.getEndDate());
//  計算startDate, endDate 相差幾天
    final double calDiffRealDays = DateTimeTool.getDiffPrecisionDay(startDate, endDate); 
    final int calDiffDays = (int) Math.ceil(calDiffRealDays); // 表示兩個時間相差, 不到一天, 但以一天計算.

    // 計算 startDate 是否超過可查區間
    startDate =
        suningApiHelperService.calIsOutOfRange(taskScheduleConfig, startDate, calDiffDays, SyncBasicParm.ORDER);

//  計算可執行次數
//  最大可取區間範圍, 也就是資料不能取超過這個天數範圍
    final int maxProcessDays = SyncBasicParm.ORDER.getMaxProcessDays(); 
//  判斷最大可取天數
    int diffDays = calDiffDays > maxProcessDays ? maxProcessDays : calDiffDays; 
//  一次取幾天的資料
    final int getDataDays = SyncBasicParm.ORDER.getGetDataDays();
    final int executeTimes = (int) Math.ceil(diffDays / (getDataDays + 0.0));
    // //存放執行失敗的 response;

    // 運算用物件（for 第一次使用）
    String sDate = DateTimeTool.format(startDate);
//  用來計算最後一天的時間
    final String originalEDate = DateTimeTool.format(endDate);
    String eDate = DateTimeTool.format(DateTimeTool.getAfterDays(startDate, getDataDays));

    // 決定要處理的訂單狀态 .
    String[] orderLineStatus = null;
    if (OrderUseTime.CREATE_TIME == outType) {
      // 订单状态 10待发货，20已发货，21部分发货，30交易成功 ，40交易关闭
      orderLineStatus = SuningCommonTool.ORDER_STATUS_CREATE_TIME;
    } else if (OrderUseTime.UPDATE_TIME == outType) {
      // 订单行项目状态 10待发货，20已发货，30交易成功
      orderLineStatus = SuningCommonTool.ORDER_STATUS_MODIFY_TIME;
    }

    DefaultSuningClient client = new DefaultSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey, appSecret, "json");

    int returnTotalRows = 0; // 記錄本次執行, 所回傳的筆數
    for (int runDay = 1; runDay <= executeTimes; runDay++) {

      for (String status : orderLineStatus) {
        // System.out.println(sDate + "_<" + status + ">_" + eDate);

        // step1. 先取得總頁數
        // 判斷是用創建時間去查資料, 還是用修改時間
        SelectSuningRequest request = null;
        SuningResponse response = null;
        String api = null;
        if (OrderUseTime.CREATE_TIME == outType) {
          api = "suning.custom.order.query 批量获取订单（三个月内的订单）";
          OrderQueryRequest req = new OrderQueryRequest();
          req.setOrderStatus(status);
          req.setStartTime(sDate);
          req.setEndTime(eDate);
          req.setPageNo(1);
          req.setPageSize(pageSize);
          request = req;
          response = client.excute(request);
        } else if (OrderUseTime.UPDATE_TIME == outType) {
          api = "suning.custom.ord.query 根据订单修改时间批量查询订单信息";
          OrdQueryRequest req = new OrdQueryRequest();
          req.setOrderLineStatus(status);
          req.setStartTime(sDate);
          req.setEndTime(eDate);
          req.setPageNo(1);
          req.setPageSize(pageSize);
          request = req;
          response = client.excute(request);
        }

        // 記錄回傳的 json 的原始格式.
        api = "[" + outType.toString() + "]|" + status + "|" + api;
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
            SuningCommonTool.STORE_TYPE,
            api, response.getBody(), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
            storeId, operateType == OrderOperateType.IS_MANUALLY ? 
                "Manually_Suning_syncOrderData" : taskScheduleConfig.getScheduleType());

        // 檢查是否有 error
        ResponseError re = SuningCommonTool.getInstance()
            .hasError(ApiInterface.DIGIWIN_ORDER_DETAIL_GET, response.getBody());

        if (re != null) {
//          continue;
          break; // 遇到错误直接停止
        } else {
          // step2. 依頁次, 處理回傳回來的資料
          // 倒序處理, 怕會漏筆數, 因為若有修改, 則被修改的資料, 會不在本次查詢區間.
          SuningPageBean pb = SuningCommonTool.getInstance().getMaxPage(response.getBody());

          // 計算本次執行, 所回傳的筆數
          returnTotalRows += pb.getReturnTotalRows();
          for (int i = pb.getPageTotal(); i > 0; i--) {

            // 檢查是不是己逹到最大處理筆數, 若己逹到, 則不做任何事, 留待下次再處理
            if (alreadyProcessStore.isFull()) {
              break;
            }

            request.setPageNo(i);
            request.setPageSize(pageSize);
            response = client.excute(request);

            // add by mowj 20151117
            loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
                SuningCommonTool.STORE_TYPE, api, response.getBody(),
                SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
                operateType == OrderOperateType.IS_MANUALLY ? // modi by mowj 20151116
                    "Manually_Suning_syncOrderData" : taskScheduleConfig.getScheduleType());

            re = SuningCommonTool.getInstance().hasError(ApiInterface.DIGIWIN_ORDER_DETAIL_GET,
                response.getBody()); // 檢查是否有 error
            if (re != null) {
              throw new SyncResponseErrorException(re, returnTotalRows);
            }
            
            List<AomsordT> data = new ArrayList<AomsordT>();
            
            if (OrderUseTime.CREATE_TIME == outType) {
              for (com.suning.api.entity.transaction.OrderQueryResponse.OrderQuery orderQuery 
                  : (((com.suning.api.entity.transaction.OrderQueryResponse)response))
                        .getSnbody().getOrderQuery()) {
                OrderGetResponse orderGetResponse = 
                    suningApiService.suningCustomOrderGet(
                        appKey, appSecret, null, orderQuery.getOrderCode());
                
                loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
                    SuningCommonTool.STORE_TYPE, api, orderGetResponse,
                    SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
                    operateType == OrderOperateType.IS_MANUALLY ?
                        "Manually_Suning_syncOrderData" : taskScheduleConfig.getScheduleType());
             // 轉換成 DB bean. 也計算最後更新時間.
                data.addAll(SuningTranslatorTool.getInstance()
                    .transOrderGetToAomsordTBean(storeId, orderGetResponse, StringTool.EMPTY));
              }
            } else if (OrderUseTime.UPDATE_TIME == outType) {
              for (com.suning.api.entity.transaction.OrdQueryResponse.OrderQuery orderQuery 
                  : ((com.suning.api.entity.transaction.OrdQueryResponse)response)
                      .getSnbody().getOrderQuery()) {
                OrderGetResponse orderGetResponse = 
                    suningApiService.suningCustomOrderGet(
                        appKey, appSecret, null, orderQuery.getOrderCode());
                
                loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
                    SuningCommonTool.STORE_TYPE, api, orderGetResponse,
                    SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
                    operateType == OrderOperateType.IS_MANUALLY ?
                        "Manually_Suning_syncOrderData" : taskScheduleConfig.getScheduleType());
                
                String orderLineStatusChangeTime = StringTool.EMPTY;
                if (!orderQuery.getOrderDetail().isEmpty()) {
                  orderLineStatusChangeTime = 
                      orderQuery.getOrderDetail().get(0).getOrderLineStatusChangeTime();
                }
                
             // 轉換成 DB bean. 也計算最後更新時間.
//                List<AomsordT> data = SuningTranslatorTool.getInstance()
//                    .transOrderGetToAomsordTBean(storeId, orderGetResponse);
                data.addAll(SuningTranslatorTool.getInstance()
                    .transOrderGetToAomsordTBean(storeId, orderGetResponse, orderLineStatusChangeTime));
              }
            }
            // 將資料存到 DB.
            taskService.newTransaction4Save(data); // 新的批次存檔方式
            alreadyProcessStore.addThisTimeProcessRows(data.size()); // 記錄目前所處理的筆數
            
//            // 存入收集器
//            OrderCollection resCollection = new OrderCollection();
//            resCollection.setResponse(response);
//
//            // 轉換成 DB bean. 也計算最後更新時間.
//            List<AomsordT> data = SuningTranslatorTool.getInstance()
//                .transOrdQueryToAomsordTBean(storeId, resCollection, modiDate);
//
//            // 將資料存到 DB.
//            taskService.newTransaction4Save(data);
////          記錄目前所處理的筆數
//            alreadyProcessStore.addThisTimeProcessRows(data.size());
          }
        }
      } // orderLineStatus for-loop
      // 第N次使用.
      sDate = eDate; // 原本的最後一天.
      int remainDays = diffDays - (runDay * getDataDays);
      eDate = suningApiHelperService.calEndDate(sDate, remainDays, getDataDays, originalEDate);
      if (eDate == null) {
        // 若算不出來, 則表示己超出範圍
        continue;
      }
    }

    // 不是手動的, 才能得新 lastUpdateTime, 不然會影響排程的運行
    if (operateType == OrderOperateType.IS_SCHEDULE
        || operateType == OrderOperateType.IS_CHECK_SCHEDULE) {
      // 若都沒有取到任何資料, 則 lastUpdateTime = endDate.
      suningApiHelperService.updateLastUpdateTime(modiDate, taskScheduleConfig);
    }

    return returnTotalRows;
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  private Long findOrderCountFromEc(OrderUseTime orderUseTime,
      String appKey, String appSecret, String startDate, String endDate) {
    // 取得本商店的的訂單筆數
    int getEcTotalRows = 0;
    // 取得 api 認証的 key.
    DefaultSuningClient client =
        new DefaultSuningClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
            appKey, appSecret, "json");

    SuningRequest request = null;
    SuningResponse response = null;

    OrderQueryRequest createTimeRequest = new OrderQueryRequest();
    OrdQueryRequest modifyTimeRequest = new OrdQueryRequest();

    String[] statusArray = orderUseTime == OrderUseTime.CREATE_TIME
        ? SuningCommonTool.ORDER_STATUS_CREATE_TIME : SuningCommonTool.ORDER_STATUS_MODIFY_TIME;
    for (String status : statusArray) {
      try {
        // 判斷是用創建時間去查資料, 還是用修改時間
        String api = null;
        if (OrderUseTime.CREATE_TIME == orderUseTime) {
          api = "suning.custom.order.query 批量获取订单（三个月内的订单）";
          createTimeRequest.setOrderStatus(status);
          createTimeRequest.setStartTime(startDate);
          createTimeRequest.setEndTime(endDate);
          createTimeRequest.setPageNo(1);
          createTimeRequest.setPageSize(1);
          request = createTimeRequest;
          response = client.excute(request);
        } else if (OrderUseTime.UPDATE_TIME == orderUseTime) {
          api = "suning.custom.ord.query 根据订单修改时间批量查询订单信息";
          modifyTimeRequest.setOrderLineStatus(status);
          modifyTimeRequest.setStartTime(startDate);
          modifyTimeRequest.setEndTime(endDate);
          modifyTimeRequest.setPageNo(1);
          modifyTimeRequest.setPageSize(1);
          request = modifyTimeRequest;
          response = client.excute(request);
        }
        // 記錄回傳的 json 的原始格式.
        api = "[" + orderUseTime + "]|" + status + "|" + api;
//        loginfoOperateService.newTransaction4SaveSource(startDate, endDate, "4", api,
//            response.getBody(), SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
//            null);

        // 檢查是否有 error
        ResponseError re = SuningCommonTool.getInstance()
            .hasError(ApiInterface.DIGIWIN_ORDER_DETAIL_GET, response.getBody());

        if (re != null) {
          continue;
        } else {
          SuningPageBean pb = SuningCommonTool.getInstance().getMaxPage(response.getBody());
          getEcTotalRows += pb.getReturnTotalRows();
        }
      } catch (Exception e) {
        // FIXME error 處理, 未完成
        e.printStackTrace();
      }
    }
    return new Long(getEcTotalRows);
  }  

}
