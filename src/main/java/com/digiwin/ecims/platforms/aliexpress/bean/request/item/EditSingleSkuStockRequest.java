package com.digiwin.ecims.platforms.aliexpress.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.EditSingleSkuStockResponse;

public class EditSingleSkuStockRequest extends AliExpressBaseRequest<EditSingleSkuStockResponse> {

  // 是 需修改编辑的商品ID 32297192242
  private Long productId;
  // 是 需修改编辑的商品单个SKUID。SKU ID可以通过api.findAeProductById接口中的aeopAeproductSKUs获取单个产品信息中"id"进行获取。
  // 14:200003699;5:100014065
  private String skuId;

  // 是 SKU的库存值 299
  private Long ipmSkuStock;
  
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public Long getIpmSkuStock() {
    return ipmSkuStock;
  }

  public void setIpmSkuStock(Long ipmSkuStock) {
    this.ipmSkuStock = ipmSkuStock;
  }

  @Override
  protected String getApiName() {
    return "api.editSingleSkuStock";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getProductId() != null) {
      apiParams.put("productId", getProductId() + "");
    }
    if (getSkuId() != null) {
      apiParams.put("skuId", getSkuId());
    }
    if (getIpmSkuStock() != null) {
      apiParams.put("ipmSkuStock", getIpmSkuStock() + "");
    }
    
    return apiParams;
  }

  @Override
  public Class<EditSingleSkuStockResponse> getResponseClass() {
    return EditSingleSkuStockResponse.class;
  }

}
