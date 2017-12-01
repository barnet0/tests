package com.digiwin.ecims.system.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SysParamMaintainQueryBean implements Serializable {

  private String pKey;

  private String status;


  public SysParamMaintainQueryBean() {

  }

  public SysParamMaintainQueryBean(String pKey, String status) {
    super();
    this.pKey = pKey;
    this.status = status;
  }

  public String getPKey() {
    return pKey;
  }

  public void setPKey(String pKey) {
    this.pKey = pKey;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }



}

