package com.digiwin.ecims.platforms.yougou.bean.enums;

public enum OrderStatus {

  WAIT_SELLER_SEND_GOODS(1, "待发货"),
  TRADE_FINISHED(2, "已完成"),
  APPLY_INTERCEPT(3, "申请拦截"),
  ABNORMAL_TRADE(4, "异常订单");
  
  private int code;
  private String name;
  private OrderStatus(int code, String name) {
    this.code = code;
    this.name = name;
  }
  
  public int getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static OrderStatus getOrderStatusByCode(int code) {
    for (OrderStatus oStatus : OrderStatus.values()) {
      if (oStatus.getCode() == code) {
        return oStatus;
      }
    }
    return null;
  }
}
