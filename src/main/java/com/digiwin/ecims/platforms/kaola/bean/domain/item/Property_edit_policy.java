package com.digiwin.ecims.platforms.kaola.bean.domain.item;

public class Property_edit_policy {
	private String desc;

	private String input_type;

	private int is_multichoice;

	private int is_necessary;

	private int max_len;

	private int need_image;

	private String property_name_id;

	public void setDesc(String desc){
	this.desc = desc;
	}
	public String getDesc(){
	return this.desc;
	}
	public void setInput_type(String input_type){
	this.input_type = input_type;
	}
	public String getInput_type(){
	return this.input_type;
	}
	public void setIs_multichoice(int is_multichoice){
	this.is_multichoice = is_multichoice;
	}
	public int getIs_multichoice(){
	return this.is_multichoice;
	}
	public void setIs_necessary(int is_necessary){
	this.is_necessary = is_necessary;
	}
	public int getIs_necessary(){
	return this.is_necessary;
	}
	public void setMax_len(int max_len){
	this.max_len = max_len;
	}
	public int getMax_len(){
	return this.max_len;
	}
	public void setNeed_image(int need_image){
	this.need_image = need_image;
	}
	public int getNeed_image(){
	return this.need_image;
	}
	public void setProperty_name_id(String property_name_id){
	this.property_name_id = property_name_id;
	}
	public String getProperty_name_id(){
	return this.property_name_id;
	}

}
