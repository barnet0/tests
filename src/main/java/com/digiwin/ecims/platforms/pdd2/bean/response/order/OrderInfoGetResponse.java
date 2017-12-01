package com.digiwin.ecims.platforms.pdd2.bean.response.order;

import com.digiwin.ecims.platforms.pdd2.bean.domain.order.OrderInfo;
import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;

public class OrderInfoGetResponse extends Pdd2BaseResponse {
	private OrderInfo order_info;

	public void setOrder_info(OrderInfo order_info){
	this.order_info = order_info;
	}
	public OrderInfo getOrder_info(){
	return this.order_info;
	}
}
