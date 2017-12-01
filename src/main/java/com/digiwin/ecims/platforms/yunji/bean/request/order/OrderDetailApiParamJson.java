package com.digiwin.ecims.platforms.yunji.bean.request.order;

public class OrderDetailApiParamJson {
	private String orderId;

	public OrderDetailApiParamJson() {
		// TODO Auto-generated constructor stub
	}
	
	public OrderDetailApiParamJson(String id) {
		this.orderId = id;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
