package com.digiwin.ecims.platforms.jingdong.bean.request.afsservice;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.jd.open.api.sdk.internal.util.JsonUtil;
import com.jd.open.api.sdk.request.AbstractRequest;
import com.jd.open.api.sdk.request.JdRequest;

import com.digiwin.ecims.platforms.jingdong.bean.response.afsservice.MyAfsserviceServicedetailListResponse;

public class MyAfsserviceServicedetailListRequest extends AbstractRequest
    implements JdRequest<MyAfsserviceServicedetailListResponse> {
  
  private Long afsServiceId;

  public void setAfsServiceId(Long afsServiceId) {
    this.afsServiceId = afsServiceId;
  }

  public Long getAfsServiceId() {
    return this.afsServiceId;
  }

  public String getApiMethod() {
    return "jingdong.afsservice.servicedetail.list";
  }

  public String getAppJsonParams() throws IOException {
    Map<String, Object> pmap = new TreeMap<String, Object>();
    pmap.put("afsServiceId", this.afsServiceId);
    return JsonUtil.toJson(pmap);
  }

  public Class<MyAfsserviceServicedetailListResponse> getResponseClass() {
    return MyAfsserviceServicedetailListResponse.class;
  }
}
