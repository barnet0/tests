/**
 * 
 */
package com.digiwin.ecims.system.enums;

/**
 * 存入LogInfo的业务类型
 * 
 * @author 维杰
 * @since 2015.09.04
 */
public enum LogInfoBizTypeEnum {

  // OMS指令请求
  OMS_REQUEST("OMS-REQUEST"),

  // ECI排程推送
  ECI_PUSH("ECI-PUSH"),

  // ECI排程拉取
  ECI_REQUEST("ECI-REQUEST"),

  // ECI排程拉取-校验
  ECI_REQUEST_CHECK("ECI-REQUEST-CHECK"),

  // SYSPARAM更新
  SYS_PARAM_UPDATE("SYS-PARAM-UPDATE"),

  // SYSPARAM新增
  SYS_PARAM_SAVE("SYS-PARAM-SAVE"),

  // SYSPARAM删除
  SYS_PARAM_DELETE("SYS-PARAM-DELETE"),

  // SYNC手动拉取
  MANUAL_SYNC("MANUAL-SYNC"),

  // PUSH手动推送
  MANUAL_PUSH("MANUAL-PUSH"),

  // LogInfo删除
  LOGINFO_DELETE("LOGINFO-DELETE"),

  // SourceLog删除
  SOURCELOG_DELETE("SOURCELOG-DELETE"),

  // OAuth授权检查
  OAUTH_CHECK("OAUTH-CHECK"),

  // log_info内其他类型
  OTHER("");

  private final String valueString;

  LogInfoBizTypeEnum(String valueString) {
    this.valueString = valueString;
  }

  /**
   * @return the valueString
   */
  public String getValueString() {
    return valueString;
  }

}
