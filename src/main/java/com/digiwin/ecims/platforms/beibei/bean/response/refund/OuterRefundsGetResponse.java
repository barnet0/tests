package com.digiwin.ecims.platforms.beibei.bean.response.refund;

import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.refund.RefundsGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterRefundsGetResponse extends BeibeiBaseResponse {

  private List<RefundsGetDto> data;
  
  private Long pageNo;
  
  private Long pageSize;
  
  private Long count;

  public List<RefundsGetDto> getData() {
    return data;
  }

  public void setData(List<RefundsGetDto> data) {
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

  public OuterRefundsGetResponse() {
  }

}
