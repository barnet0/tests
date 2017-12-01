package com.digiwin.ecims.platforms.icbc.bean.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Head {
	@XmlElement(name = "method")
	private String method;

	@XmlElement(name = "req_sid")
	private String reqSid;

	@XmlElement(name = "version")
	private String version;

	@XmlElement(name = "timestamp")
	private String timeStamp;

	@XmlElement(name = "app_key")
	private String appKey;

	@XmlElement(name = "auth_code")
	private String authCode;

	@XmlElement(name = "ret_code")
	private String returnCode;

	@XmlElement(name = "ret_msg")
	private String returnMsg;

	@XmlElement(name = "sign")
	private String sign;

	public Head() {

	};

	public Head(String method, String reqSid, String version, String timeStamp,
			String appKey, String authCode, String returnCode,
			String returnMsg, String sign) {
		this.method = method;
		this.reqSid = reqSid;
		this.version = version;
		this.timeStamp = timeStamp;
		this.appKey = appKey;
		this.authCode = authCode;
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
		this.sign = sign;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getReqSid() {
		return reqSid;
	}

	public void setReqSid(String reqSid) {
		this.reqSid = reqSid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
