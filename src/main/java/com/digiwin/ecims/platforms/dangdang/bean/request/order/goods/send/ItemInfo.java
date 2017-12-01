package com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInfo {
	
	@XmlElement(name = "itemID")
	private String itemID;
	
	@XmlElement(name = "sendGoodsCount")
	private String sendGoodsCount;
	
	@XmlElement(name = "belongProductsPromoID")
	private String belongProductsPromoID;
	
	@XmlElement(name = "productItemId")
	private String productItemId;
	
	public ItemInfo() {
		
	}
	
	public ItemInfo(String itemID, String sendGoodsCount, String productItemId) {
		this.itemID = itemID;
		this.sendGoodsCount = sendGoodsCount;
		this.productItemId = productItemId;
	}
	
	public ItemInfo(String itemID, String sendGoodsCount, String belongProductsPromoID, String productItemId) {
		this.itemID = itemID;
		this.sendGoodsCount = sendGoodsCount;
		this.belongProductsPromoID = belongProductsPromoID;
		this.productItemId = productItemId;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getSendGoodsCount() {
		return sendGoodsCount;
	}

	public void setSendGoodsCount(String sendGoodsCount) {
		this.sendGoodsCount = sendGoodsCount;
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
	
	
		
}