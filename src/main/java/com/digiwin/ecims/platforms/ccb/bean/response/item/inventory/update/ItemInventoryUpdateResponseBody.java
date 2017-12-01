package com.digiwin.ecims.platforms.ccb.bean.response.item.inventory.update;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInventoryUpdateResponseBody {

  @XmlElement(name = "sku")
  private List<ItemInventoryUpdateResponseBodyDetail> bodyDetails;

  public List<ItemInventoryUpdateResponseBodyDetail> getBodyDetails() {
    return bodyDetails;
  }

  public void setBodyDetails(List<ItemInventoryUpdateResponseBodyDetail> bodyDetails) {
    this.bodyDetails = bodyDetails;
  }
  
  
}
