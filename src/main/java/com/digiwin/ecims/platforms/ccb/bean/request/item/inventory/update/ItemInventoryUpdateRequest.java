package com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.base.RequestHead;
import com.digiwin.ecims.platforms.ccb.bean.response.item.inventory.update.ItemInventoryUpdateResponse;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInventoryUpdateRequest extends BaseRequest<ItemInventoryUpdateResponse> {

  @XmlElement(name = "body")
  private ItemInventoryUpdateRequestBody body;
  
  public ItemInventoryUpdateRequestBody getBody() {
    return body;
  }

  public void setBody(ItemInventoryUpdateRequestBody body) {
    this.body = body;
  }

  @Override
  public String getTranCode() {
    return "T0004";
  }

  public ItemInventoryUpdateRequest() {
    super();
  }

  public ItemInventoryUpdateRequest(RequestHead head) {
    super(head);
  }

  @Override
  public Class<ItemInventoryUpdateResponse> getResponseClass() {
    return ItemInventoryUpdateResponse.class;
  }

}
