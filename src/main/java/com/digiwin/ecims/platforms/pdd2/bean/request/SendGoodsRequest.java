package com.digiwin.ecims.platforms.pdd2.bean.request;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.response.SendGoodsResponse;

public class SendGoodsRequest extends Pdd2BaseRequest<SendGoodsResponse> {

	private String order_sn;

	private String logistics_id;

	private String tracking_number;

	/**
	 * @return the orderSN
	 */
	public String getOrderSN() {
		return order_sn;
	}

	/**
	 * @param orderSN
	 *            the orderSN to set
	 */
	public void setOrderSN(String orderSN) {
		this.order_sn = orderSN;
	}

	/**
	 * @return the logistics_id
	 */
	public String getLogistics_id() {
		return logistics_id;
	}

	/**
	 * @param logistics_id
	 *            the logistics_id to set
	 */
	public void setLogistics_id(String logistics_id) {
		this.logistics_id = logistics_id;
	}

	/**
	 * @return the tracking_number
	 */
	public String getTracking_number() {
		return tracking_number;
	}

	/**
	 * @param tracking_number
	 *            the tracking_number to set
	 */
	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();
		apiParams.put("order_sn", getOrderSN());
		apiParams.put("logistics_id", getLogistics_id());
		apiParams.put("tracking_number", getTracking_number());

		return apiParams;
	}

	@Override
	public String getMType() {
		return "pdd.logistics.online.send";
	}

	@Override
	public Class<SendGoodsResponse> getResponseClass() {
		return SendGoodsResponse.class;
	}

}
