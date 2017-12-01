package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

import java.util.List;

/**
 * 产品属性列表
 * @author 维杰
 *
 */
public class ItemAttrUpdate {

//	必须	属性Id(自定义属性为11)	自定义属性id默认为11；品牌属性id默认为99；其它类目属性id与对应的类目属性ID一致；自定义属性，属性值id为从1向上累加也可不填，最多可有10个自定义属性；
	private Long attrId;
//	必须	属性英文名称	自定义属性时必填；示例值：BRAND
	private String attrName;
//	必须	属性中文名称	自定义属性时必填，示例值：类型
	private String attrNameCn;
//	必须	是否是品牌属性	1:是,0:非；示例值：1
	private Integer isbrand;
//	必须	产品属性值列表	产品属性值列表
	private List<ItemAttrVal> itemAttrValList;
	
	public Long getAttrId() {
		return attrId;
	}
	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrNameCn() {
		return attrNameCn;
	}
	public void setAttrNameCn(String attrNameCn) {
		this.attrNameCn = attrNameCn;
	}
	public Integer getIsbrand() {
		return isbrand;
	}
	public void setIsbrand(Integer isbrand) {
		this.isbrand = isbrand;
	}
	public List<ItemAttrVal> getItemAttrValList() {
		return itemAttrValList;
	}
	public void setItemAttrValList(List<ItemAttrVal> itemAttrValList) {
		this.itemAttrValList = itemAttrValList;
	}
	
	public ItemAttrUpdate() {
		super();
	}
	
}
