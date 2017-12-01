package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PromoList {
	
	@XmlElement(name = "promoItem")
	private List<PromoItem> promoItems;

	public List<PromoItem> getPromoItems() {
		return promoItems;
	}

	public void setPromoItems(List<PromoItem> promoItems) {
		this.promoItems = promoItems;
	}

	
			
}