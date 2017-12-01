package com.digiwin.ecims.platforms.ccb.bean.response.delivery.send;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySendResponseBody {

  @XmlElement(name = "delivery")
  private List<DeliverySendResponseBodyDetail> bodyDetails;

  public List<DeliverySendResponseBodyDetail> getBodyDetails() {
    return bodyDetails;
  }

  public void setBodyDetails(List<DeliverySendResponseBodyDetail> bodyDetails) {
    this.bodyDetails = bodyDetails;
  }
  
  
}
