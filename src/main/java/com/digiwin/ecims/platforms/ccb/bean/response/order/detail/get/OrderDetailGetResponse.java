package com.digiwin.ecims.platforms.ccb.bean.response.order.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetailGetResponse extends BaseResponse {

  @XmlElement(name = "body")
  private OrderDetailGetResponseBody body;

  public OrderDetailGetResponseBody getBody() {
    return body;
  }

  public void setBody(OrderDetailGetResponseBody body) {
    this.body = body;
  }
  
  
}
