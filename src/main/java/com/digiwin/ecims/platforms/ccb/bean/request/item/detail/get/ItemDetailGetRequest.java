package com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.base.RequestHead;
import com.digiwin.ecims.platforms.ccb.bean.response.item.detail.get.ItemDetailGetResponse;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemDetailGetRequest extends BaseRequest<ItemDetailGetResponse> {

  @XmlElement(name = "body")
  private ItemDetailGetRequestBody body;
  
  public ItemDetailGetRequestBody getBody() {
    return body;
  }

  public void setBody(ItemDetailGetRequestBody body) {
    this.body = body;
  }

  @Override
  public String getTranCode() {
    return "T0006";
  }

  public ItemDetailGetRequest() {
    super();
  }

  public ItemDetailGetRequest(RequestHead head) {
    super(head);
  }

  @Override
  public Class<ItemDetailGetResponse> getResponseClass() {
    return ItemDetailGetResponse.class;
  }

}
