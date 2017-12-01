package com.digiwin.ecims.platforms.yhd.service.api;

import com.digiwin.ecims.core.api.EcImsApiSyncRefundsService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

/**
 * 异常退款单更新
 * @author 维杰
 *
 */
public interface YhdApiSyncRefundsAbnormalService extends EcImsApiSyncRefundsService {

  /**
   * 更新本地的异常退款订单的状态（因为第一次同步回来的状态为“未审核”）
   * @return
   * @throws Exception
   */
  Boolean syncRefundsToBeChecked() throws Exception;
}
