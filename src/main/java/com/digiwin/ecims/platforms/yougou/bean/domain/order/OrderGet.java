package com.digiwin.ecims.platforms.yougou.bean.domain.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.digiwin.ecims.platforms.yougou.bean.domain.order.base.OrderBase;

/**
 * 订单实体类
 * 
 * @author 维杰
 *
 */
public class OrderGet extends OrderBase {

  private String orderSourceId;

  private String orderSourceNo;
  
  private String outShopId;
  
  @JsonProperty("items")
  private List<OrderItemDetail> itemDetails;

  public List<OrderItemDetail> getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(List<OrderItemDetail> itemDetails) {
    this.itemDetails = itemDetails;
  }

  public String getOrderSourceId() {
    return orderSourceId;
  }

  public void setOrderSourceId(String orderSourceId) {
    this.orderSourceId = orderSourceId;
  }

  public String getOrderSourceNo() {
    return orderSourceNo;
  }

  public void setOrderSourceNo(String orderSourceNo) {
    this.orderSourceNo = orderSourceNo;
  }

  public String getOutShopId() {
    return outShopId;
  }

  public void setOutShopId(String outShopId) {
    this.outShopId = outShopId;
  }


}
