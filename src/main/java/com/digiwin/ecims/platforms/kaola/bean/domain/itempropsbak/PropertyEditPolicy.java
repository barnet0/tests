package com.digiwin.ecims.platforms.kaola.bean.domain.itempropsbak;
/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class PropertyEditPolicy {
	private String property_name_id;
	//private int input_type;
	private String input_type;
	private String desc;
	private int max_len;
	private int is_multichoice;
	private int need_image;
	private int is_necessary;
	public String getProperty_name_id() {
		return property_name_id;
	}
	public void setProperty_name_id(String property_name_id) {
		this.property_name_id = property_name_id;
	}
	/*public int getInput_type() {
		return input_type;
	}
	public void setInput_type(int input_type) {
		this.input_type = input_type;
	}*/
	
	public String getInput_type() {
		return input_type;
	}
	public void setInput_type(String input_type) {
		this.input_type = input_type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getMax_len() {
		return max_len;
	}
	public void setMax_len(int max_len) {
		this.max_len = max_len;
	}
	public int getIs_multichoice() {
		return is_multichoice;
	}
	public void setIs_multichoice(int is_multichoice) {
		this.is_multichoice = is_multichoice;
	}
	public int getNeed_image() {
		return need_image;
	}
	public void setNeed_image(int need_image) {
		this.need_image = need_image;
	}
	public int getIs_necessary() {
		return is_necessary;
	}
	public void setIs_necessary(int is_necessary) {
		this.is_necessary = is_necessary;
	}
	
	
}
