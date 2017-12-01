package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Raw_sku_property {
	private int business_id;

	private int id;

	private String image_url;

	private int is_hidden;

	private String property_value_id;

	private int sku_id;

	public void setBusiness_id(int business_id){
	this.business_id = business_id;
	}
	public int getBusiness_id(){
	return this.business_id;
	}
	public void setId(int id){
	this.id = id;
	}
	public int getId(){
	return this.id;
	}
	public void setImage_url(String image_url){
	this.image_url = image_url;
	}
	public String getImage_url(){
	return this.image_url;
	}
	public void setIs_hidden(int is_hidden){
	this.is_hidden = is_hidden;
	}
	public int getIs_hidden(){
	return this.is_hidden;
	}
	public void setProperty_value_id(String property_value_id){
	this.property_value_id = property_value_id;
	}
	public String getProperty_value_id(){
	return this.property_value_id;
	}
	public void setSku_id(int sku_id){
	this.sku_id = sku_id;
	}
	public int getSku_id(){
	return this.sku_id;
	}
}
