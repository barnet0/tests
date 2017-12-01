package com.digiwin.ecims.platforms.ccb.bean.request.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RequestHead {

  @XmlElement(name = "tran_code")
  private String tranCode;
  
  @XmlElement(name = "cust_id")
  private String custId;
  
  @XmlElement(name = "tran_sid")
  private String tranSid;

  public String getTranCode() {
    return tranCode;
  }

  public void setTranCode(String tranCode) {
    this.tranCode = tranCode;
  }

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  public String getTranSid() {
    return tranSid;
  }

  public RequestHead() {
    this("", "");
  }

  public RequestHead(String tranCode, String custId) {
    this(tranCode, custId, System.currentTimeMillis() + "");
  }
  
  public RequestHead(String tranCode, String custId, String tranSid) {
    this.tranCode = tranCode;
    this.custId = custId;
    this.tranSid = tranSid;
  }
  
  
}
