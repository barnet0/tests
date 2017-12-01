package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Giftproducts {
	@XmlElement(name = "giftproduct")
	private List<Giftproduct> giftproductList;

	public Giftproducts() {

	}

	public Giftproducts(List<Giftproduct> giftproductList) {
		this.giftproductList = giftproductList;
	}

	public List<Giftproduct> getGiftproductList() {
		return giftproductList;
	}

	public void setGiftproductList(List<Giftproduct> giftproductList) {
		this.giftproductList = giftproductList;
	}

}
