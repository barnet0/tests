package com.digiwin.ecims.platforms.baidu.bean.domain.refund;

public class OrderItemInfoEntity {

  private String goodsUrl;

  private String goodsPrice;

  private String skuOuterId;

  private Integer itemId;

  private Integer goodsNum;

  private String jsonProperties;

  private String goodsName;

  private Integer goodsType;

  public String getGoodsUrl() {
    return goodsUrl;
  }

  public void setGoodsUrl(String goodsUrl) {
    this.goodsUrl = goodsUrl;
  }

  public String getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(String goodsPrice) {
    this.goodsPrice = goodsPrice;
  }

  public String getSkuOuterId() {
    return skuOuterId;
  }

  public void setSkuOuterId(String skuOuterId) {
    this.skuOuterId = skuOuterId;
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public Integer getGoodsNum() {
    return goodsNum;
  }

  public void setGoodsNum(Integer goodsNum) {
    this.goodsNum = goodsNum;
  }

  public String getJsonProperties() {
    return jsonProperties;
  }

  public void setJsonProperties(String jsonProperties) {
    this.jsonProperties = jsonProperties;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Integer getGoodsType() {
    return goodsType;
  }

  public void setGoodsType(Integer goodsType) {
    this.goodsType = goodsType;
  }
  
}
