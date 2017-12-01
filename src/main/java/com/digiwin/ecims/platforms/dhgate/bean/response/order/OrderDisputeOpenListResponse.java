package com.digiwin.ecims.platforms.dhgate.bean.response.order;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderDisputeOpenInfo;

public class OrderDisputeOpenListResponse extends BizStatusResponse {


  /**
   * 
   */
  private static final long serialVersionUID = 1671272610481730753L;

  private int count;

  private int pages;

  private List<OrderDisputeOpenInfo> orderDisputeOpenInfos;

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

  public List<OrderDisputeOpenInfo> getOrderDisputeOpenInfos() {
    return orderDisputeOpenInfos;
  }

  public void setOrderDisputeOpenInfos(List<OrderDisputeOpenInfo> orderDisputeOpenInfos) {
    this.orderDisputeOpenInfos = orderDisputeOpenInfos;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
