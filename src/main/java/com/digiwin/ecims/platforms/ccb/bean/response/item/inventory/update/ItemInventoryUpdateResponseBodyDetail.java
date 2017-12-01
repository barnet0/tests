package com.digiwin.ecims.platforms.ccb.bean.response.item.inventory.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInventoryUpdateResponseBodyDetail {

  @XmlElement(name = "sku_id")
  private String skuId;
  
  @XmlElement(name = "pro_id")
  private String proId;
  
  @XmlElement(name = "quantity")
  private Integer quantity;
  
  @XmlElement(name = "ret_code")
  private String retCode;
  
  @XmlElement(name = "ret_msg")
  private String retMsg;
  
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

  public String getRetCode() {
    return retCode;
  }

  public void setRetCode(String retCode) {
    this.retCode = retCode;
  }

  public String getRetMsg() {
    return retMsg;
  }

  public void setRetMsg(String retMsg) {
    this.retMsg = retMsg;
  }

}
