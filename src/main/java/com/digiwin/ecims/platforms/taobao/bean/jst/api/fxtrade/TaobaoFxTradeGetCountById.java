package com.digiwin.ecims.platforms.taobao.bean.jst.api.fxtrade;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoFxTrade;

public class TaobaoFxTradeGetCountById extends AbstractTaobaoFxTrade {

  @Override
  public String getApi() {
    return API_PREFIX + "getCountById";
  }

  @Override
  public String getBizType() {
    return AomsordT.BIZ_NAME;
  }

  public TaobaoFxTradeGetCountById() {
    super();
  }

}
