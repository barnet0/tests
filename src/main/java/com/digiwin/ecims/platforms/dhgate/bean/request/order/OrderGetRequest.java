package com.digiwin.ecims.platforms.dhgate.bean.request.order;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderGetResponse;

/**
 * 订单详情请求类
 * 
 * @author 维杰
 *
 */
public class OrderGetRequest extends DhgateBaseRequest<OrderGetResponse> {

  // 必须 订单号 卖家后台登录能看到成交的订单号；示例值：1330312162
  private String orderNo;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public OrderGetRequest() {
    super();
  }

  @Override
  public Class<OrderGetResponse> getResponseClass() {
    return OrderGetResponse.class;
  }


}
