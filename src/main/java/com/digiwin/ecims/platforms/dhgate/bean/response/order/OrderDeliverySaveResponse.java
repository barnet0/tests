package com.digiwin.ecims.platforms.dhgate.bean.response.order;

import com.dhgate.open.client.BizStatusResponse;

public class OrderDeliverySaveResponse extends BizStatusResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6623085041846113006L;

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public OrderDeliverySaveResponse() {
		super();
	}

	
}
