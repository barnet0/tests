package com.digiwin.ecims.test.taobao;

public class NewPostBean {
  private String api;
  private NewJstParams params;
  public String getApi() {
    return api;
  }
  public void setApi(String api) {
    this.api = api;
  }
  public NewJstParams getParams() {
    return params;
  }
  public void setParams(NewJstParams params) {
    this.params = params;
  }
  public NewPostBean(String api, NewJstParams params) {
    super();
    this.api = api;
    this.params = params;
  }
  public NewPostBean() {
    super();
    // TODO Auto-generated constructor stub
  }
  
}
