package com.digiwin.ecims.platforms.jingdong.bean.response.afsservice;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class MyPublicResultListAfsServiceDetail implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3870553462686518509L;
  
  private Integer resultCode;
  private List<MyAfsServiceDetail> modelList;
  
  @JsonProperty("resultCode")
  public void setResultCode(Integer resultCode)
  {
    this.resultCode = resultCode;
  }
  
  @JsonProperty("resultCode")
  public Integer getResultCode()
  {
    return this.resultCode;
  }
  
  @JsonProperty("modelList")
  public void setModelList(List<MyAfsServiceDetail> modelList)
  {
    this.modelList = modelList;
  }
  
  @JsonProperty("modelList")
  public List<MyAfsServiceDetail> getModelList()
  {
    return this.modelList;
  }
  
}
