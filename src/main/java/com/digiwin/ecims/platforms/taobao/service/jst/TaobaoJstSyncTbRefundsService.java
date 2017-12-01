package com.digiwin.ecims.platforms.taobao.service.jst;

import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund.TaobaoTbRefundGetCount;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund.TaobaoTbRefundGetCountById;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund.TaobaoTbRefundGetList;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbrefund.TaobaoTbRefundGetListById;

public interface TaobaoJstSyncTbRefundsService extends TaobaoJstSyncService {

  static final TaobaoTbRefundGetList GET_LIST_API = new TaobaoTbRefundGetList();
  
  static final TaobaoTbRefundGetListById GET_LIST_BY_ID_API = new TaobaoTbRefundGetListById();

  static final TaobaoTbRefundGetCount GET_COUNT_API = new TaobaoTbRefundGetCount();
  
  static final TaobaoTbRefundGetCountById GET_COUNT_BY_ID_API = new TaobaoTbRefundGetCountById();
  
}
