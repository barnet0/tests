package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail;

import com.digiwin.ecims.platforms.baidu.bean.domain.order.OrderDetail;

public class GetDetailResponseData {

  private OrderDetail orderDetail;

  private int code;

  public OrderDetail getOrderDetail() {
    return orderDetail;
  }

  public void setOrderDetail(OrderDetail orderDetail) {
    this.orderDetail = orderDetail;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
  
}
