package com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {
	
	@XmlElement(name = "orderID")
	private String orderID;
	
	@XmlElement(name = "PickGoodsList")
	private PickGoodsList pickGoodsList;
	
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderInfo(String orderID, PickGoodsList pickGoodsList) {
		super();
		this.orderID = orderID;
		this.pickGoodsList = pickGoodsList;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return the pickGoodsList
	 */
	public PickGoodsList getPickGoodsList() {
		return pickGoodsList;
	}

	/**
	 * @param pickGoodsList the pickGoodsList to set
	 */
	public void setPickGoodsList(PickGoodsList pickGoodsList) {
		this.pickGoodsList = pickGoodsList;
	}

		
}