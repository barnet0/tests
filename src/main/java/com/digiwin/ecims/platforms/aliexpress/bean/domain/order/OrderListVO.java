package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

import java.util.List;

/**
 * 订单列表信息
 * @author 维杰
 *
 */
public class OrderListVO {

  // 订单总数
  private Integer totalItem;

  // 订单列表
  private List<OrderItemVO> orderList;

  public Integer getTotalItem() {
    return totalItem;
  }

  public void setTotalItem(Integer totalItem) {
    this.totalItem = totalItem;
  }

  public List<OrderItemVO> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<OrderItemVO> orderList) {
    this.orderList = orderList;
  }
}
