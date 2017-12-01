package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Property_name {
	private Property_edit_policy property_edit_policy;

	private Raw_property_name raw_property_name;

	public void setProperty_edit_policy(Property_edit_policy property_edit_policy){
	this.property_edit_policy = property_edit_policy;
	}
	public Property_edit_policy getProperty_edit_policy(){
	return this.property_edit_policy;
	}
	public void setRaw_property_name(Raw_property_name raw_property_name){
	this.raw_property_name = raw_property_name;
	}
	public Raw_property_name getRaw_property_name(){
	return this.raw_property_name;
	}
}
