package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Skuproduct {

	@XmlElement(name = "product_sku_id")
	private String productSkuId;

	@XmlElement(name = "product_merchant_id")
	private String productMerchantId;

	@XmlElement(name = "product_market_price")
	private String productMarketPrice;

	@XmlElement(name = "product_emall_price")
	private String productEmallPrice;

	@XmlElement(name = "product_storage")
	private String productStorage;

	@XmlElement(name = "product_bar_code")
	private String productBarCode;

	@XmlElement(name = "saleproperties")
	private Saleproperties saleproperties;

	public Skuproduct() {

	}

	public Skuproduct(String productSkuId, String productMerchantId,
			String productMarketPrice, String productEmallPrice,
			String productStorage, String productBarCode,
			Saleproperties saleproperties) {
		super();
		this.productSkuId = productSkuId;
		this.productMerchantId = productMerchantId;
		this.productMarketPrice = productMarketPrice;
		this.productEmallPrice = productEmallPrice;
		this.productStorage = productStorage;
		this.productBarCode = productBarCode;
		this.saleproperties = saleproperties;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductMerchantId() {
		return productMerchantId;
	}

	public void setProductMerchantId(String productMerchantId) {
		this.productMerchantId = productMerchantId;
	}

	public String getProductMarketPrice() {
		return productMarketPrice;
	}

	public void setProductMarketPrice(String productMarketPrice) {
		this.productMarketPrice = productMarketPrice;
	}

	public String getProductEmallPrice() {
		return productEmallPrice;
	}

	public void setProductEmallPrice(String productEmallPrice) {
		this.productEmallPrice = productEmallPrice;
	}

	public String getProductStorage() {
		return productStorage;
	}

	public void setProductStorage(String productStorage) {
		this.productStorage = productStorage;
	}

	public String getProductBarCode() {
		return productBarCode;
	}

	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}

	public Saleproperties getSaleproperties() {
		return saleproperties;
	}

	public void setSaleproperties(Saleproperties saleproperties) {
		this.saleproperties = saleproperties;
	}

}
