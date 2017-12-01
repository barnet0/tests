package com.digiwin.ecims.platforms.aliexpress.bean.domain.refund;

import java.util.List;

/**
 * 纠纷处理过程，只有detail接口展示
 * 
 * @author 维杰
 *
 */
public class IssueAPIIssueProcessDTO {

  // 否 过程id 680*************804
  private Long id;

  // 否 创建时间 20150714020749000-0700 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 修改时间 20150714020749000-0700 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtModified;

  // 否 纠纷id 680*************804
  private Long issueId;

  // 否 纠纷原因 Color_not_as_described@3rdIssueReason
  private String reason;

  // 否 纠纷描述 The produit don't turn one.
  private String content;

  // 否 退款金额本币
  // {'amount':0.1,'cent':10,'currencyCode':'RUB','centFactor':100,'currency':{'defaultFractionDigits':2,'currencyCode':'RUB','symbol':'RUB'}
  private Money refundAmount;

  // 否 退款金额美元
  // {'amount':0.01,'cent':1,'currencyCode':'USD','centFactor':100,'currency':{'defaultFractionDigits':2,'currencyCode':'USD','symbol':'$'}
  private Money refundConfirmAmount;

  // 否 操作 seller_accept
  private String actionType;

  // 否 操作人类型 seller 卖家 buyer 买家 system 系统 seller
  private String submitMemberType;

  // 否 附件列表 ['http://g02.a.alicdn.com/kf/UT8B.pjXtxbXXcUQpbXm.png']}]
  private List<String> attachments;

  // 否 是否收到货 Y N Y
  private String isReceivedGoods;

  // 否 视频列表 ['http://cloud.video.taobao.com/play/u/133146836577/p/1/e/1/t/1/d/hd/fv/27046845.swf']}]
  private List<String> videos;

  // 否 纠纷协商方案
  // {'isDefault':true,'issueMoney':{'amount':74.47,'cent':7447,'centFactor':100,'currency':{'currencyCode':'RUB','symbol':'RUB'},'currencyCode':'RUB'},'issueMoneyPost':{'amount':1.42,'cent':142,'centFactor':100,'currency':{'currencyCode':'USD','symbol':'$'},'currencyCode':'USD'},'issueRefundType':'full_amount_refund','issueReturnGoods':false}
  private List<IssueRefundSuggestionDTO> issueRefundSuggestionList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getIssueId() {
    return issueId;
  }

  public void setIssueId(Long issueId) {
    this.issueId = issueId;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Money getRefundAmount() {
    return refundAmount;
  }

  public void setRefundAmount(Money refundAmount) {
    this.refundAmount = refundAmount;
  }

  public Money getRefundConfirmAmount() {
    return refundConfirmAmount;
  }

  public void setRefundConfirmAmount(Money refundConfirmAmount) {
    this.refundConfirmAmount = refundConfirmAmount;
  }

  public String getActionType() {
    return actionType;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public String getSubmitMemberType() {
    return submitMemberType;
  }

  public void setSubmitMemberType(String submitMemberType) {
    this.submitMemberType = submitMemberType;
  }

  public List<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<String> attachments) {
    this.attachments = attachments;
  }

  public String getIsReceivedGoods() {
    return isReceivedGoods;
  }

  public void setIsReceivedGoods(String isReceivedGoods) {
    this.isReceivedGoods = isReceivedGoods;
  }

  public List<String> getVideos() {
    return videos;
  }

  public void setVideos(List<String> videos) {
    this.videos = videos;
  }

  public List<IssueRefundSuggestionDTO> getIssueRefundSuggestionList() {
    return issueRefundSuggestionList;
  }

  public void setIssueRefundSuggestionList(
      List<IssueRefundSuggestionDTO> issueRefundSuggestionList) {
    this.issueRefundSuggestionList = issueRefundSuggestionList;
  }


}
