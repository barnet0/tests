package com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class OrdersListGetResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "totalInfo")
	private TotalInfo totalInfo;
	
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

	public TotalInfo getTotalInfo() {
		return totalInfo;
	}

	public void setTotalInfo(TotalInfo totalInfo) {
		this.totalInfo = totalInfo;
	}

	public OrdersList getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(OrdersList ordersList) {
		this.ordersList = ordersList;
	}

	
		
}