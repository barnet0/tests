package com.digiwin.ecims.platforms.taobao.service.translator.refund.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Refund;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.RefundGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpTbRefundService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJdpTbRefundServiceImpl implements TaobaoJdpTbRefundService {

  @Override
  public List<AomsrefundT> parseTaobaoResToAomsrefundT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException {
    return parseRefundToAomsrefundT(taobaoRes.getResponse(), taobaoRes.getComparisonCol(), storeId);
  }

  @Override
  public List<AomsrefundT> parseResponseToAomsrefundT(
      RefundGetResponse refundGetResponse, String storeId) {
    // 取得交易物件Refund
    Refund refund = refundGetResponse.getRefund();
    return parseRefundToAomsrefundT(refund, StringTool.EMPTY, storeId);
  }

  
  /**
   * 将单笔退单的JSON字符串转换为中台的退单类型aomsrefundt
   * (总入口)
   * @param json
   * @param jdpModified
   * @return
   * @throws ApiException
   */
  private List<AomsrefundT> parseRefundToAomsrefundT(
      String json, String jdpModified, String storeId) 
          throws ApiException {
    RefundGetResponse refundGetResponse = TaobaoUtils.parseResponse(json, RefundGetResponse.class);
    Refund refund = refundGetResponse.getRefund();
    return parseRefundToAomsrefundT(refund, jdpModified, storeId);
  }
  
  /**
   * 转化淘宝refund实例为中台退单结构aomsrefundt
   * (实际转换方法)
   * @param refund
   * @param jdpModified
   * @return
   */
  private List<AomsrefundT> parseRefundToAomsrefundT(
      Refund refund, String jdpModified, String storeId) {
    // refund轉換成AomsordT
    List<AomsrefundT> list = new ArrayList<AomsrefundT>();
    AomsrefundT aomsrefundT = new AomsrefundT();
    // 單頭
    aomsrefundT.setId(CommonUtil.checkNullOrNot(refund.getRefundId()));

    aomsrefundT.setAoms002(TaobaoCommonTool.REFUND_TYPE_NOT_FX);
    aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(refund.getHasGoodReturn()));
    aomsrefundT.setModified(CommonUtil.checkNullOrNot(refund.getModified()));
    // 普通退款/货单栏位
    aomsrefundT.setAoms013(CommonUtil.checkNullOrNot(refund.getAddress()));
    aomsrefundT.setAoms014(CommonUtil.checkNullOrNot(refund.getAdvanceStatus()));
    aomsrefundT.setAoms015(CommonUtil.checkNullOrNot(refund.getAlipayNo()));
    aomsrefundT.setAoms016(CommonUtil.checkNullOrNot(refund.getAttribute()));
    aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(refund.getBuyerNick()));
    aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(refund.getCompanyName()));
    aomsrefundT.setAoms019(CommonUtil.checkNullOrNot(refund.getCsStatus()));
    aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(refund.getGoodReturnTime()));
    aomsrefundT.setAoms021(CommonUtil.checkNullOrNot(refund.getGoodStatus()));
    aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(refund.getNum()));
    /*
     * modify by lizhi 20150725 此字段改为联合主键
     */
    aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refund.getNumIid()));
    aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refund.getOid()));
    aomsrefundT.setAoms025(CommonUtil.checkNullOrNot(refund.getOperationContraint()));
    aomsrefundT.setAoms026(CommonUtil.checkNullOrNot(refund.getOrderStatus()));
    aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(refund.getOuterId()));
    aomsrefundT.setAoms028(CommonUtil.checkNullOrNot(refund.getPayment()));
    aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(refund.getPrice()));
    aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(refund.getRefundFee()));
    aomsrefundT.setAoms031(CommonUtil.checkNullOrNot(refund.getRefundPhase()));
    aomsrefundT.setAoms032(CommonUtil.checkNullOrNot(refund.getRefundVersion()));
    aomsrefundT.setAoms033(CommonUtil.checkNullOrNot(refund.getSellerNick()));
    aomsrefundT.setAoms034(CommonUtil.checkNullOrNot(refund.getShippingType()));
    aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(refund.getSid()));
    aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(refund.getSku()));
    aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refund.getStatus()));
    aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refund.getTid()));
    aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(refund.getTitle()));
    aomsrefundT.setAoms040(CommonUtil.checkNullOrNot(refund.getTotalFee()));
    // 公用栏位
    aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refund.getCreated()));
    aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refund.getDesc()));
    aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refund.getReason()));

    aomsrefundT.setStoreType(TaobaoCommonTool.STORE_TYPE);
    aomsrefundT.setStoreId(CommonUtil.checkNullOrNot(storeId));

    aomsrefundT.setJdpModified(jdpModified);
    
    Date now = new Date();
    aomsrefundT.setAomscrtdt(DateTimeTool.format(now));
    aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

    list.add(aomsrefundT);

    return list;
  }

}
