package com.digiwin.ecims.platforms.baidu.bean.domain.order;

public class Coupon {

  private Long orderNo;

  private String serialNumber;

  private Integer type;

  private String originalFee;

  private String orderDiscountFee;
  
  private String name;
  
  private String materialJson;

  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getOriginalFee() {
    return originalFee;
  }

  public void setOriginalFee(String originalFee) {
    this.originalFee = originalFee;
  }

  public String getOrderDiscountFee() {
    return orderDiscountFee;
  }

  public void setOrderDiscountFee(String orderDiscountFee) {
    this.orderDiscountFee = orderDiscountFee;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMaterialJson() {
    return materialJson;
  }

  public void setMaterialJson(String materialJson) {
    this.materialJson = materialJson;
  }

}
