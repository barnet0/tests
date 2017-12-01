package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
	
	@XmlElement(name = "OrdersList")
	private OrdersList ordersList;
	
	public Result() {
		
	}

	public OrdersList getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(OrdersList ordersList) {
		this.ordersList = ordersList;
	}
	
		
}