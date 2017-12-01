package com.digiwin.ecims.platforms.icbc.bean.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;
import com.digiwin.ecims.platforms.icbc.bean.base.Products;

/**
 * icbc.storage.modify的請求bean
 * 
 * @author Shang Hsuan Hsu
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcStorageModifyRequest implements BasicRequest {

	@XmlTransient
	private final String method = "icbcb2c.storage.modify";
	/**
	 * 商品列表節點
	 */
	@XmlElement(name = "products")
	private Products products;

	/**
	 * default constructor
	 */
	public IcbcStorageModifyRequest() {
		super();
	}

	/**
	 * constructor
	 * 
	 * @param products
	 *            商品列表節點
	 */
	public IcbcStorageModifyRequest(Products products) {
		super();
		this.products = products;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public String getMethod() {
		return method;
	}

}