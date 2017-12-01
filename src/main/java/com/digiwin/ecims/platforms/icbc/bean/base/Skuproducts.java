package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Skuproducts {

	@XmlElement(name = "skuproduct")
	private List<Skuproduct> skuproductList;

	public Skuproducts() {

	}

	public Skuproducts(List<Skuproduct> skuproductList) {
		this.skuproductList = skuproductList;
	}

	public List<Skuproduct> getSkuproductList() {
		return skuproductList;
	}

	public void setSkuproductList(List<Skuproduct> skuproductList) {
		this.skuproductList = skuproductList;
	}

}
