package com.digiwin.ecims.platforms.dangdang.bean.response.item.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SpecilaItemInfo {
	
	@XmlElement(name = "specialAttributeClass")
	private String specialAttributeClass;

	@XmlElement(name = "specialAttribute")
	private String specialAttribute;

	@XmlElement(name = "specialAttributeSeq")
	private String specialAttributeSeq;

	@XmlElement(name = "stockCount")
	private String stockCount;

	@XmlElement(name = "unitPrice")
	private String unitPrice;
	
	@XmlElement(name = "outerItemID")
	private String outerItemID;

	@XmlElement(name = "subItemID")
	private String subItemID;

	@XmlElement(name = "volume")
	private String volume;

	@XmlElement(name = "weight")
	private String weight;
	
	
	public SpecilaItemInfo() {
		
	}


	public String getSpecialAttributeClass() {
		return specialAttributeClass;
	}


	public void setSpecialAttributeClass(String specialAttributeClass) {
		this.specialAttributeClass = specialAttributeClass;
	}


	public String getSpecialAttribute() {
		return specialAttribute;
	}


	public void setSpecialAttribute(String specialAttribute) {
		this.specialAttribute = specialAttribute;
	}


	public String getSpecialAttributeSeq() {
		return specialAttributeSeq;
	}


	public void setSpecialAttributeSeq(String specialAttributeSeq) {
		this.specialAttributeSeq = specialAttributeSeq;
	}


	public String getStockCount() {
		return stockCount;
	}


	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}


	public String getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSubItemID() {
		return subItemID;
	}


	public void setSubItemID(String subItemID) {
		this.subItemID = subItemID;
	}


	public String getVolume() {
		return volume;
	}


	public void setVolume(String volume) {
		this.volume = volume;
	}


	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}


	public String getOuterItemID() {
		return outerItemID;
	}


	public void setOuterItemID(String outerItemID) {
		this.outerItemID = outerItemID;
	}

		
}