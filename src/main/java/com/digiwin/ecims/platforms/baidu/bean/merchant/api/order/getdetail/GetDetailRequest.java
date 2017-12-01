package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class GetDetailRequest extends BaiduBaseRequest {

  private Long orderNo;
  
  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  @Override
  public String getUrlPath() {
    return "OrderService/getDetail";
  }

}
