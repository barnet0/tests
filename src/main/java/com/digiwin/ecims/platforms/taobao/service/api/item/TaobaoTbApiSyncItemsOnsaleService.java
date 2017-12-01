package com.digiwin.ecims.platforms.taobao.service.api.item;

import java.io.IOException;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.api.EcImsApiSyncItemsService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

/**
 * 使用API同步淘宝出售中的商品
 * @author 维杰
 *
 */
public interface TaobaoTbApiSyncItemsOnsaleService extends EcImsApiSyncItemsService {

  Boolean syncTaobaoItemsOnsale(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws ApiException, IOException;
  
}
