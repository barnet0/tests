package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum ReturnReason {

  GOODS_INCORRECT(1, "商家发错货"),
  PROD_DATE_OR_GUARANTEE_PERIOD_INCONSISTENT_WITH_SELLER(2, "生产日期/保质期与商家承诺不符"),
  GOODS_QUALITY_PROBLEM(3, "商品质量有问题"),
  GOODS_DAMAGED(4, "商品有破损"),
  GOODS_COUNTERFEIT_OR_THREE_NON(5, "假冒伪劣商品/三无商品"),
  GOODS_INCONSISTENT_WITH_DESC(6, "实物与描述不符"),
  GOODS_DISLIKE_OR_NOT_NEED(7, "不喜欢/不想要了"),
  OTHER(8, "其他");
  
  private int code;
  private String reason;

  private ReturnReason(int code, String reason) {
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

  public static ReturnReason getReturnReasonByValue(int value) {
    for (ReturnReason returnReason : ReturnReason.values()) {
      if (returnReason.getCode() == value) {
        return returnReason;
      }
    }
    return null;
  }

  public static String getReturnReasonStringByValue(int value) {
    return ReturnReason.getReturnReasonByValue(value).getReason();
  }
}
