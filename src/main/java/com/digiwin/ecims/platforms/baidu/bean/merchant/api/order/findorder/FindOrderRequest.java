package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class FindOrderRequest extends BaiduBaseRequest {

  private List<Integer> orderStatus;
  
  private Integer queryTimeType;
  
  private String startTime;
  
  private String endTime;
  
  private Integer pageNo;
  
  private Integer pageSize;

  public List<Integer> getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(List<Integer> orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Integer getQueryTimeType() {
    return queryTimeType;
  }

  public void setQueryTimeType(Integer queryTimeType) {
    this.queryTimeType = queryTimeType;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public String getUrlPath() {
    return "OrderService/findOrder";
  }
  
  
}
