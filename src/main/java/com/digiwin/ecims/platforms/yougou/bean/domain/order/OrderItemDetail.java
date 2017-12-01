package com.digiwin.ecims.platforms.yougou.bean.domain.order;

/**
 * 订单商品详情实体类
 * 
 * @author 维杰
 *
 */
public class OrderItemDetail {

  // 商品款色编码(供应商)
  private String supplierCode;

  // 商品图片URL
  private String commodityImage;

  // 商品数量
  private Integer commodityNum;

  // 货品编码
  private String prodNo;

  // 货品单价
  private Integer prodUnitPrice;

  // 商品款号
  private String styleNo;

  // 货品优惠总价
  private Integer prodDiscountAmount;

  // 商品颜色尺码，以逗号劈开
  private String commoditySpecificationStr;

  // 商品类型
  private Integer commodityType;

  // 商家货品条码
  private String levelCode;

  // 商品编码
  private String commodityNo;

  // 货品结算总金额
  private Integer prodTotalAmt;

  // 货品重量
  private Integer commodityWeight;

  // 货品名称
  private String prodName;

  public String getSupplierCode() {
    return supplierCode;
  }

  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  public String getCommodityImage() {
    return commodityImage;
  }

  public void setCommodityImage(String commodityImage) {
    this.commodityImage = commodityImage;
  }

  public Integer getCommodityNum() {
    return commodityNum;
  }

  public void setCommodityNum(Integer commodityNum) {
    this.commodityNum = commodityNum;
  }

  public String getProdNo() {
    return prodNo;
  }

  public void setProdNo(String prodNo) {
    this.prodNo = prodNo;
  }

  public Integer getProdUnitPrice() {
    return prodUnitPrice;
  }

  public void setProdUnitPrice(Integer prodUnitPrice) {
    this.prodUnitPrice = prodUnitPrice;
  }

  public String getStyleNo() {
    return styleNo;
  }

  public void setStyleNo(String styleNo) {
    this.styleNo = styleNo;
  }

  public Integer getProdDiscountAmount() {
    return prodDiscountAmount;
  }

  public void setProdDiscountAmount(Integer prodDiscountAmount) {
    this.prodDiscountAmount = prodDiscountAmount;
  }

  public String getCommoditySpecificationStr() {
    return commoditySpecificationStr;
  }

  public void setCommoditySpecificationStr(String commoditySpecificationStr) {
    this.commoditySpecificationStr = commoditySpecificationStr;
  }

  public Integer getCommodityType() {
    return commodityType;
  }

  public void setCommodityType(Integer commodityType) {
    this.commodityType = commodityType;
  }

  public String getLevelCode() {
    return levelCode;
  }

  public void setLevelCode(String levelCode) {
    this.levelCode = levelCode;
  }

  public String getCommodityNo() {
    return commodityNo;
  }

  public void setCommodityNo(String commodityNo) {
    this.commodityNo = commodityNo;
  }

  public Integer getProdTotalAmt() {
    return prodTotalAmt;
  }

  public void setProdTotalAmt(Integer prodTotalAmt) {
    this.prodTotalAmt = prodTotalAmt;
  }

  public Integer getCommodityWeight() {
    return commodityWeight;
  }

  public void setCommodityWeight(Integer commodityWeight) {
    this.commodityWeight = commodityWeight;
  }

  public String getProdName() {
    return prodName;
  }

  public void setProdName(String prodName) {
    this.prodName = prodName;
  }


}
