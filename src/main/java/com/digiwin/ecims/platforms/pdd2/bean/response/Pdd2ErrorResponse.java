package com.digiwin.ecims.platforms.pdd2.bean.response;

public class Pdd2ErrorResponse {
	private Integer error_code;

	private String error_msg;

	/**
	 * @return the error_code
	 */
	public Integer getError_code() {
		return error_code;
	}

	/**
	 * @param error_code
	 *            the error_code to set
	 */
	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}

	/**
	 * @return the error_msg
	 */
	public String getError_msg() {
		return error_msg;
	}

	/**
	 * @param error_msg
	 *            the error_msg to set
	 */
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

}
