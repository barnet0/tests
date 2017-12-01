package com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno;

import com.digiwin.ecims.platforms.baidu.bean.domain.refund.RefundOrderItemBaseInfoResult;

public class GetRefundInfoByNoResponseData {

  private RefundOrderItemBaseInfoResult result;

  private Integer code;

  private String msg;

  public RefundOrderItemBaseInfoResult getResult() {
    return result;
  }

  public void setResult(RefundOrderItemBaseInfoResult result) {
    this.result = result;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }


}
