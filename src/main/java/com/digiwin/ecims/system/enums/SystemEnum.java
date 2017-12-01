package com.digiwin.ecims.system.enums;

public enum SystemEnum {

  IP_LOCALHOST("127.0.0.1");
  
  private final String valueString;

  SystemEnum(String value) {
    this.valueString = value;
  }

  /**
   * @return the valueString
   */
  public String getValueString() {
    return valueString;
  }


}
