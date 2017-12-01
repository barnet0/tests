package com.digiwin.ecims.platforms.aliexpress.bean.request.item;

import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.EditMutilpleSkuStocksResponse;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressApiParamMap;

public class EditMutilpleSkuStocksRequest extends AliExpressBaseRequest<EditMutilpleSkuStocksResponse> {

//  是   产品ID    32297192242
  private Long productId; 
  
//  是   SKU的库存信息(一个或着多个)    {"14:200003699;5:100014064":240, "14:200003699;5:361386": 220}
  private String skuStocks;
  
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getSkuStocks() {
    return skuStocks;
  }

  public void setSkuStocks(String skuStocks) {
    this.skuStocks = skuStocks;
  }

  @Override
  protected String getApiName() {
    return "api.editMutilpleSkuStocks";
  }

  @Override
  public Map<String, String> getApiParams() {
    AliexpressApiParamMap apiParam = new AliexpressApiParamMap();
    apiParam.put("productId", getProductId() + "");
    apiParam.put("skuStocks", getSkuStocks());
    
    return apiParam;
  }

  @Override
  public Class<EditMutilpleSkuStocksResponse> getResponseClass() {
    return EditMutilpleSkuStocksResponse.class;
  }

}
