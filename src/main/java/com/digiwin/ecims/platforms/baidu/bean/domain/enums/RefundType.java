package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum RefundType {

  REFUND_ONLY(1),
  RETURN(2);
  
  private int code;
  
  private RefundType(int code) {
    this.code = code;
  }

  public static RefundType getRefundTypeByValue(int value)
  {
    for (RefundType refundType : RefundType.values()) {
      if (refundType.getCode() == value) {
        return refundType;
      }
    }
    return null;
  }
  
  public int getCode() {
    return this.code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
