package com.digiwin.ecims.platforms.dhgate.bean.response.shipping.typelist;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

public class ShippingTypeListResponse extends BizStatusResponse {

  /**
   * 
   */
  private static final long serialVersionUID = -3092519833763519941L;
  
  private List<ShippingType> shippingTypeList;

  public List<ShippingType> getShippingTypeList() {
    return shippingTypeList;
  }

  public void setShippingTypeList(List<ShippingType> shippingTypeList) {
    this.shippingTypeList = shippingTypeList;
  }
  
  
}
