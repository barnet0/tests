package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

import com.digiwin.ecims.platforms.dhgate.bean.domain.order.base.OrderDisputeInfo;

/**
 * 纠纷开启订单基础信息
 * @author 维杰
 *
 */
public class OrderDisputeOpenInfo extends OrderDisputeInfo{

//	必须	纠纷开启时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String disputeOpenDate;
	
	public String getDisputeOpenDate() {
		return disputeOpenDate;
	}
	public void setDisputeOpenDate(String disputeOpenDate) {
		this.disputeOpenDate = disputeOpenDate;
	}
	
	public OrderDisputeOpenInfo() {
		super();
	}
	
	
}
