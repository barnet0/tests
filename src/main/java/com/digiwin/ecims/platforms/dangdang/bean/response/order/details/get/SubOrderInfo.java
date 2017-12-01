package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SubOrderInfo {
	
	@XmlElement(name = "subOrderID")
	private String subOrderID;
	
	@XmlElement(name = "outerSubOrderID")
	private String outerSubOrderID;
	
	public SubOrderInfo() {
		
	}

	public String getSubOrderID() {
		return subOrderID;
	}

	public void setSubOrderID(String subOrderID) {
		this.subOrderID = subOrderID;
	}

	public String getOuterSubOrderID() {
		return outerSubOrderID;
	}

	public void setOuterSubOrderID(String outerSubOrderID) {
		this.outerSubOrderID = outerSubOrderID;
	}

		
}