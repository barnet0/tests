package com.digiwin.ecims.platforms.yougou.bean.enums;

public enum CommodityStatus {

  INSTOCK("1", "停售");
  
  private String code;
  private String name;
  private CommodityStatus(String code, String name) {
    this.code = code;
    this.name = name;
  }
  public String getCode() {
    return code;
  }
  public String getName() {
    return name;
  }
  
  
}
