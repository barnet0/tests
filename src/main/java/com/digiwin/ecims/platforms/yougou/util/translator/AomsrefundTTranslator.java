package com.digiwin.ecims.platforms.yougou.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.yougou.bean.domain.refund.ReturnDetail;
import com.digiwin.ecims.platforms.yougou.bean.domain.refund.ReturnItem;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;

public class AomsrefundTTranslator {

  private Object entity;

  public AomsrefundTTranslator(Object entity) {
    super();
    this.entity = entity;
  }
  
  public List<AomsrefundT> doTranslate(String storeId) {
    if (entity instanceof ReturnItem) {
      return parseYougouReturnToAomsrefundT(storeId);
    } else {
      return new ArrayList<AomsrefundT>();
    }
  }

  private List<AomsrefundT> parseYougouReturnToAomsrefundT(String storeId) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    ReturnItem head = (ReturnItem) this.entity;
    
    for (ReturnDetail detail : head.getReturnDetails()) {
      AomsrefundT aomsrefundT = new AomsrefundT();
      
      aomsrefundT.setId(CommonUtil.checkNullOrNot(detail.getApplyNo()));
      aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(detail.getApplyStatus()));
      aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(detail.getApplyer()));
      aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(head.getReturnLogisticsName()));
      aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(detail.getProdNo()));
      aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(head.getOrderSubNo()));
      aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(detail.getSupplierCode()));
      aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(detail.getProdUnitPrice()));
      aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(head.getReturnExpressCode()));
      aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(detail.getCommoditySpecificationStr()));
      aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(detail.getApplyStatus()));
      
      aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(detail.getApplyTime()));
      aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(detail.getAftersaleReason()));
      
      aomsrefundT.setStoreId(storeId);
      aomsrefundT.setStoreType(YougouCommonTool.STORE_TYPE);
      
      Date now = new Date();
      aomsrefundT.setAomsstatus("0");
      aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsrefundTs.add(aomsrefundT);
    }
    
    return aomsrefundTs;
  }
}
