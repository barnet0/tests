package com.digiwin.ecims.platforms.taobao.bean.api;

import com.taobao.api.DefaultTaobaoClient;

public class MyDefaultTabaoClient extends DefaultTaobaoClient {

  public MyDefaultTabaoClient(String serverUrl, String appKey, String appSecret) {
    super(serverUrl, appKey, appSecret);
    // TODO Auto-generated constructor stub
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }
}
