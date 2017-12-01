package com.digiwin.ecims.platforms.kaola.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.order.LogisticsCompanyDeliverResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderGetResponse;


public class LogisticsCompanyDeliverRequest extends KaolaBaseRequest<LogisticsCompanyDeliverResponse>{
	private String order_id;
	private String express_company_code;
	private String express_no;
	private String sku_info;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getExpress_company_code() {
		return express_company_code;
	}
	public void setExpress_company_code(String express_company_code) {
		this.express_company_code = express_company_code;
	}
	public String getExpress_no() {
		return express_no;
	}
	public void setExpress_no(String express_no) {
		this.express_no = express_no;
	}
	public String getSku_info() {
		return sku_info;
	}
	public void setSku_info(String sku_info) {
		this.sku_info = sku_info;
	}
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();	
	    apiParams.put("order_id", getOrder_id());
	    apiParams.put("express_company_code", getExpress_company_code());
	    apiParams.put("express_no", getExpress_no());
	    apiParams.put("sku_info", getSku_info());
		return apiParams;
	}
	@Override
	public String getMType() {
		return "kaola.logistics.deliver";
	}
	@Override
	public Class<LogisticsCompanyDeliverResponse> getResponseClass() {
		return LogisticsCompanyDeliverResponse.class;
	}
}
