package com.digiwin.ecims.platforms.aliexpress.bean.response.category;

import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.category.AeopAttributeDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;


public class GetChildAttributesResultByPostCateIdAndPathResponse extends AliExpressBaseResponse {

  private List<AeopAttributeDTO> attributes;
  
  private Boolean success;

  public List<AeopAttributeDTO> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<AeopAttributeDTO> attributes) {
    this.attributes = attributes;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

}
