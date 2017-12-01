package com.digiwin.ecims.platforms.ccb.bean.response.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseHead {

  @XmlElement(name = "tran_sid")
  private String tranSid;

  @XmlElement(name = "cust_id")
  private String custId;

  @XmlElement(name = "ret_code")
  private String retCode;

  @XmlElement(name = "ret_msg")
  private String retMsg;

  public String getTranSid() {
    return tranSid;
  }

  public void setTranSid(String tranSid) {
    this.tranSid = tranSid;
  }

  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
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

  public ResponseHead(String tranSid, String custId, String retCode, String retMsg) {
    super();
    this.tranSid = tranSid;
    this.custId = custId;
    this.retCode = retCode;
    this.retMsg = retMsg;
  }

  public ResponseHead() {}

}
