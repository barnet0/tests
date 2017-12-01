package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 自定义规格列表，一个产品最多十个自定义规格
 * @author 维杰
 *
 */
public class ItemSpecSelfDefUpdate {

//	必须	自定义规格属性值id	属性值从1000开始往上递增(包括1000)；示例值：1000,1001
	private String attrValId;
//	必须	自定义规格名称	自定义规格名称不允许重复；示例值：size
	private String attrValName;
//	可选	自定义规格图片	可通过上传图片接口(dh.album.img.upload)获取；示例值：albu_615065096_00;
	private String picUrl;
	
	public String getAttrValId() {
		return attrValId;
	}
	public void setAttrValId(String attrValId) {
		this.attrValId = attrValId;
	}
	public String getAttrValName() {
		return attrValName;
	}
	public void setAttrValName(String attrValName) {
		this.attrValName = attrValName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public ItemSpecSelfDefUpdate() {
		super();
	}
	
	
}
