package com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResultErr {
	
	@XmlElement(name = "operCode")
	private String operCode;
	
	@XmlElement(name = "operation")
	private String operation;
	
	public ResultErr() {
		
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