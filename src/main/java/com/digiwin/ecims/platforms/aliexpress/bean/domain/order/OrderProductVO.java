package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 订单商品
 * @author 维杰
 *
 */
public class OrderProductVO {

  // 否 买家firstName
  private String buyerSignerFirstName;

  // 否 买家lastName
  private String buyerSignerLastName;

  // 否 子订单是否能提交纠纷 false
  private Boolean canSubmitIssue;

  // 否 子订单号
  private Long childId;

  // 否 妥投时间 5-10
  private String deliveryTime;

  // 否 限时达 27
  private String freightCommitDay;

  // 否 资金状态 NOT_PAY
  private String fundStatus;

  // 否 备货时间 30
  private Integer goodsPrepareTime;

  // 否 纠纷类型
  private String issueMode;

  // 否 纠纷状态 NO_ISSUE
  private String issueStatus;

  // 否 物流服务 EMS
  private String logisticsServiceName;

  // 否 物流类型 EMS
  private String logisticsType;

  // 否 订单备注
  private String memo;

  // 否 是否支持假一赔三 false
  private Boolean moneyBack3x;

  // 否 订单ID
  private Long orderId;

  // 否 商品数量
  private Integer productCount;

  // 否 商品ID
  private Long productId;

  // 否 商品主图Url
  private String productImgUrl;

  // 否 商品名称
  private String productName;

  // 否 快照Url
  private String productSnapUrl;

  // 否 商品规格
  private String productStandard;

  // 否 商品单位
  private String productUnit;

  // 否 卖家firstName
  private String sellerSignerFirstName;

  // 否 卖家lastName
  private String sellerSignerLastName;

  // 否 发货时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String sendGoodsTime;

  // 否 订单显示状态 PLACE_ORDER_SUCCESS
  private String showStatus;

  // 否 商品编码
  private String skuCode;

  // 否 子订单状态
  private String sonOrderStatus;

  private Money logisticsAmount;

  private Money productUnitPrice;

  private Money totalProductAmount;

  public String getBuyerSignerFirstName() {
    return buyerSignerFirstName;
  }

  public void setBuyerSignerFirstName(String buyerSignerFirstName) {
    this.buyerSignerFirstName = buyerSignerFirstName;
  }

  public String getBuyerSignerLastName() {
    return buyerSignerLastName;
  }

  public void setBuyerSignerLastName(String buyerSignerLastName) {
    this.buyerSignerLastName = buyerSignerLastName;
  }

  public Boolean getCanSubmitIssue() {
    return canSubmitIssue;
  }

  public void setCanSubmitIssue(Boolean canSubmitIssue) {
    this.canSubmitIssue = canSubmitIssue;
  }

  public Long getChildId() {
    return childId;
  }

  public void setChildId(Long childId) {
    this.childId = childId;
  }

  public String getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public String getFreightCommitDay() {
    return freightCommitDay;
  }

  public void setFreightCommitDay(String freightCommitDay) {
    this.freightCommitDay = freightCommitDay;
  }

  public String getFundStatus() {
    return fundStatus;
  }

  public void setFundStatus(String fundStatus) {
    this.fundStatus = fundStatus;
  }

  public Integer getGoodsPrepareTime() {
    return goodsPrepareTime;
  }

  public void setGoodsPrepareTime(Integer goodsPrepareTime) {
    this.goodsPrepareTime = goodsPrepareTime;
  }

  public String getIssueMode() {
    return issueMode;
  }

  public void setIssueMode(String issueMode) {
    this.issueMode = issueMode;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public String getLogisticsServiceName() {
    return logisticsServiceName;
  }

  public void setLogisticsServiceName(String logisticsServiceName) {
    this.logisticsServiceName = logisticsServiceName;
  }

  public String getLogisticsType() {
    return logisticsType;
  }

  public void setLogisticsType(String logisticsType) {
    this.logisticsType = logisticsType;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Boolean getMoneyBack3x() {
    return moneyBack3x;
  }

  public void setMoneyBack3x(Boolean moneyBack3x) {
    this.moneyBack3x = moneyBack3x;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Integer getProductCount() {
    return productCount;
  }

  public void setProductCount(Integer productCount) {
    this.productCount = productCount;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductImgUrl() {
    return productImgUrl;
  }

  public void setProductImgUrl(String productImgUrl) {
    this.productImgUrl = productImgUrl;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductSnapUrl() {
    return productSnapUrl;
  }

  public void setProductSnapUrl(String productSnapUrl) {
    this.productSnapUrl = productSnapUrl;
  }

  public String getProductStandard() {
    return productStandard;
  }

  public void setProductStandard(String productStandard) {
    this.productStandard = productStandard;
  }

  public String getProductUnit() {
    return productUnit;
  }

  public void setProductUnit(String productUnit) {
    this.productUnit = productUnit;
  }

  public String getSellerSignerFirstName() {
    return sellerSignerFirstName;
  }

  public void setSellerSignerFirstName(String sellerSignerFirstName) {
    this.sellerSignerFirstName = sellerSignerFirstName;
  }

  public String getSellerSignerLastName() {
    return sellerSignerLastName;
  }

  public void setSellerSignerLastName(String sellerSignerLastName) {
    this.sellerSignerLastName = sellerSignerLastName;
  }

  public String getSendGoodsTime() {
    return sendGoodsTime;
  }

  public void setSendGoodsTime(String sendGoodsTime) {
    this.sendGoodsTime = sendGoodsTime;
  }

  public String getShowStatus() {
    return showStatus;
  }

  public void setShowStatus(String showStatus) {
    this.showStatus = showStatus;
  }

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public String getSonOrderStatus() {
    return sonOrderStatus;
  }

  public void setSonOrderStatus(String sonOrderStatus) {
    this.sonOrderStatus = sonOrderStatus;
  }

  public Money getLogisticsAmount() {
    return logisticsAmount;
  }

  public void setLogisticsAmount(Money logisticsAmount) {
    this.logisticsAmount = logisticsAmount;
  }

  public Money getProductUnitPrice() {
    return productUnitPrice;
  }

  public void setProductUnitPrice(Money productUnitPrice) {
    this.productUnitPrice = productUnitPrice;
  }

  public Money getTotalProductAmount() {
    return totalProductAmount;
  }

  public void setTotalProductAmount(Money totalProductAmount) {
    this.totalProductAmount = totalProductAmount;
  }


}
