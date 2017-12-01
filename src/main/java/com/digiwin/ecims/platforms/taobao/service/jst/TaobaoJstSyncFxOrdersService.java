package com.digiwin.ecims.platforms.taobao.service.jst;

import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxtrade.TaobaoFxTradeGetCount;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxtrade.TaobaoFxTradeGetCountById;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxtrade.TaobaoFxTradeGetDetail;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxtrade.TaobaoFxTradeGetList;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxtrade.TaobaoFxTradeGetListById;

public interface TaobaoJstSyncFxOrdersService extends TaobaoJstSyncService {

  static final TaobaoFxTradeGetList GET_LIST_API = new TaobaoFxTradeGetList();
  
  static final TaobaoFxTradeGetListById GET_LIST_BY_ID_API = new TaobaoFxTradeGetListById();
  
  static final TaobaoFxTradeGetCount GET_COUNT_API = new TaobaoFxTradeGetCount();
  
  static final TaobaoFxTradeGetCountById GET_COUNT_BY_ID_API = new TaobaoFxTradeGetCountById();
  
  static final TaobaoFxTradeGetDetail GET_DETAIL_API = new TaobaoFxTradeGetDetail();
}
