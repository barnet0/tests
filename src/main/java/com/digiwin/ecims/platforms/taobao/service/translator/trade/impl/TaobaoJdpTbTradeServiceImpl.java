package com.digiwin.ecims.platforms.taobao.service.translator.trade.impl;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpTbTradeService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.TradeFullinfoGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TaobaoJdpTbTradeServiceImpl implements TaobaoJdpTbTradeService {

  @Override
  public List<AomsordT> parseTaobaoResToAomsordT(TaobaoRes taobaoRes, String storeId)
      throws ApiException, IOException {
    // Trade轉換成ToAomsordT物件
    return parseTradeToAomsordT(taobaoRes.getResponse(), taobaoRes.getComparisonCol(), storeId);

  }

  @Override
  public List<AomsordT> parseTradeFullinfoGetResponseToAomsordT(
      TradeFullinfoGetResponse fullinfoGetResponse, String storeId) 
          throws ApiException, IOException {
    return parseTradeToAomsordT(fullinfoGetResponse.getTrade(), StringTool.EMPTY, storeId);
  }

  /**
   * 将单笔交易的JSON字符串转换为中台的订单
   * 
   * @param json 单笔交易的JSON
   * @param jdpModified 可选。聚石塔上的jdp_modified字段的值
   * @return 中台订单List
   * @throws ApiException
   * @throws IOException
   */
  private List<AomsordT> parseTradeToAomsordT(String json, String jdpModified, String storeId) 
      throws ApiException, IOException {
    // 将字符串转换为对象
    TradeFullinfoGetResponse tradeFullinfoGetResponse =
        TaobaoUtils.parseResponse(json, TradeFullinfoGetResponse.class);
    Trade trade = tradeFullinfoGetResponse.getTrade();

    return parseTradeToAomsordT(trade, jdpModified, storeId);
  }

  /**
   * 将单笔订单Trade对象转换为中台的订单
   * 
   * @param trade 单笔交易的Trade对象
   * @param jdpModified 可选。聚石塔上的jdp_modified字段的值
   * @return 中台订单List
   * @throws ApiException
   * @throws IOException
   */
  private List<AomsordT> parseTradeToAomsordT(Trade trade, String jdpModified, String storeId) 
      throws ApiException, IOException {
    // add on 20161117 当使用详情API出现“订单不存在”时的处理
    if (trade == null) {
      return Collections.emptyList();
    }

    // add by mowj 20151022
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    Map<String, AomsordT> aomsordTMap = new HashMap<String, AomsordT>();

    for (Order order : trade.getOrders()) {
      // addd by mowj 20151022 start
      String aomsordTKey =
          trade.getTid() + "#" + TaobaoCommonTool.STORE_TYPE + "#" + (order.getSkuId() == null
              ? CommonUtil.checkNullOrNot(order.getNumIid()) : order.getSkuId());

      long aoms062 = order.getNum();
      String aoms064 = order.getPrice();
      String aoms068 = order.getTotalFee();
      String aoms069 = order.getDiscountFee();
      String aoms070 = order.getAdjustFee();
      String aoms071 = order.getPayment();
      String aoms090 = order.getDivideOrderFee();
      String aoms091 = order.getPartMjzDiscount();

      AomsordT aomsordT = null;
      

      
      if (null == aomsordTMap.get(aomsordTKey)) {
        aomsordT = new AomsordT();
        aomsordT.setAoms062(CommonUtil.checkNullOrNot(aoms062));
        aomsordT.setAoms064(CommonUtil.checkNullOrNot(aoms064));
        aomsordT.setAoms068(CommonUtil.checkNullOrNot(aoms068));
        aomsordT.setAoms069(CommonUtil.checkNullOrNot(aoms069));
        aomsordT.setAoms070(CommonUtil.checkNullOrNot(aoms070));
        aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));
        aomsordT.setAoms090(CommonUtil.checkNullOrNot(aoms090));
        aomsordT.setAoms091(CommonUtil.checkNullOrNot(aoms091));
        
        //菜鸟发货 or 水星发货
        aomsordT.setAoms096(CommonUtil.checkNullOrNot(order.getStoreCode()));
        
      } else {
        aomsordT = aomsordTMap.get(aomsordTKey);

        long oldAoms062 = Long.parseLong(aomsordT.getAoms062());
        long newAoms062 = aoms062 + oldAoms062;
        aomsordT.setAoms062(CommonUtil.checkNullOrNot(newAoms062));

        double oldAoms064 = Double.parseDouble(aomsordT.getAoms064());
        double newAoms064 = oldAoms064 * oldAoms062 + Double.parseDouble(aoms064) * aoms062;
        newAoms064 /= newAoms062;
        aomsordT.setAoms064(CommonUtil.checkNullOrNot(newAoms064));

        double newAoms068 = Double.parseDouble(aomsordT.getAoms068()) + Double.parseDouble(aoms068);
        aomsordT.setAoms068(CommonUtil.checkNullOrNot(newAoms068));

        double newAoms069 = Double.parseDouble(aomsordT.getAoms069()) + Double.parseDouble(aoms069);
        aomsordT.setAoms069(CommonUtil.checkNullOrNot(newAoms069));

        double newAoms070 = Double.parseDouble(aomsordT.getAoms070()) + Double.parseDouble(aoms070);
        aomsordT.setAoms070(CommonUtil.checkNullOrNot(newAoms070));

        double newAoms071 = Double.parseDouble(aomsordT.getAoms071()) + Double.parseDouble(aoms071);
        aomsordT.setAoms071(CommonUtil.checkNullOrNot(newAoms071));

        //菜鸟发货 or 水星发货
        aomsordT.setAoms096(CommonUtil.checkNullOrNot(order.getStoreCode()));
        
        if (aoms090 != null) {
          double newAoms090 = 0;
          // 如果新的090没有值，则什么都不做
          if (aoms090.trim().length() == 0) {
            ;
          } else {
            // 否则需要根据原来是否有值添加新的090
            if (aomsordT.getAoms090().trim().length() == 0) {
              aomsordT.setAoms090(CommonUtil.checkNullOrNot(aoms090));
            } else {
              newAoms090 = Double.parseDouble(aomsordT.getAoms090()) + Double.parseDouble(aoms090);
              aomsordT.setAoms090(CommonUtil.checkNullOrNot(newAoms090));
            }
          }

        }

        if (aoms091 != null) {
          double newAoms091 = 0;
          // 如果新的091没有值，则什么都不做
          if (aoms091.trim().length() == 0) {
            ;
          } else {
            // 否则需要根据原来是否有值添加新的091
            if (aomsordT.getAoms091().trim().length() == 0) {
              aomsordT.setAoms091(CommonUtil.checkNullOrNot(aoms091));
            } else {
              newAoms091 = Double.parseDouble(aomsordT.getAoms091()) + Double.parseDouble(aoms091);
              aomsordT.setAoms091(CommonUtil.checkNullOrNot(newAoms091));
            }
          }
        }

        continue;
      }
      // addd by mowj 20151022 end

      // 單頭
      aomsordT.setId(CommonUtil.checkNullOrNot(trade.getTid()));
      aomsordT.setStoreType(TaobaoCommonTool.STORE_TYPE);// 淘寶平台-0

      // 沒有skuId用numiid 2015 07 10 modi by sen.shen
      aomsordT.setAoms060(order.getSkuId() == null ? CommonUtil.checkNullOrNot(order.getNumIid())
          : order.getSkuId());

      aomsordT.setAoms002(CommonUtil.checkNullOrNot(trade.getType()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(trade.getStatus()));
      aomsordT.setAoms004(CommonUtil.checkNullOrNot(trade.getTradeFrom()));
      aomsordT.setAoms005(CommonUtil.checkNullOrNot(trade.getPrice()));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(trade.getCreated()));
      aomsordT.setModified(CommonUtil.checkNullOrNot(trade.getModified()));
      aomsordT.setAoms008(CommonUtil.checkNullOrNot(trade.getEndTime()));
      aomsordT.setAoms009(CommonUtil.checkNullOrNot(trade.getTradeMemo()));
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(trade.getBuyerMessage()));
      aomsordT.setAoms011(CommonUtil.checkNullOrNot(trade.getBuyerMemo()));
      aomsordT.setAoms012(CommonUtil.checkNullOrNot(trade.getSellerMemo()));
      aomsordT.setAoms013(CommonUtil.checkNullOrNot(trade.getBuyerFlag()));
      aomsordT.setAoms014(CommonUtil.checkNullOrNot(trade.getSellerFlag()));
      aomsordT.setAoms015(CommonUtil.checkNullOrNot(trade.getTitle()));
      aomsordT.setAoms016(CommonUtil.checkNullOrNot(trade.getSellerNick()));
      aomsordT.setAoms017(CommonUtil.checkNullOrNot(trade.getNum()));
      aomsordT.setAoms018(CommonUtil.checkNullOrNot(trade.getPicPath()));
      aomsordT.setAoms019(CommonUtil.checkNullOrNot(trade.getAdjustFee()));
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(trade.getDiscountFee()));
      aomsordT.setAoms021(CommonUtil.checkNullOrNot(trade.getAlipayNo()));
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(trade.getPayment()));
      aomsordT.setAoms023(trade.getAlipayNo() != null ? "ALIPAY" : "Other"); // 淘寶沒有paymentName
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(trade.getPayTime()));
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(trade.getBuyerNick()));
      aomsordT.setAoms026(CommonUtil.checkNullOrNot(trade.getBuyerEmail()));
      aomsordT.setAoms027(CommonUtil.checkNullOrNot(trade.getBuyerRate()));
      aomsordT.setAoms028(CommonUtil.checkNullOrNot(trade.getBuyerAlipayNo()));
      aomsordT.setAoms029(CommonUtil.checkNullOrNot(trade.getBuyerObtainPointFee()));
      aomsordT.setAoms030(CommonUtil.checkNullOrNot(trade.getPointFee()));
      aomsordT.setAoms031(CommonUtil.checkNullOrNot(trade.getRealPointFee()));
      aomsordT.setAoms032(CommonUtil.checkNullOrNot(trade.getTotalFee()));
      aomsordT.setAoms033(CommonUtil.checkNullOrNot(trade.getShippingType()));
      aomsordT.setAoms034(""); // 淘寶沒有expressName
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(trade.getPostFee()));
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(trade.getReceiverName()));
      aomsordT.setAoms037(CommonUtil.checkNullOrNot(trade.getReceiverState()));
      aomsordT.setAoms038(CommonUtil.checkNullOrNot(trade.getReceiverCity()));
      aomsordT.setAoms039(CommonUtil.checkNullOrNot(trade.getReceiverDistrict()));
      aomsordT.setAoms040(CommonUtil.checkNullOrNot(trade.getReceiverAddress()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(trade.getReceiverZip()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(trade.getReceiverMobile()));
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(trade.getReceiverPhone()));
      aomsordT.setAoms044(CommonUtil.checkNullOrNot(trade.getConsignTime()));
      aomsordT.setAoms045(CommonUtil.checkNullOrNot(trade.getCommissionFee()));
      aomsordT.setAoms046(CommonUtil.checkNullOrNot(trade.getSellerAlipayNo()));
      aomsordT.setAoms047(CommonUtil.checkNullOrNot(trade.getSellerMobile()));
      aomsordT.setAoms048(CommonUtil.checkNullOrNot(trade.getSellerPhone()));
      aomsordT.setAoms049(CommonUtil.checkNullOrNot(trade.getSellerName()));
      aomsordT.setAoms050(CommonUtil.checkNullOrNot(trade.getSellerEmail()));
      aomsordT.setAoms051(CommonUtil.checkNullOrNot(trade.getSellerRate()));
      aomsordT.setAoms052(CommonUtil.checkNullOrNot(trade.getAvailableConfirmFee()));
      aomsordT.setAoms053(CommonUtil.checkNullOrNot(trade.getInvoiceName())); // 不確定對不對
      aomsordT.setAoms054(CommonUtil.checkNullOrNot(trade.getInvoiceName()));
      aomsordT.setAoms055(CommonUtil.checkNullOrNot(trade.getInvoiceType()));

      aomsordT.setStoreId(CommonUtil.checkNullOrNot(storeId));

      // 單身
      aomsordT.setAoms058(CommonUtil.checkNullOrNot(order.getOid()));
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(order.getNumIid()));

      aomsordT.setAoms061(CommonUtil.checkNullOrNot(order.getSkuPropertiesName()));
      // aomsordT.setAoms062(CommonUtil.checkNullOrNot(order.getNum()));
      aomsordT.setAoms063(CommonUtil.checkNullOrNot(order.getTitle()));
      // aomsordT.setAoms064(CommonUtil.checkNullOrNot(order.getPrice()));
      aomsordT.setAoms065(CommonUtil.checkNullOrNot(order.getPicPath()));
      aomsordT.setAoms066(CommonUtil.checkNullOrNot(order.getOuterIid()));
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(order.getOuterSkuId()));
      // aomsordT.setAoms068(CommonUtil.checkNullOrNot(order.getTotalFee()));

      // aomsordT.setAoms069(CommonUtil.checkNullOrNot(order.getDiscountFee()));
      // aomsordT.setAoms070(CommonUtil.checkNullOrNot(order.getAdjustFee()));
      // aomsordT.setAoms071(CommonUtil.checkNullOrNot(order.getPayment()));
      aomsordT.setAoms072(CommonUtil.checkNullOrNot(order.getStatus()));
      aomsordT.setAoms073(CommonUtil.checkNullOrNot(order.getRefundStatus()));
      aomsordT.setAoms074(CommonUtil.checkNullOrNot(order.getRefundId()));
      aomsordT.setAoms075(CommonUtil.checkNullOrNot(order.getShippingType()));

      
      //菜鸟发货 or 水星发货
      aomsordT.setAoms095(CommonUtil.checkNullOrNot(trade.getIsShShip()));

      
      // 備用欄位
      aomsordT.setAoms088(CommonUtil.checkNullOrNot(trade.getReceiverTown()));
      aomsordT.setAoms089(CommonUtil.checkNullOrNot(trade.getHasYfx()));
      // aomsordT.setAoms090(CommonUtil.checkNullOrNot(order.getDivideOrderFee()));
      // aomsordT.setAoms091(CommonUtil.checkNullOrNot(order.getPartMjzDiscount()));
      aomsordT.setJdpModified(jdpModified);

      // add on 20161102 添加分阶段付款订单的状态和已付金额
      aomsordT.setAomsundefined009(CommonUtil.checkNullOrNot(trade.getStepTradeStatus()));
      aomsordT.setAomsundefined010(CommonUtil.checkNullOrNot(trade.getStepPaidFee()));
            
      Date now = DateTimeTool.getTodayDate();
      aomsordT.setAomscrtdt(DateTimeTool.format(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      // list.add(aomsordT);
      aomsordTMap.put(aomsordTKey, aomsordT);
    }

    // return list;
    if (null != aomsordTMap) {
      for (Map.Entry<String, AomsordT> aomsordT : aomsordTMap.entrySet()) {
        aomsordTs.add(aomsordT.getValue());
      }
    }
    return aomsordTs;
  }

//  private TradeRate getTradeRateByOid(List<TradeRate> tradeRates, long oid) {
//    TradeRate result = null;
//    for (TradeRate tradeRate : tradeRates) {
//      if (oid == tradeRate.getOid()) {
//        result = tradeRate;
//        break;
//      }
//    }
//    return result == null ? new TradeRate() : result;
//  }
}
