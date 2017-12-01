package com.digiwin.ecims.platforms.dhgate.bean.response.order;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderBaseInfo;

public class OrderListGetResponse extends BizStatusResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3776560564704246835L;
//	必须	总记录数	1000,表示总记录数为1000
	private int count;
//	必须	总页数	20,表示总共20页
	private int pages;
//	必须	订单基础信息列表	订单基础信息列表
	private List<OrderBaseInfo> orderBaseInfoList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<OrderBaseInfo> getOrderBaseInfoList() {
		return orderBaseInfoList;
	}

	public void setOrderBaseInfoList(List<OrderBaseInfo> orderBaseInfoList) {
		this.orderBaseInfoList = orderBaseInfoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OrderListGetResponse() {
		super();
	}
	
	
}
