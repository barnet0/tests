package com.digiwin.ecims.platforms.baidu.bean.domain.order;

public class Promotion {

  private Long orderNo;
  
  private Integer type;
  
  private String discountFee;
  
  private Long marketId;
  
  private String name;
  
  private String content;
  
  private String materialJson;

  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDiscountFee() {
    return discountFee;
  }

  public void setDiscountFee(String discountFee) {
    this.discountFee = discountFee;
  }

  public Long getMarketId() {
    return marketId;
  }

  public void setMarketId(Long marketId) {
    this.marketId = marketId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMaterialJson() {
    return materialJson;
  }

  public void setMaterialJson(String materialJson) {
    this.materialJson = materialJson;
  }
  
}
