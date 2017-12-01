package com.digiwin.ecims.platforms.dangdang.bean.response.items.status.update;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemsIDList {
	
	@XmlElement(name = "ItemIDInfo")
	private List<ItemIDInfo> itemIDInfos;

	public List<ItemIDInfo> getItemIDInfos() {
		return itemIDInfos;
	}

	public void setItemIDInfos(List<ItemIDInfo> itemIDInfos) {
		this.itemIDInfos = itemIDInfos;
	}
	

	
	
		
}