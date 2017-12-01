package com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SendGoodsList {
	
	@XmlElement(name = "ItemInfo")
	private List<ItemInfo> ItemInfos;

	public List<ItemInfo> getItemInfos() {
		return ItemInfos;
	}

	public void setItemInfos(List<ItemInfo> itemInfos) {
		ItemInfos = itemInfos;
	}

		
	
		
}