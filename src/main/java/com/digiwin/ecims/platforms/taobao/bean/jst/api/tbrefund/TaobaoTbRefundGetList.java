package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbRefund;

public class TaobaoTbRefundGetList extends AbstractTaobaoTbRefund {

  @Override
  public String getApi() {
    return API_PREFIX + "getList";
  }

  @Override
  public String getBizType() {
    return AomsrefundT.BIZ_NAME;
  }

  public TaobaoTbRefundGetList() {
    super();
  }

}
