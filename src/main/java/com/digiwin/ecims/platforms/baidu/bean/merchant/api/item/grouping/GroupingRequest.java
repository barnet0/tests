package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.grouping;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class GroupingRequest extends BaiduBaseRequest {

  private Long itemId;
  
  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  @Override
  public String getUrlPath() {
    return "ItemService/grouping";
  }

}
