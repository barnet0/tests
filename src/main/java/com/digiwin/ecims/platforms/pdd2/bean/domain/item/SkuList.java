package com.digiwin.ecims.platforms.pdd2.bean.domain.item;

public class SkuList {

	private String spec;
	private String sku_id;
	private String sku_quantity;
	private String outer_id;
	private String sku_img;
	private String group_price;
	private String single_price;

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getSpec() {
		return spec;
	}

	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}

	public String getSku_id() {
		return sku_id;
	}

	public void setSku_quantity(String sku_quantity) {
		this.sku_quantity = sku_quantity;
	}

	public String getSku_quantity() {
		return sku_quantity;
	}

	public void setOuter_id(String outer_id) {
		this.outer_id = outer_id;
	}

	public String getOuter_id() {
		return outer_id;
	}

	public void setSku_img(String sku_img) {
		this.sku_img = sku_img;
	}

	public String getSku_img() {
		return sku_img;
	}

	public void setGroup_price(String group_price) {
		this.group_price = group_price;
	}

	public String getGroup_price() {
		return group_price;
	}

	public void setSingle_price(String single_price) {
		this.single_price = single_price;
	}

	public String getSingle_price() {
		return single_price;
	}

}
