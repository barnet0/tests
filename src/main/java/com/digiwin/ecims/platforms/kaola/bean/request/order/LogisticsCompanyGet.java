package com.digiwin.ecims.platforms.kaola.bean.request.order;

import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.order.LogisticsCompanysResponse;

public class LogisticsCompanyGet extends KaolaBaseRequest<LogisticsCompanysResponse> {

	@Override
	public Map<String, String> getApiParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMType() {
		// TODO Auto-generated method stub
		return "kaola.logistics.companies.get";
	}

	@Override
	public Class<LogisticsCompanysResponse> getResponseClass() {
		// TODO Auto-generated method stub
		return LogisticsCompanysResponse.class;
	}

}
