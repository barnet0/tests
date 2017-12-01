package com.digiwin.ecims.platforms.dhgate.bean.request.item;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemSkuListResponse;

public class ItemSkuListRequest extends DhgateBaseRequest<ItemSkuListResponse> {

  private String itemCode;
  
  private String siteId;
  
  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  @Override
  public Class<ItemSkuListResponse> getResponseClass() {
    return ItemSkuListResponse.class;
  }

}
