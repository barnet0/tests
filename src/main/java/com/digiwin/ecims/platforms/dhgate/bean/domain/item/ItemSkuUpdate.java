package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

import java.util.List;

/**
 * 产品SKU列表
 * @author 维杰
 *
 */
public class ItemSkuUpdate {

//	可选	产品备货数量	有备货为必选，无备货为可选,数值不可小于0；示例值：200；
	private Integer inventory;
//	必须	产品SKU属性值列表	产品SKU属性值列表
	private List<ItemSkuAttrvalUpdate> itemSkuAttrValueList;
//	可选	有备货时产品sku与备货地址关联信息	有产品备货时必填，无产品备货时选填或者不填
	private List<ItemSkuInventoryUpdate> itemSkuInventoryList;
//	必须	产品sku零售价	示例值：尺寸不同设置零售价价格不同：L：10.0，XL：11.0
	private Double retailPrice;
//	必须	是否可销售	0 不可销售，1 可销售；示例值：1
	private Integer saleStatus;
//	可选	卖家自定义产品sku编码	示例值：12345
	private String skuCode;
//	可选	skuId值	产品skuId值
	private Long skuId;
//	可选	skuMD5值	SKU属性的MD5值，区分产品属性
	private String skuMD5;
	
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	public List<ItemSkuAttrvalUpdate> getItemSkuAttrValueList() {
		return itemSkuAttrValueList;
	}
	public void setItemSkuAttrValueList(List<ItemSkuAttrvalUpdate> itemSkuAttrValueList) {
		this.itemSkuAttrValueList = itemSkuAttrValueList;
	}
	public List<ItemSkuInventoryUpdate> getItemSkuInventoryList() {
		return itemSkuInventoryList;
	}
	public void setItemSkuInventoryList(List<ItemSkuInventoryUpdate> itemSkuInventoryList) {
		this.itemSkuInventoryList = itemSkuInventoryList;
	}
	public Double getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getSkuMD5() {
		return skuMD5;
	}
	public void setSkuMD5(String skuMD5) {
		this.skuMD5 = skuMD5;
	}
	
	public ItemSkuUpdate() {
		super();
	}
	
	
}
