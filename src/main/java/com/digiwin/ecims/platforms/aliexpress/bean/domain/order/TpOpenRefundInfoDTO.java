package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 退款信息
 * @author 维杰
 *
 */
public class TpOpenRefundInfoDTO {

  // 否 退款原因
  private String refundReason;

  // 否 退款状态
  private String refundStatus;

  // 否 退款类型
  private String refundType;

  public String getRefundReason() {
    return refundReason;
  }

  public void setRefundReason(String refundReason) {
    this.refundReason = refundReason;
  }

  public String getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(String refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getRefundType() {
    return refundType;
  }

  public void setRefundType(String refundType) {
    this.refundType = refundType;
  }


}
