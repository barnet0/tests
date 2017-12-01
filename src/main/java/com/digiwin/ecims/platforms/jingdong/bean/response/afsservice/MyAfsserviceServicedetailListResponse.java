package com.digiwin.ecims.platforms.jingdong.bean.response.afsservice;

import org.codehaus.jackson.annotate.JsonProperty;

import com.jd.open.api.sdk.response.AbstractResponse;

public class MyAfsserviceServicedetailListResponse extends AbstractResponse {

  /**
   * 
   */
  private static final long serialVersionUID = -1627649665986757508L;
  
  private MyPublicResultListAfsServiceDetail publicResultList;

  @JsonProperty("publicResultList")
  public void setPublicResultList(MyPublicResultListAfsServiceDetail publicResultList) {
    this.publicResultList = publicResultList;
  }

  @JsonProperty("publicResultList")
  public MyPublicResultListAfsServiceDetail getPublicResultList() {
    return this.publicResultList;
  }
}
