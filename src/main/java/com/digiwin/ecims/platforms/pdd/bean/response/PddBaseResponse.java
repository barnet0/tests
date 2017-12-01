package com.digiwin.ecims.platforms.pdd.bean.response;

public abstract class PddBaseResponse {

  private Integer result;
  
  private String cause;

  private String responseBody;
  
  /**
   * @return the result
   */
  public Integer getResult() {
    return result;
  }

  /**
   * @param result the result to set
   */
  public void setResult(Integer result) {
    this.result = result;
  }

  /**
   * @return the cause
   */
  public String getCause() {
    return cause;
  }

  /**
   * @param cause the cause to set
   */
  public void setCause(String cause) {
    this.cause = cause;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }
  
}
