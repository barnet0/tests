package com.digiwin.ecims.platforms.ccb.bean.response.delivery.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySendResponseBodyDetail {

  @XmlElement(name = "order_id")
  private String orderId;
  
  @XmlElement(name = "ret_code")
  private String retCode;
  
  @XmlElement(name = "ret_msg")
  private String retMsg;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getRetCode() {
    return retCode;
  }

  public void setRetCode(String retCode) {
    this.retCode = retCode;
  }

  public String getRetMsg() {
    return retMsg;
  }

  public void setRetMsg(String retMsg) {
    this.retMsg = retMsg;
  }
  
  
}
