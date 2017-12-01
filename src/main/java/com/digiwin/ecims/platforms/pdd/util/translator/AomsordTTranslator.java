package com.digiwin.ecims.platforms.pdd.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.pdd.bean.domain.order.OrderItem;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;

public class AomsordTTranslator {

  private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);

  private Object object;
  
  private StandardAreaService standardAreaService =
      MySpringContext.getContext().getBean(StandardAreaService.class);
  
//  private PddApiService pddApiService =
//      MySpringContext.getContext().getBean(PddApiService.class);
//  
//  private AomsShopService aomsShopService = 
//      MySpringContext.getContext().getBean(AomsShopService.class);
//  
//  private LoginfoOperateService loginfoOperateService = 
//      MySpringContext.getContext().getBean(LoginfoOperateService.class);
//  
  public AomsordTTranslator(Object orderEntity) {
    super();
    this.object = orderEntity;
  }
  
  public List<AomsordT> doTranslate(String storeId) {
    if (object instanceof OrderGetResponse) {
      return parsePddOrderToAomsordT(storeId);
    } else {
      return new ArrayList<AomsordT>();
    }
  }
  
  private List<AomsordT> parsePddOrderToAomsordT(String storeId) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    OrderGetResponse order = (OrderGetResponse)this.object;
    for (OrderItem orderItem : order.getItemList()) {
      
     /*  mark on 20161113
      * 
       * 由于拼多多返回的订单商品中，没有平台skuid这个字段，但是此字段是电商接口的主键字段之一，
       * 因此，需要调用API获取对应的平台skuid。
       
      Long aoms060 = 0L; 
      ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);
      try {
        GetGoodsResponse goodsResposne = 
            pddApiService.pddGetGoods(
                esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
                null, null, orderItem.getGoodsName(), 
                PddCommonTool.MIN_PAGE_NO, PddCommonTool.MIN_PAGE_SIZE);
        loginfoOperateService.newTransaction4SaveSource(
            "N/A", "N/A", PddCommonTool.STORE_TYPE, 
            "[goodsName]|mGetGoods 获得商品档案", goodsResposne, AomsordT.BIZ_NAME, storeId, null);
        
        if (goodsResposne.getResult() == PddCommonTool.RESPONSE_SUCCESS_CODE
            && goodsResposne.getGoodList().size() == 1) {
            Item goodItem = goodsResposne.getGoodList().get(0);
            for (ItemSku goodItemSku : goodItem.getSkuList()) {
              // 料号相同才取skuid
              if (goodItemSku.getOuterID().equals(orderItem.getSkuCode())) {
                aoms060 = goodItemSku.getSkuID();
              }
            }
        } else {
          // 当没有做商品映射时，无法取到skuid。现决定遇到这种情况时跳过此订单，通过补单来下载
          logger.info("拼多多订单{}无法取得其商品的skuid，商品料号为：{}，商品名称为：{}。处理办法：跳过由补单排程下载。", 
              order.getOrderSN(), orderItem.getSkuCode(), orderItem.getGoodsName());
          break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      // add by mowj 20161008.如果aomsord060依然是0（例如遇到API无法访问），则表示没有取到正确的skuid，直接返回。
      if (aoms060 == 0L) {
        return aomsordTs;
      }*/
      
      AomsordT aomsordT = new AomsordT();
      
      aomsordT.setId(CommonUtil.checkNullOrNot(order.getOrderSN()));
      aomsordT.setAoms003("WAIT_SELLER_SEND_GOODS");
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(order.getCreatedTime()));
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(order.getCustomerRemark()));
      aomsordT.setAoms012(CommonUtil.checkNullOrNot(order.getSellMemo()));
      
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(order.getSellerDiscount()));
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(order.getOrderAmount() + order.getPlatformDiscount()));
      aomsordT.setAoms023(CommonUtil.checkNullOrNot(order.getPayType()));
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(order.getPayTime()));
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(order.getBuyerName()));
      aomsordT.setAoms026(CommonUtil.checkNullOrNot(order.getEmail()));
      
      aomsordT.setAoms034(CommonUtil.checkNullOrNot(order.getLogisticsName()));
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(order.getPostage()));
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(order.getBuyerName()));
      
      AreaResponse standardArea =
          standardAreaService.getStandardAreaNameByKeyWord(order.getProvince(),
              order.getCity(), order.getTown());
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
      } else {
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(order.getProvince()));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(order.getCity()));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(order.getTown()));
      }
      
      aomsordT.setAoms040(CommonUtil.checkNullOrNot(order.getAddress()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(order.getZip()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(order.getPhone()));
      aomsordT.setAoms044(CommonUtil.checkNullOrNot(order.getShippingTime()));
      aomsordT.setAoms053(CommonUtil.checkNullOrNot(order.getInvoiceTitle()));
      
      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(PddCommonTool.STORE_TYPE);
      
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderItem.getGoodsID()));
      
      // mark on 20161113 
//      aomsordT.setAoms060(CommonUtil.checkNullOrNot(aoms060));
      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderItem.getSkuCode()));
      
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderItem.getGoodsSpec()));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderItem.getGoodsCount()));
      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderItem.getGoodsName()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderItem.getGoodsPrice()));
      // mark on 20161113
//      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderItem.getSkuCode()));
      
      Double aoms071= orderItem.getGoodsCount() * orderItem.getGoodsPrice();
      aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));
      
      aomsordT.setAoms078(CommonUtil.checkNullOrNot(order.getPlatformDiscount()));
      
      Date now = new Date();
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsordTs.add(aomsordT);
    }
    
   return aomsordTs; 
  }
}
