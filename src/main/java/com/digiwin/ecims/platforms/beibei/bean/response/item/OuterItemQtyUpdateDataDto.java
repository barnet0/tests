package com.digiwin.ecims.platforms.beibei.bean.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OuterItemQtyUpdateDataDto {

  @JsonProperty("sale_qty")
  private Integer saleQty;
  
  private String iid;

  public Integer getSaleQty() {
    return saleQty;
  }

  public void setSaleQty(Integer saleQty) {
    this.saleQty = saleQty;
  }

  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public OuterItemQtyUpdateDataDto() {
  }
  
  
}
