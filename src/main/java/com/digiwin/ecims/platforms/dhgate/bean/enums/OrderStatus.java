package com.digiwin.ecims.platforms.dhgate.bean.enums;


public enum OrderStatus {

  TRADE_CANCELLED("111000", "订单取消"),
  WAIT_BUYER_PAY("101003", "等待买家付款"),
  WAIT_PLATFORM_CONFIRM_PAYMENT("102001", "买家已付款，等待平台确认"),
  WAIT_SELLER_SEND_GOODS("103001", "等待发货"),
  WAIT_FOR_CONSULT_OF_APPLIED_REFUND("105001", "买家申请退款，等待协商结果"),
  REFUND_AGREEMENT_SUCCEEDED("105002", "退款协议已达成"),
  WAIT_SELLER_SEND_GOODS_AFTER_PARTIAL_REFUNDS("105003", "部分退款后，等待发货"),
  REFUND_CANCELLED("105004", "买家取消退款申请"),
  SELLER_CONSIGNED_PART("103002", "已部分发货"),
  WAIT_BUYER_CONFIRM_GOODS("101009", "等待买家确认收货"),
  WAIT_REFUND_RETURN_NEGOTIATE_REACH("106001", "退款/退货协商中，等待协议达成"),
  BUYER_COMPLAIN_TO_PLATFORM("106002", "买家投诉到平台"),
  EXECUTE_REACHED_NEGOTIATION("106003", "协议已达成，执行中"),
  MANUAL_CONFIRM_GOODS("102006", "人工确认收货"),
  EXCEEDED_BOOKING_DEADLINE_AUTO_CONFIRM_GOODS("102007", "超过预定期限，自动确认收货"),
  TRADE_FINISHED("102111", "交易成功"),
  TRADE_CLOSED("111111", "交易关闭");

  private String code;
  private String name;

  private OrderStatus(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static OrderStatus getOrderStatusByCode(String code) {
    for (OrderStatus oStatus : OrderStatus.values()) {
      if (oStatus.getCode().equals(code)) {
        return oStatus;
      }
    }
    return null;
  }

}
