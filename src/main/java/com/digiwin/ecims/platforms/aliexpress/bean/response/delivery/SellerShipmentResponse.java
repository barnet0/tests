package com.digiwin.ecims.platforms.aliexpress.bean.response.delivery;

import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class SellerShipmentResponse extends AliExpressBaseResponse {

  // 是 success=true 返回成功，否则失败
  private Boolean success;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }


}
