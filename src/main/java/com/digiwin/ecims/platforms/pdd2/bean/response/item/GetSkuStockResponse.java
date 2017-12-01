package com.digiwin.ecims.platforms.pdd2.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.StockItem;

public class GetSkuStockResponse extends Pdd2BaseResponse {

  private List<StockItem> sku_stock_list;

/**
 * @return the sku_stock_list
 */
public List<StockItem> getSku_stock_list() {
	return sku_stock_list;
}

/**
 * @param sku_stock_list the sku_stock_list to set
 */
public void setSku_stock_list(List<StockItem> sku_stock_list) {
	this.sku_stock_list = sku_stock_list;
}


  
}
