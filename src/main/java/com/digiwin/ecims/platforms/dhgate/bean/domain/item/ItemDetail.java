package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

import java.util.List;

/**
 * 产品详情
 * 
 * @author 维杰
 *
 */
public class ItemDetail {

  // 必须 产品售后模板id 通过获取售后模板接口（dh.item.aftersale.list）获取；示例值：82558025181257728；
  private String afterSaleTemplateId;
  // 必须 发布类目id 可通过获取类目列表接扣(dh.categorys.get)获取；示例值：014006004；
  private String catePubId;
  // 必须 过期时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String expireDate;
  // 必须 产品兼容属性列表 产品兼容属性列表信息
  private List<ItemAttrgroupUpdate> itemAttrGroupList;
  // 必须 产品属性列表 产品属性列表信息
  private List<ItemAttrUpdate> itemAttrList;
  // 必须 产品基础信息 产品基础信息，包含短描，长描，产品名称等信息
  private ItemBaseUpdate itemBase;
  // 必须 产品itemCode 可通过获取产品列表接口（dh.item.list）获得该入参参数；示例值：154002791
  private String itemCode;
  // 可选 产品所属产品组id 通过获取产品组列表接口（dh.item.groups.get）获取；示例值：ff808081429e87200142a194f26e5f56；
  private String itemGroupId;
  // 必须 产品图片列表 产品图片列表信息在,注意新旧图片地址路径不同，且上传产品时必须设置一张图片为站外推广图片(type=3)
  private List<ItemImgUpdate> itemImgList;
  // 可选 产品备货信息 产品备货信息，无备货时可不传
  private ItemInventoryUpdate itemInventory;
  // 必须 产品包装信息 产品包装信息
  private ItemPackageUpdate itemPackage;
  // 必须 产品销售属性设置 产品销售属性信息
  private ItemSaleSettingUpdate itemSaleSetting;
  // 必须 产品SKU列表 产品SKU列表信息，Sku列表数量=产品属性中规格属性数量*自定义规格属性数量
  private List<ItemSkuUpdate> itemSkuList;
  // 必须 自定义规格列表 一个产品最多十个自定义规格
  private List<ItemSpecSelfDefUpdate> itemSpecSelfDefList;
  // 必须 产品折扣区间 折扣区间百分比，即折扣值，如卖家设置产品折扣率为95折，则该入参为0.05；包含以下参数:discount=折扣率, startQty=起批数量
  private List<ItemWholesaleRangeUpdate> itemWholesaleRangeList;
  // 可选 产品最新修改时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String operateDate;
  // 必须 产品运费模板id 通过获取运费模板接口（dh.shipping.templates.get）获取；示例值：ff80808143d903060144259de6f2164c；
  private String shippingModelId;
  // 必须 产品所属语言站点，默认为英文站点,参数值为：EN英文站点,RU俄语站点
  private String siteId;
  // 可选 产品尺码模板id 可通过卖家获取尺码模板列表接口（dh.item.template.list）；
  private String sizeTemplateId;
  // 可选 产品状态
  // 100000=未定义;100100=上架产品;100200=待审核产品;100300=审核未通过产品;100400=下架产品;100500=品牌商投诉产品;100600=疑似侵权产品;示例值：100100
  private Integer state;
  // 必须 卖家ID 示例值：402880f100f59ccd0100f59cd37d0004
  private String supplierId;
  // 必须 产品有效期
  // 从发布产品信息成功那天开始，到产品信息在平台上停止展示那天为止的时间段。注意：产品过了有效期，若没有及时更新，产品会自动下架，有效期以天为单位：14,30,90）；可通过卖家获取有效期列表接口（dh.item.periods.get）；获取该参数；默认值：90；
  private String vaildDay;

  public String getAfterSaleTemplateId() {
    return afterSaleTemplateId;
  }

  public void setAfterSaleTemplateId(String afterSaleTemplateId) {
    this.afterSaleTemplateId = afterSaleTemplateId;
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

  public List<ItemAttrgroupUpdate> getItemAttrGroupList() {
    return itemAttrGroupList;
  }

  public void setItemAttrGroupList(List<ItemAttrgroupUpdate> itemAttrGroupList) {
    this.itemAttrGroupList = itemAttrGroupList;
  }

  public List<ItemAttrUpdate> getItemAttrList() {
    return itemAttrList;
  }

  public void setItemAttrList(List<ItemAttrUpdate> itemAttrList) {
    this.itemAttrList = itemAttrList;
  }

  public ItemBaseUpdate getItemBase() {
    return itemBase;
  }

  public void setItemBase(ItemBaseUpdate itemBase) {
    this.itemBase = itemBase;
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getItemGroupId() {
    return itemGroupId;
  }

  public void setItemGroupId(String itemGroupId) {
    this.itemGroupId = itemGroupId;
  }

  public List<ItemImgUpdate> getItemImgList() {
    return itemImgList;
  }

  public void setItemImgList(List<ItemImgUpdate> itemImgList) {
    this.itemImgList = itemImgList;
  }

  public ItemInventoryUpdate getItemInventory() {
    return itemInventory;
  }

  public void setItemInventory(ItemInventoryUpdate itemInventory) {
    this.itemInventory = itemInventory;
  }

  public ItemPackageUpdate getItemPackage() {
    return itemPackage;
  }

  public void setItemPackage(ItemPackageUpdate itemPackage) {
    this.itemPackage = itemPackage;
  }

  public ItemSaleSettingUpdate getItemSaleSetting() {
    return itemSaleSetting;
  }

  public void setItemSaleSetting(ItemSaleSettingUpdate itemSaleSetting) {
    this.itemSaleSetting = itemSaleSetting;
  }

  public List<ItemSkuUpdate> getItemSkuList() {
    return itemSkuList;
  }

  public void setItemSkuList(List<ItemSkuUpdate> itemSkuList) {
    this.itemSkuList = itemSkuList;
  }

  public List<ItemSpecSelfDefUpdate> getItemSpecSelfDefList() {
    return itemSpecSelfDefList;
  }

  public void setItemSpecSelfDefList(List<ItemSpecSelfDefUpdate> itemSpecSelfDefList) {
    this.itemSpecSelfDefList = itemSpecSelfDefList;
  }

  public List<ItemWholesaleRangeUpdate> getItemWholesaleRangeList() {
    return itemWholesaleRangeList;
  }

  public void setItemWholesaleRangeList(List<ItemWholesaleRangeUpdate> itemWholesaleRangeList) {
    this.itemWholesaleRangeList = itemWholesaleRangeList;
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

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  public String getSizeTemplateId() {
    return sizeTemplateId;
  }

  public void setSizeTemplateId(String sizeTemplateId) {
    this.sizeTemplateId = sizeTemplateId;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public String getVaildDay() {
    return vaildDay;
  }

  public void setVaildDay(String vaildDay) {
    this.vaildDay = vaildDay;
  }

  public ItemDetail() {
    super();
  }


}
