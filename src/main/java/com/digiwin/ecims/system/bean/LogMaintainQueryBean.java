package com.digiwin.ecims.system.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogMaintainQueryBean implements Serializable {
	
	private String reqTime;

	private String businessType;
	
	private String isSuccess;
	
	private String exceptionType;


	public LogMaintainQueryBean(){
		
	}
	
	public LogMaintainQueryBean(String reqTime, String businessType, String isSuccess,String exceptionType) {
		super();
		this.reqTime = reqTime;
		this.businessType = businessType;
		this.isSuccess = isSuccess;
		this.exceptionType = exceptionType;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	

}

