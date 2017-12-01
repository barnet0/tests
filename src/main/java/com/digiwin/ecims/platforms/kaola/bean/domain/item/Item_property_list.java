package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Item_property_list {
	private Property_name property_name;

	private Property_value property_value;

	private Raw_item_property raw_item_property;

	public void setProperty_name(Property_name property_name){
	this.property_name = property_name;
	}
	public Property_name getProperty_name(){
	return this.property_name;
	}
	public void setProperty_value(Property_value property_value){
	this.property_value = property_value;
	}
	public Property_value getProperty_value(){
	return this.property_value;
	}
	public void setRaw_item_property(Raw_item_property raw_item_property){
	this.raw_item_property = raw_item_property;
	}
	public Raw_item_property getRaw_item_property(){
	return this.raw_item_property;
	}
}
