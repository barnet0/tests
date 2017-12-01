package com.digiwin.ecims.platforms.beibei.bean.response;

public abstract class BeibeiBaseResponse {

  private Boolean success;
  
  private String message;
  
  private String responseBody;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public BeibeiBaseResponse() {
  }

  public BeibeiBaseResponse(Boolean success, String message, String responseBody) {
    super();
    this.success = success;
    this.message = message;
    this.responseBody = responseBody;
  }
  
  
}
