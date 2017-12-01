package com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoTbItem;

public class TaobaoTbItemGetList extends AbstractTaobaoTbItem {

  @Override
  public String getApi() {
    return API_PREFIX + "getList";
  }

  @Override
  public String getBizType() {
    return AomsitemT.BIZ_NAME;
  }

  public TaobaoTbItemGetList() {
    super();
  }

}
