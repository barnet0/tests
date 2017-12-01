package com.digiwin.ecims.platforms.aliexpress.bean.domain.refund;

/**
 * 纠纷协商方案
 * 
 * @author 维杰
 *
 */
public class IssueRefundSuggestionDTO {

  // 否 退款金额本币 1120 RUB
  private Money issueMoney;

  // 否 退款金额美金 56 USD
  private Money issueMoneyPost;

  /**
   * 否 退款类型: 1.full_amount_refund 全额退款 2.part_amount_refund 部分退款 3.no_amount_refund 不退款
   * full_amount_refund
   */
  private String issueRefundType;

  // 否 是否退货 true
  private Boolean issueReturnGoods;

  // 否 是否默认方案 true
  private Boolean isDefault;

  public Money getIssueMoney() {
    return issueMoney;
  }

  public void setIssueMoney(Money issueMoney) {
    this.issueMoney = issueMoney;
  }

  public Money getIssueMoneyPost() {
    return issueMoneyPost;
  }

  public void setIssueMoneyPost(Money issueMoneyPost) {
    this.issueMoneyPost = issueMoneyPost;
  }

  public String getIssueRefundType() {
    return issueRefundType;
  }

  public void setIssueRefundType(String issueRefundType) {
    this.issueRefundType = issueRefundType;
  }

  public Boolean getIssueReturnGoods() {
    return issueReturnGoods;
  }

  public void setIssueReturnGoods(Boolean issueReturnGoods) {
    this.issueReturnGoods = issueReturnGoods;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }


}
