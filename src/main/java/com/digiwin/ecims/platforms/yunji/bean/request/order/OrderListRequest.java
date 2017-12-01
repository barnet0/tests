package com.digiwin.ecims.platforms.yunji.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yunji.bean.request.YunjiBaseRequest;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderListResponse;

import net.sf.json.JSONObject;

public class OrderListRequest extends YunjiBaseRequest<OrderListResponse> {

	private String startTime;
	private String endTime;
	private int  pageSize;
	private int  pageNo;
	private String status;
	private int  queryType = 1;
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	@Override
	public Map<String, String> getApiParams() {
		// TODO Auto-generated method stub
		Map<String, String> apiParams = new HashMap<String,String>();
		apiParams.put("startTime", getStartTime());
		apiParams.put("endTime", getEndTime());
		apiParams.put("status", getStatus());
		apiParams.put("pageSize", getPageSize().toString());
		apiParams.put("pageNo", getPageNo().toString());
		apiParams.put("queryType", getQueryType().toString());
		return apiParams;
	}
	
	@Override
	public String getApiParamsJson(){
		OrderListApiParamJson apiJsonParam = new OrderListApiParamJson(getStartTime(), getEndTime(), getPageSize(), getPageNo(), getStatus());
		return JSONObject.fromObject(apiJsonParam).toString();
	}

	@Override
	public String getMType() {
		return "order.list";
	}

	@Override
	public Class<OrderListResponse> getResponseClass() {

		return OrderListResponse.class;
	}

}
