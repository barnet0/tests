package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

/**
 * 产品包装信息
 * @author 维杰
 *
 */
public class ItemPackageUpdate {

//	必须	产品包装后重量	以KG为单位；
	private Double grossWeight;
//	必须	产品包装后高度	以cm为单位；示例值：50
	private Double height;
//	可选	产品阶梯重量	当您选择阶梯重量计费，系统将忽略计算您产品的体积重量；例如，某产品单个产品包装后的重量是2KG，2件产品包装后的实际重量是3KG，3件产品包装后的实际重量是4KG。 若您不使用自定义计重：如果买家购买3件产品，那么系统将按照3*2=6KG的产品重量来计算买家需要支付的运费。若您使用自定义计重：且将产品的重量信息设置为：买家购买1件产品，就按2KG的重量计算运费。买家每多买一件产品，重量增加1KG。同样，如果买家购买3件产品，那么系统将按照2+(3-1)*1=4 KG的产品重量来计算买家需要支付的运费。包含以下属性：baseQt=基本数量，stepQt=阶梯数量，stepWeight=阶梯重量
	private ItemWeightRangeUpdate itemWeigthRange;
//	必须	产品包装后长度	以cm为单位；示例值：50
	private Double length;
//	 必须	产品销售单位ID	可通过获取单位列表（dh.base.measures.get）接口获得示例值：套、打、包……
	private String measureId;
//	必须	按包卖时每包产品数量	按包卖时数量大于等于2，不按包卖时数量为1
	private Integer packingQuantity;
//	必须	产品包装后宽度	以cm为单位；示例值：50
	private Double width;
	
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
	public ItemWeightRangeUpdate getItemWeigthRange() {
		return itemWeigthRange;
	}
	public void setItemWeigthRange(ItemWeightRangeUpdate itemWeigthRange) {
		this.itemWeigthRange = itemWeigthRange;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public String getMeasureId() {
		return measureId;
	}
	public void setMeasureId(String measureId) {
		this.measureId = measureId;
	}
	public Integer getPackingQuantity() {
		return packingQuantity;
	}
	public void setPackingQuantity(Integer packingQuantity) {
		this.packingQuantity = packingQuantity;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	
	public ItemPackageUpdate() {
		super();
	}
	
	
}
