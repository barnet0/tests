package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class OrderGoodsSendResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "Result")
	private Result result;

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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	
		
}