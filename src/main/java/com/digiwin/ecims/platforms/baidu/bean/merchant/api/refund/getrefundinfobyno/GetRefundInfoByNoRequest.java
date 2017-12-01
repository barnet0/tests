package com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class GetRefundInfoByNoRequest extends BaiduBaseRequest {

  private Long refundNo;

  public Long getRefundNo() {
    return refundNo;
  }

  public void setRefundNo(Long refundNo) {
    this.refundNo = refundNo;
  }

  public GetRefundInfoByNoRequest() {
    super();
  }

  @Override
  public String getUrlPath() {
    return "OrderService/getRefundInfoByNo";
  }


}
