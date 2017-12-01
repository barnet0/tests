package com.digiwin.ecims.platforms.taobao.bean.response.cainiao.waybillget;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Services {

  @JsonProperty("SVC-COD")
  private SvcCod svcCod;

  /**
   * @return the svcCod
   */
  public SvcCod getSvcCod() {
    return svcCod;
  }

  /**
   * @param svcCod the svcCod to set
   */
  public void setSvcCod(SvcCod svcCod) {
    this.svcCod = svcCod;
  }
  
  
}
