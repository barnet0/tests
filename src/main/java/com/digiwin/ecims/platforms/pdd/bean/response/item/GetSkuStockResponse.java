package com.digiwin.ecims.platforms.pdd.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.pdd.bean.domain.item.StockItem;
import com.digiwin.ecims.platforms.pdd.bean.response.PddBaseResponse;

public class GetSkuStockResponse extends PddBaseResponse {

  private List<StockItem> list;

  public List<StockItem> getList() {
    return list;
  }

  public void setList(List<StockItem> list) {
    this.list = list;
  }
  
  
}
