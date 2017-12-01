package com.digiwin.ecims.ontime.enumvar;

/**
 * @author lizhi
 *
 */

public enum TaskExceTypeEnum {
  SpringService("1", "SpringService", "Spring管理对象"), 
//  EJB3("2", "EJB3", "EJB对象"), 
//  SQL("3", "SQL", "本地SQL"), 
//  SpringService4ECFX("4", "SpringService4ECFX", "Spring管理对象,檢查機制"),
  CheckData("5", "ReCycleCheckData", "定時檢查機制"), 
  NULL("", "", "");

  private String code = "";
  private String name = "";
  private String desc = "";

  private TaskExceTypeEnum(String code, String name, String desc) {
    this.code = code;
    this.name = name;
    this.desc = desc;
  }

  public static String getName(String code) {
    for (TaskExceTypeEnum each : TaskExceTypeEnum.values()) {
      if (each.getCode().equals(code))
        return each.getName();
    }
    return "";
  }

  public static String getDesc(String code) {
    for (TaskExceTypeEnum each : TaskExceTypeEnum.values()) {
      if (each.getCode().equals(code))
        return each.getDesc();
    }
    return "";
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
