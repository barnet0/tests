package com.digiwin.ecims.platforms.pdd2.bean.domain.order;

import java.util.List;

/**
 * 订单单头
 * @author cjp
 *
 */
public class OrderInfo {
	private String order_sn;

	private String confirm_time;

	private String receiver_name;

	private String created_time;

	private String country;

	private String province;

	private String city;

	private String town;

	private String address;

	private String receiver_phone;

	private double pay_amount;

	private double goods_amount;

	private double discount_amount;

	private int postage;

	private String pay_no;

	private String pay_type;

	private String id_card_num;

	private String id_card_name;

	private int logistics_id;

	private String tracking_number;

	private String shipping_time;

	private Integer order_status;

	private int is_lucky_flag;

	private int refund_status;

	private String updated_at;

	private String last_ship_time;

	private String remark;

	private List<ItemList> item_list ;

	private double platform_discount;

	private double seller_discount;

	private double capital_free_discount;

	public void setOrder_sn(String order_sn){
	this.order_sn = order_sn;
	}
	public String getOrder_sn(){
	return this.order_sn;
	}
	public void setConfirm_time(String confirm_time){
	this.confirm_time = confirm_time;
	}
	public String getConfirm_time(){
	return this.confirm_time;
	}
	public void setReceiver_name(String receiver_name){
	this.receiver_name = receiver_name;
	}
	public String getReceiver_name(){
	return this.receiver_name;
	}
	public void setCreated_time(String created_time){
	this.created_time = created_time;
	}
	public String getCreated_time(){
	return this.created_time;
	}
	public void setCountry(String country){
	this.country = country;
	}
	public String getCountry(){
	return this.country;
	}
	public void setProvince(String province){
	this.province = province;
	}
	public String getProvince(){
	return this.province;
	}
	public void setCity(String city){
	this.city = city;
	}
	public String getCity(){
	return this.city;
	}
	public void setTown(String town){
	this.town = town;
	}
	public String getTown(){
	return this.town;
	}
	public void setAddress(String address){
	this.address = address;
	}
	public String getAddress(){
	return this.address;
	}
	public void setReceiver_phone(String receiver_phone){
	this.receiver_phone = receiver_phone;
	}
	public String getReceiver_phone(){
	return this.receiver_phone;
	}
	public void setPay_amount(double pay_amount){
	this.pay_amount = pay_amount;
	}
	public double getPay_amount(){
	return this.pay_amount;
	}
	public void setGoods_amount(double goods_amount){
	this.goods_amount = goods_amount;
	}
	public double getGoods_amount(){
	return this.goods_amount;
	}
	public void setDiscount_amount(double discount_amount){
	this.discount_amount = discount_amount;
	}
	public double getDiscount_amount(){
	return this.discount_amount;
	}
	public void setPostage(int postage){
	this.postage = postage;
	}
	public int getPostage(){
	return this.postage;
	}
	public void setPay_no(String pay_no){
	this.pay_no = pay_no;
	}
	public String getPay_no(){
	return this.pay_no;
	}
	public void setPay_type(String pay_type){
	this.pay_type = pay_type;
	}
	public String getPay_type(){
	return this.pay_type;
	}
	public void setId_card_num(String id_card_num){
	this.id_card_num = id_card_num;
	}
	public String getId_card_num(){
	return this.id_card_num;
	}
	public void setId_card_name(String id_card_name){
	this.id_card_name = id_card_name;
	}
	public String getId_card_name(){
	return this.id_card_name;
	}
	public void setLogistics_id(int logistics_id){
	this.logistics_id = logistics_id;
	}
	public int getLogistics_id(){
	return this.logistics_id;
	}
	public void setTracking_number(String tracking_number){
	this.tracking_number = tracking_number;
	}
	public String getTracking_number(){
	return this.tracking_number;
	}
	public void setShipping_time(String shipping_time){
	this.shipping_time = shipping_time;
	}
	public String getShipping_time(){
	return this.shipping_time;
	}
	public void setOrder_status(int order_status){
	this.order_status = order_status;
	}
	public Integer getOrder_status(){
	return this.order_status;
	}
	public void setIs_lucky_flag(int is_lucky_flag){
	this.is_lucky_flag = is_lucky_flag;
	}
	public int getIs_lucky_flag(){
	return this.is_lucky_flag;
	}
	public void setRefund_status(int refund_status){
	this.refund_status = refund_status;
	}
	public int getRefund_status(){
	return this.refund_status;
	}
	public void setUpdated_at(String updated_at){
	this.updated_at = updated_at;
	}
	public String getUpdated_at(){
	return this.updated_at;
	}
	public void setLast_ship_time(String last_ship_time){
	this.last_ship_time = last_ship_time;
	}
	public String getLast_ship_time(){
	return this.last_ship_time;
	}
	public void setRemark(String remark){
	this.remark = remark;
	}
	public String getRemark(){
	return this.remark;
	}
	public void setItem_list(List<ItemList> item_list){
	this.item_list = item_list;
	}
	public List<ItemList> getItem_list(){
	return this.item_list;
	}
	public void setPlatform_discount(double platform_discount){
	this.platform_discount = platform_discount;
	}
	public double getPlatform_discount(){
	return this.platform_discount;
	}
	public void setSeller_discount(double seller_discount){
	this.seller_discount = seller_discount;
	}
	public double getSeller_discount(){
	return this.seller_discount;
	}
	public void setCapital_free_discount(double capital_free_discount){
	this.capital_free_discount = capital_free_discount;
	}
	public double getCapital_free_discount(){
	return this.capital_free_discount;
	}

}
