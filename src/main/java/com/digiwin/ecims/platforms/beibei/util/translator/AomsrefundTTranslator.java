package com.digiwin.ecims.platforms.beibei.util.translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.refund.RefundDetailGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AomsrefundTTranslator {

  private Object object;

  public AomsrefundTTranslator(Object object) {
    this.object = object;
  }
  
  public List<AomsrefundT> doTranslate(String storeId) {
    if (object instanceof OuterRefundDetailGetResponse) {
      return parseBeibeiRefundToAomsrefundT(storeId);
    } else {
      return Collections.emptyList();
    }
  }

  private List<AomsrefundT> parseBeibeiRefundToAomsrefundT(String storeId) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    List<RefundDetailGetDto> refundDetails = ((OuterRefundDetailGetResponse)this.object).getData();
    
    for (RefundDetailGetDto refundDetailGetDto : refundDetails) {
      AomsrefundT aomsrefundT = new AomsrefundT();
      aomsrefundT.setId(CommonUtil.checkNullOrNot(refundDetailGetDto.getId()));
      aomsrefundT.setAoms002("1");
      aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(refundDetailGetDto.getStatus()));
      // FIXME 需要确认
      aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));
      
      aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(refundDetailGetDto.getCompany()));
      
      aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(refundDetailGetDto.getNum()));
      aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundDetailGetDto.getIid()));
      aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refundDetailGetDto.getOid()));
      aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(refundDetailGetDto.getOuterId()));
      aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(refundDetailGetDto.getPrice()));
      
      aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(refundDetailGetDto.getRefundFee()));
      aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(refundDetailGetDto.getOutSid()));
      aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(refundDetailGetDto.getSkuId()));
      aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refundDetailGetDto.getStatus()));
      aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundDetailGetDto.getOid()));
      
      aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundDetailGetDto.getCreateTime()));
      aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refundDetailGetDto.getDesc()));
      aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refundDetailGetDto.getReason()));
      
      aomsrefundT.setStoreId(storeId);
      aomsrefundT.setStoreType(BeibeiCommonTool.STORE_TYPE);
      
      Date now = new Date();
      aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsrefundTs.add(aomsrefundT);
    }
    
    return aomsrefundTs;
  }
}
