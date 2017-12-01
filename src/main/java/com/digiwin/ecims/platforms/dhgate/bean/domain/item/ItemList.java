package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品基本信息
 * @author 维杰
 *
 */
public class ItemList {

//	必须	是否如实描述:1是,0否	示例值：1
	private Integer accuratelyDescribe;
//	必须	产品展示类目	示例值：019011004001
	private String cateDispId;
//	必须	发布类目叶子类目编号	示例值：019024004002
	private String catePubId;
//	必须	产品有效期	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String expireDate;
//	必须	产品首图地址	http://image.dhgate.com/imageurl/1.0x0.jpg,变量imageurl替换为实际内容即可访问；示例值：images/no_photo.gif
	private String imgUrl;
//	必须	产品是否免运费	0:免运费,1:非免运费；示例值：1
	private Integer isFreeShip;
//	必须	产品编码	示例值：202325055
	private Long itemCode;
//	必须	产品组id	示例值：1000001（如果有叶子节点为叶子节点id）
	private String itemGroupId;
//	必须	产品名称	示例值：dengdeng
	private String itemName;
//	必须	产品最终页URL	通过该使用URL链接到买家看到的产品最终页；示例值：clob_180005461_00；
	private String itemUrl;
//	必须	产品最近修改时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String operateDate;
//	必须	运输物流模板Id	示例值：1000001
	private String shippingModelId;
//	必须	产品简短描述	示例值：denuyty
	private String shortDes;
//	必须	产品状态	100000=未定义;100100=上架产品;100200=待审核产品;100300=审核未通过产品;100400=下架产品;100500=品牌商投诉产品;100600=疑似侵权产品;示例值：100100
	private String state;
//	必须	未审核通过原因	未审核通过原因
	private String unpassCause;
//	必须	产品上架时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String upDate;
//	必须	产品有效期	单位为天；（当天时间+90天）;示例值：90
	private Integer vaildDay;
//	必须	下架操作类型	1=自主下架操作 ,2=有效期下架自动程序 ,3=强制下架问题产品 , 5=备货售完下架
	private Integer withDrawalType;
	
	public Integer getAccuratelyDescribe() {
		return accuratelyDescribe;
	}
	public void setAccuratelyDescribe(Integer accuratelyDescribe) {
		this.accuratelyDescribe = accuratelyDescribe;
	}
	public String getCateDispId() {
		return cateDispId;
	}
	public void setCateDispId(String cateDispId) {
		this.cateDispId = cateDispId;
	}
	public String getCatePubId() {
		return catePubId;
	}
	public void setCatePubId(String catePubId) {
		this.catePubId = catePubId;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getIsFreeShip() {
		return isFreeShip;
	}
	public void setIsFreeShip(Integer isFreeShip) {
		this.isFreeShip = isFreeShip;
	}
	public Long getItemCode() {
		return itemCode;
	}
	public void setItemCode(Long itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemGroupId() {
		return itemGroupId;
	}
	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getShippingModelId() {
		return shippingModelId;
	}
	public void setShippingModelId(String shippingModelId) {
		this.shippingModelId = shippingModelId;
	}
	public String getShortDes() {
		return shortDes;
	}
	public void setShortDes(String shortDes) {
		this.shortDes = shortDes;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUnpassCause() {
		return unpassCause;
	}
	public void setUnpassCause(String unpassCause) {
		this.unpassCause = unpassCause;
	}
	public String getUpDate() {
		return upDate;
	}
	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}
	public Integer getVaildDay() {
		return vaildDay;
	}
	public void setVaildDay(Integer vaildDay) {
		this.vaildDay = vaildDay;
	}
	public Integer getWithDrawalType() {
		return withDrawalType;
	}
	public void setWithDrawalType(Integer withDrawalType) {
		this.withDrawalType = withDrawalType;
	}
	
	public ItemList() {
		super();
	}
	
	
}
