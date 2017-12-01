package com.digiwin.ecims.platforms.dhgate.service.api;

import com.dhgate.open.client.CompositeResponse;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemDownshelfListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemSkuListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemUpdateRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemUpshelfListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDeliverySaveRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDisputeCloseListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDisputeOpenListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderListGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderProductGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemDownshelfListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemSkuListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemUpdateResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemUpshelfListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDeliverySaveResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeCloseListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeOpenListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderListGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderProductGetResponse;

public interface DhgateApiService extends EcImsApiService {

  public CompositeResponse<OrderListGetResponse> dhOrderListGet(
      OrderListGetRequest orderListGetRequest, String accessToken) throws Exception;

  public CompositeResponse<OrderGetResponse> dhOrderGet(OrderGetRequest orderGetRequest,
      String accessToken) throws Exception;

  public CompositeResponse<OrderProductGetResponse> dhOrderProductGet(
      OrderProductGetRequest orderProductGetRequest, String accessToken) throws Exception;

  public CompositeResponse<OrderDisputeCloseListResponse> dhOrderDisputeCloseList(
      OrderDisputeCloseListRequest orderDisputeCloseListRequest, String accessToken)
          throws Exception;

  public CompositeResponse<OrderDisputeOpenListResponse> dhOrderDisputeOpenList(
      OrderDisputeOpenListRequest orderDisputeOpenListRequest, String accessToken) throws Exception;

  public CompositeResponse<OrderDeliverySaveResponse> dhOrderDeliverySave(
      OrderDeliverySaveRequest orderDeliverySaveRequest, String accessToken) throws Exception;

  public CompositeResponse<ItemListResponse> dhItemList(ItemListRequest itemListRequest,
      String accessToken) throws Exception;

  public CompositeResponse<ItemGetResponse> dhItemGet(ItemGetRequest itemGetRequest,
      String accessToken) throws Exception;

  public CompositeResponse<ItemUpdateResponse> dhItemUpdate(ItemUpdateRequest itemUpdateRequest,
      String accessToken) throws Exception;

  public CompositeResponse<ItemUpshelfListResponse> dhItemUpshelfList(
      ItemUpshelfListRequest itemUpshelfListRequest, String accessToken) throws Exception;

  public CompositeResponse<ItemDownshelfListResponse> dhItemDownshelfList(
      ItemDownshelfListRequest itemDownshelfListRequest, String accessToken) throws Exception;
  
  public CompositeResponse<ItemSkuListResponse> dhItemSkuList(
      ItemSkuListRequest itemSkuListRequest, String accessToken) throws Exception;
}
