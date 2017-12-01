package com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopCategoryList {
	
	@XmlElement(name = "shopCategoryInfo")
	private List<ShopCategoryInfo> shopCategoryInfos;

	public List<ShopCategoryInfo> getShopCategoryInfos() {
		return shopCategoryInfos;
	}

	public void setShopCategoryInfos(List<ShopCategoryInfo> shopCategoryInfos) {
		this.shopCategoryInfos = shopCategoryInfos;
	}

		
}