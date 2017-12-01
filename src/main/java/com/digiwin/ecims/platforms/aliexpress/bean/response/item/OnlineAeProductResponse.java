package com.digiwin.ecims.platforms.aliexpress.bean.response.item;

import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class OnlineAeProductResponse extends AliExpressBaseResponse {

  // 是 操作成功返回成功产品个数。 5
  private Integer modifyCount;

  // 是 接口调用结果。成功为true, 失败为false。 true
  private Boolean success;

  public Integer getModifyCount() {
    return modifyCount;
  }

  public void setModifyCount(Integer modifyCount) {
    this.modifyCount = modifyCount;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }


}
