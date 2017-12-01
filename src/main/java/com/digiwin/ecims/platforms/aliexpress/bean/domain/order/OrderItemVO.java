package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

import java.util.List;

/**
 * 订单基础信息
 * @author 维杰
 *
 */
public class OrderItemVO {

  // 否 订单类型
  private String bizType;

  // 否 买家登录ID
  private String buyerLoginId;

  // 否 买家全名
  private String buyerSignerFullname;

  // 否 手续费率
  private Integer escrowFeeRate;

  // 否 冻结状态
  private String frozenStatus;

  // 否 资金状态
  private String fundStatus;

  // 否 订单创建时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 支付时间（和订单详情中gmtPaysuccess字段意义相同。) 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtPayTime;

  // 否 发货时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtSendGoodsTime;

  // 否 是否已请求放款 false
  private Boolean hasRequestLoan;

  // 否 纠纷状态
  private String issueStatus;

  // 否 剩余发货时间（天）
  private String leftSendGoodDay;

  // 否 剩余发货时间（小时）
  private String leftSendGoodHour;

  // 否 剩余发货时间（分钟）
  private String leftSendGoodMin;

  // 否 物流状态
  private String logisticsStatus;

  // 否 订单详情链接
  private String orderDetailUrl;

  // 否 订单ID
  private Long orderId;

  // 否 订单状态 PLACE_ORDER_SUCCESS
  private String orderStatus;

  // 否 支付类型
  private String paymentType;

  // 否 卖家登录ID
  private String sellerLoginId;

  // 否 卖家全名
  private String sellerSignerFullname;

  // 否 超时剩余时间
  private Long timeoutLeftTime;

  // 否 手续费
  private Money escrowFee;

  // 否 放款金额
  private Money loanAmount;

  // 否 付款金额
  private Money payAmount;

  // 否 商品列表
  private List<OrderProductVO> productList;

  public String getBizType() {
    return bizType;
  }

  public void setBizType(String bizType) {
    this.bizType = bizType;
  }

  public String getBuyerLoginId() {
    return buyerLoginId;
  }

  public void setBuyerLoginId(String buyerLoginId) {
    this.buyerLoginId = buyerLoginId;
  }

  public String getBuyerSignerFullname() {
    return buyerSignerFullname;
  }

  public void setBuyerSignerFullname(String buyerSignerFullname) {
    this.buyerSignerFullname = buyerSignerFullname;
  }

  public Integer getEscrowFeeRate() {
    return escrowFeeRate;
  }

  public void setEscrowFeeRate(Integer escrowFeeRate) {
    this.escrowFeeRate = escrowFeeRate;
  }

  public String getFrozenStatus() {
    return frozenStatus;
  }

  public void setFrozenStatus(String frozenStatus) {
    this.frozenStatus = frozenStatus;
  }

  public String getFundStatus() {
    return fundStatus;
  }

  public void setFundStatus(String fundStatus) {
    this.fundStatus = fundStatus;
  }

  public String getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(String gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public String getGmtPayTime() {
    return gmtPayTime;
  }

  public void setGmtPayTime(String gmtPayTime) {
    this.gmtPayTime = gmtPayTime;
  }

  public String getGmtSendGoodsTime() {
    return gmtSendGoodsTime;
  }

  public void setGmtSendGoodsTime(String gmtSendGoodsTime) {
    this.gmtSendGoodsTime = gmtSendGoodsTime;
  }

  public Boolean getHasRequestLoan() {
    return hasRequestLoan;
  }

  public void setHasRequestLoan(Boolean hasRequestLoan) {
    this.hasRequestLoan = hasRequestLoan;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public String getLeftSendGoodDay() {
    return leftSendGoodDay;
  }

  public void setLeftSendGoodDay(String leftSendGoodDay) {
    this.leftSendGoodDay = leftSendGoodDay;
  }

  public String getLeftSendGoodHour() {
    return leftSendGoodHour;
  }

  public void setLeftSendGoodHour(String leftSendGoodHour) {
    this.leftSendGoodHour = leftSendGoodHour;
  }

  public String getLeftSendGoodMin() {
    return leftSendGoodMin;
  }

  public void setLeftSendGoodMin(String leftSendGoodMin) {
    this.leftSendGoodMin = leftSendGoodMin;
  }

  public String getLogisticsStatus() {
    return logisticsStatus;
  }

  public void setLogisticsStatus(String logisticsStatus) {
    this.logisticsStatus = logisticsStatus;
  }

  public String getOrderDetailUrl() {
    return orderDetailUrl;
  }

  public void setOrderDetailUrl(String orderDetailUrl) {
    this.orderDetailUrl = orderDetailUrl;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public String getSellerLoginId() {
    return sellerLoginId;
  }

  public void setSellerLoginId(String sellerLoginId) {
    this.sellerLoginId = sellerLoginId;
  }

  public String getSellerSignerFullname() {
    return sellerSignerFullname;
  }

  public void setSellerSignerFullname(String sellerSignerFullname) {
    this.sellerSignerFullname = sellerSignerFullname;
  }

  public Long getTimeoutLeftTime() {
    return timeoutLeftTime;
  }

  public void setTimeoutLeftTime(Long timeoutLeftTime) {
    this.timeoutLeftTime = timeoutLeftTime;
  }

  public Money getEscrowFee() {
    return escrowFee;
  }

  public void setEscrowFee(Money escrowFee) {
    this.escrowFee = escrowFee;
  }

  public Money getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(Money loanAmount) {
    this.loanAmount = loanAmount;
  }

  public Money getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(Money payAmount) {
    this.payAmount = payAmount;
  }

  public List<OrderProductVO> getProductList() {
    return productList;
  }

  public void setProductList(List<OrderProductVO> productList) {
    this.productList = productList;
  }


}
