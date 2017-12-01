package com.digiwin.ecims.platforms.kaola.bean.request.item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSkuUpdateResponse;

/**
 * 
 * @author cjp
 *
 */
public class ItemSkuSalePriceUpdateRequest extends KaolaBaseRequest<ItemSkuUpdateResponse> {
	 private String key;
	 private BigDecimal sale_price;
	 
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public BigDecimal getSale_price() {
		return sale_price;
	}

	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}

	@Override
	public Map<String, String> getApiParams() {
		 Map<String, String> apiParams = new HashMap<String, String>();
		    apiParams.put("key", getKey());
		    apiParams.put("sale_price", getSale_price().toString());
		    return apiParams;		
	}

	@Override
	public String getMType() {
		return "kaola.item.sku.sale.price.update";
	}

	@Override
	public Class<ItemSkuUpdateResponse> getResponseClass() {
		return ItemSkuUpdateResponse.class;
	}

}
