package com.digiwin.ecims.platforms.pdd.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.pdd.bean.domain.order.OrderSearchResult;
import com.digiwin.ecims.platforms.pdd.bean.response.PddBaseResponse;

public class OrderSearchResponse extends PddBaseResponse {

  private Integer orderCount;
  
  private Integer page;
  
  private List<OrderSearchResult> orderList;

  /**
   * @return the orderCount
   */
  public Integer getOrderCount() {
    return orderCount;
  }

  /**
   * @param orderCount the orderCount to set
   */
  public void setOrderCount(Integer orderCount) {
    this.orderCount = orderCount;
  }

  /**
   * @return the page
   */
  public Integer getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(Integer page) {
    this.page = page;
  }

  /**
   * @return the orderList
   */
  public List<OrderSearchResult> getOrderList() {
    return orderList;
  }

  /**
   * @param orderList the orderList to set
   */
  public void setOrderList(List<OrderSearchResult> orderList) {
    this.orderList = orderList;
  }
  
  
}
