package com.digiwin.ecims.platforms.baidu.bean.merchant.client.api;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;
import com.digiwin.ecims.platforms.baidu.util.BaiduRequestParamBuilder;
import com.digiwin.ecims.core.util.HttpPostUtils;

public abstract class MyAbstractApiService implements MyApiServiceInterface {

  /**
   * 百度API通用请求方法
   * 
   * @param air
   * @return
   * @throws Exception
   */
  @Override
  public <T extends BaiduBaseRequest> String postTo(String url, Request<T> air) throws Exception {
    return HttpPostUtils.getInstance().send_common_string(url + air.getBody().getUrlPath(),
        BaiduRequestParamBuilder.getParams(air));
  }
}
