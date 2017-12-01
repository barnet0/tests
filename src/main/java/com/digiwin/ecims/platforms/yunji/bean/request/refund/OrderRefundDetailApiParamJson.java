package com.digiwin.ecims.platforms.yunji.bean.request.refund;

public class OrderRefundDetailApiParamJson {
	private String orderId;
	private String refundId;
	

	public OrderRefundDetailApiParamJson(String refundId,String orderId) {
		this.orderId = orderId;
		this.refundId = refundId;
	}
	
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
	
}
