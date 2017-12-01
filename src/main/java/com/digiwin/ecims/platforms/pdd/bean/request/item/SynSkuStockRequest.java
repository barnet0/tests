package com.digiwin.ecims.platforms.pdd.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.item.SynSkuStockResponse;

public class SynSkuStockRequest extends PddBaseRequest<SynSkuStockResponse> {

  private String pddSkuId;
  
  private String outerSkuIds;
  
  private Long quantity;
  
  public String getPddSkuId() {
    return pddSkuId;
  }

  public void setPddSkuId(String pddSkuId) {
    this.pddSkuId = pddSkuId;
  }

  public String getOuterSkuIds() {
    return outerSkuIds;
  }

  public void setOuterSkuIds(String outerSkuIds) {
    this.outerSkuIds = outerSkuIds;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("pddSkuId", getPddSkuId());
    apiParams.put("outerSkuIds", getOuterSkuIds());
    apiParams.put("quantity", getQuantity() + "");

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mSynSkuStock";
  }

  @Override
  public Class<SynSkuStockResponse> getResponseClass() {
    return SynSkuStockResponse.class;
  }

}
