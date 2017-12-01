package com.digiwin.ecims.platforms.ccb.bean.response.item.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.domain.item.list.get.ItemList;
import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemListGetResponse extends BaseResponse {

  @XmlElement(name = "flag")
  private String flag;
  
  @XmlElement(name = "products")
  private ItemList itemList;

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public ItemList getItemList() {
    return itemList;
  }

  public void setItemList(ItemList itemList) {
    this.itemList = itemList;
  }
  
  
}
