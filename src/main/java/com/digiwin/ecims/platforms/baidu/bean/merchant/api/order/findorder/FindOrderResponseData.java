package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.order.Order;

public class FindOrderResponseData {

  private Integer totalCount;

  private Integer code;

  private List<Order> orderList;

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public List<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<Order> orderList) {
    this.orderList = orderList;
  }


}
