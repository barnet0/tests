package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Property_value {
	private int is_sys_property;

	private String property_name_id;

	private String property_value;

	private String property_value_icon;

	private String property_value_id;

	private int show_order;

	private int status;

	public void setIs_sys_property(int is_sys_property){
	this.is_sys_property = is_sys_property;
	}
	public int getIs_sys_property(){
	return this.is_sys_property;
	}
	public void setProperty_name_id(String property_name_id){
	this.property_name_id = property_name_id;
	}
	public String getProperty_name_id(){
	return this.property_name_id;
	}
	public void setProperty_value(String property_value){
	this.property_value = property_value;
	}
	public String getProperty_value(){
	return this.property_value;
	}
	public void setProperty_value_icon(String property_value_icon){
	this.property_value_icon = property_value_icon;
	}
	public String getProperty_value_icon(){
	return this.property_value_icon;
	}
	public void setProperty_value_id(String property_value_id){
	this.property_value_id = property_value_id;
	}
	public String getProperty_value_id(){
	return this.property_value_id;
	}
	public void setShow_order(int show_order){
	this.show_order = show_order;
	}
	public int getShow_order(){
	return this.show_order;
	}
	public void setStatus(int status){
	this.status = status;
	}
	public int getStatus(){
	return this.status;
	}
}
