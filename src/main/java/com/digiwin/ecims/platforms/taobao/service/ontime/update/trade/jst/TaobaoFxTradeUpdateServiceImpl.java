package com.digiwin.ecims.platforms.taobao.service.ontime.update.trade.jst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncFxOrdersService;
import com.digiwin.ecims.platforms.taobao.service.jst.impl.TaobaoJstBaseUpdateServiceImpl;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 淘宝分销订单更新
 * 
 * @author KenTu 
 */
@Service("taobaoFxTradeUpdateServiceImpl")
public class TaobaoFxTradeUpdateServiceImpl extends TaobaoJstBaseUpdateServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private TaobaoJstSyncFxOrdersService taobaoJstSyncFxOrdersService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
  
    timeOutExecute(
        taobaoJstSyncFxOrdersService, 
        TaobaoCommonTool.FX_JST_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
        AomsordT.class, 
        TaobaoJstSyncFxOrdersService.GET_LIST_API, 
        TaobaoJstSyncFxOrdersService.GET_LIST_BY_ID_API, 
        TaobaoJstSyncFxOrdersService.GET_COUNT_API, 
        TaobaoJstSyncFxOrdersService.GET_COUNT_BY_ID_API, 
        TaobaoCommonTool.STORE_TYPE_FX);
    
    return false;
  }
  
}
