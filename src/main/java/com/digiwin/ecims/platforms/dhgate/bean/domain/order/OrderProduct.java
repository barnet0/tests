package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

/**
 * 订单产品信息
 * @author 维杰
 *
 */
public class OrderProduct {

//	 必须	买家备注	示例值：易碎，轻拿轻放
	private String buyerRemark;
//	必须	产品类目	示例值：Martial Arts Clothing, Shoes & Accessories
	private String categoryName;
//	必须	包装重量	示例值：10.00
	private Double grossWeight;
//	必须	产品包装尺寸:高	单位：cm，示例值：10.00
	private Double height;
//	必须	产品属性	示例值：颜色、大小、尺码等
	private String itemAttr;
//	必须	产品编号	产品最终页的URL中有产品编码；示例值:184942450
	private String itemcode;
//	必须	产品数量	示例值：10,如果单位是件，则为10件，如果单位是包，则是10包
	private Long itemCount;
//	必须	产品图片URL	示例值：100x100/albu_255699841_00
	private String itemImage;
//	必须	产品名称	示例值:Bluetooth Mini Speakers
	private String itemName;
//	必须	产品单价	示例值：100.00
	private Double itemPrice;
//	必须	产品地址URL	示例值：product/test-20130828001/151000415.html
	private String itemUrl;
//	必须	产品包装尺寸:长	单位：cm，示例值：10.00
	private Double length;
//	必须	商品售卖单位	示例值：包、件、套、千克、千米
	private String measureName;
//	必须	产品打包数量	大于1表示按包买，同时也代表每包的数量,<=1表示非按包买，itemCount代表购买数量，示例值：10
	private Long packingQuantity;
//	必须	商品编码	示例值：W00000001
	private String skuCode;
//	必须	产品包装尺寸:宽	单位：cm，示例值：10.00
	private Double width;
	
	public String getBuyerRemark() {
		return buyerRemark;
	}
	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public String getItemAttr() {
		return itemAttr;
	}
	public void setItemAttr(String itemAttr) {
		this.itemAttr = itemAttr;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public Long getItemCount() {
		return itemCount;
	}
	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemUrl() {
		return itemUrl;
	}
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public String getMeasureName() {
		return measureName;
	}
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	public Long getPackingQuantity() {
		return packingQuantity;
	}
	public void setPackingQuantity(Long packingQuantity) {
		this.packingQuantity = packingQuantity;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	
	public OrderProduct() {
		super();
	}
	
	
}
