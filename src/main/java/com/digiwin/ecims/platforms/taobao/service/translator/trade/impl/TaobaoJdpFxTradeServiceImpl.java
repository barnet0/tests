package com.digiwin.ecims.platforms.taobao.service.translator.trade.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.domain.SubPurchaseOrder;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.FenxiaoOrdersGetResponse;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpFxTradeService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoJdpFxTradeServiceImpl implements TaobaoJdpFxTradeService {

  @Override
  public List<AomsordT> parseTaobaoResToAomsordT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException {
    return parsePurchaseOrdersToAomsordT(
        taobaoRes.getResponse(), taobaoRes.getComparisonCol(), storeId);
  }

  @Override
  public List<AomsordT> parseResponseToAomsordT(
      FenxiaoOrdersGetResponse fenxiaoOrdersGetResponse, String storeId) 
      throws ApiException {
    // 採購單轉換成ToAomsordT物件
    return parsePurchaseOrdersToAomsordT(
        fenxiaoOrdersGetResponse.getPurchaseOrders(), StringTool.EMPTY, storeId);

  }

  private List<AomsordT> parsePurchaseOrdersToAomsordT(
      String json, String jdpModified, String storeId) 
      throws ApiException {
    // JSON轉換成FenxiaoOrdersGetResponse物件
    FenxiaoOrdersGetResponse fenxiaoOrdersGetResponse = 
        TaobaoUtils.parseResponse(json, FenxiaoOrdersGetResponse.class);
    
    return parsePurchaseOrdersToAomsordT(fenxiaoOrdersGetResponse.getPurchaseOrders(), jdpModified, storeId);
  }


  private List<AomsordT> parsePurchaseOrdersToAomsordT(
      List<PurchaseOrder> purchaseOrders, String jdpModified, String storeId) {
    if (purchaseOrders == null) {
      return Collections.emptyList();
    }

    List<AomsordT> list = new ArrayList<AomsordT>();
    for (PurchaseOrder purchaseOrder : purchaseOrders) {
      for (SubPurchaseOrder subPurchaseOrder : purchaseOrder.getSubPurchaseOrders()) {
        AomsordT aomsordT = new AomsordT();
        // 單頭
        aomsordT.setId(CommonUtil.checkNullOrNot(purchaseOrder.getId()));

        // 没有skuId用numiid
        aomsordT.setAoms060(subPurchaseOrder.getSkuId() == null
            ? CommonUtil.checkNullOrNot(subPurchaseOrder.getItemId())
            : CommonUtil.checkNullOrNot(subPurchaseOrder.getSkuId()));

        aomsordT.setAoms002(CommonUtil.checkNullOrNot(purchaseOrder.getTradeType()));
        aomsordT.setAoms003(CommonUtil.checkNullOrNot(purchaseOrder.getStatus()));
        aomsordT.setAoms004(CommonUtil.checkNullOrNot(purchaseOrder.getSupplierFrom()));
        aomsordT.setAoms005(CommonUtil.checkNullOrNot(purchaseOrder.getTotalFee()));
        aomsordT.setAoms006(CommonUtil.checkNullOrNot(purchaseOrder.getCreated()));
        aomsordT.setModified(CommonUtil.checkNullOrNot(purchaseOrder.getModified()));
        aomsordT.setAoms008(CommonUtil.checkNullOrNot(purchaseOrder.getEndTime()));
        aomsordT.setAoms009(CommonUtil.checkNullOrNot(purchaseOrder.getMemo()));
        
        aomsordT.setAoms012(CommonUtil.checkNullOrNot(purchaseOrder.getSupplierMemo()));
        aomsordT.setAoms014(CommonUtil.checkNullOrNot(purchaseOrder.getSupplierFlag()));
        aomsordT.setAoms016(CommonUtil.checkNullOrNot(purchaseOrder.getSupplierUsername()));
        
        aomsordT.setAoms021(CommonUtil.checkNullOrNot(purchaseOrder.getAlipayNo()));
        aomsordT.setAoms022(CommonUtil.checkNullOrNot(purchaseOrder.getDistributorPayment()));
        aomsordT.setAoms023(CommonUtil.checkNullOrNot(purchaseOrder.getPayType()));
        aomsordT.setAoms024(CommonUtil.checkNullOrNot(purchaseOrder.getPayTime()));
        aomsordT.setAoms025(CommonUtil.checkNullOrNot(purchaseOrder.getDistributorUsername()));
        
        aomsordT.setAoms032(CommonUtil.checkNullOrNot(purchaseOrder.getTotalFee()));
        aomsordT.setAoms033(CommonUtil.checkNullOrNot(purchaseOrder.getShipping()));
        aomsordT.setAoms034(CommonUtil.checkNullOrNot(purchaseOrder.getLogisticsCompanyName())); // 淘寶分销有expressName
        aomsordT.setAoms035(CommonUtil.checkNullOrNot(purchaseOrder.getPostFee()));
        aomsordT.setAoms036(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getName()));
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getState()));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getCity()));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getDistrict()));
        
        aomsordT.setAoms040(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getAddress()));
        aomsordT.setAoms041(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getZip()));
        aomsordT
            .setAoms042(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getMobilePhone()));
        aomsordT.setAoms043(CommonUtil.checkNullOrNot(purchaseOrder.getReceiver().getPhone()));
        aomsordT.setAoms044(CommonUtil.checkNullOrNot(purchaseOrder.getConsignTime()));

        aomsordT.setStoreType(TaobaoCommonTool.STORE_TYPE_FX);
        aomsordT.setStoreId(CommonUtil.checkNullOrNot(storeId));

        // 單身
        aomsordT.setAoms058(CommonUtil.checkNullOrNot(subPurchaseOrder.getId()));
        aomsordT.setAoms059(CommonUtil.checkNullOrNot(subPurchaseOrder.getItemId()));
        
        aomsordT.setAoms061(CommonUtil.checkNullOrNot(subPurchaseOrder.getSkuProperties()));
        aomsordT.setAoms062(CommonUtil.checkNullOrNot(subPurchaseOrder.getNum()));
        aomsordT.setAoms063(CommonUtil.checkNullOrNot(subPurchaseOrder.getTitle()));
        aomsordT.setAoms064(CommonUtil.checkNullOrNot(subPurchaseOrder.getPrice()));
        aomsordT.setAoms066(CommonUtil.checkNullOrNot(subPurchaseOrder.getScItemId()));
        aomsordT.setAoms067(CommonUtil.checkNullOrNot(subPurchaseOrder.getSkuOuterId()));
        aomsordT.setAoms068(CommonUtil.checkNullOrNot(subPurchaseOrder.getTotalFee()));
        aomsordT.setAoms069(CommonUtil.checkNullOrNot(subPurchaseOrder.getTcDiscountFee()));
        
        aomsordT.setAoms070(CommonUtil.checkNullOrNot(subPurchaseOrder.getTcAdjustFee()));
        aomsordT.setAoms071(CommonUtil.checkNullOrNot(subPurchaseOrder.getDistributorPayment()));
        aomsordT.setAoms072(CommonUtil.checkNullOrNot(subPurchaseOrder.getStatus()));
        // 分銷表头
        aomsordT.setAoms079(CommonUtil.checkNullOrNot(purchaseOrder.getTcOrderId()));
        
        aomsordT.setAoms080(CommonUtil.checkNullOrNot(purchaseOrder.getLogisticsId()));
        // 分銷表身
        aomsordT.setAoms081(CommonUtil.checkNullOrNot(subPurchaseOrder.getRefundFee()));
        aomsordT.setAoms082(CommonUtil.checkNullOrNot(subPurchaseOrder.getFenxiaoId()));
        aomsordT.setAoms083(CommonUtil.checkNullOrNot(subPurchaseOrder.getOrder200Status()));
        aomsordT.setAoms084(CommonUtil.checkNullOrNot(subPurchaseOrder.getPromotionType()));
        aomsordT.setAoms085(CommonUtil.checkNullOrNot(subPurchaseOrder.getDiscountFee()));
        aomsordT.setAoms086(CommonUtil.checkNullOrNot(subPurchaseOrder.getAuctionSkuId()));
        aomsordT.setAoms087(CommonUtil.checkNullOrNot(subPurchaseOrder.getAuctionId()));
        aomsordT.setJdpModified(CommonUtil.checkNullOrNot(jdpModified));

        aomsordT.setAomsundefined002(CommonUtil.checkNullOrNot(subPurchaseOrder.getBuyerPayment()));

        Date now = DateTimeTool.getTodayDate();
        aomsordT.setAomscrtdt(DateTimeTool.format(now));
        aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

        list.add(aomsordT);
      }
    }
    return list;
  }
}
