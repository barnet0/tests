package com.digiwin.ecims.platforms.ccb.bean.domain.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderProductItem {

  @XmlElement(name = "sku_id")
  private String skuId;
  
  @XmlElement(name = "pro_id")
  private String proId;

  @XmlElement(name = "prod_name")
  private String prodName;

  @XmlElement(name = "prod_desc")
  private String prodDesc;
  
  @XmlElement(name = "prod_buy_amt")
  private String prodBuyAmt;

  @XmlElement(name = "prod_price")
  private String prodPrice;

  @XmlElement(name = "prod_discount")
  private String prodDiscount;

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getProId() {
    return proId;
  }

  public void setProId(String proId) {
    this.proId = proId;
  }

  public String getProdName() {
    return prodName;
  }

  public void setProdName(String prodName) {
    this.prodName = prodName;
  }

  public String getProdDesc() {
    return prodDesc;
  }

  public void setProdDesc(String prodDesc) {
    this.prodDesc = prodDesc;
  }

  public String getProdBuyAmt() {
    return prodBuyAmt;
  }

  public void setProdBuyAmt(String prodBuyAmt) {
    this.prodBuyAmt = prodBuyAmt;
  }

  public String getProdPrice() {
    return prodPrice;
  }

  public void setProdPrice(String prodPrice) {
    this.prodPrice = prodPrice;
  }

  public String getProdDiscount() {
    return prodDiscount;
  }

  public void setProdDiscount(String prodDiscount) {
    this.prodDiscount = prodDiscount;
  }

  
}
