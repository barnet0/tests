package com.digiwin.ecims.platforms.taobao.bean.response.cainiao.waybillget;

public class CainiaoWaybillGetPrintDataBody {

  private com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto recipient;
  
  private RoutingInfo routingInfo;
  
  private com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto sender;
  
  private ShippingOption shippingOption;

  private String waybillCode;

  /**
   * @return the recipient
   */
  public com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto getRecipient() {
    return recipient;
  }

  /**
   * @param recipient the recipient to set
   */
  public void setRecipient(com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto recipient) {
    this.recipient = recipient;
  }

  /**
   * @return the routingInfo
   */
  public RoutingInfo getRoutingInfo() {
    return routingInfo;
  }

  /**
   * @param routingInfo the routingInfo to set
   */
  public void setRoutingInfo(RoutingInfo routingInfo) {
    this.routingInfo = routingInfo;
  }

  /**
   * @return the sender
   */
  public com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto getSender() {
    return sender;
  }

  /**
   * @param sender the sender to set
   */
  public void setSender(com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto sender) {
    this.sender = sender;
  }

  /**
   * @return the shippingOption
   */
  public ShippingOption getShippingOption() {
    return shippingOption;
  }

  /**
   * @param shippingOption the shippingOption to set
   */
  public void setShippingOption(ShippingOption shippingOption) {
    this.shippingOption = shippingOption;
  }

  /**
   * @return the waybillCode
   */
  public String getWaybillCode() {
    return waybillCode;
  }

  /**
   * @param waybillCode the waybillCode to set
   */
  public void setWaybillCode(String waybillCode) {
    this.waybillCode = waybillCode;
  }
  
  
}
