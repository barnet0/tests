package com.digiwin.ecims.platforms.aliexpress.bean.response.item;

import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class EditMutilpleSkuStocksResponse extends AliExpressBaseResponse {

  private Integer modifyCount;
  
  private Boolean success;
  
  private Long productId;

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

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }
  
}
