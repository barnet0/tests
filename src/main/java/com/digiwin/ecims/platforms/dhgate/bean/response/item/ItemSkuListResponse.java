package com.digiwin.ecims.platforms.dhgate.bean.response.item;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemSku;

public class ItemSkuListResponse extends BizStatusResponse {

  /**
   * 
   */
  private static final long serialVersionUID = -123691062361137144L;

  private List<ItemSku> itemSkuList;

  public List<ItemSku> getItemSkuList() {
    return itemSkuList;
  }

  public void setItemSkuList(List<ItemSku> itemSkuList) {
    this.itemSkuList = itemSkuList;
  }
}
