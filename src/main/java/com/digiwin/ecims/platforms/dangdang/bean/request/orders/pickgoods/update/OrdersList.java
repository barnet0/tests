package com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersList {
	
	@XmlElement(name = "OrderInfo")
	private List<OrderInfo> orderInfos;

	public OrdersList() {
		super();
		// TODO Auto-generated constructor stub
		this.orderInfos = new ArrayList<OrderInfo>();
	}

	public OrdersList(List<OrderInfo> orderInfos) {
		super();
		this.orderInfos = orderInfos;
	}

	public List<OrderInfo> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}
	
}