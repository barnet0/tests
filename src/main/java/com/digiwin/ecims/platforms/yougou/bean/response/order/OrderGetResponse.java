package com.digiwin.ecims.platforms.yougou.bean.response.order;

import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderGet;
import com.digiwin.ecims.platforms.yougou.bean.response.base.YougouBaseResponse;

/**
 * @author 维杰
 *
 */
public class OrderGetResponse extends YougouBaseResponse {

  private OrderGet item;

  public OrderGet getItem() {
    return item;
  }

  public void setItem(OrderGet item) {
    this.item = item;
  }

  public OrderGetResponse() {
    super();
  }


}
