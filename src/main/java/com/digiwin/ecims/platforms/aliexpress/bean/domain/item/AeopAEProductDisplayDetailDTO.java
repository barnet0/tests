package com.digiwin.ecims.platforms.aliexpress.bean.domain.item;

import java.util.List;

/**
 * 商品详情信息
 * 
 * @author 维杰
 *
 */
public class AeopAEProductDisplayDetailDTO {

  // 是 商品的类目属性
  private List<AeopAeProductPropertys> aeopAeProductPropertys;

  // 是 商品的SKU信息
  private List<AeopAeProductSKUs> aeopAeProductSKUs;

  // 是 商品的多媒体信息
  private AeopAEMultimedia aeopAEMultimedia;

  // 是 商品详描（html格式信息）
  private String detail;

  // 是 商品的备货期 60
  private Integer deliveryTime;

  // 是 商品拥有者的login_id aliqatest01
  private String ownerMemberId;

  // 是 商品拥有者的ID 1006680305
  private Integer ownerMemberSeq;

  // 是 产品ID 1234
  private Long productId;

  // 是 产品所在类目的ID 123456
  private Integer categoryId;

  // 是 产品的标题 knew odd
  private String subject;

  // 是 是否为打包出售方式 true
  private Boolean packageType;

  // 是 每包的数量 1
  private Integer lotNum;

  // 是 产品的长度 10
  private Integer packageLength;

  // 是 产品的宽度 20
  private Integer packageWidth;

  // 是 产品的高度 30
  private Integer packageHeight;

  // 是 产品的毛重 40.12
  private String grossWeight;

  // 是 是否支持是自定义计重 true
  private Boolean isPackSell;

  // 是 库存的扣减策略 place_order_withhold或payment_success_deduct
  private String reduceStrategy;

  // 是 产品所在的产品分组列表 [1002141,10024524]
  private List<Integer> groupIds;

  // 是 产品的批发折扣 90
  private Integer bulkDiscount;

  // 是 产品的主图列表，使用半角分号分隔
  // http://g01.a.alicdn.com/kf/HTB13GKLJXXXXXbYaXXXq6xXFXXXi.jpg;http://g02.a.alicdn.com/kf/HTB1DkaWJXXXXXb6XFXXq6xXFXXXp.jpg;http://g02.a.alicdn.com/kf/HTB1pMCQJXXXXXcvXVXXq6xXFXXXm.jpg;http://g03.a.alicdn.com/kf/HTB1QhORJXXXXXbiXVXXq6xXFXXXx.jpg;http://g02.a.alicdn.com/kf/HTB1q1aLJXXXXXcfaXXXq6xXFXXXv.jpg
  private String imageURLs;

  // 是 产品的单位 100000015
  private Integer productUnit;

  // 是 产品的有效期 30
  private Integer wsValidNum;

  // 是 产品的来源 isv
  private String src;

  // 是 产品的下架日期 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String wsOfflineDate;

  // 是 产品的下架原因 expire_offline
  private String wsDisplay;

  // 是 产品的状态 onSelling
  private String productStatusType;

  // 是 产品的货币单位。美元: USD, 卢布: RUB USD
  private String currencyCode;

  // 产品关联的运费模版ID 12345
  private Long freightTemplateId;

  private Integer addUnit;

  private String addWeight;

  // 是 自定义计重的基本产品件数 2
  private Integer baseUnit;

  // 是 享受批发价的产品数 10
  private Integer bulkOrder;

  // 是 产品所关联的产品分组ID 10023
  private Integer groupId;

  // 是 是否是动态图产品 true
  private Boolean isImageDynamic;

  // 是 单品产品的价格。 10.23
  private String productPrice;

  // 是 产品所关联的服务模版 100
  private Long promiseTemplateId;

  // 是 产品所关联的尺码模版ID。如果这个商品没有关联任何的尺码模版，那么返回-1。 123
  private Long sizechartId;

  // 是 接口调用结果 true
  private Boolean success;

  // 是 卡券商品的起始有效期。如果不是卡券商品，将不返回这个属性。 20160119113420000+0800
  private String couponStartDate;

  // 是 卡券商品的结束有效期。如果不是卡券商品，将不返回这个属性。 20160219113420000+0800
  private String couponEndDate;

  public List<AeopAeProductPropertys> getAeopAeProductPropertys() {
    return aeopAeProductPropertys;
  }

  public void setAeopAeProductPropertys(List<AeopAeProductPropertys> aeopAeProductPropertys) {
    this.aeopAeProductPropertys = aeopAeProductPropertys;
  }

  public List<AeopAeProductSKUs> getAeopAeProductSKUs() {
    return aeopAeProductSKUs;
  }

  public void setAeopAeProductSKUs(List<AeopAeProductSKUs> aeopAeProductSKUs) {
    this.aeopAeProductSKUs = aeopAeProductSKUs;
  }

  public AeopAEMultimedia getAeopAEMultimedia() {
    return aeopAEMultimedia;
  }

  public void setAeopAEMultimedia(AeopAEMultimedia aeopAEMultimedia) {
    this.aeopAEMultimedia = aeopAEMultimedia;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Integer getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(Integer deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public String getOwnerMemberId() {
    return ownerMemberId;
  }

  public void setOwnerMemberId(String ownerMemberId) {
    this.ownerMemberId = ownerMemberId;
  }

  public Integer getOwnerMemberSeq() {
    return ownerMemberSeq;
  }

  public void setOwnerMemberSeq(Integer ownerMemberSeq) {
    this.ownerMemberSeq = ownerMemberSeq;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Boolean getPackageType() {
    return packageType;
  }

  public void setPackageType(Boolean packageType) {
    this.packageType = packageType;
  }

  public Integer getLotNum() {
    return lotNum;
  }

  public void setLotNum(Integer lotNum) {
    this.lotNum = lotNum;
  }

  public Integer getPackageLength() {
    return packageLength;
  }

  public void setPackageLength(Integer packageLength) {
    this.packageLength = packageLength;
  }

  public Integer getPackageWidth() {
    return packageWidth;
  }

  public void setPackageWidth(Integer packageWidth) {
    this.packageWidth = packageWidth;
  }

  public Integer getPackageHeight() {
    return packageHeight;
  }

  public void setPackageHeight(Integer packageHeight) {
    this.packageHeight = packageHeight;
  }

  public String getGrossWeight() {
    return grossWeight;
  }

  public void setGrossWeight(String grossWeight) {
    this.grossWeight = grossWeight;
  }

  public Boolean getIsPackSell() {
    return isPackSell;
  }

  public void setIsPackSell(Boolean isPackSell) {
    this.isPackSell = isPackSell;
  }

  public String getReduceStrategy() {
    return reduceStrategy;
  }

  public void setReduceStrategy(String reduceStrategy) {
    this.reduceStrategy = reduceStrategy;
  }

  public List<Integer> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<Integer> groupIds) {
    this.groupIds = groupIds;
  }

  public Integer getBulkDiscount() {
    return bulkDiscount;
  }

  public void setBulkDiscount(Integer bulkDiscount) {
    this.bulkDiscount = bulkDiscount;
  }

  public String getImageURLs() {
    return imageURLs;
  }

  public void setImageURLs(String imageURLs) {
    this.imageURLs = imageURLs;
  }

  public Integer getProductUnit() {
    return productUnit;
  }

  public void setProductUnit(Integer productUnit) {
    this.productUnit = productUnit;
  }

  public Integer getWsValidNum() {
    return wsValidNum;
  }

  public void setWsValidNum(Integer wsValidNum) {
    this.wsValidNum = wsValidNum;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getWsOfflineDate() {
    return wsOfflineDate;
  }

  public void setWsOfflineDate(String wsOfflineDate) {
    this.wsOfflineDate = wsOfflineDate;
  }

  public String getWsDisplay() {
    return wsDisplay;
  }

  public void setWsDisplay(String wsDisplay) {
    this.wsDisplay = wsDisplay;
  }

  public String getProductStatusType() {
    return productStatusType;
  }

  public void setProductStatusType(String productStatusType) {
    this.productStatusType = productStatusType;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public Long getFreightTemplateId() {
    return freightTemplateId;
  }

  public void setFreightTemplateId(Long freightTemplateId) {
    this.freightTemplateId = freightTemplateId;
  }

  public Integer getAddUnit() {
    return addUnit;
  }

  public void setAddUnit(Integer addUnit) {
    this.addUnit = addUnit;
  }

  public String getAddWeight() {
    return addWeight;
  }

  public void setAddWeight(String addWeight) {
    this.addWeight = addWeight;
  }

  public Integer getBaseUnit() {
    return baseUnit;
  }

  public void setBaseUnit(Integer baseUnit) {
    this.baseUnit = baseUnit;
  }

  public Integer getBulkOrder() {
    return bulkOrder;
  }

  public void setBulkOrder(Integer bulkOrder) {
    this.bulkOrder = bulkOrder;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public Boolean getIsImageDynamic() {
    return isImageDynamic;
  }

  public void setIsImageDynamic(Boolean isImageDynamic) {
    this.isImageDynamic = isImageDynamic;
  }

  public String getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(String productPrice) {
    this.productPrice = productPrice;
  }

  public Long getPromiseTemplateId() {
    return promiseTemplateId;
  }

  public void setPromiseTemplateId(Long promiseTemplateId) {
    this.promiseTemplateId = promiseTemplateId;
  }

  public Long getSizechartId() {
    return sizechartId;
  }

  public void setSizechartId(Long sizechartId) {
    this.sizechartId = sizechartId;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getCouponStartDate() {
    return couponStartDate;
  }

  public void setCouponStartDate(String couponStartDate) {
    this.couponStartDate = couponStartDate;
  }

  public String getCouponEndDate() {
    return couponEndDate;
  }

  public void setCouponEndDate(String couponEndDate) {
    this.couponEndDate = couponEndDate;
  }


}
