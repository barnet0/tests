package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getinvoice;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class GetInvoiceRequest extends BaiduBaseRequest {

  private Long orderNo;
  
  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  @Override
  public String getUrlPath() {
    return "OrderService/getInvoice";
  }

}
