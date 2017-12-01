package com.digiwin.ecims.platforms.taobao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaobaoRes {

  @JsonProperty("response")
  private String response;

  @JsonProperty("comparisonCol")
  private String comparisonCol;

  public TaobaoRes() {
    // TODO Auto-generated constructor stub
  }

  public TaobaoRes(String response) {
    super();
    this.response = response;
  }

  public TaobaoRes(String response, String comparisonCol) {
    super();
    this.response = response;
    this.comparisonCol = comparisonCol;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public String getComparisonCol() {
    return comparisonCol;
  }

  public void setComparisonCol(String comparisonCol) {
    this.comparisonCol = comparisonCol;
  }

}
