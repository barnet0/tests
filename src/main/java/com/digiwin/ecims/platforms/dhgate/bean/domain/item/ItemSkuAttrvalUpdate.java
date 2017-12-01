package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品SKU属性值列表
 * @author 维杰
 *
 */
public class ItemSkuAttrvalUpdate {

//	必须	sku属性id	类目规格对应的类目属性id,自定一个规格默认值为：9999
	private Integer attrId;
//	必须	sku属性值id	类目规格值对应的类目属性值id,自定一个规格值为从1000向上累加，第一个值的id为1000
	private Integer attrValId;
//	必须	规格类型	1 产品规格,3自定义规格；示例值：3
	private Integer sizeSpecType;
	
	public Integer getAttrId() {
		return attrId;
	}
	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}
	public Integer getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(Integer attrValId) {
		this.attrValId = attrValId;
	}
	public Integer getSizeSpecType() {
		return sizeSpecType;
	}
	public void setSizeSpecType(Integer sizeSpecType) {
		this.sizeSpecType = sizeSpecType;
	}
	
	public ItemSkuAttrvalUpdate() {
		super();
	}
	
}
