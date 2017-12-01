package com.digiwin.ecims.platforms.aliexpress.bean.domain.item;

/**
 * 商品基本信息
 * 
 * @author 维杰
 *
 */
public class AeopAEProductDisplaySampleDTO {

  // 否 商品标题 knew odd
  private String subject;

  // 否 商品分组id 123
  private Integer groupId;

  // 否 下架时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String wsOfflineDate;

  // 否 商品ID
  private Long productId;

  // 否 图片URL.静态单图主图个数为1,动态多图主图个数为2-6. 多个图片url用‘;’分隔符连接。
  private String imageURLs;

  // 否 产品来源。'tdx'为淘宝代销产品，isv为通过API发布的商品。其他字符或空为普通产品。
  private String src;

  // 否 产品发布时间。 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 商品最后更新时间（系统更新时间也会记录）。 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtModified;

  // 否 最小价格。
  private String productMinPrice;

  // 否 最大价格。
  private String productMaxPrice;

  // 否 商品所属人loginId
  private String ownerMemberId;

  // 否 商品所属人Seq
  private Integer ownerMemberSeq;

  /**
   * 否 商品下架原因 expire_offline：过期下架，user_offline：用户下架,violate_offline：违规下架,
   * punish_offline：交易违规下架，degrade_offline：降级下架
   */
  private String wsDisplay;

  // 否 货币单位 USD;RUB
  private String currencyCode;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public String getWsOfflineDate() {
    return wsOfflineDate;
  }

  public void setWsOfflineDate(String wsOfflineDate) {
    this.wsOfflineDate = wsOfflineDate;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getImageURLs() {
    return imageURLs;
  }

  public void setImageURLs(String imageURLs) {
    this.imageURLs = imageURLs;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
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

  public String getProductMinPrice() {
    return productMinPrice;
  }

  public void setProductMinPrice(String productMinPrice) {
    this.productMinPrice = productMinPrice;
  }

  public String getProductMaxPrice() {
    return productMaxPrice;
  }

  public void setProductMaxPrice(String productMaxPrice) {
    this.productMaxPrice = productMaxPrice;
  }

  public String getOwnerMemberId() {
    return ownerMemberId;
  }

  public void setOwnerMemberId(String ownerMemberId) {
    this.ownerMemberId = ownerMemberId;
  }

  public Integer getOwnerMemberSeq() {
    return ownerMemberSeq;
  }

  public void setOwnerMemberSeq(Integer ownerMemberSeq) {
    this.ownerMemberSeq = ownerMemberSeq;
  }

  public String getWsDisplay() {
    return wsDisplay;
  }

  public void setWsDisplay(String wsDisplay) {
    this.wsDisplay = wsDisplay;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }


}
