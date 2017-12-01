package com.digiwin.ecims.core.bean.response;

import java.util.ArrayList;
import java.util.List;


public class ResponseError {
	private String code;
	private String msg;
	
	private String executeStartDate; //執行的 startDate
	private String executeEndDate;   //執行的 end Date
	private int errorPage; //出錯頁
	
	List<ResponseError> detailErrors;
		
	public ResponseError() {
		super();
	}
	
	public ResponseError(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public void addDetailErrors(ResponseError res) {
		if (detailErrors == null) {
			this.detailErrors = new ArrayList<ResponseError>();
		}
		detailErrors.add(res);
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "\n\tcode: " + getCode() + 
				"\n\tmsg: " + getMsg();
	}

	public String getExecuteStartDate() {
		return executeStartDate;
	}

	public void setExecuteStartDate(String executeStartDate) {
		this.executeStartDate = executeStartDate;
	}

	public String getExecuteEndDate() {
		return executeEndDate;
	}

	public void setExecuteEndDate(String executeEndDate) {
		this.executeEndDate = executeEndDate;
	}

	public int getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(int errorPage) {
		this.errorPage = errorPage;
	}
	
}
