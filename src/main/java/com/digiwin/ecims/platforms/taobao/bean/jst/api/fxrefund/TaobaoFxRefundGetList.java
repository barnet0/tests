package com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoFxRefund;

public class TaobaoFxRefundGetList extends AbstractTaobaoFxRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getList";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoFxRefundGetList() {
    super();
  }

}
