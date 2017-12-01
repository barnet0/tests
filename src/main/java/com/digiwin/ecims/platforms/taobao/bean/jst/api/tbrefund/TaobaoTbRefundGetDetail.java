package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbRefund;

public class TaobaoTbRefundGetDetail extends AbstractTaobaoTbRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getDetail";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoTbRefundGetDetail() {
    super();
  }

}
