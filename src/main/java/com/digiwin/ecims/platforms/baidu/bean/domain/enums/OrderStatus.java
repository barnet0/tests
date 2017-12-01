package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum OrderStatus {
  WAIT_BUYER_PAY(1, "等待买家付款"), 
  WAIT_SELLER_SEND_GOODS(2, "等待卖家发货"), 
  WAIT_BUYER_CONFIRM_GOODS(3,"等待买家确认收货"), 
  TRADE_FINISHED(4, "交易成功"), 
  TRADE_CLOSED(5, "交易关闭"), 
  TRADE_CANCELED(6, "交易取消"), 
  TRADE_CREATED(7, "交易创建"), 
  TRADE_CREATED_FAILED(8, "交易创建失败"), 
  TRADE_CANCELING_WAIT_MERCHANT_AGREE(9, "交易取消待商家确认");

  private int value;
  private String name;

  private OrderStatus(int value, String name) {
    this.value = value;
    this.name = name;
  }

  public int getValue() {
    return this.value;
  }

  public String getName() {
    return this.name;
  }

  public static OrderStatus getOrderStatusByValue(int value)
  {
    for (OrderStatus orderStatus : OrderStatus.values()) {
      if (orderStatus.getValue() == value) {
        return orderStatus;
      }
    }
    return null;
  }

  public static boolean isAfterReceive(OrderStatus state) {
    return state == TRADE_FINISHED;
  }
}
