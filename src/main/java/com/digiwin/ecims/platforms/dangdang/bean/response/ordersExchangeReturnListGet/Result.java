package com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
		
	@XmlElement(name = "operCode")
	private String operCode;
	
	@XmlElement(name = "operation")
	private String operation;
	
	public Result() {
		
	}

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

		
}