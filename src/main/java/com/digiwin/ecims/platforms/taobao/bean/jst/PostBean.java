package com.digiwin.ecims.platforms.taobao.bean.jst;

public class PostBean {
  
  public static final String SPLITTER = "♥△♥";
  
  private String api;
  private JstParam params;

  public PostBean() {

  }

  public PostBean(String api, JstParam params) {
    super();
    this.api = api;
    this.params = params;
  }

  public String getApi() {
    return api;
  }

  public void setApi(String api) {
    this.api = api;
  }

  public JstParam getParams() {
    return params;
  }

  public void setParams(JstParam params) {
    this.params = params;
  }

}
