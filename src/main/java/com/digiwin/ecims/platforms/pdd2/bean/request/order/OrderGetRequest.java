package com.digiwin.ecims.platforms.pdd2.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderInfoGetResponse;

public class OrderGetRequest extends Pdd2BaseRequest<OrderInfoGetResponse> {

  private String order_sn;
  
  /**
   * @return the orderSN
   */
  public String getOrderSN() {
    return order_sn;
  }

  /**
   * @param orderSN the orderSN to set
   */
  public void setOrderSN(String orderSN) {
    this.order_sn = orderSN;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("order_sn", getOrderSN());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "pdd.order.information.get";
  }

  @Override
  public Class<OrderInfoGetResponse> getResponseClass() {
    return OrderInfoGetResponse.class;
  }


}
