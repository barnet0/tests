package com.digiwin.ecims.platforms.kaola.bean.response.order;

import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

public class LogisticsCompanyDeliverResponse extends KaolaBaseResponse{

	private String order_id;
	private String modify_time;
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	
}
