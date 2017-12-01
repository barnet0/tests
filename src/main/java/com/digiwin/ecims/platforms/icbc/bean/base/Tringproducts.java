package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tringproducts {

	@XmlElement(name = "tringproduct")
	private List<Tringproduct> tringproductList;

	public Tringproducts() {

	}

	public Tringproducts(List<Tringproduct> tringproductList) {
		this.tringproductList = tringproductList;
	}

	public List<Tringproduct> getTringproductList() {
		return tringproductList;
	}

	public void setTringproductList(List<Tringproduct> tringproductList) {
		this.tringproductList = tringproductList;
	}

}
