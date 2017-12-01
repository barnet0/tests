package com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInventoryUpdateRequestBody {

  @XmlElement(name = "update")
  private List<ItemInventoryUpdateRequestBodyDetail> bodyDetails;

  public List<ItemInventoryUpdateRequestBodyDetail> getBodyDetails() {
    return bodyDetails;
  }

  public void setBodyDetails(List<ItemInventoryUpdateRequestBodyDetail> bodyDetails) {
    this.bodyDetails = bodyDetails;
  }
  
  
}
