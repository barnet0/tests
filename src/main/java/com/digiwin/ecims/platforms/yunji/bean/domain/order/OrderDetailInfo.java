package com.digiwin.ecims.platforms.yunji.bean.domain.order;

public class OrderDetailInfo {
	private int qty;
	private String id;
	private String tradeOrderDetailId;
	private String skuNo;
	private String barCode;
	private int returnStatus;
	private String name;
	private int itemPrice;
	private int totalPrice;
	private String description;
	private String unit;
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeOrderDetailId() {
		return tradeOrderDetailId;
	}
	public void setTradeOrderDetailId(String tradeOrderDetailId) {
		this.tradeOrderDetailId = tradeOrderDetailId;
	}
	public String getSkuNo() {
		return skuNo;
	}
	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public int getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
