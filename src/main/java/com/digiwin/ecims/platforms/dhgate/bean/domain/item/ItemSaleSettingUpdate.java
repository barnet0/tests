package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品销售属性设置
 * @author 维杰
 *
 */
public class ItemSaleSettingUpdate {

//	必须	备货期	以天为单位，有备货的产品备货期小于等于2天，没有备货的产品为1—60天；示例值：30
	private Integer leadingTime;
//	必须	买家一次最大购买量	无备货时最大购买量必须填写，最大值10000；示例值：10
	private Integer maxSaleQty;
//	可选	最小起批量	示例值：10（折扣起止数量大于1）
	private Integer minWholesaleQty;
//	必须	设置价格类型	1分别设置价格，2 统一设置价格；若产品无规格，该字段设置为2(统一设置价格)；示例值：1；
	private Integer priceConfigType;
	
	public Integer getLeadingTime() {
		return leadingTime;
	}
	public void setLeadingTime(Integer leadingTime) {
		this.leadingTime = leadingTime;
	}
	public Integer getMaxSaleQty() {
		return maxSaleQty;
	}
	public void setMaxSaleQty(Integer maxSaleQty) {
		this.maxSaleQty = maxSaleQty;
	}
	public Integer getMinWholesaleQty() {
		return minWholesaleQty;
	}
	public void setMinWholesaleQty(Integer minWholesaleQty) {
		this.minWholesaleQty = minWholesaleQty;
	}
	public Integer getPriceConfigType() {
		return priceConfigType;
	}
	public void setPriceConfigType(Integer priceConfigType) {
		this.priceConfigType = priceConfigType;
	}
	
	public ItemSaleSettingUpdate() {
		super();
	}
	
}
