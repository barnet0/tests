package com.digiwin.ecims.platforms.beibei.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.OrdersGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterTradeOrderGetResponse extends BeibeiBaseResponse {

  private List<OrdersGetDto> data;
  
  private Long pageNo;
  
  private Long pageSize;
  
  private Long cacheCount;
  
  private Long count;
  
  private List<String> oids;
  
  private Double processTime;

  public List<OrdersGetDto> getData() {
    return data;
  }

  public void setData(List<OrdersGetDto> data) {
    this.data = data;
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

  public Long getCacheCount() {
    return cacheCount;
  }

  public void setCacheCount(Long cacheCount) {
    this.cacheCount = cacheCount;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public List<String> getOids() {
    return oids;
  }

  public void setOids(List<String> oids) {
    this.oids = oids;
  }

  public Double getProcessTime() {
    return processTime;
  }

  public void setProcessTime(Double processTime) {
    this.processTime = processTime;
  }

}
