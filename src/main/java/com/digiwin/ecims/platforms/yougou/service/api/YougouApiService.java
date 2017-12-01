package com.digiwin.ecims.platforms.yougou.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.yougou.bean.request.inventory.InventoryQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.inventory.InventoryUpdateRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderCompletedUpdateRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderIncrementQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.refund.ReturnQualityQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryQueryResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryUpdateResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderCompletedUpdateResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderIncrementQueryResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderQueryResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.refund.ReturnQualityQueryResponse;

public interface YougouApiService extends EcImsApiService {

  public OrderGetResponse yougouOrderGet(OrderGetRequest request, String appKey, String appSecret)
      throws Exception;

  public OrderQueryResponse yougouOrderQuery(OrderQueryRequest request, String appKey, String appSecret)
      throws Exception;

  public OrderIncrementQueryResponse yougouOrderIncrementQuery(OrderIncrementQueryRequest request,
      String appKey, String appSecret) throws Exception;

  public ReturnQualityQueryResponse yougouReturnQualityQuery(ReturnQualityQueryRequest request,
      String appKey, String appSecret) throws Exception;

  public OrderCompletedUpdateResponse yougouOrderCompletedUpdate(OrderCompletedUpdateRequest request,
      String appKey, String appSecret) throws Exception;

  public InventoryUpdateResponse yougouInventoryUpdate(InventoryUpdateRequest request, String appKey,
      String appSecret) throws Exception;

  /**
   * 查询商家库存（顺带查询商品信息）
   * @param request
   * @param appKey
   * @param appSecret
   * @return
   * @throws Exception
   */
  public InventoryQueryResponse yougouInventoryQuery(InventoryQueryRequest request, String appKey,
      String appSecret) throws Exception;

}
