package com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.icbc.bean.base.OrderList;

@XmlAccessorType(XmlAccessType.FIELD)
public class IcbcOrderListResponseBody {

	@XmlElement(name = "order_list")
	private OrderList orderList;

	public IcbcOrderListResponseBody() {

	}

	public IcbcOrderListResponseBody(OrderList orderList) {
		this.orderList = orderList;
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

}
