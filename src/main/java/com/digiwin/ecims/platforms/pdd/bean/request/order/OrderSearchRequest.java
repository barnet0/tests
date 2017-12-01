package com.digiwin.ecims.platforms.pdd.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderSearchResponse;

public class OrderSearchRequest extends PddBaseRequest<OrderSearchResponse> {

  private Integer orderStatus;
  
  private String beginTime;
  
  private String endTime;
  
  /**
   *  用于查询返回每页的订单数量，默认100
   */
  private Integer pageSize;
  
  /**
   * 用于查询要查询的页码 默认0，页码从0开始
   */
  private Integer page;

  /**
   * @return the orderStatus
   */
  public Integer getOrderStatus() {
    return orderStatus;
  }

  /**
   * @param orderStatus the orderStatus to set
   */
  public void setOrderStatus(Integer orderStatus) {
    this.orderStatus = orderStatus;
  }

  /**
   * @return the beginTime
   */
  public String getBeginTime() {
    if (this.beginTime.length() == TIMSTAMP_LENGTH) {
      return this.beginTime;
    } else {
      return DateTimeTool.parseToDateAndFormatToTimestamp(this.beginTime);
    }
  }

  /**
   * @param beginTime the beginTime to set
   */
  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  /**
   * @return the endTime
   */
  public String getEndTime() {
    if (this.endTime.length() == TIMSTAMP_LENGTH) {
      return this.endTime;
    } else {
      return DateTimeTool.parseToDateAndFormatToTimestamp(this.endTime);
    }
  }

  /**
   * @param endTime the endTime to set
   */
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return the page
   */
  public Integer getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(Integer page) {
    this.page = page;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("orderStatus", getOrderStatus() + "");
    apiParams.put("beginTime", getBeginTime());
    apiParams.put("endTime", getEndTime());
    apiParams.put("pageSize", getPageSize().toString());
    apiParams.put("page", getPage().toString());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mOrderSearch";
  }

  @Override
  public Class<OrderSearchResponse> getResponseClass() {
    return OrderSearchResponse.class;
  }

}
