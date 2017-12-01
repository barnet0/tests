package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersList {
	
	@XmlElement(name = "OrderInfo")
	private List<OrderInfo> OrderInfos;

	public void setOrderInfo(List<OrderInfo> OrderInfo) {
		this.OrderInfos = OrderInfo;
	}

	public List<OrderInfo> getOrderInfo() {
		return this.OrderInfos;
	}
}
