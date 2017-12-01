package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReceiptInfo {
	
	@XmlElement(name = "receiptName")
	private String receiptName;
	
	@XmlElement(name = "receiptDetails")
	private String receiptDetails;
	
	@XmlElement(name = "receiptMoney")
	private String receiptMoney;
	
	@XmlElement(name = "Is_DangdangReceipt")
	private String Is_DangdangReceipt;
	
	@XmlElement(name = "receiptTel")
	private String receiptTel;
	
	@XmlElement(name = "receiptType")
	private String receiptType;
	
	public String getIs_DangdangReceipt() {
		return Is_DangdangReceipt;
	}

	public void setIs_DangdangReceipt(String is_DangdangReceipt) {
		Is_DangdangReceipt = is_DangdangReceipt;
	}

	public String getReceiptTel() {
		return receiptTel;
	}

	public void setReceiptTel(String receiptTel) {
		this.receiptTel = receiptTel;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public ReceiptInfo() {
		
	}

	public String getReceiptName() {
		return receiptName;
	}

	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}

	public String getReceiptDetails() {
		return receiptDetails;
	}

	public void setReceiptDetails(String receiptDetails) {
		this.receiptDetails = receiptDetails;
	}

	public String getReceiptMoney() {
		return receiptMoney;
	}

	public void setReceiptMoney(String receiptMoney) {
		this.receiptMoney = receiptMoney;
	}

		
}