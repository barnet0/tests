package com.digiwin.ecims.platforms.ccb.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.ccb.bean.domain.item.detail.get.ItemInfo;
import com.digiwin.ecims.platforms.ccb.bean.domain.item.detail.get.ItemSkuInfo;
import com.digiwin.ecims.platforms.ccb.bean.domain.item.list.get.ItemList;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool.ItemStatusEnum;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AomsitemTTranslator {

  private Object entity;

  public AomsitemTTranslator(Object entity) {
    super();
    this.entity = entity;
  }

  public List<AomsitemT> doTranslate(String storeId) {
    // TODO 由于商品详情与商品列表接口的文档写的不明确，这里需要测试后才能确定写法
    if (entity instanceof ItemList) {
      List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
      ItemList itemList = (ItemList) this.entity;
      for (ItemInfo itemInfo : itemList.getItems()) {
        aomsitemTs.addAll(parseCcbItemToAomsitemT(itemInfo, storeId));
      }
      return aomsitemTs;
    } else if (entity instanceof ItemInfo){
      return parseCcbItemToAomsitemT((ItemInfo) entity, storeId);
    } else {
      return new ArrayList<AomsitemT>();
    }
  }

  private List<AomsitemT> parseCcbItemToAomsitemT(ItemInfo itemInfo, String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();

    for (ItemSkuInfo itemSkuInfo : itemInfo.getSkuInfos()) {
      AomsitemT aomsitemT = new AomsitemT();

      aomsitemT.setId(CommonUtil.checkNullOrNot(itemSkuInfo.getProId()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(itemSkuInfo.getName()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSkuInfo.getSkuId()));
      aomsitemT.setAoms005(CommonUtil.checkNullOrNot(itemSkuInfo.getProId()));
      aomsitemT.setAoms006(CommonUtil.checkNullOrNot(itemSkuInfo.getDescription()));

      aomsitemT.setAoms007(CommonUtil
          .checkNullOrNot(itemSkuInfo.getStatus().equals(ItemStatusEnum.ON_SHELF.getStatus())
              ? "onsale" : "instock"));
      
      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(CcbCommonTool.STORE_TYPE);
      
      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSkuInfo.getStock()));

      Date now = new Date();
      aomsitemT.setAomsstatus("0");
      aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsitemTs.add(aomsitemT);
    }

    return aomsitemTs;
  }
}
