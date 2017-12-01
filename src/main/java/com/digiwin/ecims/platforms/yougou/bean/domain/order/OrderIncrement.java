package com.digiwin.ecims.platforms.yougou.bean.domain.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.digiwin.ecims.platforms.yougou.bean.domain.order.base.OrderBase;

/**
 * @author 维杰
 *
 */
public class OrderIncrement extends OrderBase {

  @JsonProperty("item_details")
  private List<OrderItemDetail> itemDetails;

  public List<OrderItemDetail> getItemDetails() {
      return itemDetails;
  }

  public void setItemDetails(List<OrderItemDetail> itemDetails) {
      this.itemDetails = itemDetails;
  }

  public OrderIncrement() {
    super();
  }
  
}
