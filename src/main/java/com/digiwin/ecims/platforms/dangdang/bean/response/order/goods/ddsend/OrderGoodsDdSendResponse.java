package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class OrderGoodsDdSendResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;

	@XmlElement(name = "time")
	private String time;

	@XmlElement(name = "Result")
	private Result result;

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getFunctionID() {
		return this.functionID;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return this.time;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Result getResult() {
		return this.result;
	}

}
