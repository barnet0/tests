package com.digiwin.ecims.platforms.yunji.bean.response.refund;

import java.util.List;

import com.digiwin.ecims.platforms.yunji.bean.domain.refund.ReturnOrder;
import com.digiwin.ecims.platforms.yunji.bean.response.YunjiBaseResponse;

public class OrderRefundDetailResponse extends YunjiBaseResponse {
	private List<ReturnOrder> data;

	public List<ReturnOrder> getData() {
		return data;
	}

	public void setData(List<ReturnOrder> data) {
		this.data = data;
	}


	
	
}
