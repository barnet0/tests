package com.digiwin.ecims.system.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ecinfos")
public class EcPlatformInfo {
	@JsonProperty("ecCode")
	private String ecCode;
	
	@JsonProperty("ecName")
	private String ecName;
	
	
	public EcPlatformInfo() {
		super();
	}
	
	public EcPlatformInfo(String ecCode, String ecName) {
		super();
		this.ecCode = ecCode;
		this.ecName = ecName;
	}
	
	public String getEcCode() {
		return ecCode;
	}
	
	public void setEcCode(String ecCode) {
		this.ecCode = ecCode;
	}
	
	public String getEcName() {
		return ecName;
	}
	
	public void setEcName(String ecName) {
		this.ecName = ecName;
	}
	
	
}
