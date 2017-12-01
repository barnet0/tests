package com.digiwin.ecims.platforms.yougou.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderGetResponse;

/**
 * @author 维杰
 *
 */
public class OrderGetRequest extends YougouBaseRequest<OrderGetResponse> {

  private String orderSubNo;

  public String getOrderSubNo() {
    return orderSubNo;
  }

  public void setOrderSubNo(String orderSubNo) {
    this.orderSubNo = orderSubNo;
  }

  public OrderGetRequest() {
    super();
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getOrderSubNo() != null) {
      apiParams.put("order_sub_no", getOrderSubNo());
    }

    return apiParams;
  }

  @Override
  public Class<OrderGetResponse> getResponseClass() {
    return OrderGetResponse.class;
  }

  @Override
  public String getApiMethodName() {
    return "yougou.order.get";
  }
}
