package com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Products;

@XmlAccessorType(XmlAccessType.FIELD)
public class IcbcProductListResponseBody {

	@XmlElement(name = "products")
	private Products productList;

	public IcbcProductListResponseBody() {

	}

	public IcbcProductListResponseBody(Products productList) {
		this.productList = productList;
	}

	public Products getProductList() {
		return productList;
	}

	public void setProductList(Products productList) {
		this.productList = productList;
	}

}
