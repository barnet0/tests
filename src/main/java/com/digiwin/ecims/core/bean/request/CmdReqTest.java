package com.digiwin.ecims.core.bean.request;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CmdReqTest {
  private String api; // 指令名稱

  private String ecno; // 目標電商平台

  private Map<String, Object> params; // 指令所需的參數

  private String storeid; // 商店ID


  public String getApi() {
    return api;
  }


  public void setApi(String api) {
    this.api = api;
  }


  public String getEcno() {
    return ecno;
  }


  public void setEcno(String ecno) {
    this.ecno = ecno;
  }


  public Map<String, Object> getParams() {
    return params;
  }


  public void setParams(Map<String, Object> params) {
    this.params = params;
  }


  public String getStoreid() {
    return storeid;
  }


  public void setStoreid(String storeid) {
    this.storeid = storeid;
  }


  @Override
  public String toString() {
    String str = null;
    try {
      str = new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return str;
  }
}
