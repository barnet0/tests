package com.digiwin.ecims.platforms.pdd2.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.CheckOrdersAfterSaleResponse;

public class CheckOrdersAfterSaleRequest extends Pdd2BaseRequest<CheckOrdersAfterSaleResponse> {

  private String order_sns;
  
  public String getOrderSNs() {
    return order_sns;
  }

  /**
   * 拼多多的订单编号   示例：20151117-517531782,20151117-585748504(批量请用半角逗号分开)
   * @param orderSNs
   */
  public void setOrderSNs(String orderSNs) {
    this.order_sns = orderSNs;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("order_sns", getOrderSNs());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "pdd.refund.status.check";
  }

  @Override
  public Class<CheckOrdersAfterSaleResponse> getResponseClass() {
    return CheckOrdersAfterSaleResponse.class;
  }


}
