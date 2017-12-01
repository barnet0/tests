package com.digiwin.ecims.platforms.ccb.bean.request.delivery.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.base.RequestHead;
import com.digiwin.ecims.platforms.ccb.bean.response.delivery.send.DeliverySendResponse;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySendRequest extends BaseRequest<DeliverySendResponse> {

  @XmlElement(name = "body")
  private DeliverySendRequestBody body;
  
  public DeliverySendRequestBody getBody() {
    return body;
  }

  public void setBody(DeliverySendRequestBody body) {
    this.body = body;
  }

  @Override
  public String getTranCode() {
    return "T0005";
  }

  public DeliverySendRequest() {
    super();
  }

  public DeliverySendRequest(RequestHead head) {
    super(head);
  }

  @Override
  public Class<DeliverySendResponse> getResponseClass() {
    return DeliverySendResponse.class;
  }

}
