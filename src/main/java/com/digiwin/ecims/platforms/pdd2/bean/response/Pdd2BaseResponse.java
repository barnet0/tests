package com.digiwin.ecims.platforms.pdd2.bean.response;

public abstract class Pdd2BaseResponse extends Pdd2ErrorResponse{

	private String responseBody;

	private Boolean is_success;
	
	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	/**
	 * @return the is_success
	 */
	public Boolean getIs_success() {
		return is_success;
	}

	/**
	 * @param is_success
	 *            the is_success to set
	 */
	public void setIs_success(Boolean is_success) {
		this.is_success = is_success;
	}


	

}
