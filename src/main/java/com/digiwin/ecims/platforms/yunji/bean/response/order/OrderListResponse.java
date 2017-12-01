package com.digiwin.ecims.platforms.yunji.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.yunji.bean.domain.order.OrderInfo;
import com.digiwin.ecims.platforms.yunji.bean.response.YunjiBaseResponse;



public class OrderListResponse extends YunjiBaseResponse {
	private int total;
	private List<OrderInfo> list;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<OrderInfo> getList() {
		return list;
	}
	public void setList(List<OrderInfo> list) {
		this.list = list;
	}
	
}
