package com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemDetailGetRequestBodyDetail {

  @XmlElement(name = "sku_id")
  private String skuId;
  
  @XmlElement(name = "pro_id")
  private String proId;

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
  
  
}
