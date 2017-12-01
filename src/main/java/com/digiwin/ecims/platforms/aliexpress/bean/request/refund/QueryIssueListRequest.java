package com.digiwin.ecims.platforms.aliexpress.bean.request.refund;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.refund.QueryIssueListResponse;

public class QueryIssueListRequest extends AliExpressBaseRequest<QueryIssueListResponse> {

//  否   订单ID    1234567890
  private Long orderNo;  
  
//  否   买家名称
  private String buyerName;        
  
//  否   纠纷状态：WAIT_SELLER_CONFIRM_REFUND 买家提起纠纷,SELLER_REFUSE_REFUND 卖家拒绝纠,ACCEPTISSUE 卖家接受纠纷,WAIT_BUYER_SEND_GOODS 等待买家发货,WAIT_SELLER_RECEIVE_GOODS 买家发货，等待卖家收货,ARBITRATING 仲裁中,SELLER_RESPONSE_ISSUE_TIMEOUT 卖家响应纠纷超时  WAIT_SELLER_CONFIRM_REFUND
  private String issueStatus;  
  
//  是   当前页数    1
  private Integer currentPage;
  
  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  public String getBuyerName() {
    return buyerName;
  }

  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  public String getIssueStatus() {
    return issueStatus;
  }

  public void setIssueStatus(String issueStatus) {
    this.issueStatus = issueStatus;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  @Override
  protected String getApiName() {
    return "api.queryIssueList";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getOrderNo() != null) {
      apiParams.put("orderNo", getOrderNo() + "");
    }
    if (getBuyerName() != null) {
      apiParams.put("buyerName", getBuyerName());
    }
    if (getIssueStatus() != null) {
      apiParams.put("issueStatus", getIssueStatus());
    }
    if (getCurrentPage() != null) {
      apiParams.put("currentPage", getCurrentPage() + "");
    }
    
    return apiParams;
  }

  @Override
  public Class<QueryIssueListResponse> getResponseClass() {
    return QueryIssueListResponse.class;
  }

}
