package com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoFxRefund;

public class TaobaoFxRefundGetListById extends AbstractTaobaoFxRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getListById";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoFxRefundGetListById() {
    super();
  }

}
