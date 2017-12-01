package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.item.ItemInfo;

public class GetItemInfosResponseData {

  private Integer code;

  private String msg;

  private List<ItemInfo> itemInfos;

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

  public List<ItemInfo> getItemInfos() {
    return itemInfos;
  }

  public void setItemInfos(List<ItemInfo> itemInfos) {
    this.itemInfos = itemInfos;
  }


}
