package com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInventoryUpdateRequestBodyDetail {

  @XmlElement(name = "sku_id")
  private String skuId;
  
  @XmlElement(name = "pro_id")
  private String proId;
  
  @XmlElement(name = "quantity")
  private Integer quantity;
  
  @XmlElement(name = "type")
  private Integer type;

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

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
  
  
}
