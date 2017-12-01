package com.digiwin.ecims.platforms.icbc.bean.base;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Discount {

	@XmlElement(name = "discount_type")
	private String discountType;

	@XmlElement(name = "discount_amount")
	private BigDecimal discountAmount;

	public Discount() {

	}

	// icbc.order.detail用到
	public Discount(String discountType, BigDecimal discountAmount) {
		this.discountType = discountType;
		this.discountAmount = discountAmount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

}
