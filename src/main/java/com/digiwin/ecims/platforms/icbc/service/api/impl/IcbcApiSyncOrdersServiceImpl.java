package com.digiwin.ecims.platforms.icbc.service.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.icbc.bean.base.Order;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail.IcbcOrderDetailResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist.IcbcOrderListResponse;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsordTTranslator;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiSyncOrdersService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class IcbcApiSyncOrdersServiceImpl implements IcbcApiSyncOrdersService {

  @Autowired
  private IcbcApiService icbcApiService;
  
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
//  參數設定
    Date modiDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    IcbcOrderListResponse listResponse = 
        icbcApiService.icbcb2cOrderList(
            appKey, appSecret, accessToken, null, null, sDate, eDate, null);

    // add by mowj 20150824
    // modi by mowj 20150825 增加biztype
    loginfoOperateService.newTransaction4SaveSource(sDate, eDate, IcbcCommonTool.STORE_TYPE,
        "[" + UseTimeEnum.UPDATE_TIME + "]|icbcb2c.order.list 订单列表查询接口", listResponse,
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), 
        storeId, taskScheduleConfig.getScheduleType());
    
    // 若區間內沒有資料，將結束時間設為下一次的起始時間，結束
    if (listResponse == null || listResponse.getBody() == null
        || listResponse.getBody().getOrderList() == null) {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
      return false;
    }

    int totalSize = listResponse.getBody().getOrderList().getOrderList().size();// 資料總筆數
    int maxSizePerPage = taskScheduleConfig.getMaxPageSize(); // 每一頁最大資料量
    int totalPage = (totalSize / maxSizePerPage) + (totalSize % maxSizePerPage == 0 ? 0 : 1);// 總頁數
    int finalPageIndex = totalPage - 1; // 最後一頁的索引值

    // 以頁為單位做訂單的同步(因為工商批量取訂單的API並無分頁觀念，無需考慮漏單問題，因此不用倒序同步)
    for (int i = 0; i < totalPage; i++) {
      // 取出所有訂單編號，組成訂單編號字串. ex:123,124,...,134(字串長度不可超過512)
      String orderIds = "";
      int startIndex = i * maxSizePerPage;// 起始索引值
      int length =
          (i == finalPageIndex) ? (totalSize - maxSizePerPage * finalPageIndex) : maxSizePerPage;// 長度
      for (int j = startIndex; j < startIndex + length; j++) {
        orderIds += listResponse.getBody().getOrderList().getOrderList().get(j).getOrderId();
        if (j < (startIndex + length - 1)) {
          orderIds += ",";
        }
      }

      // 取得訂單詳情
      IcbcOrderDetailResponse icbcOrderDetailResponse =
          icbcApiService.icbcb2cOrderDetail(appKey, appSecret, accessToken, orderIds);

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(
          "N/A", "N/A", IcbcCommonTool.STORE_TYPE,
          "icbcb2c.order.detail 订单详情查询", icbcOrderDetailResponse, 
          SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, taskScheduleConfig.getScheduleType());

      
      // 計算訂單中最晚更新時間
      List<Order> orders = icbcOrderDetailResponse.getBody().getOrderList().getOrderList();
      for (int j = 0; j < orders.size(); j++) {
        Date orderModiDate = DateTimeTool.parse(orders.get(j).getOrderModifyTime());
        if (orderModiDate.after(modiDate)) {
          modiDate = orderModiDate;
        }
      }

      // 將訂單詳情轉換成中台POJO
      List<AomsordT> aomsordTs =
          new IcbcAomsordTTranslator(icbcOrderDetailResponse, storeId)
              .doTranslate();

      // 存檔
      taskService.newTransaction4Save(aomsordTs);
    }

    // 將最晚的訂單更新時間當作下一次的起始時間
    logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), modiDate);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(modiDate));
    
    return true;
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    IcbcOrderListResponse listResponse = 
        icbcApiService.icbcb2cOrderList(
            appKey, appSecret, accessToken, null, null, startDate, endDate, null);

    // add by mowj 20150824
    // modi by mowj 20150825 增加biztype
    loginfoOperateService.newTransaction4SaveSource(startDate, endDate, IcbcCommonTool.STORE_TYPE,
        "[" + UseTimeEnum.UPDATE_TIME + "]|icbcb2c.order.list 订单列表查询接口", listResponse,
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), 
        storeId, null);
    
    int totalSize = listResponse.getBody().getOrderList().getOrderList().size();// 資料總筆數
    int maxSizePerPage = IcbcCommonTool.MAX_PAGE_SIZE; // 每一頁最大資料量
    int totalPage = (totalSize / maxSizePerPage) + (totalSize % maxSizePerPage == 0 ? 0 : 1);// 總頁數
    int finalPageIndex = totalPage - 1; // 最後一頁的索引值

    // 以頁為單位做訂單的同步(因為工商批量取訂單的API並無分頁觀念，無需考慮漏單問題，因此不用倒序同步)
    for (int i = 0; i < totalPage; i++) {
      // 取出所有訂單編號，組成訂單編號字串. ex:123,124,...,134(字串長度不可超過512)
      String orderIds = "";
      int startIndex = i * maxSizePerPage;// 起始索引值
      int length =
          (i == finalPageIndex) ? (totalSize - maxSizePerPage * finalPageIndex) : maxSizePerPage;// 長度
      for (int j = startIndex; j < startIndex + length; j++) {
        orderIds += listResponse.getBody().getOrderList().getOrderList().get(j).getOrderId();
        if (j < (startIndex + length - 1)) {
          orderIds += ",";
        }
      }

      // 取得訂單詳情
      IcbcOrderDetailResponse icbcOrderDetailResponse =
          icbcApiService.icbcb2cOrderDetail(appKey, appSecret, accessToken, orderIds);

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(
          "N/A", "N/A", IcbcCommonTool.STORE_TYPE,
          "icbcb2c.order.detail 订单详情查询", icbcOrderDetailResponse, 
          SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, null);
      
      // 將訂單詳情轉換成中台POJO
      List<AomsordT> aomsordTs =
          new IcbcAomsordTTranslator(icbcOrderDetailResponse, storeId)
              .doTranslate();

      // 存檔
      taskService.newTransaction4Save(aomsordTs);
    }

    return (long)totalSize;
  }

  @Override
  public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {
    
    IcbcOrderListResponse listResponse = 
        icbcApiService.icbcb2cOrderList(
            appKey, appSecret, accessToken, null, null, startDate, endDate, null);
    
    return (long)listResponse.getBody().getOrderList().getOrderList().size();
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
//  參數設定
    Date modiDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    IcbcOrderListResponse listResponse = 
        icbcApiService.icbcb2cOrderList(
            appKey, appSecret, accessToken, sDate, eDate, null, null, null);

    // add by mowj 20150824
    // modi by mowj 20150825 增加biztype
    loginfoOperateService.newTransaction4SaveSource(sDate, eDate, IcbcCommonTool.STORE_TYPE,
        "[" + UseTimeEnum.CREATE_TIME + "]|icbcb2c.order.list 订单列表查询接口", listResponse,
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), 
        storeId, taskScheduleConfig.getScheduleType());
    
    // 若區間內沒有資料，將結束時間設為下一次的起始時間，結束
    if (listResponse == null || listResponse.getBody() == null
        || listResponse.getBody().getOrderList() == null) {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
      return false;
    }

    int totalSize = listResponse.getBody().getOrderList().getOrderList().size();// 資料總筆數
    int maxSizePerPage = taskScheduleConfig.getMaxPageSize(); // 每一頁最大資料量
    int totalPage = (totalSize / maxSizePerPage) + (totalSize % maxSizePerPage == 0 ? 0 : 1);// 總頁數
    int finalPageIndex = totalPage - 1; // 最後一頁的索引值

    // 以頁為單位做訂單的同步(因為工商批量取訂單的API並無分頁觀念，無需考慮漏單問題，因此不用倒序同步)
    for (int i = 0; i < totalPage; i++) {
      // 取出所有訂單編號，組成訂單編號字串. ex:123,124,...,134(字串長度不可超過512)
      String orderIds = "";
      int startIndex = i * maxSizePerPage;// 起始索引值
      int length =
          (i == finalPageIndex) ? (totalSize - maxSizePerPage * finalPageIndex) : maxSizePerPage;// 長度
      for (int j = startIndex; j < startIndex + length; j++) {
        orderIds += listResponse.getBody().getOrderList().getOrderList().get(j).getOrderId();
        if (j < (startIndex + length - 1)) {
          orderIds += ",";
        }
      }

      // 取得訂單詳情
      IcbcOrderDetailResponse icbcOrderDetailResponse =
          icbcApiService.icbcb2cOrderDetail(appKey, appSecret, accessToken, orderIds);

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(
          "N/A", "N/A", IcbcCommonTool.STORE_TYPE,
          "icbcb2c.order.detail 订单详情查询", icbcOrderDetailResponse, 
          SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, taskScheduleConfig.getScheduleType());

      
      // 計算訂單中最晚更新時間
      List<Order> orders = icbcOrderDetailResponse.getBody().getOrderList().getOrderList();
      for (int j = 0; j < orders.size(); j++) {
        Date orderModiDate = DateTimeTool.parse(orders.get(j).getOrderModifyTime());
        if (orderModiDate.after(modiDate)) {
          modiDate = orderModiDate;
        }
      }

      // 將訂單詳情轉換成中台POJO
      List<AomsordT> aomsordTs =
          new IcbcAomsordTTranslator(icbcOrderDetailResponse, storeId)
              .doTranslate();

      // 存檔
      taskService.newTransaction4Save(aomsordTs);
    }

    // 將最晚的訂單更新時間當作下一次的起始時間
    logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), modiDate);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(modiDate));
    
    return true;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    
    IcbcOrderListResponse listResponse = 
        icbcApiService.icbcb2cOrderList(
            appKey, appSecret, accessToken, startDate, endDate, null, null, null);

    // add by mowj 20150824
    // modi by mowj 20150825 增加biztype
    loginfoOperateService.newTransaction4SaveSource(startDate, endDate, IcbcCommonTool.STORE_TYPE,
        "[" + UseTimeEnum.CREATE_TIME + "]|icbcb2c.order.list 订单列表查询接口", listResponse,
        SourceLogBizTypeEnum.AOMSORDT.getValueString(), 
        storeId, null);
    
    int totalSize = listResponse.getBody().getOrderList().getOrderList().size();// 資料總筆數
    int maxSizePerPage = IcbcCommonTool.MAX_PAGE_SIZE; // 每一頁最大資料量
    int totalPage = (totalSize / maxSizePerPage) + (totalSize % maxSizePerPage == 0 ? 0 : 1);// 總頁數
    int finalPageIndex = totalPage - 1; // 最後一頁的索引值

    // 以頁為單位做訂單的同步(因為工商批量取訂單的API並無分頁觀念，無需考慮漏單問題，因此不用倒序同步)
    for (int i = 0; i < totalPage; i++) {
      // 取出所有訂單編號，組成訂單編號字串. ex:123,124,...,134(字串長度不可超過512)
      String orderIds = "";
      int startIndex = i * maxSizePerPage;// 起始索引值
      int length =
          (i == finalPageIndex) ? (totalSize - maxSizePerPage * finalPageIndex) : maxSizePerPage;// 長度
      for (int j = startIndex; j < startIndex + length; j++) {
        orderIds += listResponse.getBody().getOrderList().getOrderList().get(j).getOrderId();
        if (j < (startIndex + length - 1)) {
          orderIds += ",";
        }
      }

      // 取得訂單詳情
      IcbcOrderDetailResponse icbcOrderDetailResponse =
          icbcApiService.icbcb2cOrderDetail(appKey, appSecret, accessToken, orderIds);

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(
          "N/A", "N/A", IcbcCommonTool.STORE_TYPE,
          "icbcb2c.order.detail 订单详情查询", icbcOrderDetailResponse, 
          SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, null);

      
      // 將訂單詳情轉換成中台POJO
      List<AomsordT> aomsordTs =
          new IcbcAomsordTTranslator(icbcOrderDetailResponse, storeId)
              .doTranslate();

      // 存檔
      taskService.newTransaction4Save(aomsordTs);
    }

    return (long)totalSize;
  }

  @Override
  public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken,
      String startDate, String endDate) throws Exception {

    IcbcOrderListResponse listResponse = 
        icbcApiService.icbcb2cOrderList(
            appKey, appSecret, accessToken, startDate, endDate, null, null, null);
    
    return (long)listResponse.getBody().getOrderList().getOrderList().size();
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
 // 取得授權
    AomsshopT aomsshopT = aomsShopService.getStoreByStoreId(storeId);

    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    IcbcOrderDetailResponse response =
        icbcApiService.icbcb2cOrderDetail(appKey, appSecret, accessToken, orderId);

    // 若無此訂單，結束
    if (response == null || response.getBody() == null
        || response.getBody().getOrderList() == null) {
      return false;
    }

    // 將訂單詳情轉換成中台POJO
    List<AomsordT> aomsordTs =
        new IcbcAomsordTTranslator(response, aomsshopT.getAomsshop001()).doTranslate();

    // 存檔
    taskService.newTransaction4Save(aomsordTs);

    return true;
  }

}
