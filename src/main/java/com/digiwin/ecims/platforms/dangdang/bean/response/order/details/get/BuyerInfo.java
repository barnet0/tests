package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BuyerInfo {
	
	@XmlElement(name = "buyerPayMode")
	private String buyerPayMode;
	
	@XmlElement(name = "goodsMoney")
	private String goodsMoney;
	
	@XmlElement(name = "realPaidAmount")
	private String realPaidAmount;
	
	@XmlElement(name = "deductAmount")
	private String deductAmount;
	
	@XmlElement(name = "totalBarginPrice")
	private String totalBarginPrice;
	
	@XmlElement(name = "promoDeductAmount")
	private String promoDeductAmount;
	
	@XmlElement(name = "postage")
	private String postage;
	
	@XmlElement(name = "giftCertMoney")
	private String giftCertMoney;
	
	@XmlElement(name = "giftCardMoney")
	private String giftCardMoney;
	
	@XmlElement(name = "accountBalance")
	private String accountBalance;
	
	@XmlElement(name = "activityDeductAmount")
	private String activityDeductAmount;
	
	@XmlElement(name = "custPointUsed")
	private String custPointUsed;
	
	@XmlElement(name = "pointDeductionAmount")
	private String pointDeductionAmount;
	
	public BuyerInfo() {
		
	}

	public String getBuyerPayMode() {
		return buyerPayMode;
	}

	public void setBuyerPayMode(String buyerPayMode) {
		this.buyerPayMode = buyerPayMode;
	}

	public String getGoodsMoney() {
		return goodsMoney;
	}

	public void setGoodsMoney(String goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public String getRealPaidAmount() {
		return realPaidAmount;
	}

	public void setRealPaidAmount(String realPaidAmount) {
		this.realPaidAmount = realPaidAmount;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getTotalBarginPrice() {
		return totalBarginPrice;
	}

	public void setTotalBarginPrice(String totalBarginPrice) {
		this.totalBarginPrice = totalBarginPrice;
	}

	public String getPromoDeductAmount() {
		return promoDeductAmount;
	}

	public void setPromoDeductAmount(String promoDeductAmount) {
		this.promoDeductAmount = promoDeductAmount;
	}

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

	public String getGiftCertMoney() {
		return giftCertMoney;
	}

	public void setGiftCertMoney(String giftCertMoney) {
		this.giftCertMoney = giftCertMoney;
	}

	public String getGiftCardMoney() {
		return giftCardMoney;
	}

	public void setGiftCardMoney(String giftCardMoney) {
		this.giftCardMoney = giftCardMoney;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getActivityDeductAmount() {
		return activityDeductAmount;
	}

	public void setActivityDeductAmount(String activityDeductAmount) {
		this.activityDeductAmount = activityDeductAmount;
	}

	public String getCustPointUsed() {
		return custPointUsed;
	}

	public void setCustPointUsed(String custPointUsed) {
		this.custPointUsed = custPointUsed;
	}

	public String getPointDeductionAmount() {
		return pointDeductionAmount;
	}

	public void setPointDeductionAmount(String pointDeductionAmount) {
		this.pointDeductionAmount = pointDeductionAmount;
	}


		
}