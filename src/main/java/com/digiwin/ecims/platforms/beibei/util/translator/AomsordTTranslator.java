package com.digiwin.ecims.platforms.beibei.util.translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.OrdersGetDto;
import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderDto;
import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderItemDto;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;

public class AomsordTTranslator {

  private Object object;
  
  private StandardAreaService standardAreaService =
      MySpringContext.getContext().getBean(StandardAreaService.class);

  public AomsordTTranslator() {
  }

  public AomsordTTranslator(Object object) {
    super();
    this.object = object;
  }
  
  public List<AomsordT> doTranslate(String storeId) {
    if (object instanceof OuterTradeOrderDetailGetResponse) {
      return parseBeibeiOrderToAomsordTTest(
          ((OuterTradeOrderDetailGetResponse)object).getData(), storeId);
    } else if (object instanceof OrdersGetDto) {
      return parseBeibeiOrderToAomsordTTest((OrdersGetDto)object, storeId);
    } else {
      return Collections.emptyList();
    }
  }

//  private List<AomsordT> parseBeibeiOrderToAomsordT(String storeId) {
//    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
//    OrderDetailGetDto orderDetailGetDto = ((OuterTradeOrderDetailGetResponse)this.object).getData();
//    
//    Double platformDiscount = 0.0;
//    Double sellerDiscount = 0.0;
//    for (OrderDetailGetItemDto orderDetailGetItemDto : orderDetailGetDto.getItem()) {
//      AomsordT aomsordT = new AomsordT();
//      
//      aomsordT.setId(CommonUtil.checkNullOrNot(orderDetailGetDto.getOid()));
//      aomsordT.setAoms002(CommonUtil.checkNullOrNot(orderDetailGetDto.getOrderType()));
//      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderDetailGetDto.getStatus()));
//      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderDetailGetDto.getCreateTime()));
//      aomsordT.setModified(CommonUtil.checkNullOrNot(orderDetailGetDto.getModifiedTime()));
//      aomsordT.setAoms008(CommonUtil.checkNullOrNot(orderDetailGetDto.getEndTime()));
//      
//      aomsordT.setAoms011(CommonUtil.checkNullOrNot(orderDetailGetDto.getRemark()));
//      aomsordT.setAoms012(CommonUtil.checkNullOrNot(orderDetailGetDto.getSellerRemark()));
//      aomsordT.setAoms017(CommonUtil.checkNullOrNot(orderDetailGetDto.getItemNum()));
//      
//      aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderDetailGetDto.getTotalFee()));
//      aomsordT.setAoms024(CommonUtil.checkNullOrNot(orderDetailGetDto.getPayTime()));
//      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderDetailGetDto.getUser()));
//      
//      aomsordT.setAoms034(CommonUtil.checkNullOrNot(orderDetailGetDto.getCompany()));
//      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderDetailGetDto.getShippingFee()));
//      aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderDetailGetDto.getReceiverName()));
//      
//      AreaResponse standardArea =
//          standardAreaService.getStandardAreaNameByKeyWord(
//              orderDetailGetDto.getProvince(),
//              orderDetailGetDto.getCity(), 
//              orderDetailGetDto.getCounty());
//      if (standardArea != null) {
//        String standardProvince = standardArea.getProvince();
//        String standardCity = standardArea.getCity();
//        String standardDistrict = standardArea.getDistrict();
//
//        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
//        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
//        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
//      } else {
//        aomsordT.setAoms037(CommonUtil.checkNullOrNot(orderDetailGetDto.getProvince()));
//        aomsordT.setAoms038(CommonUtil.checkNullOrNot(orderDetailGetDto.getCity()));
//        aomsordT.setAoms039(CommonUtil.checkNullOrNot(orderDetailGetDto.getCounty()));
//      }
//      
//      aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderDetailGetDto.getAddress()));
//      aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderDetailGetDto.getReceiverPhone()));
//      
//      aomsordT.setAoms053(CommonUtil.checkNullOrNot(orderDetailGetDto.getInvoiceName()));
//      aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getIid()));
//      
//      aomsordT.setStoreId(storeId);
//      aomsordT.setStoreType(BeibeiCommonTool.STORE_TYPE);
//      
//      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getSkuId()));
//      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getSkuProperties()));
//      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getNum()));
//      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getTitle()));
//      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getPrice()));
//      aomsordT.setAoms066(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getGoodsNum()));
//      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getOuterId()));
//      
//      Double itemSubtotal = orderDetailGetItemDto.getPrice() * orderDetailGetItemDto.getNum();
//      aomsordT.setAoms071(CommonUtil.checkNullOrNot(itemSubtotal));
//      aomsordT.setAoms073(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getRefundStatus()));
//      
//      aomsordT.setAoms090(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getTotalFee()));
//      
//      Double itemPlatformDiscount = orderDetailGetItemDto.getTotalFee() - orderDetailGetItemDto.getSubtotal();
//      Double itemSellerDiscount = itemSubtotal - orderDetailGetItemDto.getTotalFee();
//      sellerDiscount += itemSellerDiscount;
//      platformDiscount += itemPlatformDiscount;
//      
//      aomsordT.setAoms091(CommonUtil.checkNullOrNot(itemSellerDiscount));
//      aomsordT.setAoms094(CommonUtil.checkNullOrNot(itemPlatformDiscount));
//      
//      Date now = new Date();
//      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
//      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
//      
//      aomsordTs.add(aomsordT);
//    }
//    
//    // 最后再加上累计的单头平台优惠aomsord078
//    for (AomsordT at : aomsordTs) {
//      at.setAoms020(CommonUtil.checkNullOrNot(sellerDiscount));
//      at.setAoms078(CommonUtil.checkNullOrNot(platformDiscount));
//    }
//    
//    return aomsordTs;
//  }
//  
//  private List<AomsordT> parseBeibeiOrderToAomsordT2(String storeId) {
//    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
//    OrdersGetDto orderDetailGetDto = ((OrdersGetDto)this.object);
//    
//    Double platformDiscount = 0.0;
//    Double sellerDiscount = 0.0;
//    for (OrdersGetItemDto orderDetailGetItemDto : orderDetailGetDto.getItem()) {
//      AomsordT aomsordT = new AomsordT();
//      
//      aomsordT.setId(CommonUtil.checkNullOrNot(orderDetailGetDto.getOid()));
////      aomsordT.setAoms002(CommonUtil.checkNullOrNot(orderDetailGetDto.getOrderType()));
//      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderDetailGetDto.getStatus()));
//      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderDetailGetDto.getCreateTime()));
//      aomsordT.setModified(CommonUtil.checkNullOrNot(orderDetailGetDto.getModifiedTime()));
//      aomsordT.setAoms008(CommonUtil.checkNullOrNot(orderDetailGetDto.getEndTime()));
//      
//      aomsordT.setAoms011(CommonUtil.checkNullOrNot(orderDetailGetDto.getRemark()));
//      aomsordT.setAoms012(CommonUtil.checkNullOrNot(orderDetailGetDto.getSellerRemark()));
//      aomsordT.setAoms017(CommonUtil.checkNullOrNot(orderDetailGetDto.getItemNum()));
//      
//      aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderDetailGetDto.getTotalFee()));
//      aomsordT.setAoms024(CommonUtil.checkNullOrNot(orderDetailGetDto.getPayTime()));
////      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderDetailGetDto.getUser()));
//      
//      aomsordT.setAoms034(CommonUtil.checkNullOrNot(orderDetailGetDto.getCompany()));
//      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderDetailGetDto.getShippingFee()));
//      aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderDetailGetDto.getReceiverName()));
//      
//      AreaResponse standardArea =
//          standardAreaService.getStandardAreaNameByKeyWord(
//              orderDetailGetDto.getProvince(),
//              orderDetailGetDto.getCity(), 
//              orderDetailGetDto.getCounty());
//      if (standardArea != null) {
//        String standardProvince = standardArea.getProvince();
//        String standardCity = standardArea.getCity();
//        String standardDistrict = standardArea.getDistrict();
//
//        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
//        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
//        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
//      } else {
//        aomsordT.setAoms037(CommonUtil.checkNullOrNot(orderDetailGetDto.getProvince()));
//        aomsordT.setAoms038(CommonUtil.checkNullOrNot(orderDetailGetDto.getCity()));
//        aomsordT.setAoms039(CommonUtil.checkNullOrNot(orderDetailGetDto.getCounty()));
//      }
//      
//      aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderDetailGetDto.getAddress()));
//      aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderDetailGetDto.getReceiverPhone()));
//      
//      aomsordT.setAoms053(CommonUtil.checkNullOrNot(orderDetailGetDto.getInvoiceName()));
//      aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getIid()));
//      
//      aomsordT.setStoreId(storeId);
//      aomsordT.setStoreType(BeibeiCommonTool.STORE_TYPE);
//      
//      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getSkuId()));
//      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getSkuProperties()));
//      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getNum()));
//      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getTitle()));
//      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getPrice()));
//      aomsordT.setAoms066(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getGoodsNum()));
//      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getOuterId()));
//      
//      Double itemSubtotal = orderDetailGetItemDto.getPrice() * orderDetailGetItemDto.getNum();
//      aomsordT.setAoms071(CommonUtil.checkNullOrNot(itemSubtotal));
//      aomsordT.setAoms073(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getRefundStatus()));
//      
//      aomsordT.setAoms090(CommonUtil.checkNullOrNot(orderDetailGetItemDto.getTotalFee()));
//      
//      Double itemPlatformDiscount = orderDetailGetItemDto.getTotalFee() - orderDetailGetItemDto.getSubtotal();
//      Double itemSellerDiscount = itemSubtotal - orderDetailGetItemDto.getTotalFee();
//      sellerDiscount += itemSellerDiscount;
//      platformDiscount += itemPlatformDiscount;
//      
//      aomsordT.setAoms091(CommonUtil.checkNullOrNot(itemSellerDiscount));
//      aomsordT.setAoms094(CommonUtil.checkNullOrNot(itemPlatformDiscount));
//      
//      Date now = new Date();
//      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
//      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
//      
//      aomsordTs.add(aomsordT);
//    }
//    
//    // 最后再加上累计的单头平台优惠aomsord078
//    for (AomsordT at : aomsordTs) {
//      at.setAoms020(CommonUtil.checkNullOrNot(sellerDiscount));
//      at.setAoms078(CommonUtil.checkNullOrNot(platformDiscount));
//    }
//    
//    return aomsordTs;
//  }
// 
  private List<AomsordT> parseBeibeiOrderToAomsordTTest(AbstractOrderDto orderDetailGetDto,String storeId) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    
    Double platformDiscount = 0.0;
    Double sellerDiscount = 0.0;
    for (AbstractOrderItemDto orderItemDto : orderDetailGetDto.getItems()) {
      AomsordT aomsordT = new AomsordT();
      
      aomsordT.setId(CommonUtil.checkNullOrNot(orderDetailGetDto.getOid()));
//      aomsordT.setAoms002(CommonUtil.checkNullOrNot(orderDetailGetDto.getOrderType()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderDetailGetDto.getStatus()));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderDetailGetDto.getCreateTime()));
      aomsordT.setModified(CommonUtil.checkNullOrNot(orderDetailGetDto.getModifiedTime()));
      aomsordT.setAoms008(CommonUtil.checkNullOrNot(orderDetailGetDto.getEndTime()));
      
      aomsordT.setAoms011(CommonUtil.checkNullOrNot(orderDetailGetDto.getRemark()));
      aomsordT.setAoms012(CommonUtil.checkNullOrNot(orderDetailGetDto.getSellerRemark()));
      aomsordT.setAoms017(CommonUtil.checkNullOrNot(orderDetailGetDto.getItemNum()));
      
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderDetailGetDto.getTotalFee()));
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(orderDetailGetDto.getPayTime()));
//      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderDetailGetDto.getUser()));
      
      aomsordT.setAoms034(CommonUtil.checkNullOrNot(orderDetailGetDto.getCompany()));
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderDetailGetDto.getShippingFee()));
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderDetailGetDto.getReceiverName()));
      
      AreaResponse standardArea =
          standardAreaService.getStandardAreaNameByKeyWord(
              orderDetailGetDto.getProvince(),
              orderDetailGetDto.getCity(), 
              orderDetailGetDto.getCounty());
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
      } else {
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(orderDetailGetDto.getProvince()));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(orderDetailGetDto.getCity()));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(orderDetailGetDto.getCounty()));
      }
      
      aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderDetailGetDto.getAddress()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderDetailGetDto.getReceiverPhone()));
      
      aomsordT.setAoms053(CommonUtil.checkNullOrNot(orderDetailGetDto.getInvoiceName()));
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderItemDto.getIid()));
      
      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(BeibeiCommonTool.STORE_TYPE);
      
      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderItemDto.getSkuId()));
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderItemDto.getSkuProperties()));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderItemDto.getNum()));
      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderItemDto.getTitle()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderItemDto.getPrice()));
      aomsordT.setAoms066(CommonUtil.checkNullOrNot(orderItemDto.getGoodsNum()));
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderItemDto.getOuterId()));
      
      Double itemSubtotal = orderItemDto.getPrice() * orderItemDto.getNum();
      aomsordT.setAoms071(CommonUtil.checkNullOrNot(itemSubtotal));
      aomsordT.setAoms073(CommonUtil.checkNullOrNot(orderItemDto.getRefundStatus()));
      
      aomsordT.setAoms090(CommonUtil.checkNullOrNot(orderItemDto.getTotalFee()));
      
      Double itemPlatformDiscount = orderItemDto.getTotalFee() - orderItemDto.getSubtotal();
      Double itemSellerDiscount = itemSubtotal - orderItemDto.getTotalFee();
      sellerDiscount += itemSellerDiscount;
      platformDiscount += itemPlatformDiscount;
      
      aomsordT.setAoms091(CommonUtil.checkNullOrNot(itemSellerDiscount));
      aomsordT.setAoms094(CommonUtil.checkNullOrNot(itemPlatformDiscount));
      
      Date now = new Date();
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsordTs.add(aomsordT);
    }
    
    // 最后再加上累计的单头平台优惠aomsord078
    for (AomsordT at : aomsordTs) {
      at.setAoms020(CommonUtil.checkNullOrNot(sellerDiscount));
      at.setAoms078(CommonUtil.checkNullOrNot(platformDiscount));
    }
    
    return aomsordTs;
  }
}
