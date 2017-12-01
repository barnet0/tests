package com.digiwin.ecims.platforms.dangdang.bean.response.items.status.update;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ItemsStatusUpdateResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "ItemsIDList")
	private ItemsIDList itemsIDList;

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ItemsIDList getItemsIDList() {
		return itemsIDList;
	}

	public void setItemsIDList(ItemsIDList itemsIDList) {
		this.itemsIDList = itemsIDList;
	}


	
	
	
		
}