package com.digiwin.ecims.platforms.baidu.util;

import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;
import com.digiwin.ecims.core.util.JsonUtil;

public class BaiduRequestParamBuilder {

  public static <T> String getParams(Request<T> air) {
    return JsonUtil.format(air);
  }
}
