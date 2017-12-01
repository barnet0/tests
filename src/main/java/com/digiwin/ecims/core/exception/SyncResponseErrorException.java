package com.digiwin.ecims.core.exception;

import com.digiwin.ecims.core.bean.response.ResponseError;

/**
 * 執行同步時, 所發生的error
 * @author Xavier
 *
 */
public class SyncResponseErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private ResponseError responseError;
	private int returnTotalRows = 0;
	
	/**
	 * 
	 * @param responseError 記錄API 所回傳的 error code, error msg
	 * @param returnTotalRows
	 */
	public SyncResponseErrorException(ResponseError responseError, int returnTotalRows) {
		super(responseError.getCode() + ","+ responseError.getMsg());
		this.responseError = responseError;
		this.returnTotalRows = returnTotalRows;
	}

	public ResponseError getResponseError() {
		return responseError;
	}

	/**
	 * 取得這次執, 所回傳的筆數
	 * @return
	 */
	public int getReturnTotalRows() {
		return returnTotalRows;
	}
}
