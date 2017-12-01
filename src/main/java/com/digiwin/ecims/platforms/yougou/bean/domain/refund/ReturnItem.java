package com.digiwin.ecims.platforms.yougou.bean.domain.refund;

import java.util.List;

/**
 * 退换货实体类
 * @author 维杰
 *
 */
public class ReturnItem {

	private String warehouseName;

	private int expressFee;

	private String outOrderId;

	private String qaPerson;

	private String qaRemark;

	private String returnLogisticsName;

	private String returnExpressCode;

	private String orderSubNo;

	private List<ReturnDetail> returnDetails ;

	private String returnId;

	private String expressCode;

	private String logisticsName;

	private String qaDate;

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public int getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(int expressFee) {
		this.expressFee = expressFee;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getQaPerson() {
		return qaPerson;
	}

	public void setQaPerson(String qaPerson) {
		this.qaPerson = qaPerson;
	}

	public String getQaRemark() {
		return qaRemark;
	}

	public void setQaRemark(String qaRemark) {
		this.qaRemark = qaRemark;
	}

	public String getReturnLogisticsName() {
		return returnLogisticsName;
	}

	public void setReturnLogisticsName(String returnLogisticsName) {
		this.returnLogisticsName = returnLogisticsName;
	}

	public String getReturnExpressCode() {
		return returnExpressCode;
	}

	public void setReturnExpressCode(String returnExpressCode) {
		this.returnExpressCode = returnExpressCode;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}

	public List<ReturnDetail> getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(List<ReturnDetail> returnDetails) {
		this.returnDetails = returnDetails;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getQaDate() {
		return qaDate;
	}

	public void setQaDate(String qaDate) {
		this.qaDate = qaDate;
	}

	public ReturnItem() {
		super();
	}
	
	
}
