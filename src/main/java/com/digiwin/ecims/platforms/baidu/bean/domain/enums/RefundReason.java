package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum RefundReason {

  ORDERING_ERROR_OR_NOT_NEED(1, "订购错误/不想要了"),
  SEND_TIMEOUT(2, "未按约定时间发货"),
  EMPTY_PACKAGE_OR_LACK_OF_GOODS(3, "空包裹/少货"),
  NO_DELIVERY_RECORD_OR_NOT_RECEIVE_ALLTIME(4, "无快递记录/一直未送达"),
  OTHER(5,"其他");
  
  private int code;
  private String reason;

  private RefundReason(int code, String reason) {
    this.code = code;
    this.reason = reason;
  }
  
  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
  
  public static RefundReason getRefundReasonByValue(int value)
  {
    for (RefundReason refundReason : RefundReason.values()) {
      if (refundReason.getCode() == value) {
        return refundReason;
      }
    }
    return null;
  }
  
  public static String getRefundReasonStringByValue(int value) {
    return RefundReason.getRefundReasonByValue(value).getReason();
  }
}
