package com.digiwin.ecims.platforms.aliexpress.service.authorize.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.AliExpressClient;
import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderListQueryResponse;
import com.digiwin.ecims.platforms.aliexpress.service.authorize.AliexpressApiAuthorizationService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class AliexpressApiAuthorizationServiceImpl implements AliexpressApiAuthorizationService {

  private static final Logger logger = LoggerFactory.getLogger(AliexpressApiAuthorizationServiceImpl.class);

  @Autowired
  private BaseDAO baseDao;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  @Override
  public void updateAuthorizationInfo(AomsshopT aomsshopT) {
    baseDao.update(aomsshopT);
  }

  @Override
  public boolean isAuthorizationValid(String storeId, String appKey, String appSecret, String accessToken) {
    AliExpressClient client =
        new AliExpressClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.ALIEXPRESS_API), 
            appKey, appSecret);

    FindOrderListQueryRequest request = new FindOrderListQueryRequest();
    request.setPage(AliexpressCommonTool.MIN_PAGE_NO);
    request.setPageSize(AliexpressCommonTool.MIN_PAGE_SIZE);
    FindOrderListQueryResponse response = null;
    try {
      response = client.execute(request, accessToken);
    } catch (Exception e) {
      return false;
    }

    if (response != null) {
      return true;
    }
    return false;
  }

  @Override
  public String reauthorize(String storeId, String appKey, String appSecret, String accessToken, String refreshToken) {
    String result = null;
    result = getNewAccessToken(appKey, appSecret, refreshToken);
    if (result == null || result.toLowerCase().contains("err")) {
      result = getNewRefreshToken(appKey, appSecret, refreshToken, accessToken);
    }
    
    return result;
  }

  @Override
  public String getNewAccessToken(String appKey, String appSecret, String refreshToken) {
    String baseUrl = paramSystemService.getEcApiUrl(EcApiUrlEnum.ALIEXPRESS_API);
    String oauthGetTokenUrl = changeToHttps(baseUrl) + "param2/1/system.oauth2/getToken/" + appKey;
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("grant_type", "refresh_token");
    paramMap.put("client_id", appKey);
    paramMap.put("client_secret", appSecret);
    paramMap.put("refresh_token", refreshToken);
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
    String baseUrl = paramSystemService.getEcApiUrl(EcApiUrlEnum.ALIEXPRESS_API);
    String oauthGetTokenUrl = changeToHttps(baseUrl) + "param2/1/system.oauth2/postponeToken/" + appKey;
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("client_id", appKey);
    paramMap.put("client_secret", appSecret);
    paramMap.put("refresh_token", refreshToken);
    paramMap.put("access_token", accessToken);
    String result = null;
    try {
      result = HttpPostUtils.getInstance().send_common_after_url(oauthGetTokenUrl, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return result;
  }
  
  private String changeToHttps(String apiHttpUrl) {
    return apiHttpUrl.replace("http", "https");
  }
}
