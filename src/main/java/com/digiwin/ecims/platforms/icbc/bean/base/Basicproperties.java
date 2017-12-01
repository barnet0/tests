package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Basicproperties {

	@XmlElement(name = "basicproperty")
	private List<Basicproperty> basicpropertyList;

	public Basicproperties() {

	}

	public Basicproperties(List<Basicproperty> basicpropertyList) {
		this.basicpropertyList = basicpropertyList;
	}

	public List<Basicproperty> getBasicpropertyList() {
		return basicpropertyList;
	}

	public void setBasicpropertyList(List<Basicproperty> basicpropertyList) {
		this.basicpropertyList = basicpropertyList;
	}

}
