package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbRefund;

public class TaobaoTbRefundGetCountById extends AbstractTaobaoTbRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getCountById";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoTbRefundGetCountById() {
    super();
  }

}
