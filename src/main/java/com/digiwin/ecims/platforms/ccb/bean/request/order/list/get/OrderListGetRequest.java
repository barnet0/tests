package com.digiwin.ecims.platforms.ccb.bean.request.order.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.base.RequestHead;
import com.digiwin.ecims.platforms.ccb.bean.response.order.list.get.OrderListGetResponse;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderListGetRequest extends BaseRequest<OrderListGetResponse> {

  @XmlElement(name = "body")
  private OrderListGetRequestBody body;

  public OrderListGetRequestBody getBody() {
    return body;
  }

  public void setBody(OrderListGetRequestBody body) {
    this.body = body;
  }

  @Override
  public String getTranCode() {
    return "T0008";
  }

  public OrderListGetRequest() {
    super();
  }

  public OrderListGetRequest(RequestHead head) {
    super(head);
  }

  @Override
  public Class<OrderListGetResponse> getResponseClass() {
    return OrderListGetResponse.class;
  }
  
  
}
