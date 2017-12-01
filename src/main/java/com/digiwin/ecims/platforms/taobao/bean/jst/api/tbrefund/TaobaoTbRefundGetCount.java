package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbRefund;

public class TaobaoTbRefundGetCount extends AbstractTaobaoTbRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getCount";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoTbRefundGetCount() {
    super();
  }

}
