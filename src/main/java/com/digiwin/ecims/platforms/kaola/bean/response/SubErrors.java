package com.digiwin.ecims.platforms.kaola.bean.response;

/**
 * 考拉返回的错误
 * 
 * @author cjp
 *
 */
public class SubErrors {
	private String code;

	private String msg;

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}
}
