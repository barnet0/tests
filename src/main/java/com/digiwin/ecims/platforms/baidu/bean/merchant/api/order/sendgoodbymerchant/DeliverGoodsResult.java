package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant;

import java.util.List;

public class DeliverGoodsResult {

  private String message;

  private List<Long> successOrderNoList;

  private Boolean success;

  private List<Long> failedOrderNoList;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Long> getSuccessOrderNoList() {
    return successOrderNoList;
  }

  public void setSuccessOrderNoList(List<Long> successOrderNoList) {
    this.successOrderNoList = successOrderNoList;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public List<Long> getFailedOrderNoList() {
    return failedOrderNoList;
  }

  public void setFailedOrderNoList(List<Long> failedOrderNoList) {
    this.failedOrderNoList = failedOrderNoList;
  }


}
