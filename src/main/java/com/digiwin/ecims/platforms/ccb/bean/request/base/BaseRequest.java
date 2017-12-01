package com.digiwin.ecims.platforms.ccb.bean.request.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseRequest<T extends BaseResponse> {

  @XmlElement(name = "head")
  private RequestHead head;
  
  public abstract String getTranCode();
  
  public abstract Class<T> getResponseClass();
  
  public RequestHead getHead() {
    return head;
  }

  public void setHead(RequestHead head) {
    this.head = head;
  }

  public BaseRequest() {
    this.head = new RequestHead();
  }

  public BaseRequest(RequestHead head) {
    this.head = head;
  }

}
