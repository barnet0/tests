package com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class OrdersRefundListResponse {
	
	@XmlElement(name = "result")
	private String result;
	
	@XmlElement(name = "resultCode")
	private String resultCode;
	
	@XmlElement(name = "resultMessage")
	private String resultMessage;
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "refundInfos")
	private RefundInfos refundInfos;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public RefundInfos getRefundInfos() {
		return refundInfos;
	}

	public void setRefundInfos(RefundInfos refundInfos) {
		this.refundInfos = refundInfos;
	}
	
	
		
}