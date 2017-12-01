package com.digiwin.ecims.platforms.taobao.bean.jst.api;

public abstract class AbstractTaobaoJstApi {

  public static final String COMPARISON_COL_JDP_MODIFIED = "jdp_modified";
  
  public static final String COMPARISON_COL_CREATED = "created";
  
  public abstract String getApi();
  
  public abstract String getBizType();
  
}
