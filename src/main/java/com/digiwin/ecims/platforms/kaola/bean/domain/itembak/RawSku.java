package com.digiwin.ecims.platforms.kaola.bean.domain.itembak;

import java.math.BigDecimal;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public class RawSku {
	private long id;
	private long item_id;
	private long business_id;
	private BigDecimal market_price;
	private BigDecimal sale_price;
	private String bar_code;
	private String material_num;
	private int stock_can_sale;
	private int stock_freeze;
	
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
	public BigDecimal getMarket_price() {
		return market_price;
	}
	public void setMarket_price(BigDecimal market_price) {
		this.market_price = market_price;
	}
	public BigDecimal getSale_price() {
		return sale_price;
	}
	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}
	public String getMaterial_num() {
		return material_num;
	}
	public void setMaterial_num(String material_num) {
		this.material_num = material_num;
	}
	public int getStock_can_sale() {
		return stock_can_sale;
	}
	public void setStock_can_sale(int stock_can_sale) {
		this.stock_can_sale = stock_can_sale;
	}
	public int getStock_freeze() {
		return stock_freeze;
	}
	public void setStock_freeze(int stock_freeze) {
		this.stock_freeze = stock_freeze;
	}
	
}
