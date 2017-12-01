package com.digiwin.ecims.platforms.ccb.bean.response.order.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.domain.order.detail.get.OrderInfos;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetailGetResponseBody {

  @XmlElement(name = "order_items")
  private OrderInfos orderItems;

  public OrderInfos getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(OrderInfos orderItems) {
    this.orderItems = orderItems;
  }
  
  
}
