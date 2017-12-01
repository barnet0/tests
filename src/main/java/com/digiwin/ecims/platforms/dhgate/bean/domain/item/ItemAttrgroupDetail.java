package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 兼容性属性列表的属性和属性值
 * @author 维杰
 *
 */
public class ItemAttrgroupDetail {

//	必须	属性Id	属性Id
	private Integer attrId;
//	必须	兼容性属性英文名称	兼容性属性英文名称
	private String attrName;
//	必须	兼容性属性中文名称	兼容性属性中文名称
	private String attrNameCn;
//	必须	属性值id	属性值id
	private Integer attrValId;
//	必须	兼容性属性值英文名称	兼容性属性值英文名称
	private String lineAttrvalName;
//	必须	兼容性属性值中文名称	兼容性属性值中文名称
	private String lineAttrvalNameCn;
	
	public Integer getAttrId() {
		return attrId;
	}
	public void setAttrId(Integer attrId) {
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
	public Integer getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(Integer attrValId) {
		this.attrValId = attrValId;
	}
	public String getLineAttrvalName() {
		return lineAttrvalName;
	}
	public void setLineAttrvalName(String lineAttrvalName) {
		this.lineAttrvalName = lineAttrvalName;
	}
	public String getLineAttrvalNameCn() {
		return lineAttrvalNameCn;
	}
	public void setLineAttrvalNameCn(String lineAttrvalNameCn) {
		this.lineAttrvalNameCn = lineAttrvalNameCn;
	}
	
	
}
