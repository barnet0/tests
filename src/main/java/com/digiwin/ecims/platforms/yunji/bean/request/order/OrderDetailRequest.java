package com.digiwin.ecims.platforms.yunji.bean.request.order;

import java.util.Map;

import com.digiwin.ecims.platforms.yunji.bean.request.YunjiBaseRequest;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderDetailResponse;


import net.sf.json.JSONObject;

public class OrderDetailRequest extends YunjiBaseRequest<OrderDetailResponse>{
	private String orderId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public Map<String, String> getApiParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMType() {
		// TODO Auto-generated method stub
		return "order.detail";
	}

	@Override
	public Class<OrderDetailResponse> getResponseClass() {
		return OrderDetailResponse.class;
	}

	@Override
	public String getApiParamsJson() {
		OrderDetailApiParamJson apiJsonParam = new OrderDetailApiParamJson(getOrderId());
		return JSONObject.fromObject(apiJsonParam).toString();
	}

}
