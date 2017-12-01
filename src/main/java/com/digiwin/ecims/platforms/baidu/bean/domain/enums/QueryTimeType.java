package com.digiwin.ecims.platforms.baidu.bean.domain.enums;

public enum QueryTimeType {
  CREATE(1), PAY(2), SEND_GOODS(3), RECEIVE_GOODS(4), UPDATE(5);

  private int code;

  private QueryTimeType(int code) {
    this.code = code;
  }

  public int getCode() {
    return this.code;
  }

  public static QueryTimeType getByCode(int code) {
    for (QueryTimeType type : QueryTimeType.values()) {
      if (type.getCode() == code) {
        return type;
      }
    }
    return null;
  }
}
