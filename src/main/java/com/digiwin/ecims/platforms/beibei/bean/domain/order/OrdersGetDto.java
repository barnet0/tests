package com.digiwin.ecims.platforms.beibei.bean.domain.order;

import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderDto;
import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderItemDto;

/**
 * for 订单列表查询API返回数据
 * @author zaregoto
 *
 */
public class OrdersGetDto extends AbstractOrderDto {

  private List<OrdersGetItemDto> item;

  public List<OrdersGetItemDto> getItem() {
    return item;
  }

  public void setItem(List<OrdersGetItemDto> item) {
    this.item = item;
  }

  public OrdersGetDto() {
  }

  @Override
  public List<? extends AbstractOrderItemDto> getItems() {
    return getItem();
  }
  
  
}
