package com.digiwin.ecims.platforms.yunji.bean.request.logistic;

import java.util.List;

import com.digiwin.ecims.platforms.yunji.bean.domain.logistic.LogisticList;

public class LogisticApiParamJson {
	

	private  String orderId;
	private  int status;
	private  List<LogisticList> logisticsList;
	
	public LogisticApiParamJson(String id, int status, List<LogisticList> logisList) {
		this.orderId = id;
		this.status = status;
		this.logisticsList = logisList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<LogisticList> getLogisticsList() {
		return logisticsList;
	}
	public void setLogisticsList(List<LogisticList> logisticsList) {
		this.logisticsList = logisticsList;
	}
	
}
