package com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class OrdersPickGoodsUpdateRequest {
	
	public static final String FUNCTION_ID = "pickGoods"; 
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "OrdersList")
	private OrdersList ordersList;

	public OrdersPickGoodsUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrdersPickGoodsUpdateRequest(String functionID, String time,
			OrdersList ordersList) {
		super();
		this.functionID = functionID;
		this.time = time;
		this.ordersList = ordersList;
	}

	/**
	 * @return the functionID
	 */
	public String getFunctionID() {
		return functionID;
	}

	/**
	 * @param functionID the functionID to set
	 */
	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the ordersList
	 */
	public OrdersList getOrdersList() {
		return ordersList;
	}

	/**
	 * @param ordersList the ordersList to set
	 */
	public void setOrdersList(OrdersList ordersList) {
		this.ordersList = ordersList;
	}
	
	
}
