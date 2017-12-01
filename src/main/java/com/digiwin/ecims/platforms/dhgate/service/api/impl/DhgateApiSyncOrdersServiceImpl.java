package com.digiwin.ecims.platforms.dhgate.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhgate.open.client.CompositeResponse;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemSku;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderBaseInfo;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderProduct;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemSkuListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderListGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderProductGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemSkuListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderListGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderProductGetResponse;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiService;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiSyncOrdersService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool.OrderUseTime;
import com.digiwin.ecims.platforms.dhgate.util.translator.AomsordTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class DhgateApiSyncOrdersServiceImpl implements DhgateApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(DhgateApiSyncOrdersServiceImpl.class);

  @Autowired
  private DhgateApiService dhgateApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return syncOrderByOrderUseTime(taskScheduleConfig, aomsshopT, OrderUseTime.PAY_TIME);
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getIncrementalOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // TODO Auto-generated method stub
    syncOrderByOrderUseTime(taskScheduleConfig, aomsshopT, OrderUseTime.CREATE_TIME);
    return null;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
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
  
  private Boolean syncOrderByOrderUseTime(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      OrderUseTime orderUseTime) throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    // String appKey = aomsshopT.getAomsshop004();
    // String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    OrderListGetRequest orderListGetRequest = new OrderListGetRequest();
    orderListGetRequest.setPageNo(DhgateCommonTool.MIN_PAGE_NO);
    orderListGetRequest.setPageSize(DhgateCommonTool.DEFAULT_PAGE_SIZE);
    orderListGetRequest.setQuerytimeType(orderUseTime.getTimeType());

    CompositeResponse<OrderListGetResponse> listResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;

    do {
      orderListGetRequest.setStartDate(sDate);
      orderListGetRequest.setEndDate(eDate);
      listResponse = dhgateApiService.dhOrderListGet(orderListGetRequest, accessToken);
      if (listResponse == null || !listResponse.isSuccessful()
          || listResponse.getSuccessResponse().getStatus() == null) {
        return false;
      } else {
        totalSize = listResponse.getSuccessResponse().getCount();
      }

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
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    OrderGetRequest orderGetRequest = new OrderGetRequest();
    OrderProductGetRequest orderProductGetRequest = new OrderProductGetRequest();

    // TODO 需要验证是否是最新的资料在第一页
    for (int i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

      orderListGetRequest.setPageNo(i);

      listResponse = dhgateApiService.dhOrderListGet(orderListGetRequest, accessToken);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DhgateCommonTool.STORE_TYPE,
          "[" + orderUseTime + "]|dh.order.list.get$2.0  获取seller订单列表",
          JsonUtil.format(listResponse), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, taskScheduleConfig.getScheduleType());

      List<OrderBaseInfo> baseOrders = listResponse.getSuccessResponse().getOrderBaseInfoList();

      for (OrderBaseInfo orderBaseInfo : baseOrders) {
        String orderNo = orderBaseInfo.getOrderNo();

        orderGetRequest.setOrderNo(orderNo);

        CompositeResponse<OrderGetResponse> orderGetResponse =
            dhgateApiService.dhOrderGet(orderGetRequest, accessToken);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", DhgateCommonTool.STORE_TYPE,
            "dh.order.get$2.0  订单详情接口", JsonUtil.format(orderGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        orderProductGetRequest.setOrderNo(orderNo);
        CompositeResponse<OrderProductGetResponse> orderProductGetResponse =
            dhgateApiService.dhOrderProductGet(orderProductGetRequest, accessToken);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", DhgateCommonTool.STORE_TYPE,
            "dh.order.product.get$2.0 订单产品信息接口",
            JsonUtil.format(orderProductGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        List<OrderProduct> orderProducts =
            orderProductGetResponse.getSuccessResponse().getOrderProductList();

        Map<String, Long> outerSkuToSkuMap = new HashMap<String, Long>();
        // 根据订单中商品的itemId与outerId，通过dh.item.sku.list接口获取商品在平台的SKUID
        // 这么做的原因是因为dh.order.product.get无法获取商品在平台的SKUID，而OMS这边会拿这个值关联铺货资料表
        for (OrderProduct orderProduct : orderProductGetResponse.getSuccessResponse()
            .getOrderProductList()) {
          // 平台商品编号
          String itemCode = orderProduct.getItemcode();
          // 平台商品SKU编号
          Long itemSkuId = 0l;
          // 商家外部SKU编号（料号）
          String outerSkuId = orderProduct.getSkuCode();

          ItemSkuListRequest itemSkuListRequest = new ItemSkuListRequest();
          itemSkuListRequest.setItemCode(itemCode);
          CompositeResponse<ItemSkuListResponse> itemSkuListResponse =
              dhgateApiService.dhItemSkuList(itemSkuListRequest, accessToken);
          if (itemSkuListResponse != null
              && itemSkuListResponse.getSuccessResponse().getStatus() != null) {
            for (ItemSku itemSku : itemSkuListResponse.getSuccessResponse().getItemSkuList()) {
              if (itemSku.getItemSkuInvenList().get(0).getSkuCode().equals(outerSkuId)) {
                itemSkuId = itemSku.getSkuId();
                break;
              }
            }
            outerSkuToSkuMap.put(outerSkuId, itemSkuId);
          }
        }
        List<AomsordT> list = new AomsordTTranslator(orderGetResponse.getSuccessResponse(),
            orderProducts, outerSkuToSkuMap).doTranslate(aomsshopT.getAomsshop001());

        aomsordTs.addAll(list);

        // 按创建时间的订单同步才需要比较lastUpdateTime
        if (orderUseTime == OrderUseTime.CREATE_TIME) {
          if (DateTimeTool.parse(orderGetResponse.getSuccessResponse().getStartedDate())
              .after(lastUpdateTime)) {
            lastUpdateTime =
                DateTimeTool.parse(orderGetResponse.getSuccessResponse().getStartedDate());
          }
        }
      } // end for one order

      taskService.newTransaction4Save(aomsordTs);
    } // end for one page

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    // 另外，针对敦煌网，如果是支付时间的同步，也不更新lastUpdateTime
    if (taskScheduleConfig.isReCycle() || orderUseTime == OrderUseTime.PAY_TIME) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

}
