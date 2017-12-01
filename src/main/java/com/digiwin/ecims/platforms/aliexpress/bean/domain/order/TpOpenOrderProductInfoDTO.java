package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 订单详情商品信息
 * 
 * @author 维杰
 *
 */
public class TpOpenOrderProductInfoDTO {

  // 否 商品ID
  private Long productId;

  // String 否 商品标题
  private String productName;

  // 否 商品数量
  private Integer quantity;

  // 否 商品SKU
  private String sku;

  // 否 商品单价
  private Money unitPrice;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public Money getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Money unitPrice) {
    this.unitPrice = unitPrice;
  }
  
  
}
