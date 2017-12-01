package com.digiwin.ecims.platforms.beibei.bean.response.item;

import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterItemQtyUpdateResponse extends BeibeiBaseResponse {

  private OuterItemQtyUpdateDataDto data;

  public OuterItemQtyUpdateDataDto getData() {
    return data;
  }

  public void setData(OuterItemQtyUpdateDataDto data) {
    this.data = data;
  }

  public OuterItemQtyUpdateResponse() {
  }
  
}
