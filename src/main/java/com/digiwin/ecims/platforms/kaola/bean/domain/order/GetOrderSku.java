package com.digiwin.ecims.platforms.kaola.bean.domain.order;

public class GetOrderSku {
	private String sku_key;
	private String item_key;
	private String product_name;
	private String origin_price;
	private int count;
	private String real_totle_price;
	private String activity_totle_amount;
	private String goods_no;
	private String coupon_totle_amount;
	private String barcode;
	private String hs_key;
	private int warehouse_code;
	private String warehouse_name;
	private int order_serial_num;
	
	private String first_unit;
	private String first_unit_num;
	private int is_gift;
	
	private String second_unit;
	private String second_unit_num;
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getFirst_unit() {
		return first_unit;
	}
	public void setFirst_unit(String first_unit) {
		this.first_unit = first_unit;
	}
	public String getFirst_unit_num() {
		return first_unit_num;
	}
	public void setFirst_unit_num(String first_unit_num) {
		this.first_unit_num = first_unit_num;
	}
	public int getIs_gift() {
		return is_gift;
	}
	public void setIs_gift(int is_gift) {
		this.is_gift = is_gift;
	}
	public String getSecond_unit() {
		return second_unit;
	}
	public void setSecond_unit(String second_unit) {
		this.second_unit = second_unit;
	}
	public String getSecond_unit_num() {
		return second_unit_num;
	}
	public void setSecond_unit_num(String second_unit_num) {
		this.second_unit_num = second_unit_num;
	}
	public String getSku_key() {
		return sku_key;
	}
	public void setSku_key(String sku_key) {
		this.sku_key = sku_key;
	}
	public String getItem_key() {
		return item_key;
	}
	public void setItem_key(String item_key) {
		this.item_key = item_key;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getOrigin_price() {
		return origin_price;
	}
	public void setOrigin_price(String origin_price) {
		this.origin_price = origin_price;
	}
//	public int getCount() {
//		return count;
//	}
//	public void setCount(int count) {
//		this.count = count;
//	}
	public String getReal_totle_price() {
		return real_totle_price;
	}
	public void setReal_totle_price(String real_totle_price) {
		this.real_totle_price = real_totle_price;
	}
	public String getActivity_totle_amount() {
		return activity_totle_amount;
	}
	public void setActivity_totle_amount(String activity_totle_amount) {
		this.activity_totle_amount = activity_totle_amount;
	}
	public String getGoods_no() {
		return goods_no;
	}
	public void setGoods_no(String goods_no) {
		this.goods_no = goods_no;
	}
	public String getCoupon_totle_amount() {
		return coupon_totle_amount;
	}
	public void setCoupon_totle_amount(String coupon_totle_amount) {
		this.coupon_totle_amount = coupon_totle_amount;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getHs_key() {
		return hs_key;
	}
	public void setHs_key(String hs_key) {
		this.hs_key = hs_key;
	}
	public int getWarehouse_code() {
		return warehouse_code;
	}
	public void setWarehouse_code(int warehouse_code) {
		this.warehouse_code = warehouse_code;
	}
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public int getOrder_serial_num() {
		return order_serial_num;
	}
	public void setOrder_serial_num(int order_serial_num) {
		this.order_serial_num = order_serial_num;
	}
	
}
