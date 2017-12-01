package com.digiwin.ecims.platforms.pdd.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderGetResponse;

public class OrderGetRequest extends PddBaseRequest<OrderGetResponse> {

  private String orderSN;
  
  /**
   * @return the orderSN
   */
  public String getOrderSN() {
    return orderSN;
  }

  /**
   * @param orderSN the orderSN to set
   */
  public void setOrderSN(String orderSN) {
    this.orderSN = orderSN;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("orderSN", getOrderSN());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mGetOrder";
  }

  @Override
  public Class<OrderGetResponse> getResponseClass() {
    return OrderGetResponse.class;
  }

}
