package com.digiwin.ecims.platforms.aliexpress.service.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface AliexpressApiHelperService {

  /**
   * 根据中台数据库查询状态非“FINISH”的订单号，调用API查询是否为纠纷中的订单，如果是，作为aomsrefund_t的资料插入
   * @param taskScheduleConfig
   * @param aomsshopT
   */
//  public void updateOrderRefundStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT);
  
  
  /**
   * 根据中台数据库查询未完成状态订单的订单号，一个个更新
   * @param taskScheduleConfig
   * @param aomsshopT
   * @throws Exception 
   */
  public void updateOrderStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception;
}
