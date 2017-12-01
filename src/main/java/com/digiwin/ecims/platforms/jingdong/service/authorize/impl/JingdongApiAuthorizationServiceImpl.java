package com.digiwin.ecims.platforms.jingdong.service.authorize.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.platforms.jingdong.service.authorize.JingdongApiAuthorizationService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class JingdongApiAuthorizationServiceImpl implements JingdongApiAuthorizationService {

  private static final Logger logger = LoggerFactory.getLogger(JingdongApiAuthorizationServiceImpl.class);

  @Autowired
  private BaseDAO baseDao;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  
  @Override
  public boolean isAuthorizationValid(String storeId, String appKey, String appSecret,
      String accessToken) {
    // TODO Auto-generated method stub
    try {
      JdClient client = new DefaultJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API),
          accessToken, appKey, appSecret);

      OrderSearchRequest request = new OrderSearchRequest();

      // 根據更新時間
      Date todayDate = DateTimeTool.getTodayDate();
      request.setStartDate(DateTimeTool.format(DateTimeTool.getBeforeDays(todayDate, 7)));
      request.setEndDate(DateTimeTool.format(todayDate));
      
      request.setOrderState(JingdongCommonTool.ORDER_STATUS);
      request.setPage(JingdongCommonTool.MIN_PAGE_NO + "");
      request.setPageSize(JingdongCommonTool.MIN_PAGE_SIZE + "");

      request.setDateType(JingdongCommonTool.CREATE_TIME);

      request.setOptionalFields(JingdongCommonTool.ORDER_FIELDS);

      OrderSearchResponse response = client.execute(request);
      if (response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE)) {
        return true;
      }
      
    } catch (Exception e) {
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
    // TODO Auto-generated method stub
    String url = paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_TOKEN);
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("grant_type", "refresh_token");
    paramMap.put("client_id", appKey);
    paramMap.put("client_secret", appSecret);
    paramMap.put("refresh_token", refreshToken);
    String result = null;
    try {
      result = HttpPostUtils.getInstance().send_common_after_url(url, paramMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return result;
  }

  @Override
  public String getNewRefreshToken(String appKey, String appSecret, String refreshToken,
      String accessToken) {
    // TODO Auto-generated method stub
    return null;
  }

}
