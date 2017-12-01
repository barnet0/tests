package com.digiwin.ecims.platforms.pdd.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.order.CheckOrdersAfterSaleResponse;

public class CheckOrdersAfterSaleRequest extends PddBaseRequest<CheckOrdersAfterSaleResponse> {

  private String orderSNs;
  
  public String getOrderSNs() {
    return orderSNs;
  }

  /**
   * 拼多多的订单编号   示例：20151117-517531782,20151117-585748504(批量请用半角逗号分开)
   * @param orderSNs
   */
  public void setOrderSNs(String orderSNs) {
    this.orderSNs = orderSNs;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("orderSNs", getOrderSNs());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mCheckOrdersAfterSale";
  }

  @Override
  public Class<CheckOrdersAfterSaleResponse> getResponseClass() {
    return CheckOrdersAfterSaleResponse.class;
  }

}
