package com.digiwin.ecims.platforms.taobao.service.authorize.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryClusterTaobaoClient;
import com.taobao.api.Constants;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.platforms.taobao.service.authorize.TaobaoApiAuthorizationService;

@Service
public class TaobaoApiAuthorizationServiceImpl implements TaobaoApiAuthorizationService {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoApiAuthorizationServiceImpl.class);

  @Autowired
  private BaseDAO baseDao;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  @Override
  public boolean isAuthorizationValid(String storeId, String appKey, String appSecret,
      String accessToken) {
    try {
      TaobaoClient client =
          new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
              appKey, appSecret, Constants.FORMAT_JSON);
      TradesSoldGetRequest request = new TradesSoldGetRequest();
      request.setFields("tid");
      request.setPageNo(1L);
      request.setPageSize(1L);
      Date todayDate = DateTimeTool.getTodayDate();
      request.setStartCreated(DateTimeTool.getBeforeDays(todayDate, 7));
      request.setEndCreated(todayDate);
      TradesSoldGetResponse response = client.execute(request, accessToken);
      if (response.isSuccess()) {
        return true;
      }
    } catch (ApiException e) {
      e.printStackTrace();
      return false;
    } 
    return false;
  }

  @Override
  public String reauthorize(String storeId, String appKey, String appSecret, String accessToken,
      String refreshToken) {
    String result = null;
    result = getNewAccessToken(appKey, appSecret, refreshToken);
    
    return result;
  }

  @Override
  public void updateAuthorizationInfo(AomsshopT aomsshopT) {
    baseDao.update(aomsshopT);
  }

  @Override
  public String getNewAccessToken(String appKey, String appSecret, String refreshToken) {
    String url = paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_TOKEN);
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("grant_type", "refresh_token");
    paramMap.put("client_id", appKey);
    paramMap.put("client_secret", appSecret);
    paramMap.put("refresh_token", refreshToken);
    String result = null;
    try {
      result = HttpPostUtils.getInstance().send_common(url, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return result;
  }

  @Override
  public String getNewRefreshToken(String appKey, String appSecret, String refreshToken,
      String accessToken) {
    return null;
  }

}
