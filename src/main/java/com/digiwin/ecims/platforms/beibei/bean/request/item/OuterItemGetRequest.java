package com.digiwin.ecims.platforms.beibei.bean.request.item;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemGetResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterItemGetRequest extends BeibeiBaseRequest<OuterItemGetResponse> {

  private String iid;
  
  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  @Override
  public Map<String, String> getApiParams() {
    EcImsApiHashMap<String, String> apiParams = new EcImsApiHashMap<String, String>();
    apiParams.put("iid", getIid());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.item.get";
  }

  @Override
  public Class<OuterItemGetResponse> getResponseClass() {
    return OuterItemGetResponse.class;
  }

}
