package com.digiwin.ecims.platforms.beibei.bean.request.item;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemWarehouseGetResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterItemWarehouseGetRequest extends BeibeiBaseRequest<OuterItemWarehouseGetResponse> {

  private Long pageNo;
  
  private Long pageSize;
  
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
    apiParams.put("page_no", getPageNo());
    apiParams.put("page_size", getPageSize());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.item.warehouse.get";
  }

  @Override
  public Class<OuterItemWarehouseGetResponse> getResponseClass() {
    return OuterItemWarehouseGetResponse.class;
  }

}
