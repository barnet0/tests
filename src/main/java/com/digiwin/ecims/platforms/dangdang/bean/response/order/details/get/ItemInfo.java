package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInfo {
	
	@XmlElement(name = "itemID")
	private String itemID;
	
	@XmlElement(name = "outerItemID")
	private String outerItemID;
	
	@XmlElement(name = "itemName")
	private String itemName;
	
	@XmlElement(name = "itemType")
	private String itemType;
	
	@XmlElement(name = "specialAttribute")
	private String specialAttribute;
	
	@XmlElement(name = "marketPrice")
	private String marketPrice;
	
	@XmlElement(name = "is_energySubsidy")
	private String is_energySubsidy;
	
	@XmlElement(name = "subsidyPrice")
	private String subsidyPrice;
	
	@XmlElement(name = "unitPrice")
	private String unitPrice;
	
	@XmlElement(name = "orderCount")
	private String orderCount;
	
	@XmlElement(name = "belongProductsPromoID")
	private String belongProductsPromoID;
	
	@XmlElement(name = "sendGoodsCount")
	private String sendGoodsCount;
	
	@XmlElement(name = "giftCardCharge")
	private String giftCardCharge;
	
	@XmlElement(name = "productItemId")
	private String productItemId;
	
	public ItemInfo() {
		
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getOuterItemID() {
		return outerItemID;
	}

	public void setOuterItemID(String outerItemID) {
		this.outerItemID = outerItemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSpecialAttribute() {
		return specialAttribute;
	}

	public void setSpecialAttribute(String specialAttribute) {
		this.specialAttribute = specialAttribute;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getIs_energySubsidy() {
		return is_energySubsidy;
	}

	public void setIs_energySubsidy(String is_energySubsidy) {
		this.is_energySubsidy = is_energySubsidy;
	}

	public String getSubsidyPrice() {
		return subsidyPrice;
	}

	public void setSubsidyPrice(String subsidyPrice) {
		this.subsidyPrice = subsidyPrice;
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

	public String getBelongProductsPromoID() {
		return belongProductsPromoID;
	}

	public void setBelongProductsPromoID(String belongProductsPromoID) {
		this.belongProductsPromoID = belongProductsPromoID;
	}

	public String getProductItemId() {
		return productItemId;
	}

	public void setProductItemId(String productItemId) {
		this.productItemId = productItemId;
	}

	public String getSendGoodsCount() {
		return sendGoodsCount;
	}

	public void setSendGoodsCount(String sendGoodsCount) {
		this.sendGoodsCount = sendGoodsCount;
	}

	public String getGiftCardCharge() {
		return giftCardCharge;
	}

	public void setGiftCardCharge(String giftCardCharge) {
		this.giftCardCharge = giftCardCharge;
	}

		
}