package com.digiwin.ecims.platforms.dhgate.bean.domain.api;

/**
 * 操作返回信息
 * @author 维杰
 *
 */
public class Result {

//	必须	操作失败原因	操作失败原因；示例值：产品itemCode不存在
	private String failReason;
//	必须	是否操作成功	true=成功，false=失败；示例值：false
	private Boolean isSuccess;
//	必须	产品itemCode	示例值：154002234
	private String itemCode;
	
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public Result() {
		super();
	}
	
	
}
