package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Basicproperty {

	@XmlElement(name = "basic_prop_name")
	private String basicPropName;

	@XmlElement(name = "basic_prop_value")
	private String basicPropValue;

	public String getBasicPropName() {
		return basicPropName;
	}

	public Basicproperty() {

	}

	public Basicproperty(String basicPropName, String basicPropValue) {
		super();
		this.basicPropName = basicPropName;
		this.basicPropValue = basicPropValue;
	}

	public void setBasicPropName(String basicPropName) {
		this.basicPropName = basicPropName;
	}

	public String getBasicPropValue() {
		return basicPropValue;
	}

	public void setBasicPropValue(String basicPropValue) {
		this.basicPropValue = basicPropValue;
	}

}
