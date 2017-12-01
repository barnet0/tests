package com.digiwin.ecims.platforms.yunji.bean.request.logistic;

import java.util.List;
import java.util.Map;

import com.digiwin.ecims.platforms.yunji.bean.domain.logistic.LogisticList;
import com.digiwin.ecims.platforms.yunji.bean.request.YunjiBaseRequest;
import com.digiwin.ecims.platforms.yunji.bean.response.logistic.LogisticResponse;

import net.sf.json.JSONObject;

public class LogisticRequest extends YunjiBaseRequest<LogisticResponse>{

	private  String orderId;
	private  int status;
	private  List<LogisticList> logisticsList;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<LogisticList> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<LogisticList> logisticsList) {
		this.logisticsList = logisticsList;
	}

	@Override
	public Map<String, String> getApiParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMType() {
		return "logistic.sync";
	}

	@Override
	public Class<LogisticResponse> getResponseClass() {
		// TODO Auto-generated method stub
		return LogisticResponse.class;
	}

	@Override
	public String getApiParamsJson() {
		LogisticApiParamJson apiJsonParamLogistic = new LogisticApiParamJson(getOrderId(), getStatus(), getLogisticsList());
		return JSONObject.fromObject(apiJsonParamLogistic).toString();
	}

}
