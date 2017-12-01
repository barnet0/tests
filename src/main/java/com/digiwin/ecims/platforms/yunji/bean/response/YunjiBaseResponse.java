package com.digiwin.ecims.platforms.yunji.bean.response;

import java.util.List;




public abstract class YunjiBaseResponse {
	private int code;

	private String desc;
	

	private String responseBody;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

}
