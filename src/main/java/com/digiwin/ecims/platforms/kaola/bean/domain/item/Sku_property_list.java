package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Sku_property_list {
	private Property_name property_name;

	private Property_value property_value;

	private Raw_sku_property raw_sku_property;

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
	public void setRaw_sku_property(Raw_sku_property raw_sku_property){
	this.raw_sku_property = raw_sku_property;
	}
	public Raw_sku_property getRaw_sku_property(){
	return this.raw_sku_property;
	}
}
