package com.digiwin.ecims.platforms.ccb.bean.request.delivery.send;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySendRequestBody {

  @XmlElement(name = "delivery")
  private List<DeliverySendRequestBodyDetail> deliveries;

  public List<DeliverySendRequestBodyDetail> getDeliveries() {
    return deliveries;
  }

  public void setDeliveries(List<DeliverySendRequestBodyDetail> deliveries) {
    this.deliveries = deliveries;
  }
  
  
}
