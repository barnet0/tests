package com.digiwin.ecims.platforms.beibei.bean.domain.item.base;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public abstract class AbstractItemDto {

  private String iid;
  
  private String goodsNum;
  
  private String title;
  
  private String img;
  
  private Double price;
  
  private Double originPrice;
  
  private List<ItemSkuDto> skus; //addbycs at 20170418
  
  //private List<ItemSkuDto> sku; //markbycs at 20170418

  public List<ItemSkuDto> getSkus() {
	return skus;
}

public void setSkus(List<ItemSkuDto> skus) {
	this.skus = skus;
}

public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public String getGoodsNum() {
    return goodsNum;
  }

  public void setGoodsNum(String goodsNum) {
    this.goodsNum = goodsNum;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
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

  /*public List<ItemSkuDto> getSku() {
    return sku;
  }

  public void setSku(List<ItemSkuDto> sku) {
    this.sku = sku;
  }*/

  public AbstractItemDto() {
  }
  
}
