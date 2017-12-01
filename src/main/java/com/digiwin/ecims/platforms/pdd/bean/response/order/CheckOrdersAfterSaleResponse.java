package com.digiwin.ecims.platforms.pdd.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.pdd.bean.domain.order.OrderSearchResult;
import com.digiwin.ecims.platforms.pdd.bean.response.PddBaseResponse;

public class CheckOrdersAfterSaleResponse extends PddBaseResponse {

  private List<OrderSearchResult> list;

  public List<OrderSearchResult> getList() {
    return list;
  }

  public void setList(List<OrderSearchResult> list) {
    this.list = list;
  }

}
