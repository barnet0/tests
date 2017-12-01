package com.digiwin.ecims.platforms.taobao.service.translator.refund.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.RefundDetail;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.FenxiaoRefundGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpFxRefundService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJdpFxRefundServiceImpl implements TaobaoJdpFxRefundService {

  @Override
  public List<AomsrefundT> parseTaobaoResToAomsrefundT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException {
    return parseRefundDetailToAomsrefundT(taobaoRes.getResponse(), taobaoRes.getComparisonCol(), storeId);
  }
  
  @Override
  public List<AomsrefundT> parseResponseToAomsrefundT(
      FenxiaoRefundGetResponse fenxiaoRefundGetResponse, String storeId) {
    // 取得交易物件Refund
    RefundDetail refundDetail = fenxiaoRefundGetResponse.getRefundDetail();

    return parseRefundDetailToAomsrefundT(refundDetail, StringTool.EMPTY, storeId);
  }

  private List<AomsrefundT> parseRefundDetailToAomsrefundT(String json, String jdpModified, String storeId) 
      throws ApiException {
    // JSON轉換成TradeFullinfoGetResponse物件
    FenxiaoRefundGetResponse fenxiaoRefundGetResponse =
        TaobaoUtils.parseResponse(json, FenxiaoRefundGetResponse.class);
  
    return parseRefundDetailToAomsrefundT(
        fenxiaoRefundGetResponse.getRefundDetail(), jdpModified, storeId);
  }

  /**
   * 將RefundDetail物件透過該mapping方法轉換成AomsrefundT物件並返回
   * 
   * @param RefundDetail Taobao的RefundDetail物件
   * @return List<Object> AomsrefundT的List
   */
  private List<AomsrefundT> parseRefundDetailToAomsrefundT(
      RefundDetail refundDetail, String jdpModified, String storeId) {
    // refund轉換成AomsordT
    if (refundDetail == null) {
      return Collections.emptyList();
    }

    List<AomsrefundT> list = new ArrayList<AomsrefundT>();

    // refund轉換成AomsordT
    AomsrefundT aomsrefundT = new AomsrefundT();
    // 單頭
    aomsrefundT.setId(CommonUtil.checkNullOrNot(refundDetail.getSubOrderId()));
    aomsrefundT.setAoms002(TaobaoCommonTool.REFUND_TYPE_FX);
    // 采购单退款栏位
    aomsrefundT.setAoms003(CommonUtil.checkNullOrNot(refundDetail.getDistributorNick()));
    aomsrefundT.setAoms004(CommonUtil.checkNullOrNot(refundDetail.getPaySupFee()));
    aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(refundDetail.getPurchaseOrderId()));
    aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(refundDetail.getRefundFee()));
    aomsrefundT.setAoms007(CommonUtil.checkNullOrNot(refundDetail.getRefundFlowType()));
    aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(refundDetail.getRefundStatus()));
 // 公用栏位
    aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(refundDetail.getIsReturnGoods()));
    
    aomsrefundT.setAoms010(CommonUtil.checkNullOrNot(refundDetail.getSubOrderId()));
    aomsrefundT.setAoms011(CommonUtil.checkNullOrNot(refundDetail.getSupplierNick()));
    aomsrefundT.setModified(CommonUtil.checkNullOrNot(refundDetail.getModified()));
    
    /*
     * modify by lizhi 20150725 没有店铺商品id，此处赋值为 主订单号，此字段改为联合主键
     */
    aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundDetail.getPurchaseOrderId()));
    aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refundDetail.getSubOrderId()));
    
    aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundDetail.getPurchaseOrderId()));
    
    // 公用栏位
    aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundDetail.getRefundCreateTime()));
    aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refundDetail.getRefundDesc()));
    aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refundDetail.getRefundReason()));

    aomsrefundT.setStoreType(TaobaoCommonTool.STORE_TYPE_FX);
    aomsrefundT.setStoreId(CommonUtil.checkNullOrNot(storeId));

    aomsrefundT.setJdpModified(CommonUtil.checkNullOrNot(jdpModified));

    Date now = new Date();
    aomsrefundT.setAomscrtdt(DateTimeTool.format(now));
    aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

    list.add(aomsrefundT);

    return list;
  }
}
