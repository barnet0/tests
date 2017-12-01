package com.digiwin.ecims.platforms.kaola.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderGetResponse;


public class OrderGetRequest extends KaolaBaseRequest<OrderGetResponse>{
	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();	
	    apiParams.put("order_id", getOrder_id());
		return apiParams;
	}
	@Override
	public String getMType() {
		return "kaola.order.get";
	}
	@Override
	public Class<OrderGetResponse> getResponseClass() {
		return OrderGetResponse.class;
	}
	
}
