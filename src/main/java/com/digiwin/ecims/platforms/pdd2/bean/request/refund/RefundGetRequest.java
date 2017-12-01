package com.digiwin.ecims.platforms.pdd2.bean.request.refund;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.refund.RefundGetResponse;

/**
 * 
 * @author cjp
 *
 */
public class RefundGetRequest extends Pdd2BaseRequest<RefundGetResponse> {
	
     private String after_sales_status;
     private String after_sales_type;
     private String start_updated_at;
     private String end_updated_at;
     
 	private int page_size;
    private int page;
    
     public String getAfter_sales_status() {
		return after_sales_status;
	}
	public void setAfter_sales_status(String after_sales_status) {
		this.after_sales_status = after_sales_status;
	}
	public String getAfter_sales_type() {
		return after_sales_type;
	}
	public void setAfter_sales_type(String after_sales_type) {
		this.after_sales_type = after_sales_type;
	}
	public String getStart_updated_at() {
		return start_updated_at;
	}
	public void setStart_updated_at(String start_updated_at) {
		this.start_updated_at = start_updated_at;
	}
	public String getEnd_updated_at() {
		return end_updated_at;
	}
	public void setEnd_updated_at(String end_updated_at) {
		this.end_updated_at = end_updated_at;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	

	public Integer getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}


	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();
	    apiParams.put("after_sales_status", getAfter_sales_status());
	    apiParams.put("after_sales_type", getAfter_sales_type());
	    apiParams.put("start_updated_at", getStart_updated_at());
	    apiParams.put("end_updated_at", getEnd_updated_at());	
	    apiParams.put("page", getPage().toString());	 
	    apiParams.put("page_size", getPage_size().toString());

		return apiParams;
	}
	@Override
	public String getMType() {
		return "pdd.refund.list.increment.get";
	}
	@Override
	public Class<RefundGetResponse> getResponseClass() {
		return RefundGetResponse.class;
	}

}
