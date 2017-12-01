package com.digiwin.ecims.platforms.ccb.service.api;

import com.digiwin.ecims.platforms.ccb.bean.request.delivery.send.DeliverySendRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get.ItemDetailGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update.ItemInventoryUpdateRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.item.list.get.ItemListGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.order.list.get.OrderListGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.response.delivery.send.DeliverySendResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.item.detail.get.ItemDetailGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.item.inventory.update.ItemInventoryUpdateResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.item.list.get.ItemListGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.order.detail.get.OrderDetailGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.order.list.get.OrderListGetResponse;
import com.digiwin.ecims.core.api.EcImsApiService;

public interface CcbApiService extends EcImsApiService {

  public OrderDetailGetResponse CcbOrderDetailGet(OrderDetailGetRequest request, String custId)
      throws Exception;

  public OrderListGetResponse CcbOrderListGet(OrderListGetRequest request, String custId) throws Exception;

  public ItemDetailGetResponse CcbItemDetailGet(ItemDetailGetRequest request, String custId)
      throws Exception;

  public ItemListGetResponse CcbItemListGet(ItemListGetRequest request, String custId) throws Exception;

  public DeliverySendResponse CcbDeliverySend(DeliverySendRequest request, String custId) throws Exception;

  public ItemInventoryUpdateResponse CcbItemInventoryUpdate(ItemInventoryUpdateRequest request,
      String custId) throws Exception;

}
