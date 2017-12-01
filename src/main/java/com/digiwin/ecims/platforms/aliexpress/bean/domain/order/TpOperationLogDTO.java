package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 操作日志信息
 * 
 * @author 维杰
 *
 */
public class TpOperationLogDTO {

  /**
   * 否 操作类型，用于记录操作事件。 可能值为front_create_order, front_seller_send_all_goods,
   * front_seller_send_part_goods, front_pay_online_success, front_trade_completed,
   * front_trade_success, trade_close, front_buyerComfirmAcceptGoods, front_sellerAnnounceSendGoods,
   * front_paypal_pay_success, front_pay_wu_success, front_pay_qw_success, front_mb_pay_success,
   * cancel_order_close_trade, buyer_close_cancel, pledge_refund_ing, pledge_refund_fail,
   * pledge_refund_success, pledge_issue_buyer_create, pledge_issue_buyer_modify,
   * pledge_issue_buyer_accept, pledge_issue_buyer_cancel, pledge_issue_cs_cancel_money,
   * risk_control_pass, buyer_cancle_group_after_payment, group_success, group_failure,
   * buyer_cancle_group_after_payment, presell_promotion_end front_create_order
   */
  private String actionType;

  // 否 子订单ID
  private Long childOrderId;

  // 否 创建时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 修改时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtModified;

  // 否 事件ID
  private Long id;

  // 否 备注
  private String memo;

  // 否 操作员
  private String operator;

  // 否 订单ID
  private Long orderId;

  public String getActionType() {
    return actionType;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public Long getChildOrderId() {
    return childOrderId;
  }

  public void setChildOrderId(Long childOrderId) {
    this.childOrderId = childOrderId;
  }

  public String getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(String gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public String getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(String gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }


}
