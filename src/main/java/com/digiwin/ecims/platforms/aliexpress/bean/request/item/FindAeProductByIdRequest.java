package com.digiwin.ecims.platforms.aliexpress.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindAeProductByIdResponse;

public class FindAeProductByIdRequest extends AliExpressBaseRequest<FindAeProductByIdResponse> {

  // 是 商品ID 1307422965
  private Long productId;
  
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  protected String getApiName() {
    return "api.findAeProductById";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getProductId() != null) {
      apiParams.put("productId", getProductId() + "");
    }
    return apiParams;
  }

  @Override
  public Class<FindAeProductByIdResponse> getResponseClass() {
    return FindAeProductByIdResponse.class;
  }

}
