package com.digiwin.ecims.platforms.pdd2.bean.domain.order;

/**
 * 订单单身
 * @author cjp
 *
 */
public class ItemList {
	private String goods_id;

	private String sku_id;

	private String outer_id;

	private String goods_name;

	private int goods_price;

	private String goods_spec;

	private int goods_count;

	private String goods_img;

	public void setGoods_id(String goods_id){
	this.goods_id = goods_id;
	}
	public String getGoods_id(){
	return this.goods_id;
	}
	public void setSku_id(String sku_id){
	this.sku_id = sku_id;
	}
	public String getSku_id(){
	return this.sku_id;
	}
	public void setOuter_id(String outer_id){
	this.outer_id = outer_id;
	}
	public String getOuter_id(){
	return this.outer_id;
	}
	public void setGoods_name(String goods_name){
	this.goods_name = goods_name;
	}
	public String getGoods_name(){
	return this.goods_name;
	}
	public void setGoods_price(int goods_price){
	this.goods_price = goods_price;
	}
	public int getGoods_price(){
	return this.goods_price;
	}
	public void setGoods_spec(String goods_spec){
	this.goods_spec = goods_spec;
	}
	public String getGoods_spec(){
	return this.goods_spec;
	}
	public void setGoods_count(int goods_count){
	this.goods_count = goods_count;
	}
	public int getGoods_count(){
	return this.goods_count;
	}
	public void setGoods_img(String goods_img){
	this.goods_img = goods_img;
	}
	public String getGoods_img(){
	return this.goods_img;
	}
}
