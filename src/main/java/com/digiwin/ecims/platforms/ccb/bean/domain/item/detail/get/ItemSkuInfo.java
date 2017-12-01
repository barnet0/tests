package com.digiwin.ecims.platforms.ccb.bean.domain.item.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSkuInfo {

  @XmlElement(name = "sku_id")
  private String skuId;
  
  @XmlElement(name = "pro_id")
  private String proId;
  
  @XmlElement(name = "name")
  private String name;
  
  @XmlElement(name = "status")
  private String status;
  
  @XmlElement(name = "stock")
  private Integer stock;
  
  @XmlElement(name = "price")
  private Double price;
  
  @XmlElement(name = "description")
  private String description;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  
}
