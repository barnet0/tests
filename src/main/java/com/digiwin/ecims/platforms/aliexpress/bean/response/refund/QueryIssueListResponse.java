package com.digiwin.ecims.platforms.aliexpress.bean.response.refund;

import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.refund.IssueAPIIssueDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class QueryIssueListResponse extends AliExpressBaseResponse {

  // 是 纠纷数据
  private List<IssueAPIIssueDTO> dataList;

  // 是 纠纷总数 14
  private Integer totalItem;

  // 是 每页条数 2
  private Integer pageSize;

  // 是 当前页数 1
  private Integer currentPage;

  // 是 是否成功 true
  private Boolean success;

  // 是 错误码，当success=false时有值 06100001
  private String code;

  // String 是 错误原因，当success=false时有值 参数错误
  private String msg;

  public List<IssueAPIIssueDTO> getDataList() {
    return dataList;
  }

  public void setDataList(List<IssueAPIIssueDTO> dataList) {
    this.dataList = dataList;
  }

  public Integer getTotalItem() {
    return totalItem;
  }

  public void setTotalItem(Integer totalItem) {
    this.totalItem = totalItem;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
