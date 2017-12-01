package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfo;

import com.digiwin.ecims.platforms.baidu.bean.domain.item.ItemInfo;

public class GetItemInfoResponseData {

  private Integer code;

  private String msg;

  private ItemInfo itemInfos;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public ItemInfo getItemInfos() {
    return itemInfos;
  }

  public void setItemInfos(ItemInfo itemInfos) {
    this.itemInfos = itemInfos;
  }


}
