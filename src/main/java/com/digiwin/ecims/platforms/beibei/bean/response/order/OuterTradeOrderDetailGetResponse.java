package com.digiwin.ecims.platforms.beibei.bean.response.order;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.OrderDetailGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public class OuterTradeOrderDetailGetResponse extends BeibeiBaseResponse {

  private OrderDetailGetDto data;

  public OrderDetailGetDto getData() {
    return data;
  }

  public void setData(OrderDetailGetDto data) {
    this.data = data;
  }

  public OuterTradeOrderDetailGetResponse() {
  }
  
  
}
