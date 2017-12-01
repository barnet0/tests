package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

import java.util.List;

public class ItemSku {

  // 必须 产品备货数量 有备货为必选，无备货为可选,数值不可小于0；示例值：200；
  private Integer inventory;

  // 必须 产品编码 示例值：202869901
  private Long itemCode;

  // 必须 产品SKU属性值列表 产品SKU属性值列表
  private List<ItemSkuAttrval> itemSkuAttrvalList;

  // 可选 有备货时产品sku与备货地址关联信息 有产品备货时必填，无产品备货时选填或者不填
  private List<ItemSkuInventory> itemSkuInvenList;

  // 必须 SKU价格的类型 0 null，1 buyer价格，2 seller价格；示例值：2
  private Integer priceType;

  // 必须 产品sku零售价 示例值：尺寸不同设置零售价价格不同：L：10.0，XL：11.0
  private Double retailPrice;

  // 必须 卖家自定义产品sku编码 示例值：632870918
  private String skuCode;

  // 必须 主键ID 示例值：1165661007
  private Long skuId;

  // 必须 产品skuMD5 区分产品的唯一性，可通过获取产品详情(dh.item.get 2.0)接口获得
  private String skuMD5;

  // 必须 卖家ID 示例值：402880f100f59ccd0100f59cd37d0004
  private String supplierId;

  public Integer getInventory() {
    return inventory;
  }

  public void setInventory(Integer inventory) {
    this.inventory = inventory;
  }

  public Long getItemCode() {
    return itemCode;
  }

  public void setItemCode(Long itemCode) {
    this.itemCode = itemCode;
  }

  public List<ItemSkuAttrval> getItemSkuAttrvalList() {
    return itemSkuAttrvalList;
  }

  public void setItemSkuAttrvalList(List<ItemSkuAttrval> itemSkuAttrvalList) {
    this.itemSkuAttrvalList = itemSkuAttrvalList;
  }

  public List<ItemSkuInventory> getItemSkuInvenList() {
    return itemSkuInvenList;
  }

  public void setItemSkuInvenList(List<ItemSkuInventory> itemSkuInvenList) {
    this.itemSkuInvenList = itemSkuInvenList;
  }

  public Integer getPriceType() {
    return priceType;
  }

  public void setPriceType(Integer priceType) {
    this.priceType = priceType;
  }

  public Double getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(Double retailPrice) {
    this.retailPrice = retailPrice;
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

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

}
