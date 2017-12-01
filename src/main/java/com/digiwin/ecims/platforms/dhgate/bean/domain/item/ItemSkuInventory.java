package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

public class ItemSkuInventory {

//  可选  有备货时，产品备货地址编码   示例值：12345
  private String inventoryLocation;
  
//  可选  是否可销售   0 不可销售，1 可销售示例值：1
  private Integer saleStatus;
  
//  可选  卖家自定义产品sku编码    示例值：12345
  private String skuCode;
  
//  可选  有备货时，备货地址下sku的备货数量  示例值：10
  private Integer skuInventoryQty;
  
//  可选  skuMD5值 SKU属性的MD5值，区分产品属性
  private String skuMD5;

  public String getInventoryLocation() {
    return inventoryLocation;
  }

  public void setInventoryLocation(String inventoryLocation) {
    this.inventoryLocation = inventoryLocation;
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

  public Integer getSkuInventoryQty() {
    return skuInventoryQty;
  }

  public void setSkuInventoryQty(Integer skuInventoryQty) {
    this.skuInventoryQty = skuInventoryQty;
  }

  public String getSkuMD5() {
    return skuMD5;
  }

  public void setSkuMD5(String skuMD5) {
    this.skuMD5 = skuMD5;
  }
  
}
