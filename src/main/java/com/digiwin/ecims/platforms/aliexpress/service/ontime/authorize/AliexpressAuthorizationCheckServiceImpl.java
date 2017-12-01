package com.digiwin.ecims.platforms.aliexpress.service.ontime.authorize;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import com.digiwin.ecims.platforms.aliexpress.service.authorize.AliexpressApiAuthorizationService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

@Service("aliexpressAuthorizationCheckServiceImpl")
public class AliexpressAuthorizationCheckServiceImpl implements OnTimeTaskBusiJob {

  private static final String CLASS_NAME = "AliexpressAuthorizationCheckServiceImpl";

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AliexpressApiAuthorizationService aliexpressApiAuthorizationService;

  /*
   * (non-Javadoc)
   * 
   * @see com.mercuryecinf.ontime.service.OnTimeTaskBusiJob#timeOutExecute() 速卖通API调用授权检查 1.
   * 调用查找订单接口验证，如果无法正常返回，表示需要重新授权。 2. 使用refresh_token尝试获取新的access_token，如果成功则成功。 3.
   * 如果第2步失败，则需要重新获取refresh_token和access_token 授权时长： access_token:10小时 refresh_token:30天
   */
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    List<AomsshopT> aomsShopList =
        aomsShopService.getStoreByStoreType(AliexpressCommonTool.STORE_TYPE);

    for (AomsshopT aomsshopT : aomsShopList) {
      String storeId = aomsshopT.getAomsshop001();
      String appKey = aomsshopT.getAomsshop004();
      String appSecret = aomsshopT.getAomsshop005();
      String accessToken = aomsshopT.getAomsshop006();
      String refreshToken = aomsshopT.getAomsshop007();
      if (refreshToken == null || refreshToken.length() == 0 || accessToken == null
          || accessToken.length() == 0) {
        return false;
      }

      if (!aliexpressApiAuthorizationService.isAuthorizationValid(storeId, appKey, appSecret,
          accessToken)) {
        String result = aliexpressApiAuthorizationService.reauthorize(storeId, appKey, appSecret,
            accessToken, refreshToken);
        if (result != null) {
          LogInfoT logInfoT = new LogInfoT();
          logInfoT.setIpAddress(InetAddressTool.getLocalIpv4());
          logInfoT.setCallMethod(CLASS_NAME);
          logInfoT.setBusinessType(LogInfoBizTypeEnum.OAUTH_CHECK.getValueString());
          logInfoT.setReqType("taskSchedule");
          logInfoT.setResSize(BigInteger.valueOf(result.length()));
          logInfoT.setIsSuccess(true);
          logInfoT.setReqTime(new Date());
          logInfoT.setResCode("success");
          logInfoT.setResMsg("重新授权成功" + result);
          logInfoT.setErrMsg("");
          logInfoT.setPushLimits(5);
          logInfoT.setFinalStatus("1");
          logInfoT.setErrStoreId(storeId);
          loginfoOperateService.newTransaction4SaveLog(logInfoT);

          // 设定新的获取access_token的返回值
          aomsshopT.setAomsshop008(result);
          String newAccessToken = JsonUtil.getJsonNodeTextByFieldName(result, "access_token");
          // 设定新的access_token
          aomsshopT.setAomsshop006(newAccessToken);
          JsonNode newRefreshTokenNode = JsonUtil.getJsonNodeByFieldName(result, "refresh_token");
          boolean isNewRefreshToken = newRefreshTokenNode != null;
          // 设定新的refresh_token
          if (isNewRefreshToken) {
            aomsshopT.setAomsshop007(newRefreshTokenNode.textValue());
          }

          aomsshopT.setAomsshopmoddt(DateTimeTool.getTodayBySecond()); // add by mowj
                                                                       // 20160530设定修改时间，方便追溯问题
          aliexpressApiAuthorizationService.updateAuthorizationInfo(aomsshopT);

        }
      }
    }

    return true;
  }

}
