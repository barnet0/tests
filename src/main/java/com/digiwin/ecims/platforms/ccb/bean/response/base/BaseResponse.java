package com.digiwin.ecims.platforms.ccb.bean.response.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseResponse {

  @XmlElement(name = "head")
  private ResponseHead head;
  
  public ResponseHead getHead() {
    return head;
  }

  public void setHead(ResponseHead head) {
    this.head = head;
  }

  public BaseResponse() {
  }

  
}
