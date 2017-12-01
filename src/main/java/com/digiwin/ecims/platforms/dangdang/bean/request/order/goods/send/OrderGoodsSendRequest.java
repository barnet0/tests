package com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class OrderGoodsSendRequest {
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "OrdersList")
	private OrdersList ordersList;

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public OrdersList getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(OrdersList ordersList) {
		this.ordersList = ordersList;
	}
	
	
		
}