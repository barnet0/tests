package com.digiwin.ecims.platforms.taobao.service.jst;

import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade.TaobaoTbTradeGetCount;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade.TaobaoTbTradeGetCountById;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade.TaobaoTbTradeGetDetail;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade.TaobaoTbTradeGetList;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbtrade.TaobaoTbTradeGetListById;

public interface TaobaoJstSyncTbOrdersService extends TaobaoJstSyncService {

  static final TaobaoTbTradeGetList GET_LIST_API = new TaobaoTbTradeGetList();
  
  static final TaobaoTbTradeGetListById GET_LIST_BY_ID_API = new TaobaoTbTradeGetListById();

  static final TaobaoTbTradeGetCount GET_COUNT_API = new TaobaoTbTradeGetCount();
  
  static final TaobaoTbTradeGetCountById GET_COUNT_BY_ID_API = new TaobaoTbTradeGetCountById();
  
  static final TaobaoTbTradeGetDetail GET_DETAIL_API = new TaobaoTbTradeGetDetail();
}
