package com.digiwin.ecims.platforms.beibei.bean.request.order;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderDetailGetResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterTradeOrderDetailGetRequest extends BeibeiBaseRequest<OuterTradeOrderDetailGetResponse> {

  private String oid;

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  @Override
  public Map<String, String> getApiParams() {
    EcImsApiHashMap<String, String> apiParams = new EcImsApiHashMap<String, String>();
    apiParams.put("oid", getOid());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.trade.order.detail.get";
  }

  @Override
  public Class<OuterTradeOrderDetailGetResponse> getResponseClass() {
    return OuterTradeOrderDetailGetResponse.class;
  }

}
