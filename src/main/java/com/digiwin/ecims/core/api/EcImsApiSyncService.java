package com.digiwin.ecims.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

/**
 * 电商接口同步Service接口
 * @author 维杰
 *
 */
public interface EcImsApiSyncService {

  static final Logger logger = LoggerFactory.getLogger(EcImsApiSyncService.class);
  /**
   * 所有电商接口同步Service实现类的启动方法
   * @param taskScheduleConfig
   * @param aomsshopT
   * @throws Exception
   */
  void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception;
}
