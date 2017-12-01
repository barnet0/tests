package com.digiwin.ecims.platforms.icbc.bean.order.icbcordersendmess;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;
import com.digiwin.ecims.platforms.icbc.bean.base.Products;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcOrderSendmessRequest implements BasicRequest{

	@XmlTransient
	private final String method = "icbcb2c.order.sendmess";

	@XmlElement(name = "order_id")
	private String orderId;

	@XmlElement(name = "logistics_company")
	private String logisticsCompany;

	@XmlElement(name = "shipping_code")
	private String shippingCode;

	@XmlElement(name = "shipping_time")
	private String shippingTime;

	@XmlElement(name = "shipping_user")
	private String shippingUser;

	@XmlElement(name = "notes")
	private String notes;

	@XmlElement(name = "products")
	private Products products;

	public IcbcOrderSendmessRequest(String orderId, String logisticsCompany,
			String shippingCode, String shippingTime, String shippingUser,
			String notes, Products products) {
		this.orderId = orderId;
		this.logisticsCompany = logisticsCompany;
		this.shippingCode = shippingCode;
		this.shippingTime = shippingTime;
		this.shippingUser = shippingUser;
		this.notes = notes;
		this.products = products;
	}

	public IcbcOrderSendmessRequest() {

	}

	public IcbcOrderSendmessRequest(String order_id, String logistics_company,
			String shipping_code, String shipping_time) {
		this.orderId = order_id;
		this.logisticsCompany = logistics_company;
		this.shippingCode = shipping_code;
		this.shippingTime = shipping_time;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingUser() {
		return shippingUser;
	}

	public void setShippingUser(String shippingUser) {
		this.shippingUser = shippingUser;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
