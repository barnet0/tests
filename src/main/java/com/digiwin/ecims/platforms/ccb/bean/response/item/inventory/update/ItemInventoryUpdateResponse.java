package com.digiwin.ecims.platforms.ccb.bean.response.item.inventory.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInventoryUpdateResponse extends BaseResponse {

  @XmlElement(name = "body")
  private ItemInventoryUpdateResponseBody body;

  public ItemInventoryUpdateResponseBody getBody() {
    return body;
  }

  public void setBody(ItemInventoryUpdateResponseBody body) {
    this.body = body;
  }
  
  
  
}
