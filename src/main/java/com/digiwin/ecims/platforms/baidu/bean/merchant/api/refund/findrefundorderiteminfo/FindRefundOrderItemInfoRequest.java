package com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class FindRefundOrderItemInfoRequest extends BaiduBaseRequest {

	private List<Integer> refundStatusList;
	private String startTime;
	private Long refundNo;
	private Long orderNo;
	private Integer pageNo;
	private Integer pageSize;
	private String mobileNumber;
	
	public List<Integer> getRefundStatusList() {
		return refundStatusList;
	}
	public void setRefundStatusList(List<Integer> refundStatusList) {
		this.refundStatusList = refundStatusList;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Long getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(Long refundNo) {
		this.refundNo = refundNo;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public FindRefundOrderItemInfoRequest() {
		super();
	}
  @Override
  public String getUrlPath() {
    return "OrderService/findRefundOrderItemInfo";
  }
	
}
