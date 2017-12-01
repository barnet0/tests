package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品属性值列表
 * @author 维杰
 *
 */
public class ItemAttrVal {

//	必须	属性Id	自定义属性id默认为11；品牌属性id默认为99；其它类目属性id与对应的类目属性ID一致；自定义属性，属性值id为从1向上累加也可不填，最多可有10个自定义属性；
	private Long attrId;
//	必须	产品属性英文名称	自定义属性时必填；示例值：BRAND
	private String attrName;
//	必须	产品属性值id	自定义属性，属性值id为从1向上累加也可不填;品牌属性值默认为99;与对应的类目属性值ID一致；
	private Long attrValId;
//	必须	产品属性值英文名称	自定义属性时必填；示例值：type
	private String lineAttrvalName;
//	必须	属性值中文名称	自定义属性时必填，示例值：类型
	private String lineAttrvalNameCn;
//	必须	产品属性图片路径	自定义属性时必填，通过上传图片接口(dh.album.img.upload)获取；示例值：albu_841432510_00/1.0x0.jpg
	private String picUrl;
	
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
	public Long getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(Long attrValId) {
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
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public ItemAttrVal() {
		super();
	}
	
	
}
