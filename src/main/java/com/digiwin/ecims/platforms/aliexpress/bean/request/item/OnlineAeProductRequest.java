package com.digiwin.ecims.platforms.aliexpress.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.OnlineAeProductResponse;

public class OnlineAeProductRequest extends AliExpressBaseRequest<OnlineAeProductResponse> {

  // 是 需要上架的产品id列表。可输入多个，之前用半角分号分割。
  private String productIds;

  public String getProductIds() {
    return productIds;
  }

  public void setProductIds(String productIds) {
    this.productIds = productIds;
  }

  @Override
  protected String getApiName() {
    return "api.onlineAeProduct";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getProductIds() != null) {
      apiParams.put("productIds", getProductIds());
    }
    return apiParams;
  }

  @Override
  public Class<OnlineAeProductResponse> getResponseClass() {
    return OnlineAeProductResponse.class;
  }

}
