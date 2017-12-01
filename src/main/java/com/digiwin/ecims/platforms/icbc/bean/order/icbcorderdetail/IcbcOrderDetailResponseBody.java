package com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.icbc.bean.base.OrderList;

@XmlAccessorType(XmlAccessType.FIELD)
public class IcbcOrderDetailResponseBody {

	@XmlElement(name = "order_list")
	private OrderList orderList;

	public IcbcOrderDetailResponseBody() {

	}

	public IcbcOrderDetailResponseBody(OrderList orderList) {
		this.orderList = orderList;
	}

	public OrderList getOrderList() {
		return orderList;
	}

	public void setOrderList(OrderList orderList) {
		this.orderList = orderList;
	}

}
