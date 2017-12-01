package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

import java.util.List;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class Sku {

	private RawSku raw_sku;
	private List<SkuProperty> sku_property_list;
	private String key;
	
	public RawSku getRaw_sku() {
		return raw_sku;
	}
	public void setRaw_sku(RawSku raw_sku) {
		this.raw_sku = raw_sku;
	}
	public List<SkuProperty> getSku_property_list() {
		return sku_property_list;
	}
	public void setSku_property_list(List<SkuProperty> sku_property_list) {
		this.sku_property_list = sku_property_list;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


}
