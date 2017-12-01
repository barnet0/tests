package com.digiwin.ecims.platforms.baidu.bean.merchant.client.api;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;

public interface MyApiServiceInterface {

  public <T extends BaiduBaseRequest> String postTo(String url, Request<T> air) throws Exception;

}
