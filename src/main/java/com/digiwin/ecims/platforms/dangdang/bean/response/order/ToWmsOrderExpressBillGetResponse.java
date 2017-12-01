package com.digiwin.ecims.platforms.dangdang.bean.response.order;

import java.util.List;

import javax.xml.bind.annotation.XmlValue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 与WMS交换当当打印面单接口数据的响应格式。请求格式使用中台与ERP的Cmd格式
 * @author 维杰
 * @since 2015.09.08
 */
public class ToWmsOrderExpressBillGetResponse {
	
	public static final String EXCEPTION_MSG_WHEN_ERROR = "<title>请求错误</title><msg>错误信息</msg>";
	
	private String exceptionMsg;
	
	private List<ToWmsOrderExpressBillGetResponseMessage> message;
	
	private boolean result;

	public ToWmsOrderExpressBillGetResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ToWmsOrderExpressBillGetResponse(String exceptionMsg,
			List<ToWmsOrderExpressBillGetResponseMessage> message,
			boolean result) {
		super();
		this.exceptionMsg = exceptionMsg;
		this.message = message;
		this.result = result;
	}

	/**
	 * @return the exceptionMsg
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/**
	 * @param exceptionMsg the exceptionMsg to set
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	/**
	 * @return the message
	 */
	public List<ToWmsOrderExpressBillGetResponseMessage> getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(List<ToWmsOrderExpressBillGetResponseMessage> message) {
		this.message = message;
	}

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
}
