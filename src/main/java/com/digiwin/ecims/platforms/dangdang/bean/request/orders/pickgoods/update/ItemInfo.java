package com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ItemInfo {
	
	@XmlElement(name = "pickGoodsCount")
	private String pickGoodsCount;
	
	@XmlElement(name = "productItemId")
	private String productItemId;

	public ItemInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemInfo(String pickGoodsCount, String productItemId) {
		super();
		this.pickGoodsCount = pickGoodsCount;
		this.productItemId = productItemId;
	}

	/**
	 * @return the pickGoodsCount
	 */
	public String getPickGoodsCount() {
		return pickGoodsCount;
	}

	/**
	 * @param pickGoodsCount the pickGoodsCount to set
	 */
	public void setPickGoodsCount(String pickGoodsCount) {
		this.pickGoodsCount = pickGoodsCount;
	}

	/**
	 * @return the productItemId
	 */
	public String getProductItemId() {
		return productItemId;
	}

	/**
	 * @param productItemId the productItemId to set
	 */
	public void setProductItemId(String productItemId) {
		this.productItemId = productItemId;
	}
	
	
}