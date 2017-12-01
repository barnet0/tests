package com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.base.RequestHead;
import com.digiwin.ecims.platforms.ccb.bean.response.order.detail.get.OrderDetailGetResponse;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetailGetRequest extends BaseRequest<OrderDetailGetResponse> {

  @XmlElement(name = "body")
  private OrderDetailGetRequestBody body;

  public OrderDetailGetRequestBody getBody() {
    return body;
  }

  public void setBody(OrderDetailGetRequestBody body) {
    this.body = body;
  }

  @Override
  public String getTranCode() {
    return "T0007";
  }

  @Override
  public Class<OrderDetailGetResponse> getResponseClass() {
    return OrderDetailGetResponse.class;
  }

  public OrderDetailGetRequest() {
    super();
  }

  public OrderDetailGetRequest(RequestHead head) {
    super(head);
  }
  
}
