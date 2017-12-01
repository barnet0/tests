package com.digiwin.ecims.platforms.aliexpress.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.OrderItemVO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class FindOrderListQueryResponse extends AliExpressBaseResponse {

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
