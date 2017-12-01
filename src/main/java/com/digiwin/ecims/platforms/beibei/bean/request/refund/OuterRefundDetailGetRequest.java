package com.digiwin.ecims.platforms.beibei.bean.request.refund;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.request.BeibeiBaseRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundDetailGetResponse;
import com.digiwin.ecims.core.util.EcImsApiHashMap;

public class OuterRefundDetailGetRequest extends BeibeiBaseRequest<OuterRefundDetailGetResponse> {

  private String oid;
  
  private String refundId;
  
  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getRefundId() {
    return refundId;
  }

  public void setRefundId(String refundId) {
    this.refundId = refundId;
  }

  @Override
  public Map<String, String> getApiParams() {
    EcImsApiHashMap<String, String> apiParams = new EcImsApiHashMap<String, String>();
    apiParams.put("oid", getOid());
    apiParams.put("refund_id", getRefundId());
    
    return apiParams;
  }

  @Override
  public String getMethod() {
    return "beibei.outer.refund.get";
  }

  @Override
  public Class<OuterRefundDetailGetResponse> getResponseClass() {
    return OuterRefundDetailGetResponse.class;
  }

}
