package com.digiwin.ecims.platforms.yunji.bean.response.refund;

import java.util.List;

import com.digiwin.ecims.platforms.yunji.bean.domain.refund.ReturnOrder;
import com.digiwin.ecims.platforms.yunji.bean.response.YunjiBaseResponse;

public class OrderRefundListResponse extends YunjiBaseResponse{
	private int total;
	private List<ReturnOrder> returnorderlist;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<ReturnOrder> getReturnorderlist() {
		return returnorderlist;
	}
	public void setReturnorderlist(List<ReturnOrder> returnorderlist) {
		this.returnorderlist = returnorderlist;
	}
	
	
	
}
