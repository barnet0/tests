package com.digiwin.ecims.platforms.kaola.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderSearchResponse;


public class OrderSearchRequest extends KaolaBaseRequest<OrderSearchResponse> {

	private int order_status;
	private int date_type;
	private String start_time;
	private String end_time;
	private String order_id;
	private int page_no;
	private int page_size;
	public Integer getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public Integer getDate_type() {
		return date_type;
	}
	public void setDate_type(int date_type) {
		this.date_type = date_type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Integer getPage_no() {
		return page_no;
	}
	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();
	    apiParams.put("order_status", getOrder_status().toString());
	    apiParams.put("date_type", getDate_type().toString());
	    apiParams.put("start_time", getStart_time());
	    apiParams.put("end_time", getEnd_time());	
	    apiParams.put("order_id", getOrder_id());
	    apiParams.put("page_no", getPage_no().toString());
	    apiParams.put("page_size", getPage_size().toString());

		return apiParams;
	}
	@Override
	public String getMType() {
		return "kaola.order.search";
	}
	@Override
	public Class<OrderSearchResponse> getResponseClass() {
		return OrderSearchResponse.class;
	}
}
