package com.digiwin.ecims.platforms.kaola.bean.domain.item;

import java.util.List;

public class Sku_list {
	private String key;

	private Raw_sku raw_sku;

	private int seq;

	//private List<Sku_custom_property_list> sku_custom_property_list ;
	
	//private List<String> sku_custom_property_list ;

	private List<Sku_property_list> sku_property_list ;

	public void setKey(String key){
	this.key = key;
	}
	public String getKey(){
	return this.key;
	}
	public void setRaw_sku(Raw_sku raw_sku){
	this.raw_sku = raw_sku;
	}
	public Raw_sku getRaw_sku(){
	return this.raw_sku;
	}
	public void setSeq(int seq){
	this.seq = seq;
	}
	public int getSeq(){
	return this.seq;
	}
/*	public void setSku_custom_property_list(List<Sku_custom_property_list> sku_custom_property_list){
	this.sku_custom_property_list = sku_custom_property_list;
	}
	public List<Sku_custom_property_list> getSku_custom_property_list(){
	return this.sku_custom_property_list;
	}*
	
	/*public void String(List<String> sku_custom_property_list){
	this.sku_custom_property_list = sku_custom_property_list;
	}
	public List<String> getSku_custom_property_list(){
	return this.sku_custom_property_list;
	}*/
	
	public void setSku_property_list(List<Sku_property_list> sku_property_list){
	this.sku_property_list = sku_property_list;
	}
	public List<Sku_property_list> getSku_property_list(){
	return this.sku_property_list;
	}
}
