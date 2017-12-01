package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class UpdateStockRequest extends BaiduBaseRequest {

  private SkuStock skuStock;
  
  public SkuStock getSkuStock() {
    return skuStock;
  }

  public void setSkuStock(SkuStock skuStock) {
    this.skuStock = skuStock;
  }

  @Override
  public String getUrlPath() {
    return "ItemService/updateStock";
  }

}
