/**
 * 20150625 create by ShangHsuan Hsu
 * 20150705 modi by ShangHsuan Hsu:	modify field names
 * 20150715 modi by ShangHsuan Hsu: 增加param fields，紀錄request下的參數
 * Command response類別
 */

package com.digiwin.ecims.core.bean.response;

import java.util.List;
import java.util.Map;

import com.digiwin.ecims.core.bean.request.CmdReq;


public class CmdRes {
	private String api;					//指令名稱
	private String ecno;				//目標店商平台
	private Map<String, String> params;	//CmdReq的params
	private String storeid;				//store id
	private boolean isSuccess;			//是否成功
	private ResponseError error;
	private List<? extends Object> returnValue;
	
	public CmdRes() {
		super();
	}
	
	/**
	 * constructor
	 * @param api API名稱
	 * @param ecno 目標電商平台代號
	 */
//	public CmdRes(String api, String ecno){
//		super();
//		this.api = api;
//		this.ecno = ecno;
//	}
	
	public CmdRes(CmdReq cmdReq, boolean isSuccess, ResponseError responseError, List<? extends Object> returnValue){
		super();
		this.api = cmdReq.getApi();
		this.ecno = cmdReq.getEcno();
		this.params = cmdReq.getParams();
		this.storeid = cmdReq.getStoreid();
		this.isSuccess = isSuccess;
		this.error = responseError;
		this.returnValue = returnValue;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getEcno() {
		return ecno;
	}

	public void setEcno(String ecno) {
		this.ecno = ecno;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public ResponseError getError() {
		return error;
	}

	public void setError(ResponseError error) {
		this.error = error;
	}

	public List<? extends Object> getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(List<? extends Object> returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public String toString() {
		return "\n\tapi: " + getApi() + 
				"\n\tecno: " + getEcno() + 
				"\n\tsuccess: " + isSuccess() + 
				"\n\terror:" + getError() + 
				"\n\treturnValue: " + getReturnValue();
	}
}
