package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.off;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class OffRequest extends BaiduBaseRequest {

  private Long itemId;
  
  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  @Override
  public String getUrlPath() {
    return "ItemService/off";
  }

}
