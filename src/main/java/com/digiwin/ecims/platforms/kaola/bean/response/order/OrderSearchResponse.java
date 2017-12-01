package com.digiwin.ecims.platforms.kaola.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.kaola.bean.domain.order.Order;
import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

public class OrderSearchResponse  extends KaolaBaseResponse{

	private List<Order> orders;
	private int  total_count;
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	
}
