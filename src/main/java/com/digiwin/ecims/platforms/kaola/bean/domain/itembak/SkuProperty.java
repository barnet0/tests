package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

import com.digiwin.ecims.platforms.kaola.bean.domain.itempropsbak.PropertyName;
import com.digiwin.ecims.platforms.kaola.bean.domain.itempropsbak.PropertyValue;
/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class SkuProperty {
	private  RawSkuProperty raw_sku_property;

	private PropertyName property_name;

	private PropertyValue property_value;

	public RawSkuProperty getRaw_sku_property() {
		return raw_sku_property;
	}

	public void setRaw_sku_property(RawSkuProperty raw_sku_property) {
		this.raw_sku_property = raw_sku_property;
	}

	public PropertyName getProperty_name() {
		return property_name;
	}

	public void setProperty_name(PropertyName property_name) {
		this.property_name = property_name;
	}

	public PropertyValue getProperty_value() {
		return property_value;
	}

	public void setProperty_value(PropertyValue property_value) {
		this.property_value = property_value;
	}
	
	

}
