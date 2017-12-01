package com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemsList {
	
	@XmlElement(name = "ItemInfo")
	private List<ItemInfo> itemInfos;
	
	public ItemsList() {
		
	}

	public List<ItemInfo> getItemInfos() {
		return itemInfos;
	}

	public void setItemInfos(List<ItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
	}

	
		
}