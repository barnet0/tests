package com.digiwin.ecims.platforms.taobao.service.jst;

import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem.TaobaoTbItemGetCount;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem.TaobaoTbItemGetCountById;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem.TaobaoTbItemGetList;
import com.digiwin.ecims.platforms.taobao.bean.jst.api.tbitem.TaobaoTbItemGetListById;

public interface TaobaoJstSyncTbItemsService extends TaobaoJstSyncService {

  static final TaobaoTbItemGetList GET_LIST_API = new TaobaoTbItemGetList();
  
  static final TaobaoTbItemGetListById GET_LIST_BY_ID_API = new TaobaoTbItemGetListById();
  
  static final TaobaoTbItemGetCount GET_COUNT_API = new TaobaoTbItemGetCount();
  
  static final TaobaoTbItemGetCountById GET_COUNT_BY_ID_API = new TaobaoTbItemGetCountById();
}
