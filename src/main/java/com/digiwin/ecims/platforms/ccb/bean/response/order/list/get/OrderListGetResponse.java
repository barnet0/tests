package com.digiwin.ecims.platforms.ccb.bean.response.order.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderListGetResponse extends BaseResponse {

  @XmlElement(name = "body")
  private OrderListGetResponseBody body;

  public OrderListGetResponseBody getBody() {
    return body;
  }

  public void setBody(OrderListGetResponseBody body) {
    this.body = body;
  }
  
  
}
