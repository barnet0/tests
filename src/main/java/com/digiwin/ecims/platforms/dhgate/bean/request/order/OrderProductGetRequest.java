package com.digiwin.ecims.platforms.dhgate.bean.request.order;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderProductGetResponse;

/**
 * 订单内商品获取请求类
 * 
 * @author 维杰
 *
 */
public class OrderProductGetRequest extends DhgateBaseRequest<OrderProductGetResponse> {

  private String orderNo;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public OrderProductGetRequest() {
    super();
  }

  @Override
  public Class<OrderProductGetResponse> getResponseClass() {
    return OrderProductGetResponse.class;
  }


}
