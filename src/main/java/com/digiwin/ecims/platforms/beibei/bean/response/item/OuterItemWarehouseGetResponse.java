package com.digiwin.ecims.platforms.beibei.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.item.ItemWarehouseGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterItemWarehouseGetResponse extends BeibeiBaseResponse {

  private List<ItemWarehouseGetDto> data;
  
  private Long pageNo;
  
  private Long pageSize;
  
  private Long count;

  public List<ItemWarehouseGetDto> getData() {
    return data;
  }

  public void setData(List<ItemWarehouseGetDto> data) {
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

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public OuterItemWarehouseGetResponse() {
  }
  
  
}
