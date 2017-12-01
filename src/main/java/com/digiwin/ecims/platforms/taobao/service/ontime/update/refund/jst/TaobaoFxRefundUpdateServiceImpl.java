package com.digiwin.ecims.platforms.taobao.service.ontime.update.refund.jst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncFxRefundsService;
import com.digiwin.ecims.platforms.taobao.service.jst.impl.TaobaoJstBaseUpdateServiceImpl;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 淘宝分销退货更新
 * 
 * @author KenTu 
 */
@Service("taobaoFxRefundUpdateServiceImpl")
public class TaobaoFxRefundUpdateServiceImpl extends TaobaoJstBaseUpdateServiceImpl implements OnTimeTaskBusiJob {
  
  @Autowired
  private TaobaoJstSyncFxRefundsService taobaoJstSyncFxRefundsService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
  
    timeOutExecute(
        taobaoJstSyncFxRefundsService, 
        TaobaoCommonTool.FX_JST_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
        AomsrefundT.class, 
        TaobaoJstSyncFxRefundsService.GET_LIST_API, 
        TaobaoJstSyncFxRefundsService.GET_LIST_BY_ID_API, 
        TaobaoJstSyncFxRefundsService.GET_COUNT_API, 
        TaobaoJstSyncFxRefundsService.GET_COUNT_BY_ID_API, 
        TaobaoCommonTool.STORE_TYPE_FX);
    
    return false;
  }
  
}
