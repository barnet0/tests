package com.digiwin.ecims.platforms.beibei.bean.request.order;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderGetResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterTradeOrderGetRequest extends BeibeiBaseRequest<OuterTradeOrderGetResponse> {

  private String status;
  
  private String timeRange;
  
  private String startTime;
  
  private String endTime;
  
  private Long pageNo;
  
  private Long pageSize;
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimeRange() {
    return timeRange;
  }

  public void setTimeRange(String timeRange) {
    this.timeRange = timeRange;
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

  public Long getPageNo() {
    return pageNo;
  }

  public void setPageNo(Long pageNo) {
    this.pageNo = pageNo;
  }

  public Long getPageSize() {
    return pageSize;
  }

  public void setPageSize(Long pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public Map<String, String> getApiParams() {
    EcImsApiHashMap<String, String> apiParams = new EcImsApiHashMap<String, String>();
    apiParams.put("status", getStatus());
    apiParams.put("time_range", getTimeRange());
    apiParams.put("start_time", getStartTime());
    apiParams.put("end_time", getEndTime());
    apiParams.put("page_no", getPageNo());
    apiParams.put("page_size", getPageSize());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.trade.order.get";
  }

  @Override
  public Class<OuterTradeOrderGetResponse> getResponseClass() {
    return OuterTradeOrderGetResponse.class;
  }

}
