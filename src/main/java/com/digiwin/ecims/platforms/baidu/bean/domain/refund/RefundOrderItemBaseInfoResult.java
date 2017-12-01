package com.digiwin.ecims.platforms.baidu.bean.domain.refund;

public class RefundOrderItemBaseInfoResult {

  private RefundInfoEntity refundInfoEntity;

  private OrderInfoEntity orderInfoEntity;

  private OrderItemInfoEntity orderItemInfoEntity;

  public RefundInfoEntity getRefundInfoEntity() {
    return refundInfoEntity;
  }

  public void setRefundInfoEntity(RefundInfoEntity refundInfoEntity) {
    this.refundInfoEntity = refundInfoEntity;
  }

  public OrderInfoEntity getOrderInfoEntity() {
    return orderInfoEntity;
  }

  public void setOrderInfoEntity(OrderInfoEntity orderInfoEntity) {
    this.orderInfoEntity = orderInfoEntity;
  }

  public OrderItemInfoEntity getOrderItemInfoEntity() {
    return orderItemInfoEntity;
  }

  public void setOrderItemInfoEntity(OrderItemInfoEntity orderItemInfoEntity) {
    this.orderItemInfoEntity = orderItemInfoEntity;
  }


}
