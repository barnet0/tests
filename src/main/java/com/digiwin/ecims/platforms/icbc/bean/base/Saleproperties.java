package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Saleproperties {

	@XmlElement(name = "saleproperty")
	private List<Saleproperty> salepropertyList;

	public Saleproperties() {

	}

	public Saleproperties(List<Saleproperty> salepropertyList) {
		this.salepropertyList = salepropertyList;
	}

	public List<Saleproperty> getSalepropertyList() {
		return salepropertyList;
	}

	public void setSalepropertyList(List<Saleproperty> salepropertyList) {
		this.salepropertyList = salepropertyList;
	}

}
