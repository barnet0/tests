package com.digiwin.ecims.platforms.kaola.bean.request.item;


import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSkuUpdateResponse;

/**
 * 
 * @author cjp
 *
 */
public class ItemSkuStockUpdateRequest extends KaolaBaseRequest<ItemSkuUpdateResponse> {
	 private String key;
	 private int stock;
	 
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public Map<String, String> getApiParams() {
		 Map<String, String> apiParams = new HashMap<String, String>();
		    apiParams.put("key", getKey());
		    apiParams.put("stock", getStock().toString());
		    return apiParams;		
	}

	@Override
	public String getMType() {
		return "kaola.item.sku.stock.update";
	}

	@Override
	public Class<ItemSkuUpdateResponse> getResponseClass() {
		return ItemSkuUpdateResponse.class;
	}

}
