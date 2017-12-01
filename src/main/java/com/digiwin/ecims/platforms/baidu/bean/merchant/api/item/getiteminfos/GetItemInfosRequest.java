package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class GetItemInfosRequest extends BaiduBaseRequest {

  private List<Long> itemIds;
  
  private List<String> fields;
  
  public List<Long> getItemIds() {
    return itemIds;
  }

  public void setItemIds(List<Long> itemIds) {
    this.itemIds = itemIds;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

  @Override
  public String getUrlPath() {
    return "ItemService/getItemInfos";
  }

}
