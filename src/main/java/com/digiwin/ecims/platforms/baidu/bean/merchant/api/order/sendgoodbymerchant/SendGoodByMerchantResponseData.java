package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant;

public class SendGoodByMerchantResponseData {

  private DeliverGoodsResult result;

  private Integer code;
  
  public DeliverGoodsResult getResult() {
    return result;
  }

  public void setResult(DeliverGoodsResult result) {
    this.result = result;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

}
