package com.digiwin.ecims.platforms.kaola.bean.response.order;

import com.digiwin.ecims.platforms.kaola.bean.domain.order.logistics_company;
import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

/**
 * 快递公司信息
 * @author cjp
 *
 */
public class LogisticsCompanysResponse extends KaolaBaseResponse {
	private logistics_company logisticsCompany=new logistics_company();

	public logistics_company getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(logistics_company logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	
	
}
