package com.digiwin.ecims.platforms.beibei.bean.domain.order.base;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 订单中商品基础类
 * @author zaregoto
 *
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public abstract class AbstractOrderItemDto {

  private Long skuId;

  private String iid;

  private String url;

  private String outerId;

  private String goodsNum;

  private String title;

  private Double price;

  private Double originPrice;

  private Integer num;

  private String refundStatus;

  private Double subtotal;

  private Double totalFee;

  private String skuProperties;

  private String shipCityProperty;

  private String img;

  private String brand;

  public Long getSkuId() {
    return skuId;
  }

  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }

  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
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

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(String refundStatus) {
    this.refundStatus = refundStatus;
  }

  public Double getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(Double subtotal) {
    this.subtotal = subtotal;
  }

  public Double getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(Double totalFee) {
    this.totalFee = totalFee;
  }

  public String getSkuProperties() {
    return skuProperties;
  }

  public void setSkuProperties(String skuProperties) {
    this.skuProperties = skuProperties;
  }

  public String getShipCityProperty() {
    return shipCityProperty;
  }

  public void setShipCityProperty(String shipCityProperty) {
    this.shipCityProperty = shipCityProperty;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

}
