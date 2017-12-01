package com.digiwin.ecims.platforms.aliexpress.bean.domain.refund;

/**
 * 纠纷详情
 * 
 * @author 维杰
 *
 */
public class IssueAPIDetailDTO {

  // 否 交易快照 http://www.aliexpress.com/snapshot/3005612434.html?orderId=30***********804
  private String snapshotUrl;

  // 否 纠纷详情
  private IssueAPIIssueDTO issueAPIIssueDTO;

  public String getSnapshotUrl() {
    return snapshotUrl;
  }

  public void setSnapshotUrl(String snapshotUrl) {
    this.snapshotUrl = snapshotUrl;
  }

  public IssueAPIIssueDTO getIssueAPIIssueDTO() {
    return issueAPIIssueDTO;
  }

  public void setIssueAPIIssueDTO(IssueAPIIssueDTO issueAPIIssueDTO) {
    this.issueAPIIssueDTO = issueAPIIssueDTO;
  }
  
  
}
