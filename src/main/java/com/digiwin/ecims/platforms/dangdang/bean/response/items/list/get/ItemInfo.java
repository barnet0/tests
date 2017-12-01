package com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInfo {
	
	@XmlElement(name = "itemID")
	private String itemID;
	
	@XmlElement(name = "itemName")
	private String itemName;
	
	@XmlElement(name = "itemSubhead")
	private String itemSubhead;
	
	@XmlElement(name = "brand")
	private String brand;
	
	@XmlElement(name = "model")
	private String model;
	
	@XmlElement(name = "unitPrice")
	private String unitPrice;
	
	@XmlElement(name = "stockCount")
	private String stockCount;
	
	@XmlElement(name = "shopCategoryList")
	private ShopCategoryList shopCategoryList;
	
	@XmlElement(name = "itemState")
	private String itemState;
	
	@XmlElement(name = "itemCheckState")
	private String itemCheckState;
	
	@XmlElement(name = "updateTime")
	private String updateTime;
	
	@XmlElement(name = "ty")
	private String ty;
	
	@XmlElement(name = "datatype")
	private String datatype;
	
	public ItemInfo() {
		
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSubhead() {
		return itemSubhead;
	}

	public void setItemSubhead(String itemSubhead) {
		this.itemSubhead = itemSubhead;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getStockCount() {
		return stockCount;
	}

	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}

	public ShopCategoryList getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(ShopCategoryList shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public String getItemState() {
		return itemState;
	}

	public void setItemState(String itemState) {
		this.itemState = itemState;
	}

	public String getItemCheckState() {
		return itemCheckState;
	}

	public void setItemCheckState(String itemCheckState) {
		this.itemCheckState = itemCheckState;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTy() {
		return ty;
	}

	public void setTy(String ty) {
		this.ty = ty;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

		
}