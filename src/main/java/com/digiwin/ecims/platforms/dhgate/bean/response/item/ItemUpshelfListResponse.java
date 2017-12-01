package com.digiwin.ecims.platforms.dhgate.bean.response.item;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.api.Result;

public class ItemUpshelfListResponse extends BizStatusResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8415974160894138218L;

	private boolean isSuccess;
	
	private List<Result> resultList;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public void setResultList(List<Result> resultList) {
		this.resultList = resultList;
	}

	public ItemUpshelfListResponse() {
		super();
	}
	
	
}
