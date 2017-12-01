package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Discounts {

	@XmlElement(name = "discount")
	private List<Discount> discountList;

	public Discounts() {

	}

	public Discounts(List<Discount> discountList) {
		this.discountList = discountList;
	}

	public List<Discount> getDiscountList() {
		return discountList;
	}

	public void setDiscountList(List<Discount> discountList) {
		this.discountList = discountList;
	}

}
