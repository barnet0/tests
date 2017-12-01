package com.digiwin.ecims.platforms.beibei.bean.domain.item.base;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ItemSkuDto {

  private String id;

  private String outerId;

  private String skuProperties;

  private Integer num;

  private Double price;

  private Double originPrice;

  private Integer soldNum;

  private Integer holdNum;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public String getSkuProperties() {
    return skuProperties;
  }

  public void setSkuProperties(String skuProperties) {
    this.skuProperties = skuProperties;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getOriginPrice() {
    return originPrice;
  }

  public void setOriginPrice(Double originPrice) {
    this.originPrice = originPrice;
  }

  public Integer getSoldNum() {
    return soldNum;
  }

  public void setSoldNum(Integer soldNum) {
    this.soldNum = soldNum;
  }

  public Integer getHoldNum() {
    return holdNum;
  }

  public void setHoldNum(Integer holdNum) {
    this.holdNum = holdNum;
  }

  public ItemSkuDto() {
  }

  
}
