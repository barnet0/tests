package com.digiwin.ecims.platforms.yougou.bean.response.inventory;

import com.digiwin.ecims.platforms.yougou.bean.response.base.YougouBaseResponse;

/**
 * @author 维杰
 *
 */
public class InventoryUpdateResponse extends YougouBaseResponse {

  private InventoryUpdateItem item;

  public InventoryUpdateItem getItem() {
    return item;
  }

  public void setItem(InventoryUpdateItem item) {
    this.item = item;
  }


}
