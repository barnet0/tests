package com.digiwin.ecims.platforms.ccb.bean.request.item.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.base.RequestHead;
import com.digiwin.ecims.platforms.ccb.bean.response.item.list.get.ItemListGetResponse;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemListGetRequest extends BaseRequest<ItemListGetResponse> {

  @XmlElement(name = "body")
  private ItemListGetRequestBody body;
  
  public ItemListGetRequestBody getBody() {
    return body;
  }

  public void setBody(ItemListGetRequestBody body) {
    this.body = body;
  }

  @Override
  public String getTranCode() {
    return "T0011";
  }

  public ItemListGetRequest() {
    super();
  }

  public ItemListGetRequest(RequestHead head) {
    super(head);
  }

  @Override
  public Class<ItemListGetResponse> getResponseClass() {
    return ItemListGetResponse.class;
  }

}
