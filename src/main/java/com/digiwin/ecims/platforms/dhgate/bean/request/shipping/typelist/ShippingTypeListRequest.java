package com.digiwin.ecims.platforms.dhgate.bean.request.shipping.typelist;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.shipping.typelist.ShippingTypeListResponse;

public class ShippingTypeListRequest extends DhgateBaseRequest<ShippingTypeListResponse> {

  @Override
  public Class<ShippingTypeListResponse> getResponseClass() {
    return ShippingTypeListResponse.class;
  }

}
