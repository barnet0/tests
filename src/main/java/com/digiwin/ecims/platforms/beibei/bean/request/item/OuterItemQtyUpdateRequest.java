package com.digiwin.ecims.platforms.beibei.bean.request.item;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemQtyUpdateResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterItemQtyUpdateRequest extends BeibeiBaseRequest<OuterItemQtyUpdateResponse> {

  private String iid;
  
  private String outerId;
  
  private String qty;
  
  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public String getQty() {
    return qty;
  }

  public void setQty(String qty) {
    this.qty = qty;
  }

  @Override
  public Map<String, String> getApiParams() {
    EcImsApiHashMap<String, String> apiParams = new EcImsApiHashMap<String, String>();
    apiParams.put("iid", getIid());
    apiParams.put("outer_id", getOuterId());
    apiParams.put("qty", getQty());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.item.qty.update";
  }

  @Override
  public Class<OuterItemQtyUpdateResponse> getResponseClass() {
    return OuterItemQtyUpdateResponse.class;
  }

  
}
