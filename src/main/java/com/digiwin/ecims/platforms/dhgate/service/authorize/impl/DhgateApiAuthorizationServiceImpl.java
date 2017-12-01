package com.digiwin.ecims.platforms.dhgate.service.authorize.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhgate.open.client.ClientRequest;
import com.dhgate.open.client.CompositeResponse;
import com.dhgate.open.client.DhgateDefaultClient;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderListGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderListGetResponse;
import com.digiwin.ecims.platforms.dhgate.service.authorize.DhgateApiAuthorizationService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.dhgate.util.DhgateRequestParamBuilder;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool.DhgateApiEnum;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool.OrderUseTime;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class DhgateApiAuthorizationServiceImpl implements DhgateApiAuthorizationService {

  private static final String ACCESS_TOKEN_URL = "https://secure.dhgate.com/dop/oauth2/access_token";
  
  private static final Logger logger =
      LoggerFactory.getLogger(DhgateApiAuthorizationServiceImpl.class);

  @Autowired
  private BaseDAO baseDao;

  @Autowired
  private ParamSystemService paramSystemService;

  @Override
  public boolean isAuthorizationValid(String storeId, String appKey, String appSecret,
      String accessToken) {
    DhgateDefaultClient client =
        new DhgateDefaultClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DHGATE_API));

    CompositeResponse<OrderListGetResponse> response = null;
    client = client.buildRequestClient(accessToken);
    OrderListGetRequest orderListGetRequest = new OrderListGetRequest();
    orderListGetRequest.setPageNo(DhgateCommonTool.MIN_PAGE_NO);
    orderListGetRequest.setPageSize(DhgateCommonTool.MIN_PAGE_SIZE);
    orderListGetRequest.setQuerytimeType(OrderUseTime.CREATE_TIME.getTimeType());
    orderListGetRequest.setStartDate("2016-01-01 00:00:00");
    orderListGetRequest.setEndDate("2016-03-31 23:59:59");

    ClientRequest request =
        DhgateRequestParamBuilder.addParams(client, accessToken, orderListGetRequest);
    DhgateApiEnum apiEnum = DhgateApiEnum.DH_ORDER_LIST_GET;
    response = request.post(OrderListGetResponse.class, apiEnum.getApiName(), apiEnum.getVersion());

    if (response != null && response.getSuccessResponse().getStatus() != null) {
      return true;
    }
    return false;
  }

  @Override
  public String reauthorize(String storeId, String appKey, String appSecret, String accessToken,
      String refreshToken) {
    String result = null;
    result = getNewAccessToken(appKey, appSecret, refreshToken);
    if (result == null) {
      result = getNewRefreshToken(appKey, appSecret, refreshToken, accessToken);
    }
    
    return result;
  }

  @Override
  public String getNewAccessToken(String appKey, String appSecret, String refreshToken) {
    String oauthGetTokenUrl = ACCESS_TOKEN_URL;
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("grant_type", "refresh_token");
    paramMap.put("client_id", appKey);
    paramMap.put("client_secret", appSecret);
    paramMap.put("refresh_token", refreshToken);
    paramMap.put("scope", "basic");
    String result = null;
    try {
      result = HttpPostUtils.getInstance().send_common_after_url(oauthGetTokenUrl, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return result;
  }
  
  @Override
  public String getNewRefreshToken(String appKey, String appSecret, String refreshToken, String accessToken) {
    String oauthGetTokenUrl = ACCESS_TOKEN_URL;
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("grant_type", "password");
    paramMap.put("username", "MERCURYStore");
    paramMap.put("password", "mercurystore1234");
    paramMap.put("client_id", appKey);
    paramMap.put("client_secret", appSecret);
    String result = null;
    try {
      result = HttpPostUtils.getInstance().send_common_after_url(oauthGetTokenUrl, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return result;
  }
  
  @Override
  public void updateAuthorizationInfo(AomsshopT aomsshopT) {
    baseDao.update(aomsshopT);
  }

}
