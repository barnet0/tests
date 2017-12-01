package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 子订单信息
 * 
 * @author 维杰
 *
 */
public class TpOpenChildOrderDTO {

  // 否 冻结状态("NO_FROZEN"无冻结；"IN_FROZEN"冻结中；) NO_FROZEN
  private String frozenStatus;

  // 否 资金状态(NOT_PAY,未付款； PAY_SUCCESS,付款成功； WAIT_SELLER_CHECK，卖家验款) NOT_PAY
  private String fundStatus;

  // 否 子订单ID
  private Long id;

  // 否 纠纷状态("NO_ISSUE"无纠纷；"IN_ISSUE"纠纷中；“END_ISSUE”纠纷结束。)
  // frozenStatus:冻结状态("NO_FROZEN"无冻结；"IN_FROZEN"冻结中；) NO_ISSUE
  private String issueStatus;

  // 否 lot数量 1
  private Integer lotNum;

  // 否 子订单状态 PLACE_ORDER_SUCCESS
  private String orderStatus;

  // 否 商品属性
  private String productAttributes;

  // 否 商品数量
  private Integer productCount;

  // 否 商品ID
  private Long productId;

  // 否 商品标题
  private String productName;

  // 否 产品规格
  private String productStandard;

  // 否 产品单位
  private String productUnit;

  // 否 SKU编码
  private String skuCode;

  // 否 订单原始总金额
  private Money initOrderAmt;

  private Money productPrice;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public Integer getLotNum() {
    return lotNum;
  }

  public void setLotNum(Integer lotNum) {
    this.lotNum = lotNum;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getProductAttributes() {
    return productAttributes;
  }

  public void setProductAttributes(String productAttributes) {
    this.productAttributes = productAttributes;
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

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
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

  public String getSkuCode() {
    return skuCode;
  }

  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  public Money getInitOrderAmt() {
    return initOrderAmt;
  }

  public void setInitOrderAmt(Money initOrderAmt) {
    this.initOrderAmt = initOrderAmt;
  }

  public Money getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(Money productPrice) {
    this.productPrice = productPrice;
  }

  
}
