package com.digiwin.ecims.platforms.icbc.bean.base;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tringproduct {

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

	@XmlElement(name = "product_price")
	private BigDecimal productPrice;

	@XmlElement(name = "product_merchant_id")
	private BigDecimal productMerchantId;

	@XmlElement(name = "product_emall_price")
	private BigDecimal productEmallPrice;

	@XmlElement(name = "product_emall_discount_price")
	private BigDecimal productEmallDiscountPrice;

	public Tringproduct() {

	}

	// icbc.order.detail用到
	public Tringproduct(String productId, String productSkuId,
			String productCode, String productName, Integer productNumber,
			BigDecimal productPrice) {
		super();
		this.productId = productId;
		this.productSkuId = productSkuId;
		this.productCode = productCode;
		this.productName = productName;
		this.productNumber = productNumber;
		this.productPrice = productPrice;
	}

	public Tringproduct(String productSkuId, String productName,
			BigDecimal productMerchantId, BigDecimal productEmallPrice,
			BigDecimal productEmallDiscountPrice) {
		super();
		this.productSkuId = productSkuId;
		this.productName = productName;
		this.productMerchantId = productMerchantId;
		this.productEmallPrice = productEmallPrice;
		this.productEmallDiscountPrice = productEmallDiscountPrice;
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

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductMerchantId() {
		return productMerchantId;
	}

	public void setProductMerchantId(BigDecimal productMerchantId) {
		this.productMerchantId = productMerchantId;
	}

	public BigDecimal getProductEmallPrice() {
		return productEmallPrice;
	}

	public void setProductEmallPrice(BigDecimal productEmallPrice) {
		this.productEmallPrice = productEmallPrice;
	}

	public BigDecimal getProductEmallDiscountPrice() {
		return productEmallDiscountPrice;
	}

	public void setProductEmallDiscountPrice(
			BigDecimal productEmallDiscountPrice) {
		this.productEmallDiscountPrice = productEmallDiscountPrice;
	}

}
