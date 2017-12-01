package com.digiwin.ecims.platforms.baidu.bean.domain.item;

import java.util.List;

public class SkuInfo {
  
  private Long itemId;
  
  private Integer skuType;

  private List<BasePropertyValue> propertyInfos;

  private List<UserDefinedPropertyValue> userDefinedPropertyInfos;

  private Boolean deleted;
  
  private Long upc;
  
  private Float weight;
  
  private Float volume;
  
  private String addTime;
  
  private String updateTime;
  
  private String outerId;
  
  private List<SkuPp> skuPps;

  private List<UserDefinedSellAttribute> userDefinedSellAttribute;

  private Long skuId;

  private List<SellAttribute> sellAttribute;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Integer getSkuType() {
    return skuType;
  }

  public void setSkuType(Integer skuType) {
    this.skuType = skuType;
  }

  public List<BasePropertyValue> getPropertyInfos() {
    return propertyInfos;
  }

  public void setPropertyInfos(List<BasePropertyValue> propertyInfos) {
    this.propertyInfos = propertyInfos;
  }

  public List<UserDefinedPropertyValue> getUserDefinedPropertyInfos() {
    return userDefinedPropertyInfos;
  }

  public void setUserDefinedPropertyInfos(List<UserDefinedPropertyValue> userDefinedPropertyInfos) {
    this.userDefinedPropertyInfos = userDefinedPropertyInfos;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public Long getUpc() {
    return upc;
  }

  public void setUpc(Long upc) {
    this.upc = upc;
  }

  public Float getWeight() {
    return weight;
  }

  public void setWeight(Float weight) {
    this.weight = weight;
  }

  public Float getVolume() {
    return volume;
  }

  public void setVolume(Float volume) {
    this.volume = volume;
  }

  public String getAddTime() {
    return addTime;
  }

  public void setAddTime(String addTime) {
    this.addTime = addTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public List<SkuPp> getSkuPps() {
    return skuPps;
  }

  public void setSkuPps(List<SkuPp> skuPps) {
    this.skuPps = skuPps;
  }

  public List<UserDefinedSellAttribute> getUserDefinedSellAttribute() {
    return userDefinedSellAttribute;
  }

  public void setUserDefinedSellAttribute(List<UserDefinedSellAttribute> userDefinedSellAttribute) {
    this.userDefinedSellAttribute = userDefinedSellAttribute;
  }

  public Long getSkuId() {
    return skuId;
  }

  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }

  public List<SellAttribute> getSellAttribute() {
    return sellAttribute;
  }

  public void setSellAttribute(List<SellAttribute> sellAttribute) {
    this.sellAttribute = sellAttribute;
  }

}
