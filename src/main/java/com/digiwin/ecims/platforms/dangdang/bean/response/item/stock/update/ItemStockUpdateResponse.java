package com.digiwin.ecims.platforms.dangdang.bean.response.item.stock.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ItemStockUpdateResponse {
	@XmlElement(name = "functionID")
	private String functionId;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "Result")
	private Result result;

	/**
	 * @return the functionId
	 */
	public String getFunctionId() {
		return functionId;
	}

	/**
	 * @param functionId the functionId to set
	 */
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
