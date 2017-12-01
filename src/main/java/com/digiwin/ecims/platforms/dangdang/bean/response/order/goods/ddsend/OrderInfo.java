package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {
	
	@XmlElement(name = "orderID")
	private String orderID;

	@XmlElement(name = "orderOperCode")
	private String orderOperCode;

	@XmlElement(name = "orderOperation")
	private String orderOperation;

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderID() {
		return this.orderID;
	}

	public void setOrderOperCode(String orderOperCode) {
		this.orderOperCode = orderOperCode;
	}

	public String getOrderOperCode() {
		return this.orderOperCode;
	}

	public void setOrderOperation(String orderOperation) {
		this.orderOperation = orderOperation;
	}

	public String getOrderOperation() {
		return this.orderOperation;
	}
}
