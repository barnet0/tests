package com.digiwin.ecims.platforms.ccb.bean.response.order.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.domain.order.list.get.OrderList;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderListGetResponseBody {

  @XmlElement(name = "total_results_count")
  private String totalResultsCount;
  
  @XmlElement(name = "has_next")
  private String hasNext;
  
  @XmlElement(name = "orders")
  private OrderList orders;

  public String getTotalResultsCount() {
    return totalResultsCount;
  }

  public void setTotalResultsCount(String totalResultsCount) {
    this.totalResultsCount = totalResultsCount;
  }

  public String getHasNext() {
    return hasNext;
  }

  public void setHasNext(String hasNext) {
    this.hasNext = hasNext;
  }

  public OrderList getOrders() {
    return orders;
  }

  public void setOrders(OrderList orders) {
    this.orders = orders;
  }
  
  
}
