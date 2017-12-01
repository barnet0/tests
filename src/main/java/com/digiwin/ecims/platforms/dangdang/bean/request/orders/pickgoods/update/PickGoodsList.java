package com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PickGoodsList {
	
	@XmlElement(name = "ItemInfo")
	private List<ItemInfo> itemInfos;

	public PickGoodsList() {
		super();
		// TODO Auto-generated constructor stub
		this.itemInfos = new ArrayList<ItemInfo>();
	}

	public PickGoodsList(List<ItemInfo> itemInfos) {
		super();
		this.itemInfos = itemInfos;
	}

	/**
	 * @return the itemInfos
	 */
	public List<ItemInfo> getItemInfos() {
		return itemInfos;
	}

	/**
	 * @param itemInfos the itemInfos to set
	 */
	public void setItemInfos(List<ItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
	}

}