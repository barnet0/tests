package com.digiwin.ecims.platforms.ccb.bean.domain.item.list.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.ccb.bean.domain.item.detail.get.ItemInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemList {

  @XmlElement(name = "product")
  private List<ItemInfo> items;

  public List<ItemInfo> getItems() {
    return items;
  }

  public void setItems(List<ItemInfo> items) {
    this.items = items;
  }

  
  
}
