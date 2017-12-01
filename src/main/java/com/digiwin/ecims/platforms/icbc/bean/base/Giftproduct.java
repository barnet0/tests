package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Giftproduct {

	@XmlElement(name = "product_id")
	private String productId;

	@XmlElement(name = "product_sku_id")
	private String productSkuId;

	@XmlElement(name = "product_code")
	private String productCode;

	@XmlElement(name = "product_name")
	private String productName;

	@XmlElement(name = "product_number")
	private Integer productNumber;

	public Giftproduct() {

	}

	// icbc.order.detail用到
	public Giftproduct(String productId, String productSkuId,
			String productCode, String productName, Integer productNumber) {
		super();
		this.productId = productId;
		this.productSkuId = productSkuId;
		this.productCode = productCode;
		this.productName = productName;
		this.productNumber = productNumber;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

}
