package com.digiwin.ecims.system.service;

import com.digiwin.ecims.core.model.AomsshopT;

public interface ApiAuthorizationService {

  boolean isAuthorizationValid(String storeId, String appKey, String appSecret, String accessToken);
  
  String reauthorize(String storeId, String appKey, String appSecret, String accessToken, String refreshToken);
  
  void updateAuthorizationInfo(AomsshopT aomsshopT);
  
  String getNewAccessToken(String appKey, String appSecret, String refreshToken);

  String getNewRefreshToken(String appKey, String appSecret, String refreshToken, String accessToken);
  
  
}
