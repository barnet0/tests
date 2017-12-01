package com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoFxRefund;

public class TaobaoFxRefundGetDetail extends AbstractTaobaoFxRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getDetail";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoFxRefundGetDetail() {
    super();
  }

}
