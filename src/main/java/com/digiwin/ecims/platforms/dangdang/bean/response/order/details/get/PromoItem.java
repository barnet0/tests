package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PromoItem {
	
	@XmlElement(name = "promotionID")
	private String promotionID;
	
	@XmlElement(name = "promotionName")
	private String promotionName;
	
	@XmlElement(name = "promotionType")
	private String promotionType;
	
	@XmlElement(name = "promoDicount")
	private String promoDicount;
	
	@XmlElement(name = "promoAmount")
	private String promoAmount;
	
	public PromoItem() {
		
	}

	public String getPromotionID() {
		return promotionID;
	}

	public void setPromotionID(String promotionID) {
		this.promotionID = promotionID;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromoDicount() {
		return promoDicount;
	}

	public void setPromoDicount(String promoDicount) {
		this.promoDicount = promoDicount;
	}

	public String getPromoAmount() {
		return promoAmount;
	}

	public void setPromoAmount(String promoAmount) {
		this.promoAmount = promoAmount;
	}


		
}