package com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetailGetRequestBody {

  @XmlElement(name = "order")
  private OrderDetailGetRequestBodyDetail bodyDetail;

  public OrderDetailGetRequestBodyDetail getBodyDetail() {
    return bodyDetail;
  }

  public void setBodyDetail(OrderDetailGetRequestBodyDetail bodyDetail) {
    this.bodyDetail = bodyDetail;
  }
  
  
  
}
