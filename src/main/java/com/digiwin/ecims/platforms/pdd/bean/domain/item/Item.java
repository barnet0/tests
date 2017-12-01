package com.digiwin.ecims.platforms.pdd.bean.domain.item;

import java.util.List;

public class Item {

  private Long goodsID;
  
  private String goodsName;
  
  private Double goodsPrice;
  
  private Integer isSku;
  
  private Integer num;
  
  private List<ItemSku> skuList;

  public Long getGoodsID() {
    return goodsID;
  }

  public void setGoodsID(Long goodsID) {
    this.goodsID = goodsID;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Double getGoodsPrice() {
    return goodsPrice;
  }

  public void setGoodsPrice(Double goodsPrice) {
    this.goodsPrice = goodsPrice;
  }

  public Integer getIsSku() {
    return isSku;
  }

  public void setIsSku(Integer isSku) {
    this.isSku = isSku;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public List<ItemSku> getSkuList() {
    return skuList;
  }

  public void setSkuList(List<ItemSku> skuList) {
    this.skuList = skuList;
  }

  
}
