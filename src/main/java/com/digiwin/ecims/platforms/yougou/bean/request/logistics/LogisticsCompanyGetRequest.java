package com.digiwin.ecims.platforms.yougou.bean.request.logistics;

import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.logistics.LogisticsCompanyGetResponse;
import com.digiwin.ecims.platforms.yougou.util.YougouApiParamMap;

public class LogisticsCompanyGetRequest extends YougouBaseRequest<LogisticsCompanyGetResponse> {

  @Override
  public Map<String, String> getApiParams() {
    return new YougouApiParamMap();
  }

  @Override
  public String getApiMethodName() {
    return "yougou.logisticscompany.get";
  }

  @Override
  public Class<LogisticsCompanyGetResponse> getResponseClass() {
    return LogisticsCompanyGetResponse.class;
  }

}
