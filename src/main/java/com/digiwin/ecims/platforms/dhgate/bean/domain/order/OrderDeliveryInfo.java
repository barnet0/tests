package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

/**
 * 订单发货信息
 * @author 维杰
 *
 */
public class OrderDeliveryInfo {

//	必须	投诉状态	0：投诉已取消，2：真实运单号，3：请更新运单号，4：调查后真实运单号，5：已处罚
	private String complaintStatus;
//	必须	发货时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String deliveryDate;
//	必须	运单号	示例值：1Z68A9X70467731838
	private String deliveryNo;
//	必须	修改后运单号	没修改运单号此字段为空
	private String newDeliveryNo;
//	必须	修改后备注	已发货，附赠小赠品等等
	private String newRemark;
//	必须	修改后物流方式	没有修改时为空
	private String newShippingType;
//	必须	处理结果	如买家来信要求取消、真实运单号、重复投诉等
	private String processingResults;
//	必须	备注	已发货，附赠小赠品等等
	private String remark;
//	必须	物流方式(卖家填写的)	示例值：UPS,D-LINK等
	private String shippingType;
	
	public String getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public String getNewDeliveryNo() {
		return newDeliveryNo;
	}
	public void setNewDeliveryNo(String newDeliveryNo) {
		this.newDeliveryNo = newDeliveryNo;
	}
	public String getNewRemark() {
		return newRemark;
	}
	public void setNewRemark(String newRemark) {
		this.newRemark = newRemark;
	}
	public String getNewShippingType() {
		return newShippingType;
	}
	public void setNewShippingType(String newShippingType) {
		this.newShippingType = newShippingType;
	}
	public String getProcessingResults() {
		return processingResults;
	}
	public void setProcessingResults(String processingResults) {
		this.processingResults = processingResults;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	
	public OrderDeliveryInfo() {
		super();
	}
	
	
}
