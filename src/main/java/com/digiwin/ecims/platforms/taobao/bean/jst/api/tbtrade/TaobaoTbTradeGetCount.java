package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbTrade;

public class TaobaoTbTradeGetCount extends AbstractTaobaoTbTrade {

  @Override
  public String getApi() {
    return API_PREFIX + "getCount";
  }

  @Override
  public String getBizType() {
    return AomsordT.BIZ_NAME;
  }

  public TaobaoTbTradeGetCount() {
    super();
  }

}
