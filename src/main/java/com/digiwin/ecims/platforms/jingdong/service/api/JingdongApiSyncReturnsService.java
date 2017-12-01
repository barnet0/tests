package com.digiwin.ecims.platforms.jingdong.service.api;

import java.io.IOException;

import com.jd.open.api.sdk.JdException;

import com.digiwin.ecims.core.api.EcImsApiSyncRefundsService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface JingdongApiSyncReturnsService extends EcImsApiSyncRefundsService {

  /**
   * 使用API的方式，同步京东服务单
   * 
   * @param taskScheduleConfig 同步設定檔
   * @param aomsshopT 商店授權
   * @throws JdException 
   * @throws IOException 
   */
  Boolean syncJingdongAfss(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) 
      throws JdException, IOException;
}
