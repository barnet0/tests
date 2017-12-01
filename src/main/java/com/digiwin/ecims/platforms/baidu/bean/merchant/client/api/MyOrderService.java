package com.digiwin.ecims.platforms.baidu.bean.merchant.client.api;

import com.fasterxml.jackson.core.type.TypeReference;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies.FindDeliveryCompaniesRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies.FindDeliveryCompaniesResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder.FindOrderRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder.FindOrderResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getinvoice.GetInvoiceRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getinvoice.GetInvoiceResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant.SendGoodByMerchantRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant.SendGoodByMerchantResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo.FindRefundOrderItemInfoRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo.FindRefundOrderItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno.GetRefundInfoByNoRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno.GetRefundInfoByNoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.core.util.JsonUtil;

public class MyOrderService extends MyAbstractApiService {

  private String url;

  public void setUrl(String url) {
    this.url = url;
  }

  public MyOrderService() {}

  public MyOrderService(String url) {
    this.url = url;
  }

  /**
   * 获取订单列表
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<FindOrderResponse> findOrder(Request<FindOrderRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<FindOrderResponse>>() {});
  }

  /**
   * 获取订单详情
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<GetDetailResponse> getDetail(Request<GetDetailRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<GetDetailResponse>>() {});
  }

  /**
   * 商家发货
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<SendGoodByMerchantResponse> sendGoodByMerchant(
      Request<SendGoodByMerchantRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<SendGoodByMerchantResponse>>() {});
  }

  /**
   * 获取卖家的退款单列表
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<FindRefundOrderItemInfoResponse> findRefundOrderItemInfo(
      Request<FindRefundOrderItemInfoRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<FindRefundOrderItemInfoResponse>>() {});
  }

  /**
   * 获取单个退款单的详情
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<GetRefundInfoByNoResponse> getRefundInfoByNo(
      Request<GetRefundInfoByNoRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<GetRefundInfoByNoResponse>>() {});
  }


  /**
   * 获取发票信息
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<GetInvoiceResponse> getInvoice(Request<GetInvoiceRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<GetInvoiceResponse>>() {});
  }


  /**
   * 获取物流供应商信息
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<FindDeliveryCompaniesResponse> findDeliveryCompanies(
      Request<FindDeliveryCompaniesRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<FindDeliveryCompaniesResponse>>() {});
  }
}


