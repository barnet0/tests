package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum MerchantStatus {

  ONLINE_MERCHANT(0), OFFLINE_MERCHANT(1), EDIT_MERCHANT(2);

  private int code;

  private MerchantStatus(int code) {
    this.code = code;
  }

  public static MerchantStatus getMerchantStatusByValue(int value) {
    for (MerchantStatus merchantStatus : MerchantStatus.values()) {
      if (merchantStatus.getCode() == value) {
        return merchantStatus;
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
