package com.digiwin.ecims.platforms.aliexpress.service.manual.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderListQueryResponse;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiSyncOrdersService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool.OrderStatusEnum;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service("syncAliexpressOrderDataByHandServiceImpl")
public class SyncAliexpressOrderDataByHandServiceImpl implements SyncOrderDataByHandService {

  private static final Logger logger =
      LoggerFactory.getLogger(SyncAliexpressOrderDataByHandServiceImpl.class);

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private AliexpressApiService aliexpressApiService;

  @Autowired
  private AliexpressApiSyncOrdersService aliexpressApiSyncOrdersService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Override
  public String syncOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    try {
      return syncOrderData(storeId, YhdCommonTool.OrderDateType.CREATE_TIME.getDateType(), startDate,
          endDate);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "SyncAliexpressOrderDataByHandServiceImpl",
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), DateTimeTool.parse(endDate),
          "json数据转换异常", "Exception ：" + e.getMessage(), "AomsordT", "");
      return JsonUtil.format(
          new SyncResOrderHandBean(false, "0", "failed", "程序执行异常!" + e.getMessage()));
    }

  }

  @Override
  public String syncOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public String syncOrderDataByOrderId(String storeId, String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String syncOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderCountFromEcByCreateDate(String scheduleUpdateCheckType, String storeId,
      String startDate, String endDate) {
    return findOrderCountFromEc(UseTimeEnum.CREATE_TIME, scheduleUpdateCheckType, storeId,
        startDate, endDate);
  }

  @Override
  public Long findOrderCountFromEcByCreateDate(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderCountFromEcByModifyDate(String scheduleUpdateCheckType, String storeId,
      String startDate, String endDate) {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public Long findOrderCountFromEcByModifyDate(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) {
    throw new UnsupportedOperationException("_034");
  }

  private String syncOrderData(String storeId, int dateType, String startDate, String endDate)
      throws Exception {
    long totalSize = aliexpressApiSyncOrdersService.syncOrdersByCreated(startDate, endDate, storeId, null);
    return JsonUtil.format(new SyncResOrderHandBean(true, totalSize + ""));
  }

  private Long findOrderCountFromEc(UseTimeEnum createTime, String scheduleUpdateCheckType,
      String storeId, String startDate, String endDate) {
    // 取得授權
    AomsshopT aomsshopT =
        aomsShopService.getStoreByStoreId(storeId);

    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    FindOrderListQueryRequest listRequest = new FindOrderListQueryRequest();
    listRequest.setPage(AliexpressCommonTool.MIN_PAGE_NO);
    listRequest.setPageSize(AliexpressCommonTool.DEFAULT_PAGE_SIZE);

    long ecCount = 0L;

    // 已完成的订单需要单独查询，所以这里循环两种情形查询
    for (OrderStatusEnum orderStatus : AliexpressCommonTool.OrderStatusEnum.values()) {
      listRequest.setOrderStatus(orderStatus.getOrderStatus());

      FindOrderListQueryResponse listResponse = null;
      int totalSize = 0;

      listRequest.setCreateDateStart(startDate);
      listRequest.setCreateDateEnd(endDate);

      try {
        listResponse =
            aliexpressApiService.ApiFindOrderListQuery(listRequest, appKey, appSecret, accessToken);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (listResponse != null) {
        totalSize = listResponse.getTotalItem();
        ecCount += totalSize;
      }

    }

    return ecCount;
  }
}
