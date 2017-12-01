package com.digiwin.ecims.platforms.aliexpress.bean.request.refund;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.refund.QueryIssueDetailResponse;

public class QueryIssueDetailRequest extends AliExpressBaseRequest<QueryIssueDetailResponse> {

//  是   纠纷ID    60740200006352
  private Long issueId;
  
  public Long getIssueId() {
    return issueId;
  }

  public void setIssueId(Long issueId) {
    this.issueId = issueId;
  }

  @Override
  protected String getApiName() {
    return "api.queryIssueDetail";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getIssueId() != null) {
      apiParams.put("issueId", getIssueId() + "");
    }
    
    return apiParams;
  }

  @Override
  public Class<QueryIssueDetailResponse> getResponseClass() {
    return QueryIssueDetailResponse.class;
  }

}
