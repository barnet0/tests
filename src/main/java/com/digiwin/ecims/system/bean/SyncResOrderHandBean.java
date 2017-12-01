package com.digiwin.ecims.system.bean;

/**
 * hand error msg 的載體
 * 手动同步/推送的操作结果的实体类
 * @author Xavier
 *
 */
public class SyncResOrderHandBean {
//	isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''

	public String isSuccess;
	public String resultSize;
	public String errCode;
	public String errMsg;
	
	public SyncResOrderHandBean(){
		
	}
	
	/**
	 *系統執行失則調用
	 * @param errMsg
	 */
	public SyncResOrderHandBean(String errMsg) {
		this.isSuccess = "1";
		this.resultSize = "0";
		this.errCode = "digiwin-sys-error";
		this.errMsg = errMsg;
	}
	
	/**
	 * 執行成功調用
	 * @param isSuccess
	 * @param resultSize
	 */
	public SyncResOrderHandBean(boolean isSuccess, String resultSize) {
		this.isSuccess = isSuccess ? "0" : "1";
		this.resultSize = resultSize;
		this.errCode = "";
		this.errMsg = "";
	}
	
	/**
	 * api 回傳訊息調用
	 * @param isSuccess
	 * @param resultSize
	 * @param errCode
	 * @param errMsg
	 */
	public SyncResOrderHandBean(boolean isSuccess, String resultSize, String errCode, String errMsg) {
		this.isSuccess = isSuccess ? "0" : "1";
		this.resultSize = resultSize;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getResultSize() {
		return resultSize;
	}

	public void setResultSize(String resultSize) {
		this.resultSize = resultSize;
	}
	
}
