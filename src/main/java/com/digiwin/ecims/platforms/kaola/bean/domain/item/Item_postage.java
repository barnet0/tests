package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Item_postage {
	private int business_id;

	private double ems_fee;

	private double express_fee;

	private int id;

	private int is_postage_free;

	private int item_id;

	private double post_fee;

	private String postage_template_id;

	public void setBusiness_id(int business_id){
	this.business_id = business_id;
	}
	public int getBusiness_id(){
	return this.business_id;
	}
	public void setEms_fee(double ems_fee){
	this.ems_fee = ems_fee;
	}
	public double getEms_fee(){
	return this.ems_fee;
	}
	public void setExpress_fee(double express_fee){
	this.express_fee = express_fee;
	}
	public double getExpress_fee(){
	return this.express_fee;
	}
	public void setId(int id){
	this.id = id;
	}
	public int getId(){
	return this.id;
	}
	public void setIs_postage_free(int is_postage_free){
	this.is_postage_free = is_postage_free;
	}
	public int getIs_postage_free(){
	return this.is_postage_free;
	}
	public void setItem_id(int item_id){
	this.item_id = item_id;
	}
	public int getItem_id(){
	return this.item_id;
	}
	public void setPost_fee(double post_fee){
	this.post_fee = post_fee;
	}
	public double getPost_fee(){
	return this.post_fee;
	}
	public void setPostage_template_id(String postage_template_id){
	this.postage_template_id = postage_template_id;
	}
	public String getPostage_template_id(){
	return this.postage_template_id;
	}
}
