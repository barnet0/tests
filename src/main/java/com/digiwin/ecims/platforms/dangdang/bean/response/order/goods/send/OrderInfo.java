package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send;

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
	
	public OrderInfo() {
		
	}
	
	public OrderInfo(String orderID, String orderOperCode, String orderOperation) {
		this.orderID = orderID;
		this.orderOperCode = orderOperCode;
		this.orderOperation = orderOperation;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderOperCode() {
		return orderOperCode;
	}

	public void setOrderOperCode(String orderOperCode) {
		this.orderOperCode = orderOperCode;
	}

	public String getOrderOperation() {
		return orderOperation;
	}

	public void setOrderOperation(String orderOperation) {
		this.orderOperation = orderOperation;
	}

		
}