package com.digiwin.ecims.system.enums;

/**
 * 推送排程默认设置，根据枚举的value值从系统参数中获取
 * @author 维杰
 *
 */
public enum PostTaskDefaultParamEnum {

  PLANT("PostPlant"),
  ENT_ID("EntId"),
  COMPANY_ID("CompanyId"),
  ORDER_SERVICE("OrderService"),
  REFUND_SERVICE("RefundService"),
  ITEM_SERVICE("ItemService"),
  USER("PostUser"),
  POST_URL_PATH("PostUrlPath"),
  POST_IP("PostIp"),
  
  PUSH_TIME_SPAN("PushTimeSpan");
  
  private String paramKeyName;
  private PostTaskDefaultParamEnum(String paramKey) {
    this.paramKeyName = paramKey;
  }
  public String getParamKeyName() {
    return paramKeyName;
  }
  
}
