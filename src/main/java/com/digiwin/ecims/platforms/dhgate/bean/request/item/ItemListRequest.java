package com.digiwin.ecims.platforms.dhgate.bean.request.item;

import java.util.List;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemListResponse;

/**
 * 卖家获取产品列表接口请求参数
 * 
 * @author 维杰
 *
 */
public class ItemListRequest extends DhgateBaseRequest<ItemListResponse> {

  // 可选 产品下架结束时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String downDateEnd;
  // 可选 产品下架开始时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String downDateStart;
  // 可选 产品有效期截止时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String expireDateEnd;
  // 可选 产品有效期开始时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String expireDateStart;
  // 可选 产品编码列表 示例值：[100111332,123111641]
  private List<Long> itemCodes;
  // 可选 是否为非黄金展位产品 0=否,1=是；示例值：1
  private String notGoldStall;
  // 可选 是否为非海外退货产品 0=否,1=是；示例值：1
  private String notLocalReturn;
  // 可选 产品更新结束时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String operateDateEnd;
  // 必须 产品更新开始时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String operateDateStart;
  // 可选 排序列(字段) 支持按照operateDateStart，expireDateStart，itemCodes，downDateStart四个字段排序;示例值：1
  private String orderBy;
  // 可选 排序方式 0:降序；1：升序；示例值：1
  private String orderByAsc;
  // 必须 搜索页码 示例值：默认1
  private String pages;
  // 必须 每页记录数 最多不超过60;示例值：20
  private String pageSize;
  // 可选 站点类型 EN=英文站，RU=俄文站;示例值：EN
  private String siteId;
  // 必须 产品状态
  // 100000=未定义;100100=上架产品;100200=待审核产品;100300=审核未通过产品;100400=下架产品;100500=品牌商投诉产品;100600=疑似侵权产品;示例值：100100
  private String state;
  // 可选 产品上架结束时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String upDateEnd;
  // 可选 产品上架开始时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String upDateStart;

  public String getDownDateEnd() {
    return downDateEnd;
  }

  public void setDownDateEnd(String downDateEnd) {
    this.downDateEnd = downDateEnd;
  }

  public String getDownDateStart() {
    return downDateStart;
  }

  public void setDownDateStart(String downDateStart) {
    this.downDateStart = downDateStart;
  }

  public String getExpireDateEnd() {
    return expireDateEnd;
  }

  public void setExpireDateEnd(String expireDateEnd) {
    this.expireDateEnd = expireDateEnd;
  }

  public String getExpireDateStart() {
    return expireDateStart;
  }

  public void setExpireDateStart(String expireDateStart) {
    this.expireDateStart = expireDateStart;
  }

  public List<Long> getItemCodes() {
    return itemCodes;
  }

  public void setItemCodes(List<Long> itemCodes) {
    this.itemCodes = itemCodes;
  }

  public String getNotGoldStall() {
    return notGoldStall;
  }

  public void setNotGoldStall(String notGoldStall) {
    this.notGoldStall = notGoldStall;
  }

  public String getNotLocalReturn() {
    return notLocalReturn;
  }

  public void setNotLocalReturn(String notLocalReturn) {
    this.notLocalReturn = notLocalReturn;
  }

  public String getOperateDateEnd() {
    return operateDateEnd;
  }

  public void setOperateDateEnd(String operateDateEnd) {
    this.operateDateEnd = operateDateEnd;
  }

  public String getOperateDateStart() {
    return operateDateStart;
  }

  public void setOperateDateStart(String operateDateStart) {
    this.operateDateStart = operateDateStart;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getOrderByAsc() {
    return orderByAsc;
  }

  public void setOrderByAsc(String orderByAsc) {
    this.orderByAsc = orderByAsc;
  }

  public String getPages() {
    return pages;
  }

  public void setPages(String pages) {
    this.pages = pages;
  }

  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getUpDateEnd() {
    return upDateEnd;
  }

  public void setUpDateEnd(String upDateEnd) {
    this.upDateEnd = upDateEnd;
  }

  public String getUpDateStart() {
    return upDateStart;
  }

  public void setUpDateStart(String upDateStart) {
    this.upDateStart = upDateStart;
  }

  public ItemListRequest() {
    super();
  }

  @Override
  public Class<ItemListResponse> getResponseClass() {
    return ItemListResponse.class;
  }


}
