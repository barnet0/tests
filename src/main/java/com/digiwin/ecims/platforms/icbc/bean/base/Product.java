package com.digiwin.ecims.platforms.icbc.bean.base;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

	@XmlElement(name = "product_id")
	private String productId;

	@XmlElement(name = "product_sku_id")
	private String productSkuId;

	@XmlElement(name = "product_code")
	private String productCode;

	@XmlElement(name = "product_name")
	private String productName;

	@XmlElement(name = "product_number")
	private String productNumber;

	@XmlElement(name = "product_price")
	private String productPrice;

	@XmlElement(name = "product_discount")
	private String productDiscount;

	@XmlElement(name = "storage")
	private Integer storage;

	@XmlElement(name = "activities")
	private Activities activities;

	@XmlElement(name = "tringproducts")
	private Tringproducts tringproducts;

	@XmlElement(name = "giftproducts")
	private Giftproducts giftproducts;

	@XmlElement(name = "product_refund_amount")
	private BigDecimal productRefundAmount;

	@XmlElement(name = "refund_reason")
	private String refundReason;

	@XmlElement(name = "refund_desc")
	private String refundDesc;

	@XmlElement(name = "product_merchant_id")
	private String productMerchantId;

	@XmlElement(name = "product_status")
	private String productStatus;

	@XmlElement(name = "prod_sub_title")
	private String prodSubTitle;

	@XmlElement(name = "product_emall_price")
//	private BigDecimal productEmallPrice; // mark by mowj 20150819
	private Double productEmallPrice; // add by mowj 20150819

	@XmlElement(name = "product_storage")
	private Integer productStorage;

	@XmlElement(name = "deduct_mode")
	private Integer deductMode;

	@XmlElement(name = "product_bar_code")
	private String productBarCode;

	@XmlElement(name = "product_unit")
	private String productUnit;

	@XmlElement(name = "product_brand")
	private String productBrand;

	@XmlElement(name = "product_brand_id")
	private String productBrandId;

	@XmlElement(name = "buy_channel")
	private String buyChannel;

	@XmlElement(name = "product_weight")
	private String productWeight;

	@XmlElement(name = "product_bulk")
	private String productBulk;

	@XmlElement(name = "puton_type")
	private String putonType;

	@XmlElement(name = "puton_time")
	private String putonTime;

	@XmlElement(name = "product_market_price")
//	private BigDecimal productMarketPrice; // mark by mowj 20150819
	private Double productMarketPrice; // add by mowj 20150819

	@XmlElement(name = "basicproperties")
	private Basicproperties basicproperties;

	@XmlElement(name = "skuproducts")
	private Skuproducts skuproducts;

	public Product() {

	}

	// icbcb2c.storage.modify
	public Product(String productId, String productSkuId, String productCode,
			int storage) {
		this.productId = productId;
		this.productSkuId = productSkuId;
		this.productCode = productCode;
		this.storage = storage;
	}

	public Product(String productId, String productCode, String productName) {
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
	}

	// icbc.order.detail用到
	public Product(String productId, String productSkuId, String productCode,
			String productName, String productNumber, String productPrice,
			String productDiscount, Activities activities,
			Tringproducts tringproducts, Giftproducts giftproducts) {
		super();
		this.productId = productId;
		this.productSkuId = productSkuId;
		this.productCode = productCode;
		this.productName = productName;
		this.productNumber = productNumber;
		this.productPrice = productPrice;
		this.productDiscount = productDiscount;
		this.activities = activities;
		this.tringproducts = tringproducts;
		this.giftproducts = giftproducts;
	}

	// icbcb2c.refund.query
	public Product(String productId, String productSkuId, String productCode,
			String productName, String productNumber,
			BigDecimal productRefundAmount, String refundReason,
			String refundDesc) {
		super();
		this.productId = productId;
		this.productSkuId = productSkuId;
		this.productCode = productCode;
		this.productName = productName;
		this.productNumber = productNumber;
		this.productRefundAmount = productRefundAmount;
		this.refundReason = refundReason;
		this.refundDesc = refundDesc;
	}

	// icbcb2c.product.list
	public Product(String productId, String productName, String productStatus,
			String productMerchantId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productStatus = productStatus;
		this.productMerchantId = productMerchantId;
	}

	public Product(String productId, String productName, String prodSubTitle,
			String productStatus, String productMerchantId,
			Double productMarketPrice, Double productEmallPrice,
			Integer productStorage, Integer deductMode, String productBarCode,
			String productUnit, String productBrand, String productBrandId,
			String buyChannel, String productWeight, String productBulk,
			String putonType, String putonTime,
			Basicproperties basicproperties, Skuproducts skuproducts,
			Tringproducts tringproducts) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productMerchantId = productMerchantId;
		this.productStatus = productStatus;
		this.prodSubTitle = prodSubTitle;
		this.productEmallPrice = productEmallPrice;
		this.productStorage = productStorage;
		this.deductMode = deductMode;
		this.productBarCode = productBarCode;
		this.productUnit = productUnit;
		this.productBrand = productBrand;
		this.productBrandId = productBrandId;
		this.buyChannel = buyChannel;
		this.productWeight = productWeight;
		this.productBulk = productBulk;
		this.putonType = putonType;
		this.putonTime = putonTime;
		this.productMarketPrice = productMarketPrice;
		this.basicproperties = basicproperties;
		this.skuproducts = skuproducts;
		this.tringproducts = tringproducts;
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

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(String productDiscount) {
		this.productDiscount = productDiscount;
	}

	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public Activities getActivities() {
		return activities;
	}

	public void setActivities(Activities activities) {
		this.activities = activities;
	}

	public Tringproducts getTringproducts() {
		return tringproducts;
	}

	public void setTringproducts(Tringproducts tringproducts) {
		this.tringproducts = tringproducts;
	}

	public Giftproducts getGiftproducts() {
		return giftproducts;
	}

	public void setGiftproducts(Giftproducts giftproducts) {
		this.giftproducts = giftproducts;
	}

	public BigDecimal getProductRefundAmount() {
		return productRefundAmount;
	}

	public void setProductRefundAmount(BigDecimal productRefundAmount) {
		this.productRefundAmount = productRefundAmount;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getProductMerchantId() {
		return productMerchantId;
	}

	public void setProductMerchantId(String productMerchantId) {
		this.productMerchantId = productMerchantId;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProdSubTitle() {
		return prodSubTitle;
	}

	public void setProdSubTitle(String prodSubTitle) {
		this.prodSubTitle = prodSubTitle;
	}

	public Double getProductEmallPrice() {
		return productEmallPrice;
	}

	public void setProductEmallPrice(Double productEmallPrice) {
		this.productEmallPrice = productEmallPrice;
	}

	public Integer getProductStorage() {
		return productStorage;
	}

	public void setProductStorage(Integer productStorage) {
		this.productStorage = productStorage;
	}

	public Integer getDeductMode() {
		return deductMode;
	}

	public void setDeductMode(Integer deductMode) {
		this.deductMode = deductMode;
	}

	public String getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductBrandId() {
		return productBrandId;
	}

	public void setProductBrandId(String productBrandId) {
		this.productBrandId = productBrandId;
	}

	public String getBuyChannel() {
		return buyChannel;
	}

	public void setBuyChannel(String buyChannel) {
		this.buyChannel = buyChannel;
	}

	public String getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(String productWeight) {
		this.productWeight = productWeight;
	}

	public String getProductBulk() {
		return productBulk;
	}

	public void setProductBulk(String productBulk) {
		this.productBulk = productBulk;
	}

	public String getPutonType() {
		return putonType;
	}

	public void setPutonType(String putonType) {
		this.putonType = putonType;
	}

	public String getPutonTime() {
		return putonTime;
	}

	public void setPutonTime(String putonTime) {
		this.putonTime = putonTime;
	}

	public Double getProductMarketPrice() {
		return productMarketPrice;
	}

	public void setProductMarketPrice(Double productMarketPrice) {
		this.productMarketPrice = productMarketPrice;
	}

	public Basicproperties getBasicproperties() {
		return basicproperties;
	}

	public void setBasicproperties(Basicproperties basicproperties) {
		this.basicproperties = basicproperties;
	}

	public Skuproducts getSkuproducts() {
		return skuproducts;
	}

	public void setSkuproducts(Skuproducts skuproducts) {
		this.skuproducts = skuproducts;
	}

}
