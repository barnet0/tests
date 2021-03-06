package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbTrade;

public class TaobaoTbTradeGetListById extends AbstractTaobaoTbTrade {

  @Override
  public String getApi() {
    return API_PREFIX + "getListById";
  }

  @Override
  public String getBizType() {
    return AomsordT.BIZ_NAME;
  }

  public TaobaoTbTradeGetListById() {
    super();
  }

}
