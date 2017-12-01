package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品折扣区间
 * @author 维杰
 *
 */
public class ItemWholesaleRangeUpdate {

//	必须	折扣率	折扣百分比，如卖家想设置产品折扣率为95折，则该入参参数应输入为0.05；示例值：0.05
	private Double discount;
//	必须	折扣限制数量	从第几件开始是什么折扣；例如：3件以上0.05折扣，即3件以上95折。
	private Integer startQty;
	
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getStartQty() {
		return startQty;
	}
	public void setStartQty(Integer startQty) {
		this.startQty = startQty;
	}
	
	public ItemWholesaleRangeUpdate() {
		super();
	}
	
	
}
