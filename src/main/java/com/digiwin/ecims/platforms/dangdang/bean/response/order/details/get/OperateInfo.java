package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OperateInfo {
	
	@XmlElement(name = "operateRole")
	private String operateRole;
	
	@XmlElement(name = "operateTime")
	private String operateTime;
	
	@XmlElement(name = "operateDetails")
	private String operateDetails;
	
	public OperateInfo() {
		
	}

	public String getOperateRole() {
		return operateRole;
	}

	public void setOperateRole(String operateRole) {
		this.operateRole = operateRole;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateDetails() {
		return operateDetails;
	}

	public void setOperateDetails(String operateDetails) {
		this.operateDetails = operateDetails;
	}
	

		
}