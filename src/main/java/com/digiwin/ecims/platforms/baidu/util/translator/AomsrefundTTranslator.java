package com.digiwin.ecims.platforms.baidu.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.RefundReason;
import com.digiwin.ecims.platforms.baidu.bean.domain.enums.RefundType;
import com.digiwin.ecims.platforms.baidu.bean.domain.enums.ReturnReason;
import com.digiwin.ecims.platforms.baidu.bean.domain.refund.OrderItemInfoEntity;
import com.digiwin.ecims.platforms.baidu.bean.domain.refund.RefundInfoEntity;
import com.digiwin.ecims.platforms.baidu.bean.domain.refund.RefundOrderItemBaseInfoResult;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AomsrefundTTranslator {

  private Object entity;

  public void setEntity(Object entity) {
    this.entity = entity;
  }

  public AomsrefundTTranslator() {
  }

  public AomsrefundTTranslator(Object entity) {
    this.entity = entity;
  }
  
  public List<AomsrefundT> doTranslate(String storeId) {
    if (entity instanceof RefundOrderItemBaseInfoResult) {
      return parseBaiduRefundToAomsrefundT(storeId);
    } else {
      return new ArrayList<AomsrefundT>();
    }
  }

  private List<AomsrefundT> parseBaiduRefundToAomsrefundT(String storeId) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    RefundOrderItemBaseInfoResult result = (RefundOrderItemBaseInfoResult) this.entity;
    RefundInfoEntity refundInfo = result.getRefundInfoEntity();
    OrderItemInfoEntity refundItemInfo = result.getOrderItemInfoEntity();
    
    AomsrefundT aomsrefundT = new AomsrefundT();
    
    aomsrefundT.setId(CommonUtil.checkNullOrNot(refundInfo.getRefundNo()));
    aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(refundInfo.getMoney()));
    aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(refundInfo.getBackEndRefundStatus()));
    
    RefundType refundType = RefundType.getRefundTypeByValue(refundInfo.getRefundType());
    aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(refundType == RefundType.REFUND_ONLY ? Boolean.FALSE : Boolean.TRUE));
    
    aomsrefundT.setModified(CommonUtil.checkNullOrNot(refundInfo.getUpdateTime()));
    aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(refundInfo.getUserId()));
    aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(refundInfo.getDeliveryCompanyId()));
    aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(refundInfo.getQuantity()));
    aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundItemInfo.getItemId()));
    aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refundInfo.getOrderItemNo()));
    aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(refundItemInfo.getSkuOuterId()));
    aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(refundItemInfo.getGoodsPrice()));
    
    aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(refundInfo.getMoney()));
    aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(refundInfo.getDeliveryNo()));
    aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(refundItemInfo.getJsonProperties()));
    aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refundInfo.getBackEndRefundStatus()));
    aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundInfo.getOrderNo()));
    
    aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundInfo.getCreateTime()));
    aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refundInfo.getMemo()));
    
    String reason = "";
    if (refundType == RefundType.REFUND_ONLY) {
      reason = RefundReason.getRefundReasonStringByValue(refundInfo.getReasonType());
    }
    if (refundType == RefundType.RETURN) {
      reason = ReturnReason.getReturnReasonStringByValue(refundInfo.getReasonType());
    }
    aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(reason));
    
    aomsrefundT.setStoreId(storeId);
    aomsrefundT.setStoreType(BaiduCommonTool.STORE_TYPE);

    Date now = new Date();
    aomsrefundT.setAomsstatus("0");
    aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
    aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
    
    aomsrefundTs.add(aomsrefundT);
    
    return aomsrefundTs;
  }
}
