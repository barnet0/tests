package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Item_text_property_list {
	private int business_id;

	private int id;

	private int item_id;

	private String prop_name_id;

	private String propn_name_cn;

	private String text_value;

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
	public void setProp_name_id(String prop_name_id){
	this.prop_name_id = prop_name_id;
	}
	public String getProp_name_id(){
	return this.prop_name_id;
	}
	public void setPropn_name_cn(String propn_name_cn){
	this.propn_name_cn = propn_name_cn;
	}
	public String getPropn_name_cn(){
	return this.propn_name_cn;
	}
	public void setText_value(String text_value){
	this.text_value = text_value;
	}
	public String getText_value(){
	return this.text_value;
	}
}
