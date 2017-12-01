package com.digiwin.ecims.platforms.kaola.bean.response;

import java.util.List;

/**
 * 
 * @author cjp 2017/5/26
 *
 */
public abstract class KaolaBaseResponse {
	private String code;

	private String msg;

	private String responseBody;

	private Integer result;

	private List<SubErrors> subErrors ;

	public void setSubErrors(List<SubErrors> subErrors) {
		this.subErrors = subErrors;
	}

	public List<SubErrors> getSubErrors() {
		return this.subErrors;
	}

	/**
	 * @return the result
	 */
	public Integer getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Integer result) {
		this.result = result;
	}

	/**
	 * @return the Code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param Code
	 *            the Code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the Msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param Msg
	 *            the Msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
}
