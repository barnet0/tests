package com.digiwin.ecims.platforms.ccb.bean.request.delivery.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySendRequestBodyDetail {

  @XmlElement(name = "order_id")
  private String orderId;
  
  @XmlElement(name = "company_code")
  private String companyCode;
  
  @XmlElement(name = "out_sid")
  private String outSid;
  
  @XmlElement(name = "type")
  private String type;
  
  @XmlElement(name = "send_time")
  private String sendTime;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public String getOutSid() {
    return outSid;
  }

  public void setOutSid(String outSid) {
    this.outSid = outSid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSendTime() {
    return sendTime;
  }

  public void setSendTime(String sendTime) {
    this.sendTime = sendTime;
  }
  
  
}
