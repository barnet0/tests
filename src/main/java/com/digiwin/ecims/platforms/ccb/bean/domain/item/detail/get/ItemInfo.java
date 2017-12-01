package com.digiwin.ecims.platforms.ccb.bean.domain.item.detail.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInfo {

  @XmlElement(name = "sku_info")
  private List<ItemSkuInfo> skuInfos;

  public List<ItemSkuInfo> getSkuInfos() {
    return skuInfos;
  }

  public void setSkuInfos(List<ItemSkuInfo> skuInfos) {
    this.skuInfos = skuInfos;
  }
  
  
}
