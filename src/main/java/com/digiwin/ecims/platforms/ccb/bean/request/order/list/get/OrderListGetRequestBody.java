package com.digiwin.ecims.platforms.ccb.bean.request.order.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderListGetRequestBody {

  @XmlElement(name = "order")
  private OrderListGetRequestBodyDetail bodyDetail;

  public OrderListGetRequestBodyDetail getBodyDetail() {
    return bodyDetail;
  }

  public void setBodyDetail(OrderListGetRequestBodyDetail bodyDetail) {
    this.bodyDetail = bodyDetail;
  }
  
  
}
