package com.digiwin.ecims.platforms.ccb.bean.response.delivery.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySendResponse extends BaseResponse {

  @XmlElement(name = "body")
  private DeliverySendResponseBody body;

  public DeliverySendResponseBody getBody() {
    return body;
  }

  public void setBody(DeliverySendResponseBody body) {
    this.body = body;
  }
  
  
}
