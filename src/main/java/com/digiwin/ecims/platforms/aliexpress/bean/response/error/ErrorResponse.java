package com.digiwin.ecims.platforms.aliexpress.bean.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class ErrorResponse extends AliExpressBaseResponse {

  @JsonProperty("error_code")
  private String errorCode;

  @JsonProperty("error_message")
  private String errorMessage;

  private String exception;

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public ErrorResponse() {
  }

  public ErrorResponse(String errorCode, String errorMessage, String exception) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.exception = exception;
  }


}
