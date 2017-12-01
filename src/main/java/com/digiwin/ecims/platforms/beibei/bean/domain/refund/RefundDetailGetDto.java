package com.digiwin.ecims.platforms.beibei.bean.domain.refund;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.digiwin.ecims.platforms.beibei.bean.domain.refund.base.AbstractRefundDto;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class RefundDetailGetDto extends AbstractRefundDto {

  private String skuId;

  private String outerId;

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public RefundDetailGetDto() {
  }
  
}
