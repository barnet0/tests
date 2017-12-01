package com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersList {
	
	@XmlElement(name = "OrderInfo")
	private List<OrderInfo> orderInfos;

	public List<OrderInfo> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}

	

		
	
		
}