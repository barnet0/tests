package com.digiwin.ecims.platforms.ccb.bean.domain.order.detail.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfos {

  @XmlElement(name = "order_info")
  private List<OrderInfo> orderInfos;

  public List<OrderInfo> getOrderInfos() {
    return orderInfos;
  }

  public void setOrderInfos(List<OrderInfo> orderInfos) {
    this.orderInfos = orderInfos;
  }
  
  
}
