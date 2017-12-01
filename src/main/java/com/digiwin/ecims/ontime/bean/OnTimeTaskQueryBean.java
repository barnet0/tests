package com.digiwin.ecims.ontime.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OnTimeTaskQueryBean implements Serializable {
	
	private String code;

	private String name;
	
	private String status;

	public OnTimeTaskQueryBean(){
		
	}
	
	public OnTimeTaskQueryBean(String code, String name, String status) {
		super();
		this.code = code;
		this.name = name;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

