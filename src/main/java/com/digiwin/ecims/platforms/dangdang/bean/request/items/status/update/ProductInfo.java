package com.digiwin.ecims.platforms.dangdang.bean.request.items.status.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInfo {
	
	@XmlElement(name = "itemID")
	private String itemID;
	
	@XmlElement(name = "productStatus")
	private String productStatus;
	
	public ProductInfo() {
		
	}
	
	public ProductInfo(String itemID, String productStatus) {
		this.itemID = itemID;
		this.productStatus = productStatus;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	
	
		
}