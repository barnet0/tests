package com.digiwin.ecims.platforms.ccb.bean.response.item.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemDetailGetResponse extends BaseResponse {

  @XmlElement(name = "body")
  private ItemDetailGetResponseBody body;

  public ItemDetailGetResponseBody getBody() {
    return body;
  }

  public void setBody(ItemDetailGetResponseBody body) {
    this.body = body;
  }
  
  
}
