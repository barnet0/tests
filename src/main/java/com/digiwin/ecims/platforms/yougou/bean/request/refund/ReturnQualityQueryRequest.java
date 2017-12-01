package com.digiwin.ecims.platforms.yougou.bean.request.refund;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.refund.ReturnQualityQueryResponse;

/**
 * @author 维杰
 *
 */
public class ReturnQualityQueryRequest extends YougouBaseRequest<ReturnQualityQueryResponse> {

  // 可选 退换货质检单号
  private String returnId;

  // 可选 售后申请单号
  private String applyNo;

  // 可选 申请单状态
  private String applyStatus;

  // 可选 订单号或者外部订单号
  private String orderNo;

  // 可选 申请时间（开始）
  private String applyStartTime;

  // 可选 申请时间（结束）
  private String applyEndTime;

  // 可选 页码
  private String pageIndex;

  // 可选 页大小
  private String pageSize;

  public String getReturnId() {
    return returnId;
  }

  public void setReturnId(String returnId) {
    this.returnId = returnId;
  }

  public String getApplyNo() {
    return applyNo;
  }

  public void setApplyNo(String applyNo) {
    this.applyNo = applyNo;
  }

  public String getApplyStatus() {
    return applyStatus;
  }

  public void setApplyStatus(String applyStatus) {
    this.applyStatus = applyStatus;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getApplyStartTime() {
    return applyStartTime;
  }

  public void setApplyStartTime(String applyStartTime) {
    this.applyStartTime = applyStartTime;
  }

  public String getApplyEndTime() {
    return applyEndTime;
  }

  public void setApplyEndTime(String applyEndTime) {
    this.applyEndTime = applyEndTime;
  }

  public String getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(String pageIndex) {
    this.pageIndex = pageIndex;
  }

  public String getPageSize() {
    return pageSize;
  }

  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }

  public ReturnQualityQueryRequest() {
    super();
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getReturnId() != null) {
      apiParams.put("returnId", getReturnId());
    }
    if (getApplyNo() != null) {
      apiParams.put("applyNo", getApplyNo());
    }
    if (getApplyStatus() != null) {
      apiParams.put("applyStatus", getApplyStatus());
    }
    if (getOrderNo() != null) {
      apiParams.put("orderNo", getOrderNo());
    }
    if (getApplyStartTime() != null) {
      apiParams.put("applyStartTime", getApplyStartTime());
    }
    if (getApplyEndTime() != null) {
      apiParams.put("applyEndTime", getApplyEndTime());
    }
    if (getPageIndex() != null) {
      apiParams.put("page_index", getPageIndex());
    }
    if (getPageSize() != null) {
      apiParams.put("page_size", getPageSize());
    }


    return apiParams;
  }

  @Override
  public Class<ReturnQualityQueryResponse> getResponseClass() {
    return ReturnQualityQueryResponse.class;
  }

  @Override
  public String getApiMethodName() {
    return "yougou.return.quality.query";
  }
}
