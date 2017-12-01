package com.digiwin.ecims.platforms.yunji.bean.request.order;

public class OrderListApiParamJson {
	private String startTime;
	private String endTime;
	private int  pageSize;
	private int  pageNo;
	private String status;
	private int  queryType;
	
	public OrderListApiParamJson(String sTime, String eTime, Integer pSize, Integer pNo, String status) {
		this.startTime = sTime;
		this.endTime = eTime;
		this.pageSize = pSize;
		this.pageNo = pNo;
		this.status = status;
	}
	public OrderListApiParamJson(){
		
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getQueryType() {
		return queryType;
	}
	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}
}
