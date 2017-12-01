package com.digiwin.ecims.system.enums;

/**
 * 可用API列表缓存值
 * @author zaregoto
 *
 */
public enum EcApiValidListEnum {

  /**
   * 设置可以用的淘宝API列表
   */
  TAOBAO_VALID_APIS("TaobaoValidApis");

  private String keyName;

  private EcApiValidListEnum(String keyName) {
    this.keyName = keyName;
  }

  public String getKeyName() {
    return keyName;
  }
  

}
