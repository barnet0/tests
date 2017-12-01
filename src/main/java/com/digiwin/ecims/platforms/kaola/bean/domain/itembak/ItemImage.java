package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class ItemImage {
	private long id;
	private long item_id;
	private long biusiness_id;
	private String image_url;
	//private Enum image_type;
	private String image_type;
	private int order_value;
	
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
	public long getBiusiness_id() {
		return biusiness_id;
	}
	public void setBiusiness_id(long biusiness_id) {
		this.biusiness_id = biusiness_id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	/*public Enum getImage_type() {
		return image_type;
	}
	public void setImage_type(Enum image_type) {
		this.image_type = image_type;
	}*/
	
	public String getImage_type() {
		return image_type;
	}
	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}
	public int getOrder_value() {
		return order_value;
	}
	public void setOrder_value(int order_value) {
		this.order_value = order_value;
	}
	
	
	
}
