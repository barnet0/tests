package com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {
	
	@XmlElement(name = "orderID")
	private String orderID;
	
	@XmlElement(name = "returnExchangeStatus")
	private String returnExchangeStatus;
	
	@XmlElement(name = "returnExchangeCode")
	private String returnExchangeCode;
	
	@XmlElement(name = "orderMoney")
	private String orderMoney;
	
	@XmlElement(name = "orderTime")
	private String orderTime;
	
	@XmlElement(name = "orderStatus")
	private String orderStatus;
	
	@XmlElement(name = "orderResult")
	private String orderResult;
	
	@XmlElement(name = "returnExchangeOrdersApprStatus")
	private String returnExchangeOrdersApprStatus;
	
	@XmlElement(name = "itemsList")
	private ItemsList itemsList;
	
	public OrderInfo() {
		
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getReturnExchangeStatus() {
		return returnExchangeStatus;
	}

	public void setReturnExchangeStatus(String returnExchangeStatus) {
		this.returnExchangeStatus = returnExchangeStatus;
	}

	public String getReturnExchangeCode() {
		return returnExchangeCode;
	}

	public void setReturnExchangeCode(String returnExchangeCode) {
		this.returnExchangeCode = returnExchangeCode;
	}

	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(String orderResult) {
		this.orderResult = orderResult;
	}

	public String getReturnExchangeOrdersApprStatus() {
		return returnExchangeOrdersApprStatus;
	}

	public void setReturnExchangeOrdersApprStatus(
			String returnExchangeOrdersApprStatus) {
		this.returnExchangeOrdersApprStatus = returnExchangeOrdersApprStatus;
	}

	public ItemsList getItemsList() {
		return itemsList;
	}

	public void setItemsList(ItemsList itemsList) {
		this.itemsList = itemsList;
	}

		
}