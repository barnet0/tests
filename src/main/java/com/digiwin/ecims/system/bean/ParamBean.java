package com.digiwin.ecims.system.bean;

import java.util.Date;

public class ParamBean {
	
	private String processType;
	private String callMethod;
	private String storeId;
	private String storeType;
	private Integer conditionType;
	private String orderId;
	private String startDate;
	private String endDate;
	private Date nowDate;
	private String param;
	
	public ParamBean(String processType, String callMethod,String storeId, String storeType, 
			Integer conditionType, String orderId, 
			String startDate, String endDate, Date nowDate){
		this.processType = processType;
		this.callMethod = callMethod;
		this.storeId = storeId;
		this.storeType = storeType;
		this.conditionType = conditionType;
		this.orderId = orderId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nowDate = nowDate;
	}
	
	public ParamBean(String processType, String callMethod, String param, Date nowDate){
		this.processType = processType;
		this.callMethod = callMethod;
		this.param = param;
		this.nowDate = nowDate;
	}
	
	public ParamBean(String callMethod, String param, Date nowDate) {
		this.callMethod = callMethod;
		this.param = param;
		this.nowDate = nowDate;
	}
	
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getCallMethod() {
		return callMethod;
	}

	public void setCallMethod(String callMethod) {
		this.callMethod = callMethod;
	}

	public String getStoreId() {
		return storeId;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String getStoreType() {
		return storeType;
	}
	
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	public Integer getConditionType() {
		return conditionType;
	}
	
	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
}
