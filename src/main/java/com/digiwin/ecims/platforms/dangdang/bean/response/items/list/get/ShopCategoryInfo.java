package com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopCategoryInfo {
	
	@XmlElement(name = "shopCategoryID")
	private String shopCategoryID;
	
	@XmlElement(name = "shopCategoryName")
	private String shopCategoryName;
	
	public ShopCategoryInfo() {
		
	}

	public String getShopCategoryID() {
		return shopCategoryID;
	}

	public void setShopCategoryID(String shopCategoryID) {
		this.shopCategoryID = shopCategoryID;
	}

	public String getShopCategoryName() {
		return shopCategoryName;
	}

	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}


		
}