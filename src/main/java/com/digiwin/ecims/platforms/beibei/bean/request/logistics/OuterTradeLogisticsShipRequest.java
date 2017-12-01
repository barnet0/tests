package com.digiwin.ecims.platforms.beibei.bean.request.logistics;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.logistics.OuterTradeLogisticsShipResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterTradeLogisticsShipRequest extends BeibeiBaseRequest<OuterTradeLogisticsShipResponse> {

  private String oid;
  
  private String company;
  
  private String outSid;
  
  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getOutSid() {
    return outSid;
  }

  public void setOutSid(String outSid) {
    this.outSid = outSid;
  }

  @Override
  public Map<String, String> getApiParams() {
    EcImsApiHashMap<String, String> apiParams = new EcImsApiHashMap<String, String>();
    apiParams.put("oid", getOid());
    apiParams.put("company", getCompany());
    apiParams.put("out_sid", getOutSid());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.trade.logistics.ship";
  }

  @Override
  public Class<OuterTradeLogisticsShipResponse> getResponseClass() {
    // TODO Auto-generated method stub
    return OuterTradeLogisticsShipResponse.class;
  }

}
