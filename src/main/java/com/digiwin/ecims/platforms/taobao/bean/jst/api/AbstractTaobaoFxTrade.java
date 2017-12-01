package com.digiwin.ecims.platforms.taobao.bean.jst.api;

public abstract class AbstractTaobaoFxTrade extends AbstractTaobaoJstApi {

  protected static final String API_PREFIX = "taobao.fx.trade."; 
  
  public static boolean isApiInScope(String api) {
    if (api.startsWith(API_PREFIX)) {
      return true;
    }
    return false;
  }
}
