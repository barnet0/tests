package com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInfo {
	
	@XmlElement(name = "itemID")
	private String itemID;
	
	@XmlElement(name = "itemName")
	private String itemName;
	
	@XmlElement(name = "itemSubhead")
	private String itemSubhead;
	
	@XmlElement(name = "unitPrice")
	private String unitPrice;
	
	@XmlElement(name = "orderCount")
	private String orderCount;
	
	@XmlElement(name = "outerItemID")
	private String outerItemID;
	
	@XmlElement(name = "oneLevelReverseReason")
	private String oneLevelReverseReason;
	
	@XmlElement(name = "twoLevelReverseReason")
	private String twoLevelReverseReason;
	
	@XmlElement(name = "reverseDetailReason")
	private String reverseDetailReason;
	
	@XmlElement(name = "itemCode")
	private String itemCode;
	
	public ItemInfo() {
		
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSubhead() {
		return itemSubhead;
	}

	public void setItemSubhead(String itemSubhead) {
		this.itemSubhead = itemSubhead;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getOuterItemID() {
		return outerItemID;
	}

	public void setOuterItemID(String outerItemID) {
		this.outerItemID = outerItemID;
	}

	public String getOneLevelReverseReason() {
		return oneLevelReverseReason;
	}

	public void setOneLevelReverseReason(String oneLevelReverseReason) {
		this.oneLevelReverseReason = oneLevelReverseReason;
	}

	public String getTwoLevelReverseReason() {
		return twoLevelReverseReason;
	}

	public void setTwoLevelReverseReason(String twoLevelReverseReason) {
		this.twoLevelReverseReason = twoLevelReverseReason;
	}

	public String getReverseDetailReason() {
		return reverseDetailReason;
	}

	public void setReverseDetailReason(String reverseDetailReason) {
		this.reverseDetailReason = reverseDetailReason;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
		
}