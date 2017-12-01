package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class ItemTextProperty {
	private long id;
	private long item_id;
	private long business_id;
	private String prop_name_id;
	private String propn_name_cn;
	private String text_value;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public long getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(long business_id) {
		this.business_id = business_id;
	}
	public String getProp_name_id() {
		return prop_name_id;
	}
	public void setProp_name_id(String prop_name_id) {
		this.prop_name_id = prop_name_id;
	}
	public String getPropn_name_cn() {
		return propn_name_cn;
	}
	public void setPropn_name_cn(String propn_name_cn) {
		this.propn_name_cn = propn_name_cn;
	}
	public String getText_value() {
		return text_value;
	}
	public void setText_value(String text_value) {
		this.text_value = text_value;
	}
	
	

}
