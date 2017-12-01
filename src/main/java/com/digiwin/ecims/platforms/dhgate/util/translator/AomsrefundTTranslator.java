package com.digiwin.ecims.platforms.dhgate.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderDisputeCloseInfo;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderDisputeOpenInfo;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.base.OrderDisputeInfo;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;

public class AomsrefundTTranslator {

  private Object info;

  public AomsrefundTTranslator(Object info) {
    super();
    this.info = info;
  }

  public List<AomsrefundT> doTranslate(String storeId) {
    if (this.info instanceof OrderDisputeOpenInfo) {
      return parseDhDisputeOrderToAomsrefundT(storeId, true);
    } else if (this.info instanceof OrderDisputeCloseInfo) {
      return parseDhDisputeOrderToAomsrefundT(storeId, false);
    } else {
      return new ArrayList<AomsrefundT>();
    }
  }

  private <T> List<AomsrefundT> parseDhDisputeOrderToAomsrefundT(String storeId, boolean isOpen) {
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    OrderDisputeInfo info = null;
    if (isOpen) {
      info = (OrderDisputeOpenInfo) this.info;
    } else {
      info = (OrderDisputeCloseInfo) this.info;
    }

    AomsrefundT aomsrefundT = new AomsrefundT();

    aomsrefundT.setId(CommonUtil.checkNullOrNot(info.getOrderNo()));
    aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(info.getDisputeStatus()));
    // TODO 2016.3.7更新 由于敦煌网是整单退，所以这里可以自己写一个固定的值.仍然需要测试后验证
    aomsrefundT.setAoms023("REF" + info.getOrderNo());

    aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(info.getDisputeStatus()));
    aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(info.getOrderNo()));
    if (isOpen) {
      aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(((OrderDisputeOpenInfo)info).getDisputeOpenDate()));
    }

    aomsrefundT.setStoreId(storeId);
    aomsrefundT.setStoreType(DhgateCommonTool.STORE_TYPE);

    Date now = new Date();
    aomsrefundT.setAomsstatus("0");
    aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
    aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

    aomsrefundTs.add(aomsrefundT);

    return aomsrefundTs;
  }
}
