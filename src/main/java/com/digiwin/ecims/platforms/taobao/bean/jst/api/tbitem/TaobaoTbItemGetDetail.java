package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbItem;

public class TaobaoTbItemGetDetail extends AbstractTaobaoTbItem {

  @Override
  public String getApi() {
    return API_PREFIX + "getDetail";
  }

  @Override
  public String getBizType() {
    return AomsitemT.BIZ_NAME;
  }

  public TaobaoTbItemGetDetail() {
    super();
  }

}
