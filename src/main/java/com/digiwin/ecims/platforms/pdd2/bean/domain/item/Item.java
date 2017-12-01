package com.digiwin.ecims.platforms.pdd2.bean.domain.item;

import java.util.List;

public class Item {

	private int goods_id;
	private String goods_name;
	private String image_url;
	private int is_more_sku;
	private int goods_quantity;
	private int is_onsale;
	private List<ItemSku> sku_list;

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setIs_more_sku(int is_more_sku) {
		this.is_more_sku = is_more_sku;
	}

	public int getIs_more_sku() {
		return is_more_sku;
	}

	public void setGoods_quantity(int goods_quantity) {
		this.goods_quantity = goods_quantity;
	}

	public int getGoods_quantity() {
		return goods_quantity;
	}

	public void setIs_onsale(int is_onsale) {
		this.is_onsale = is_onsale;
	}

	public int getIs_onsale() {
		return is_onsale;
	}

	public void setSku_list(List<ItemSku> sku_list) {
		this.sku_list = sku_list;
	}

	public List<ItemSku> getSku_list() {
		return sku_list;
	}

}
