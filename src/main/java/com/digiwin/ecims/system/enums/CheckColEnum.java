package com.digiwin.ecims.system.enums;

/**
 * 推送资料时选取资料的条件栏位
 * @author 维杰
 * @since 2015.10.12
 */
public enum CheckColEnum {

	IS_MANUALLY_ORDER_BY_CREATE_DATE("aoms006"),
	IS_MANUALLY_ORDER_BY_MODIFY_DATE("modified"),
	IS_MANUALLY_ORDER_BY_PAY_DATE("aoms024"),
	
	IS_MANUALLY_REFUND_BY_CREATE_DATE("aoms041"),
//	IS_MANUALLY_REFUND_BY_MODIFY_DATE("modified"),
	
	// 商品的创建时间应该取上架时间，但是苏宁API同步回来的商品资料没有上架时间，所以无法使用这个栏位作为过滤条件
//	IS_MANUALLY_ITEM_BY_CREATE_DATE(""),
//	IS_MANUALLY_ITEM_BY_MODIFY_DATE("modified"),
	
	/**
	 * 正常定时排程使用的过滤条件栏位 
	 */
	IS_SCHEDULE("aomsmoddt");
	
	private String colName;
	private CheckColEnum(String colName) {
		this.colName = colName;
	}
	
	public String toString() {
		return this.colName;
	}
}
