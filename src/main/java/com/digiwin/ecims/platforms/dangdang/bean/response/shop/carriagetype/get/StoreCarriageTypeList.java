package com.digiwin.ecims.platforms.dangdang.bean.response.shop.carriagetype.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StoreCarriageTypeList {
	
	@XmlElement(name = "storeCarriageType")
	private List<StoreCarriageType> storeCarriageTypes;

	public void setStoreCarriageType(List<StoreCarriageType> storeCarriageType) {
		this.storeCarriageTypes = storeCarriageType;
	}

	public List<StoreCarriageType> getStoreCarriageType() {
		return this.storeCarriageTypes;
	}
}
