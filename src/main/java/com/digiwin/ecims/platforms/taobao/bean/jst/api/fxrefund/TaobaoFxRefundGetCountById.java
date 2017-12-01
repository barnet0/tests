package com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoFxRefund;

public class TaobaoFxRefundGetCountById extends AbstractTaobaoFxRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getCountById";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoFxRefundGetCountById() {
    super();
  }

}
