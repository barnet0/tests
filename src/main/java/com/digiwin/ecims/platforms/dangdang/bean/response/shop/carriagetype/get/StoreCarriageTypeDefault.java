package com.digiwin.ecims.platforms.dangdang.bean.response.shop.carriagetype.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StoreCarriageTypeDefault {
	
	@XmlElement(name = "name")	
	private String name;

	@XmlElement(name = "type")
	private String type;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
