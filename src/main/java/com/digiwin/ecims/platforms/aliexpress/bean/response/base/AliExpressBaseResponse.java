package com.digiwin.ecims.platforms.aliexpress.bean.response.base;

import com.digiwin.ecims.platforms.aliexpress.bean.response.error.ErrorResponse;

public abstract class AliExpressBaseResponse {

  private ErrorResponse errorResponse;

  public ErrorResponse getErrorResponse() {
    return errorResponse;
  }

  public void setErrorResponse(ErrorResponse errorResponse) {
    this.errorResponse = errorResponse;
  }
  
}
