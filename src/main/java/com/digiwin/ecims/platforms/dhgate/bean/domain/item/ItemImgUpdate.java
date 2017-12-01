package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品图片列表
 * @author 维杰
 *
 */
public class ItemImgUpdate {

//	必须	图片MD5值	通过上传图片接口(dh.album.img.upload)获取；示例值：d9ec536b1792f2562c59e6ad4fd45f9f;
	private String imgMd5;
//	必须	图片url地址	通过上传图片接口(dh.album.img.upload)获取；上传第一张默认为首图;示例值：/f2/albu/g1/M00/01/02/rBVaIVPxjnCIBEuBAAB1EDXekqMAAAYbgGJ1EkAAHUo678.jpg;
	private String imgUrl;
//	必须	图片来源	3=googalshopping推广图片,其他数值=其他来源；示例值：3;
	private Integer type;

	public String getImgMd5() {
		return imgMd5;
	}
	public void setImgMd5(String imgMd5) {
		this.imgMd5 = imgMd5;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public ItemImgUpdate() {
		super();
	}
	
}
