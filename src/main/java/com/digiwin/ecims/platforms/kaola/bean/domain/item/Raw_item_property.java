package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Raw_item_property {
	private int business_id;

	private int id;

	private int item_id;

	private String property_value_id;

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
	public void setItem_id(int item_id){
	this.item_id = item_id;
	}
	public int getItem_id(){
	return this.item_id;
	}
	public void setProperty_value_id(String property_value_id){
	this.property_value_id = property_value_id;
	}
	public String getProperty_value_id(){
	return this.property_value_id;
	}
}
