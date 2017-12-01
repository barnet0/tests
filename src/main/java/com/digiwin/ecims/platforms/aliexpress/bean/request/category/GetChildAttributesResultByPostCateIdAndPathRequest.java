package com.digiwin.ecims.platforms.aliexpress.bean.request.category;

import java.util.HashMap;
import java.util.Map;

import antlr.collections.List;
import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.category.GetChildAttributesResultByPostCateIdAndPathResponse;


public class GetChildAttributesResultByPostCateIdAndPathRequest extends AliExpressBaseRequest<GetChildAttributesResultByPostCateIdAndPathResponse> {

  private Integer cateId;

  public Integer getCateId() { 
    return cateId;
  }

  public void setCateId(Integer cateId) {
    this.cateId = cateId;
  }

  @Override
  protected String getApiName() {
    return "api.getAttributesResultByCateId";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParamMap = new HashMap<String, String>();
    apiParamMap.put("cateId", getCateId() + "");
    apiParamMap.put("parentAttrValueList", "[[5090301,81]]");
    
    return apiParamMap;
  }

  @Override
  public Class<GetChildAttributesResultByPostCateIdAndPathResponse> getResponseClass() {
    return GetChildAttributesResultByPostCateIdAndPathResponse.class;
  }

}
