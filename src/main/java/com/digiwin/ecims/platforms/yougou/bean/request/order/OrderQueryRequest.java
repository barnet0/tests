package com.digiwin.ecims.platforms.yougou.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderQueryResponse;

/**
 * @author 维杰
 *
 */
public class OrderQueryRequest extends YougouBaseRequest<OrderQueryResponse> {

  // 可选 原始订单号
  private String originalOrderNo;

  // 可选
  private String orderSubNo;

  // 可选
  private String commodityNo;

  // 可选
  private String commodityKeyworkds;

  // 可选
  private String productNo;

  // 必填
  private String orderStatus;

  // 可选
  private String logisticsCode;

  // 可选
  private String payment;

  // 可选
  private String consigneeName;

  // 可选
  private String startCreated;

  // 可选
  private String endCreated;

  // 可选
  private String startPosted;

  // 可选
  private String endPosted;

  // 必填
  private Integer pageIndex;

  // 必填
  private Integer pageSize;

  public String getOriginalOrderNo() {
    return originalOrderNo;
  }

  public void setOriginalOrderNo(String originalOrderNo) {
    this.originalOrderNo = originalOrderNo;
  }

  public String getOrderSubNo() {
    return orderSubNo;
  }

  public void setOrderSubNo(String orderSubNo) {
    this.orderSubNo = orderSubNo;
  }

  public String getCommodityNo() {
    return commodityNo;
  }

  public void setCommodityNo(String commodityNo) {
    this.commodityNo = commodityNo;
  }

  public String getCommodityKeyworkds() {
    return commodityKeyworkds;
  }

  public void setCommodityKeyworkds(String commodityKeyworkds) {
    this.commodityKeyworkds = commodityKeyworkds;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getLogisticsCode() {
    return logisticsCode;
  }

  public void setLogisticsCode(String logisticsCode) {
    this.logisticsCode = logisticsCode;
  }

  public String getPayment() {
    return payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

  public String getConsigneeName() {
    return consigneeName;
  }

  public void setConsigneeName(String consigneeName) {
    this.consigneeName = consigneeName;
  }

  public String getStartCreated() {
    return startCreated;
  }

  public void setStartCreated(String startCreated) {
    this.startCreated = startCreated;
  }

  public String getEndCreated() {
    return endCreated;
  }

  public void setEndCreated(String endCreated) {
    this.endCreated = endCreated;
  }

  public String getStartPosted() {
    return startPosted;
  }

  public void setStartPosted(String startPosted) {
    this.startPosted = startPosted;
  }

  public String getEndPosted() {
    return endPosted;
  }

  public void setEndPosted(String endPosted) {
    this.endPosted = endPosted;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public OrderQueryRequest() {
    super();
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("original_order_no", getOriginalOrderNo());
    apiParams.put("order_sub_no", getOrderSubNo());
    apiParams.put("commodity_no", getCommodityNo());
    apiParams.put("commodity_keywords", getCommodityKeyworkds());
    apiParams.put("product_no", getProductNo());
    apiParams.put("order_status", getOrderStatus());
    apiParams.put("logistics_code", getLogisticsCode());
    apiParams.put("payment", getPayment());
    apiParams.put("consignee_name", getConsigneeName());
    apiParams.put("start_created", getStartCreated());
    apiParams.put("end_created", getEndCreated());
    apiParams.put("start_posted", getStartPosted());
    apiParams.put("end_posted", getEndPosted());
    apiParams.put("page_index", getPageIndex().toString());
    apiParams.put("page_size", getPageSize().toString());

    return apiParams;
  }

  @Override
  public Class<OrderQueryResponse> getResponseClass() {
    return OrderQueryResponse.class;
  }

  @Override
  public String getApiMethodName() {
    return "yougou.order.query";
  }
}
