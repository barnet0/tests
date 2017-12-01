package com.digiwin.ecims.platforms.dhgate.bean.response.order;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderProduct;

public class OrderProductGetResponse extends BizStatusResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282123492470799413L;

	private List<OrderProduct> orderProductList;

	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}

	public OrderProductGetResponse() {
		super();
	}
	
	
}
