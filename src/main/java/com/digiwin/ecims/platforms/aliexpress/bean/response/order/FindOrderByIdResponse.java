package com.digiwin.ecims.platforms.aliexpress.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.Money;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenAddressDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenChildOrderDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenIssueInfoDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenLoanInfoDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenLogisticInfoDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenOrderMsgDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenOrderProductInfoDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenPersonDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOpenRefundInfoDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.order.TpOperationLogDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class FindOrderByIdResponse extends AliExpressBaseResponse {

  // 否 买家全名
  private String buyerSignerFullname;

  // 否 买家账号
  private String buyerloginid;

  // 否 冻结状态("NO_FROZEN"无冻结；"IN_FROZEN"冻结中；)
  private String frozenStatus;

  // 否 资金状态(NOT_PAY,未付款； PAY_SUCCESS,付款成功； WAIT_SELLER_CHECK，卖家验款)
  private String fundStatus;

  // 否 订单创建时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 修改时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtModified;

  // 否 gmtPaySuccess:支付成功时间（与订单列表中gmtPayTime字段意义相同）
  // 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtPaySuccess;

  // 否 交易结束时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtTradeEnd;

  // 否 主订单ID
  private Long id;

  // 否 是否手机订单 true
  private Boolean isPhone;

  // 否 纠纷状态("NO_ISSUE"无纠纷；"IN_ISSUE"纠纷中；“END_ISSUE”纠纷结束。)
  private String issueStatus;

  // 否 放款状态
  private String loanStatus;

  // 否
  // 物流状态（"WAIT_SELLER_SEND_GOODS"等待卖家发货;"SELLER_SEND_PART_GOODS"卖家部分发货；"SELLER_SEND_GOODS"卖家已发货；"BUYER_ACCEPT_GOODS"买家已确认收货；"NO_LOGISTICS"没有物流流转信息）
  private String logisticsStatus;

  // 否 订单状态
  private String orderStatus;

  // 否 付款方式 （migs信用卡支付走人民币渠道； migs102MasterCard支付并且走人民币渠道； migs101Visa支付并且走人民币渠道； pp101 PayPal； mb
  // MoneyBooker渠道； tt101 Bank Transfer支付； wu101 West Union支付； wp101 Visa走美金渠道的支付； wp102 Mastercard
  // 走美金渠道的支付； qw101 QIWI支付； cybs101 Visa走CYBS渠道的支付； cybs102 Mastercard 走CYBS渠道的支付； wm101
  // WebMoney支付； ebanx101 巴西Beloto支付 ；）
  private String paymentType;

  // 否 卖家子帐号
  private String sellerOperatorAliidloginid;

  // 否 负责人loginId
  private String sellerOperatorLoginId;

  // 否 卖家名称
  private String sellerSignerFullname;

  // 否 买家信息
  private TpOpenPersonDTO buyerInfo;

  // 否 商品信息
  private List<TpOpenOrderProductInfoDTO> childOrderExtInfoList;

  // 否 子订单列表
  private List<TpOpenChildOrderDTO> childOrderList;

  // 否 手续费
  private Money escrowFee;

  // 否 商品总金额
  private Money initOderAmount;

  // 否 纠纷信息
  private TpOpenIssueInfoDTO issueInfo;

  // 否 放款信息
  private TpOpenLoanInfoDTO loanInfo;

  // 否 物流信息
  private List<TpOpenLogisticInfoDTO> logisticInfoList;

  // 否 物流费用
  private Money logisticsAmount;

  // 否 操作日志列表
  private List<TpOperationLogDTO> oprLogDtoList;

  // 否 订单金额
  private Money orderAmount;

  // 否 留言信息
  private List<TpOpenOrderMsgDTO> orderMsgList;

  // 否 收货地址
  private TpOpenAddressDTO receiptAddress;

  // 否 退款信息
  private TpOpenRefundInfoDTO refundInfo;

  public String getBuyerSignerFullname() {
    return buyerSignerFullname;
  }

  public void setBuyerSignerFullname(String buyerSignerFullname) {
    this.buyerSignerFullname = buyerSignerFullname;
  }

  public String getBuyerloginid() {
    return buyerloginid;
  }

  public void setBuyerloginid(String buyerloginid) {
    this.buyerloginid = buyerloginid;
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

  public String getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(String gmtModified) {
    this.gmtModified = gmtModified;
  }

  public String getGmtPaySuccess() {
    return gmtPaySuccess;
  }

  public void setGmtPaySuccess(String gmtPaySuccess) {
    this.gmtPaySuccess = gmtPaySuccess;
  }

  public String getGmtTradeEnd() {
    return gmtTradeEnd;
  }

  public void setGmtTradeEnd(String gmtTradeEnd) {
    this.gmtTradeEnd = gmtTradeEnd;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getIsPhone() {
    return isPhone;
  }

  public void setIsPhone(Boolean isPhone) {
    this.isPhone = isPhone;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public String getLoanStatus() {
    return loanStatus;
  }

  public void setLoanStatus(String loanStatus) {
    this.loanStatus = loanStatus;
  }

  public String getLogisticsStatus() {
    return logisticsStatus;
  }

  public void setLogisticsStatus(String logisticsStatus) {
    this.logisticsStatus = logisticsStatus;
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

  public String getSellerOperatorAliidloginid() {
    return sellerOperatorAliidloginid;
  }

  public void setSellerOperatorAliidloginid(String sellerOperatorAliidloginid) {
    this.sellerOperatorAliidloginid = sellerOperatorAliidloginid;
  }

  public String getSellerOperatorLoginId() {
    return sellerOperatorLoginId;
  }

  public void setSellerOperatorLoginId(String sellerOperatorLoginId) {
    this.sellerOperatorLoginId = sellerOperatorLoginId;
  }

  public String getSellerSignerFullname() {
    return sellerSignerFullname;
  }

  public void setSellerSignerFullname(String sellerSignerFullname) {
    this.sellerSignerFullname = sellerSignerFullname;
  }

  public TpOpenPersonDTO getBuyerInfo() {
    return buyerInfo;
  }

  public void setBuyerInfo(TpOpenPersonDTO buyerInfo) {
    this.buyerInfo = buyerInfo;
  }

  public List<TpOpenOrderProductInfoDTO> getChildOrderExtInfoList() {
    return childOrderExtInfoList;
  }

  public void setChildOrderExtInfoList(List<TpOpenOrderProductInfoDTO> childOrderExtInfoList) {
    this.childOrderExtInfoList = childOrderExtInfoList;
  }

  public List<TpOpenChildOrderDTO> getChildOrderList() {
    return childOrderList;
  }

  public void setChildOrderList(List<TpOpenChildOrderDTO> childOrderList) {
    this.childOrderList = childOrderList;
  }

  public Money getEscrowFee() {
    return escrowFee;
  }

  public void setEscrowFee(Money escrowFee) {
    this.escrowFee = escrowFee;
  }

  public Money getInitOderAmount() {
    return initOderAmount;
  }

  public void setInitOderAmount(Money initOderAmount) {
    this.initOderAmount = initOderAmount;
  }

  public TpOpenIssueInfoDTO getIssueInfo() {
    return issueInfo;
  }

  public void setIssueInfo(TpOpenIssueInfoDTO issueInfo) {
    this.issueInfo = issueInfo;
  }

  public TpOpenLoanInfoDTO getLoanInfo() {
    return loanInfo;
  }

  public void setLoanInfo(TpOpenLoanInfoDTO loanInfo) {
    this.loanInfo = loanInfo;
  }

  public List<TpOpenLogisticInfoDTO> getLogisticInfoList() {
    return logisticInfoList;
  }

  public void setLogisticInfoList(List<TpOpenLogisticInfoDTO> logisticInfoList) {
    this.logisticInfoList = logisticInfoList;
  }

  public Money getLogisticsAmount() {
    return logisticsAmount;
  }

  public void setLogisticsAmount(Money logisticsAmount) {
    this.logisticsAmount = logisticsAmount;
  }

  public List<TpOperationLogDTO> getOprLogDtoList() {
    return oprLogDtoList;
  }

  public void setOprLogDtoList(List<TpOperationLogDTO> oprLogDtoList) {
    this.oprLogDtoList = oprLogDtoList;
  }

  public Money getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(Money orderAmount) {
    this.orderAmount = orderAmount;
  }

  public List<TpOpenOrderMsgDTO> getOrderMsgList() {
    return orderMsgList;
  }

  public void setOrderMsgList(List<TpOpenOrderMsgDTO> orderMsgList) {
    this.orderMsgList = orderMsgList;
  }

  public TpOpenAddressDTO getReceiptAddress() {
    return receiptAddress;
  }

  public void setReceiptAddress(TpOpenAddressDTO receiptAddress) {
    this.receiptAddress = receiptAddress;
  }

  public TpOpenRefundInfoDTO getRefundInfo() {
    return refundInfo;
  }

  public void setRefundInfo(TpOpenRefundInfoDTO refundInfo) {
    this.refundInfo = refundInfo;
  }

}
