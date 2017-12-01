package com.digiwin.ecims.platforms.taobao.enums;

/**
 * 此处存放菜鸟物流需要的参数的key，可通过ParamSystemService获取
 * @author 维杰 . at 2016.08.11
 *
 */
public enum CainiaoParamEnum {

  /**
   * 圆通速递面单打印模板URL
   */
  YTO_TEMPLATE_URL("YtoTemplateUrl"),
  /**
   * 韵达快递面单打印模板URL
   */
  YUNDA_TEMPLATE_URL("YundaTemplateUrl"),
  /**
   * EMS快递面单打印模板URL
   */
  EMS_TEMPLATE_URL("EmsTemplateUrl"),
  
  /**
   * EMS经济快递面单打印模板URL
   */
  EYB_TEMPLATE_URL("EybTemplateUrl");
  
  private String key;

  private CainiaoParamEnum(String key) {
    this.key = key;
  }
  
  public String getKey() {
    return key;
  }

  /**
   * 根据菜鸟物流定义的物流公司code，获取电商接口对应的参数值
   * @param cpCode
   * @return
   */
  public static String getKeyByCpcode(String cpCode) {
    String result = "";
    switch (cpCode) {
      case "YTO":
        result = YTO_TEMPLATE_URL.getKey();
        break;
      case "YUNDA":
        result = YUNDA_TEMPLATE_URL.getKey();
        break;
      case "EMS":
        result = EMS_TEMPLATE_URL.getKey();
        break;
      case "EYB":
        result = EYB_TEMPLATE_URL.getKey();
        break;
      default:
        break;
    }
    return result;
  }
  
}
