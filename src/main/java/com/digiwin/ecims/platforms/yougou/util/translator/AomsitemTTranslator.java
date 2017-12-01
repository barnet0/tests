package com.digiwin.ecims.platforms.yougou.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.yougou.bean.domain.item.Item;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;

public class AomsitemTTranslator {

  private static final String YOUGOU_DOMAIN_URL = "www.yougou.com/";
  
  private Object entity;

  public void setEntity(Object entity) {
    this.entity = entity;
  }

  public AomsitemTTranslator(Object entity) {
    super();
    this.entity = entity;
  }

  public AomsitemTTranslator() {
  }
  
  public List<AomsitemT> doTranslate(String storeId) {
    if (entity instanceof List) {
      return parseYougouItemToAomsitemT(storeId);
    } else {
      return new ArrayList<AomsitemT>();
    }
  }

  private List<AomsitemT> parseYougouItemToAomsitemT(String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    @SuppressWarnings("unchecked")
    List<Item> itemList = (List<Item>) this.entity;
    
    for (Item item : itemList) {
      AomsitemT aomsitemT = new AomsitemT();
      
      aomsitemT.setId(CommonUtil.checkNullOrNot(item.getCommodityNo()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(item.getCommodityName()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(item.getProductNo()));
      aomsitemT.setAoms005(CommonUtil.checkNullOrNot(item.getThirdPartyCode()));
      String itemStatus = item.getCommodityStatus();
      aomsitemT.setAoms007(CommonUtil.checkNullOrNot(itemStatus.equals("2") ? "onsale" : "instock"));
      aomsitemT.setAoms013(CommonUtil.checkNullOrNot(YOUGOU_DOMAIN_URL + item.getCommodityItemsPage()));
      aomsitemT.setAoms014(CommonUtil.checkNullOrNot(item.getCommodityName()));
      aomsitemT.setAoms018(CommonUtil.checkNullOrNot(item.getTotalStockQuantity()));
      aomsitemT.setAoms019(CommonUtil.checkNullOrNot(item.getModified()));
      aomsitemT.setAoms024(CommonUtil.checkNullOrNot(item.getSalePrice()));
      
      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(YougouCommonTool.STORE_TYPE);
      
      Date now = new Date();
      aomsitemT.setAomsstatus("0");
      aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsitemTs.add(aomsitemT);
    }
    
    
    return aomsitemTs;
  }
}
