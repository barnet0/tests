package com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.refund.RefundInfoEntity;

public class FindRefundOrderItemInfoResponseData {

  private List<RefundInfoEntity> refundOrderItemInfoEntityList;
  private Integer pageNo;
  private Integer pageSize;
  private Integer totalRecords;
  private Integer code;

  public List<RefundInfoEntity> getRefundOrderItemInfoEntityList() {
    return refundOrderItemInfoEntityList;
  }

  public void setRefundOrderItemInfoEntityList(
      List<RefundInfoEntity> refundOrderItemInfoEntityList) {
    this.refundOrderItemInfoEntityList = refundOrderItemInfoEntityList;
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

  public Integer getTotalCount() {
    return totalRecords;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalRecords = totalCount;
  }

  public Integer getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(Integer totalRecords) {
    this.totalRecords = totalRecords;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public FindRefundOrderItemInfoResponseData() {
    super();
  }


}
