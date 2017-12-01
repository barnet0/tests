package com.digiwin.ecims.platforms.aliexpress.bean.domain.refund;

import java.util.List;

/**
 * 纠纷信息
 * 
 * @author 维杰
 *
 */
public class IssueAPIIssueDTO {

  // 否 纠纷ID 680*************804
  private Long id;

  // 否 纠纷创建时间 20150714020749000-0700 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 纠纷修改时间 20150714021033000-0700 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtModified;

  // 否 子订单ID 680*************804
  private Long orderId;

  // 否 父订单ID 0
  private Long parentOrderId;

  // 否 纠纷状态 WAIT_SELLER_CONFIRM_REFUND 买家提起纠纷 SELLER_REFUSE_REFUND 卖家拒绝纠纷 ACCEPTISSUE 卖家接受纠纷
  // WAIT_BUYER_SEND_GOODS 等待买家发货 WAIT_SELLER_RECEIVE_GOODS 买家发货，等待卖家收货 ARBITRATING 仲裁中
  // SELLER_RESPONSE_ISSUE_TIMEOUT 卖家响应纠纷超时 WAIT_SELLER_CONFIRM_REFUND
  private String issueStatus;

  // 否 纠纷处理过程，只有detail接口展示
  private List<IssueAPIIssueProcessDTO> issueProcessDTOs;

  // 否 退款金额美金
  // {'amount':0.01,'cent':1,'currencyCode':'USD','centFactor':100,'currency':{'defaultFractionDigits':2,'currencyCode':'USD','symbol':'$'}
  private Money limitRefundAmount;

  // 否 退款金额本币
  // {'amount':0.1,'cent':10,'currencyCode':'RUB','centFactor':100,'currency':{'defaultFractionDigits':2,'currencyCode':'RUB','symbol':'RUB'}
  private Money limitRefundLocalAmount;

  // 否 纠纷原因中文描述 产品数量不符
  private String reasonChinese;

  // 否 纠纷原因英文描述 Quantity shortage
  private String reasonEnglish;

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

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getParentOrderId() {
    return parentOrderId;
  }

  public void setParentOrderId(Long parentOrderId) {
    this.parentOrderId = parentOrderId;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public List<IssueAPIIssueProcessDTO> getIssueProcessDTOs() {
    return issueProcessDTOs;
  }

  public void setIssueProcessDTOs(List<IssueAPIIssueProcessDTO> issueProcessDTOs) {
    this.issueProcessDTOs = issueProcessDTOs;
  }

  public Money getLimitRefundAmount() {
    return limitRefundAmount;
  }

  public void setLimitRefundAmount(Money limitRefundAmount) {
    this.limitRefundAmount = limitRefundAmount;
  }

  public Money getLimitRefundLocalAmount() {
    return limitRefundLocalAmount;
  }

  public void setLimitRefundLocalAmount(Money limitRefundLocalAmount) {
    this.limitRefundLocalAmount = limitRefundLocalAmount;
  }

  public String getReasonChinese() {
    return reasonChinese;
  }

  public void setReasonChinese(String reasonChinese) {
    this.reasonChinese = reasonChinese;
  }

  public String getReasonEnglish() {
    return reasonEnglish;
  }

  public void setReasonEnglish(String reasonEnglish) {
    this.reasonEnglish = reasonEnglish;
  }


}
