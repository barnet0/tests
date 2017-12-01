package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbRefund;

public class TaobaoTbRefundGetListById extends AbstractTaobaoTbRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getListById";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoTbRefundGetListById() {
    super();
  }

}
