package com.digiwin.ecims.platforms.pdd2.bean.domain.order;

public class OrderItem {

  private Long goods_id;
  
 // private String skmall_id;
  
  private String goods_name;
  
  private Double goods_price;
  
  private String goods_spec;
  
  private Integer goods_count;
  
 // private String goodsRemark;
  
  private String goods_img;
  
  private String sku_id;
  
  private String outer_id;
  

  public String getSku_id() {
	return sku_id;
}

public void setSku_id(String sku_id) {
	this.sku_id = sku_id;
}

public String getOuter_id() {
	return outer_id;
}

public void setOuter_id(String outer_id) {
	this.outer_id = outer_id;
}

/**
   * @return the goodsID
   */
  public Long getGoodsID() {
    return goods_id;
  }

  /**
   * @param goodsID the goodsID to set
   */
  public void setGoodsID(Long goodsID) {
    this.goods_id = goodsID;
  }

  /**
   * @return the skmall_id
   */
 /* public String getSkmall_id() {
    return skmall_id;
  }*/

  /**
   * @param skmall_id the skmall_id to set
   */
  /*public void setSkmall_id(String skmall_id) {
    this.skmall_id = skmall_id;
  }*/

  /**
   * @return the goodsName
   */
  public String getGoodsName() {
    return goods_name;
  }

  /**
   * @param goodsName the goodsName to set
   */
  public void setGoodsName(String goodsName) {
    this.goods_name = goodsName;
  }

  /**
   * @return the goodsPrice
   */
  public Double getGoodsPrice() {
    return goods_price;
  }

  /**
   * @param goodsPrice the goodsPrice to set
   */
  public void setGoodsPrice(Double goodsPrice) {
    this.goods_price = goodsPrice;
  }

  /**
   * @return the goodsSpec
   */
  public String getGoodsSpec() {
    return goods_spec;
  }

  /**
   * @param goodsSpec the goodsSpec to set
   */
  public void setGoodsSpec(String goodsSpec) {
    this.goods_spec = goodsSpec;
  }

  /**
   * @return the goodsCount
   */
  public Integer getGoodsCount() {
    return goods_count;
  }

  /**
   * @param goodsCount the goodsCount to set
   */
  public void setGoodsCount(Integer goodsCount) {
    this.goods_count = goodsCount;
  }

  /**
   * @return the goodsRemark
   */
 /* public String getGoodsRemark() {
    return goodsRemark;
  }*/

  /**
   * @param goodsRemark the goodsRemark to set
   */
 /* public void setGoodsRemark(String goodsRemark) {
    this.goodsRemark = goodsRemark;
  }*/

  /**
   * @return the goodsImg
   */
  public String getGoodsImg() {
    return goods_img;
  }

  /**
   * @param goodsImg the goodsImg to set
   */
  public void setGoodsImg(String goodsImg) {
    this.goods_img = goodsImg;
  }
  
  
}
