package com.digiwin.ecims.platforms.beibei.bean.response.item;

import com.digiwin.ecims.platforms.beibei.bean.domain.item.ItemGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterItemGetResponse extends BeibeiBaseResponse {

  private ItemGetDto data;

  public ItemGetDto getData() {
    return data;
  }

  public void setData(ItemGetDto data) {
    this.data = data;
  }

  public OuterItemGetResponse() {
  }
  
  
}
