package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品阶梯重量
 * @author 维杰
 *
 */
public class ItemWeightRangeUpdate {

//	可选	基本数量	示例值：2；2KG每件
	private Long baseQt;
//	可选	阶梯数量	示例值：2；每增加2件
	private Long stepQt;
//	可选	阶梯重量	示例值：3；增加3KG
	private Double stepWeight;
	
	public Long getBaseQt() {
		return baseQt;
	}
	public void setBaseQt(Long baseQt) {
		this.baseQt = baseQt;
	}
	public Long getStepQt() {
		return stepQt;
	}
	public void setStepQt(Long stepQt) {
		this.stepQt = stepQt;
	}
	public Double getStepWeight() {
		return stepWeight;
	}
	public void setStepWeight(Double stepWeight) {
		this.stepWeight = stepWeight;
	}
	
	public ItemWeightRangeUpdate() {
		super();
	}
	
}
