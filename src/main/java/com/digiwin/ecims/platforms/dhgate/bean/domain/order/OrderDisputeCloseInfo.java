package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

import com.digiwin.ecims.platforms.dhgate.bean.domain.order.base.OrderDisputeInfo;

/**
 * 纠纷关闭订单基础信息
 * @author 维杰
 *
 */
public class OrderDisputeCloseInfo extends OrderDisputeInfo{

//	必须	纠纷关闭时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String disputeCloseDate;

	public String getDisputeCloseDate() {
		return disputeCloseDate;
	}

	public void setDisputeCloseDate(String disputeCloseDate) {
		this.disputeCloseDate = disputeCloseDate;
	}

	public OrderDisputeCloseInfo() {
		super();
	}
	
}
