package com.digiwin.ecims.platforms.yunji.bean.request.refund;

public class OrderRefundApiParamJson {
	private String startTime;
	private String endTime;
	private int pageSize;
	private int pageNo;
	private String returnStatus;
	private int queryType;
	public OrderRefundApiParamJson(String sTime, String eTime, int pSize, int pNo, String retStatus) {
		this.startTime = sTime;
		this.endTime = eTime;
		this.pageSize = pSize;
		this.pageNo = pNo;
		this.returnStatus = retStatus;
	}
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
}
