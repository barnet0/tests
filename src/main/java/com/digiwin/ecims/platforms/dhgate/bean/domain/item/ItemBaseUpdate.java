package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品基本信息
 * @author 维杰
 *
 */
public class ItemBaseUpdate {

//	必须	产品详情内容描述	详细描述一般包含产品功能属性、产品细节图片等内容；最多80000字符；详细描述中可包含文字、图片；图片可通过上传图片接口(dh.album.img.upload)获得图片imageUrl，然后根据图片地址模板获得完整图片路径；示例值：http://image.dhgate.com/albu_834770213_00/1.0*0.jpg;
	private String htmlContent;
//	必须	产品名称	最多140个字符，示例值：FashionHat
	private String itemName;
//	必须	搜索关键字	只允许字母、字符、下划线、数字，（比如某个产品是“New White Strapless Formal Prom Wedding Dress Ball Gown”，则关键词可以填写为Prom Wedding Dress，Formal Prom Wedding Dress等）；示例值：black
	private String keyWord1;
	private String keyWord2;
	private String keyWord3;
//	必须	产品简短描述	产品参数，如：颜色、尺寸、款式、配件、贸易方式等，最多500个字符；示例值：young people like it
	private String shortDesc;
//	必须	产品视频地址	只允许输入youtube的地址；示例值：http://www.youtube.com/xxxxx
	private String videoUrl;
	
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getKeyWord1() {
		return keyWord1;
	}
	public void setKeyWord1(String keyWord1) {
		this.keyWord1 = keyWord1;
	}
	public String getKeyWord2() {
		return keyWord2;
	}
	public void setKeyWord2(String keyWord2) {
		this.keyWord2 = keyWord2;
	}
	public String getKeyWord3() {
		return keyWord3;
	}
	public void setKeyWord3(String keyWord3) {
		this.keyWord3 = keyWord3;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public ItemBaseUpdate() {
		super();
	}
	
}
