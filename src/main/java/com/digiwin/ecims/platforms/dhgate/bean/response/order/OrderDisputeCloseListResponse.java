package com.digiwin.ecims.platforms.dhgate.bean.response.order;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderDisputeCloseInfo;

public class OrderDisputeCloseListResponse extends BizStatusResponse {

  /**
   * 
   */
  private static final long serialVersionUID = -5912388487716017613L;

  private int count;

  private int pages;

  private List<OrderDisputeCloseInfo> orderDisputeCloseInfos;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public List<OrderDisputeCloseInfo> getOrderDisputeCloseInfos() {
    return orderDisputeCloseInfos;
  }

  public void setOrderDisputeCloseInfos(List<OrderDisputeCloseInfo> orderDisputeCloseInfos) {
    this.orderDisputeCloseInfos = orderDisputeCloseInfos;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public OrderDisputeCloseListResponse() {
    super();
  }


}
