package com.digiwin.ecims.platforms.dangdang.bean.response.orders.pickgoods.update;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersList {
	
	@XmlElement(name = "OrderInfo")
	private List<OrderInfo> orderInfos;

	/**
	 * @return the orderInfos
	 */
	public List<OrderInfo> getOrderInfos() {
		return orderInfos;
	}

	/**
	 * @param orderInfos the orderInfos to set
	 */
	public void setOrderInfos(List<OrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}

}
