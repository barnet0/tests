package com.digiwin.ecims.platforms.pdd.bean.domain.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemSku {

  private String spec;
  
  @JsonProperty("skuID")
  private Long skuID;
  
  @JsonProperty("Num")
  private Integer num;
  
  private String outerID;

  public String getSpec() {
    return spec;
  }

  public void setSpec(String spec) {
    this.spec = spec;
  }

  public Long getSkuID() {
    return skuID;
  }

  public void setSkuID(Long skuID) {
    this.skuID = skuID;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getOuterID() {
    return outerID;
  }

  public void setOuterID(String outerID) {
    this.outerID = outerID;
  }
  
  
}
