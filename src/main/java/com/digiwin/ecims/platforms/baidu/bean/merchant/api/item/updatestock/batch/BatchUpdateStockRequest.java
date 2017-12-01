package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.batch;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.SkuStock;

public class BatchUpdateStockRequest extends BaiduBaseRequest {

  private List<SkuStock> skuStocks;

  public List<SkuStock> getSkuStocks() {
    return skuStocks;
  }

  public void setSkuStocks(List<SkuStock> skuStocks) {
    this.skuStocks = skuStocks;
  }

  @Override
  public String getUrlPath() {
    return "ItemService/batchUpdateStock";
  }

}
