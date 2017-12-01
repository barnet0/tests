package com.digiwin.ecims.platforms.aliexpress.bean.response.refund;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.refund.IssueAPIDetailDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class QueryIssueDetailResponse extends AliExpressBaseResponse {

  // 是 纠纷数据
  private IssueAPIDetailDTO data;

  // 是 是否成功 true
  private Boolean success;

  // 是 错误码，当success=false时有值 06100001
  private String code;

  // String 是 错误原因，当success=false时有值 参数错误
  private String msg;

  public IssueAPIDetailDTO getData() {
    return data;
  }

  public void setData(IssueAPIDetailDTO data) {
    this.data = data;
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
