package com.digiwin.ecims.platforms.ccb.bean.response.item.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.domain.item.detail.get.ItemSkuInfo;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemDetailGetResponseBody {

  @XmlElement(name = "sku_info")
  private ItemSkuInfo item;

  public ItemSkuInfo getItem() {
    return item;
  }

  public void setItem(ItemSkuInfo item) {
    this.item = item;
  }
  
  
}
