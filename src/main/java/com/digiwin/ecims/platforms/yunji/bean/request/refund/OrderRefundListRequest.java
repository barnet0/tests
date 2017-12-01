package com.digiwin.ecims.platforms.yunji.bean.request.refund;

import java.util.Map;
import com.digiwin.ecims.platforms.yunji.bean.request.YunjiBaseRequest;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundListResponse;

import net.sf.json.JSONObject;

public class OrderRefundListRequest extends YunjiBaseRequest<OrderRefundListResponse>{
	private String startTime;
	private String endTime;
	private int pageSize;
	private int pageNo;
	private String returnStatus;
	private int queryType;
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
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
	@Override
	public Map<String, String> getApiParams() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getMType() {
		// TODO Auto-generated method stub
		return "order.refund.list";
	}
	@Override
	public Class<OrderRefundListResponse> getResponseClass() {
		// TODO Auto-generated method stub
		return OrderRefundListResponse.class;
	}
	@Override
	public String getApiParamsJson() {
		OrderRefundApiParamJson apiJsonParam = new OrderRefundApiParamJson(getStartTime(), getEndTime(), getPageSize(), getPageNo(), getReturnStatus());
		return JSONObject.fromObject(apiJsonParam).toString();
	}
	
	
}
