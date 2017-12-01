package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum PayMethod {
  DELIVERY(1, "货到付款"), ONLINE(2, "在线支付");

  private int value;
  private String name;

  private PayMethod(int value, String name) {
    this.value = value;
    this.name = name;
  }

  public int getValue() {
    return this.value;
  }

  public String getName() {
    return this.name;
  }

  public static PayMethod getPayMethodByValue(int value)
  {
    for (PayMethod payMethod : PayMethod.values()) {
      if (payMethod.getValue() == value) {
        return payMethod;
      }
    }
    return null;
  }
}
