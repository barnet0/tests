package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class RawItemProperty {
	private long id;
	private long item_id;
	private long business_id;
	private String property_value_id;

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

	public String getProperty_value_id() {
		return property_value_id;
	}

	public void setProperty_value_id(String property_value_id) {
		this.property_value_id = property_value_id;
	}

}
