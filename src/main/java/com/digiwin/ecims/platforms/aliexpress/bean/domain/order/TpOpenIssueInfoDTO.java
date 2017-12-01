package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 纠纷信息
 * 
 * @author 维杰
 *
 */
public class TpOpenIssueInfoDTO {

  // 否 纠纷类型
  private String issueModel;

  // 否 纠纷状态
  private String issueStatus;

  // 否 纠纷原因ID
  private String reasonCategoryId;

  public String getIssueModel() {
    return issueModel;
  }

  public void setIssueModel(String issueModel) {
    this.issueModel = issueModel;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public String getReasonCategoryId() {
    return reasonCategoryId;
  }

  public void setReasonCategoryId(String reasonCategoryId) {
    this.reasonCategoryId = reasonCategoryId;
  }


}
