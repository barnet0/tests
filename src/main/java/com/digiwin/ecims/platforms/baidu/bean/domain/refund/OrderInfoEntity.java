package com.digiwin.ecims.platforms.baidu.bean.domain.refund;

public class OrderInfoEntity {

  private String createTime;

  private String realPayFee;

  private String wholeFavorablePrice;

  private String orderPaySuccessTime;

  private String orderNo;

  private String shopName;

  private Integer shopId;

  private String deliveryFee;

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getRealPayFee() {
    return realPayFee;
  }

  public void setRealPayFee(String realPayFee) {
    this.realPayFee = realPayFee;
  }

  public String getWholeFavorablePrice() {
    return wholeFavorablePrice;
  }

  public void setWholeFavorablePrice(String wholeFavorablePrice) {
    this.wholeFavorablePrice = wholeFavorablePrice;
  }

  public String getOrderPaySuccessTime() {
    return orderPaySuccessTime;
  }

  public void setOrderPaySuccessTime(String orderPaySuccessTime) {
    this.orderPaySuccessTime = orderPaySuccessTime;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public Integer getShopId() {
    return shopId;
  }

  public void setShopId(Integer shopId) {
    this.shopId = shopId;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }


}
