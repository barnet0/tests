package com.digiwin.ecims.platforms.taobao.service.jst;

import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund.TaobaoFxRefundGetCount;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund.TaobaoFxRefundGetCountById;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund.TaobaoFxRefundGetList;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.fxrefund.TaobaoFxRefundGetListById;

public interface TaobaoJstSyncFxRefundsService extends TaobaoJstSyncService {

  static final TaobaoFxRefundGetList GET_LIST_API = new TaobaoFxRefundGetList();
  
  static final TaobaoFxRefundGetListById GET_LIST_BY_ID_API = new TaobaoFxRefundGetListById();

  static final TaobaoFxRefundGetCount GET_COUNT_API = new TaobaoFxRefundGetCount();
  
  static final TaobaoFxRefundGetCountById GET_COUNT_BY_ID_API = new TaobaoFxRefundGetCountById();
  
}
