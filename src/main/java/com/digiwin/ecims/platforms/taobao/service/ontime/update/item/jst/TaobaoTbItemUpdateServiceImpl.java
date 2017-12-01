package com.digiwin.ecims.platforms.taobao.service.ontime.update.item.jst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.jst.TaobaoJstSyncTbItemsService;
import com.digiwin.ecims.platforms.taobao.service.jst.impl.TaobaoJstBaseUpdateServiceImpl;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 淘宝商品更新
 * 
 * @author KenTu
 */
@Service("taobaoTbItemUpdateServiceImpl")
public class TaobaoTbItemUpdateServiceImpl extends TaobaoJstBaseUpdateServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private TaobaoJstSyncTbItemsService taobaoJstSyncTbItemsService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
  
    timeOutExecute(
        taobaoJstSyncTbItemsService,
        TaobaoCommonTool.TB_JST_ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
        AomsitemT.class, 
        TaobaoJstSyncTbItemsService.GET_LIST_API, 
        TaobaoJstSyncTbItemsService.GET_LIST_BY_ID_API, 
        TaobaoJstSyncTbItemsService.GET_COUNT_API, 
        TaobaoJstSyncTbItemsService.GET_COUNT_BY_ID_API, 
        TaobaoCommonTool.STORE_TYPE);
    
    return false;
  }
  
}
