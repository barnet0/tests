package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {

	@XmlElement(name = "invoice_type")
	private Integer invoiceType;

	@XmlElement(name = "invoice_title")
	private String invoiceTitle;

	@XmlElement(name = "invoice_content")
	private String invoiceContent;

	public Invoice() {

	}

	// icbc.order.detail用到
	public Invoice(Integer invoiceType, String invoiceTitle,
			String invoiceContent) {
		super();
		this.invoiceType = invoiceType;
		this.invoiceTitle = invoiceTitle;
		this.invoiceContent = invoiceContent;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

}
