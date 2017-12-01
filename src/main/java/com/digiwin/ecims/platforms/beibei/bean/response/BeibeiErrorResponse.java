package com.digiwin.ecims.platforms.beibei.bean.response;

public class BeibeiErrorResponse extends BeibeiBaseResponse {

  public BeibeiErrorResponse() {
  }

  public BeibeiErrorResponse(Boolean success, String message, String responseBody) {
    super(success, message, responseBody);
  }

}
