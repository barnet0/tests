package com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Products;

@XmlAccessorType(XmlAccessType.FIELD)
public class IcbcProductDetailResponseBody {
	@XmlElement(name = "products")
	private Products productList;

	public IcbcProductDetailResponseBody() {

	}

	public IcbcProductDetailResponseBody(Products productList) {
		this.productList = productList;
	}

	public Products getProductList() {
		return productList;
	}

	public void setProductList(Products productList) {
		this.productList = productList;
	}

}
