package com.digiwin.ecims.platforms.ccb.bean.domain.order.list.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderList {

  @XmlElement(name = "order")
  private List<OrderBriefInfo> orderBriefInfos;

  public List<OrderBriefInfo> getOrderBriefInfos() {
    return orderBriefInfos;
  }

  public void setOrderBriefInfos(List<OrderBriefInfo> orderBriefInfos) {
    this.orderBriefInfos = orderBriefInfos;
  }
  
  
}
