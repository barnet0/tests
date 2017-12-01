package com.digiwin.ecims.platforms.kaola.util.translator;

import java.util.List;

import com.digiwin.ecims.core.util.SkuColorSizeAttribute;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Raw_property_name;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Sku_property_list;

public class KaolaSkuColorSizeAttribute extends SkuColorSizeAttribute {
	private List<Sku_property_list> sku_property_list;

	public KaolaSkuColorSizeAttribute(List<Sku_property_list> sku_property_list) {
		super();
		this.sku_property_list = sku_property_list;
	}

	public KaolaSkuColorSizeAttribute() {

	}

	public List<Sku_property_list> getSku_property_list() {
		return sku_property_list;
	}

	public void setSku_property_list(List<Sku_property_list> sku_property_list) {
		this.sku_property_list = sku_property_list;
	}
	/*
	 * public KaolaSkuColorSizeAttribute(String color, String size) {
	 * super(color, size); // TODO Auto-generated constructor stub }
	 */

	/*
	 * @Override public String toString() { if (color == null || color.length()
	 * == 0) { return size; } if (size == null || size.length() == 0) { return
	 * color; } return color + ";" + size; }
	 */

	public String getKaolaSkuColor() {
		String property_value = "";
		for (Sku_property_list skuProperty : sku_property_list) {
			Raw_property_name rawPropertyName = skuProperty.getProperty_name().getRaw_property_name();
			String prop_name_cn = rawPropertyName.getProp_name_cn();
			if (prop_name_cn.equals("颜色")) {
				property_value = skuProperty.getProperty_value().getProperty_value();
				break;
			}
		}
		return property_value;
	}

	public String getKaolaSkuSize() {
		String property_value = "";
		for (Sku_property_list skuProperty : sku_property_list) {
			Raw_property_name rawPropertyName = skuProperty.getProperty_name().getRaw_property_name();
			String prop_name_cn = rawPropertyName.getProp_name_cn();
			if (prop_name_cn.contains("尺寸")||prop_name_cn.contains("尺码")) {
				property_value = skuProperty.getProperty_value().getProperty_value();
				break;
			}
		}
		return property_value;
	}
	
	public String getKaolaSkuProperty() {
		String property="";
		String prop_name_cn="";
		for (Sku_property_list skuProperty : sku_property_list) {
			Raw_property_name rawPropertyName = skuProperty.getProperty_name().getRaw_property_name();
			prop_name_cn = rawPropertyName.getProp_name_cn();
			if (prop_name_cn.length()>0) {
				property =property+prop_name_cn+":"+skuProperty.getProperty_value().getProperty_value()+";";
			}
		}
		return property;
	}
}
