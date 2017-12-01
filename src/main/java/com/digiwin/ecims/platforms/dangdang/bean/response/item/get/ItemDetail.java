package com.digiwin.ecims.platforms.dangdang.bean.response.item.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemDetail {
	
	@XmlElement(name = "specialAttributeClass")
	private String specialAttributeClass;
	
	@XmlElement(name = "itemID")
	private String itemID;

	@XmlElement(name = "itemName")
	private String itemName;

	@XmlElement(name = "classificationCode1")
	private String classificationCode1;

	@XmlElement(name = "brand")
	private String brand;

	@XmlElement(name = "model")
	private String model;

	@XmlElement(name = "attribute")
	private String attribute;

	@XmlElement(name = "itemDetail")
	private String itemDetail;

	@XmlElement(name = "stockPrice")
	private String stockPrice;

	@XmlElement(name = "marketPrice")
	private String marketPrice;

	@XmlElement(name = "isShopVip")
	private String isShopVip;

	@XmlElement(name = "itemState")
	private String itemState;

	@XmlElement(name = "is_cod")
	private String is_cod;

	@XmlElement(name = "itemType")
	private String itemType;
	
	@XmlElement(name = "outerItemID")
	private String outerItemID;

	public String getOuterItemID() {
		return outerItemID;
	}

	public void setOuterItemID(String outerItemID) {
		this.outerItemID = outerItemID;
	}

	@XmlElement(name = "unitPrice")
	private String unitPrice;

	@XmlElement(name = "stockCount")
	private String stockCount;

	@XmlElement(name = "pic1")
	private String pic1;

	@XmlElement(name = "pic2")
	private String pic2;

	@XmlElement(name = "pic3")
	private String pic3;

	@XmlElement(name = "pic4")
	private String pic4;

	@XmlElement(name = "pic5")
	private String pic5;

	@XmlElement(name = "SpecilaItemInfo")
	private List<SpecilaItemInfo> SpecilaItemInfos ;

	@XmlElement(name = "volume")
	private String volume;

	@XmlElement(name = "weight")
	private String weight;

	@XmlElement(name = "templateName")
	private String templateName;
	
	public ItemDetail() {
		
	}

	public String getSpecialAttributeClass() {
		return specialAttributeClass;
	}

	public void setSpecialAttributeClass(String specialAttributeClass) {
		this.specialAttributeClass = specialAttributeClass;
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

	public String getClassificationCode1() {
		return classificationCode1;
	}

	public void setClassificationCode1(String classificationCode1) {
		this.classificationCode1 = classificationCode1;
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

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}

	public String getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(String stockPrice) {
		this.stockPrice = stockPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getIsShopVip() {
		return isShopVip;
	}

	public void setIsShopVip(String isShopVip) {
		this.isShopVip = isShopVip;
	}

	public String getItemState() {
		return itemState;
	}

	public void setItemState(String itemState) {
		this.itemState = itemState;
	}

	public String getIs_cod() {
		return is_cod;
	}

	public void setIs_cod(String is_cod) {
		this.is_cod = is_cod;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public String getPic3() {
		return pic3;
	}

	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}

	public String getPic4() {
		return pic4;
	}

	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}

	public String getPic5() {
		return pic5;
	}

	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}

	public List<SpecilaItemInfo> getSpecilaItemInfos() {
		return SpecilaItemInfos;
	}

	public void setSpecilaItemInfos(List<SpecilaItemInfo> specilaItemInfos) {
		SpecilaItemInfos = specilaItemInfos;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


		
}