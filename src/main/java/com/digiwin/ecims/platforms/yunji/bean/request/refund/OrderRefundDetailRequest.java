package com.digiwin.ecims.platforms.yunji.bean.request.refund;

import java.util.Map;

import com.digiwin.ecims.platforms.yunji.bean.request.YunjiBaseRequest;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundDetailResponse;

import net.sf.json.JSONObject;


public class OrderRefundDetailRequest extends YunjiBaseRequest<OrderRefundDetailResponse>{

	private String orderId;
	private String refundId;
	
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

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
		return "order.refund.detail";
	}

	@Override
	public Class<OrderRefundDetailResponse> getResponseClass() {
		// TODO Auto-generated method stub
		return OrderRefundDetailResponse.class;
	}

	@Override
	public String getApiParamsJson() {
		// TODO Auto-generated method stub
		OrderRefundDetailApiParamJson apiJsonParam = new OrderRefundDetailApiParamJson(getRefundId(),getOrderId());
		return JSONObject.fromObject(apiJsonParam).toString();
	}

}
