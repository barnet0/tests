package com.digiwin.ecims.platforms.suning.bean;

import java.util.List;

import com.suning.api.entity.transaction.OrdQueryResponse;
import com.suning.api.entity.transaction.OrderQueryResponse;

public class OrderCollection {

  private OrderQueryResponse createRes;
  private OrdQueryResponse updateRes;

  public void setResponse(Object o) {
    if (o instanceof OrderQueryResponse) {
      this.createRes = (OrderQueryResponse) o;
    } else if (o instanceof OrdQueryResponse) {
      this.updateRes = (OrdQueryResponse) o;
    }
  }

  public List<?> getOrderQuery() {
    if (createRes != null) {
      return this.createRes.getSnbody().getOrderQuery();
      // } else if (createRes != null) { // modi by mowj 20151116
    } else if (updateRes != null) { // add by mowj 20151116
      return this.updateRes.getSnbody().getOrderQuery();
    } else {
      return null;
    }
  }

}
