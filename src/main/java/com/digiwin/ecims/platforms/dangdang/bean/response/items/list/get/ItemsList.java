package com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemsList {
	
	@XmlElement(name = "ItemInfo")
	private List<ItemInfo> ItemInfos;

	public List<ItemInfo> getItemInfos() {
		return ItemInfos;
	}

	public void setItemInfos(List<ItemInfo> itemInfos) {
		ItemInfos = itemInfos;
	}

	

	
	
		
}