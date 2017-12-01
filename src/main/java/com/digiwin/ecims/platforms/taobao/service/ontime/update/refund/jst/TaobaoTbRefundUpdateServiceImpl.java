package com.digiwin.ecims.platforms.taobao.service.ontime.update.refund.jst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncTbRefundsService;
import com.digiwin.ecims.platforms.taobao.service.jst.impl.TaobaoJstBaseUpdateServiceImpl;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 淘宝一般退货更新
 * 
 * @author KenTu
 */
@Service("taobaoTbRefundUpdateServiceImpl")
public class TaobaoTbRefundUpdateServiceImpl extends TaobaoJstBaseUpdateServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private TaobaoJstSyncTbRefundsService taobaoJstSyncTbRefundsService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
  
    timeOutExecute(
        taobaoJstSyncTbRefundsService, 
        TaobaoCommonTool.TB_JST_REFUND_UPDATE_SCHEDULE_NAME_PREFIX,
        AomsrefundT.class, 
        TaobaoJstSyncTbRefundsService.GET_LIST_API, 
        TaobaoJstSyncTbRefundsService.GET_LIST_BY_ID_API, 
        TaobaoJstSyncTbRefundsService.GET_COUNT_API, 
        TaobaoJstSyncTbRefundsService.GET_COUNT_BY_ID_API, 
        TaobaoCommonTool.STORE_TYPE);
    
    return false;
  }
  
}
