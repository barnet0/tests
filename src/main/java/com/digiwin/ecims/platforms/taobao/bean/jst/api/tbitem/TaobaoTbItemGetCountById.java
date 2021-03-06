package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbItem;

public class TaobaoTbItemGetCountById extends AbstractTaobaoTbItem {

  @Override
  public String getApi() {
    return API_PREFIX + "getCountById";
  }

  @Override
  public String getBizType() {
    return AomsitemT.BIZ_NAME;
  }

  public TaobaoTbItemGetCountById() {
    super();
  }

}
