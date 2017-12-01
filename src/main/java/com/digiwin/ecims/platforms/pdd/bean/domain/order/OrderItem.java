package com.digiwin.ecims.platforms.pdd.bean.domain.order;

public class OrderItem {

  private Long goodsID;
  
  private String skuCode;
  
  private String goodsName;
  
  private Double goodsPrice;
  
  private String goodsSpec;
  
  private Integer goodsCount;
  
  private String goodsRemark;
  
  private String goodsImg;

  /**
   * @return the goodsID
   */
  public Long getGoodsID() {
    return goodsID;
  }

  /**
   * @param goodsID the goodsID to set
   */
  public void setGoodsID(Long goodsID) {
    this.goodsID = goodsID;
  }

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @param skuCode the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * @return the goodsName
   */
  public String getGoodsName() {
    return goodsName;
  }

  /**
   * @param goodsName the goodsName to set
   */
  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  /**
   * @return the goodsPrice
   */
  public Double getGoodsPrice() {
    return goodsPrice;
  }

  /**
   * @param goodsPrice the goodsPrice to set
   */
  public void setGoodsPrice(Double goodsPrice) {
    this.goodsPrice = goodsPrice;
  }

  /**
   * @return the goodsSpec
   */
  public String getGoodsSpec() {
    return goodsSpec;
  }

  /**
   * @param goodsSpec the goodsSpec to set
   */
  public void setGoodsSpec(String goodsSpec) {
    this.goodsSpec = goodsSpec;
  }

  /**
   * @return the goodsCount
   */
  public Integer getGoodsCount() {
    return goodsCount;
  }

  /**
   * @param goodsCount the goodsCount to set
   */
  public void setGoodsCount(Integer goodsCount) {
    this.goodsCount = goodsCount;
  }

  /**
   * @return the goodsRemark
   */
  public String getGoodsRemark() {
    return goodsRemark;
  }

  /**
   * @param goodsRemark the goodsRemark to set
   */
  public void setGoodsRemark(String goodsRemark) {
    this.goodsRemark = goodsRemark;
  }

  /**
   * @return the goodsImg
   */
  public String getGoodsImg() {
    return goodsImg;
  }

  /**
   * @param goodsImg the goodsImg to set
   */
  public void setGoodsImg(String goodsImg) {
    this.goodsImg = goodsImg;
  }
  
  
}
