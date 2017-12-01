package com.digiwin.ecims.platforms.yougou.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderGet;
import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderIncrement;
import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderItemDetail;
import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderQuery;
import com.digiwin.ecims.platforms.yougou.bean.domain.order.base.OrderBase;
import com.digiwin.ecims.platforms.yougou.bean.enums.OrderStatus;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;

public class AomsordTTranslator {

  private Object entity;

  private StandardAreaService standardAreaService =
      MySpringContext.getContext().getBean(StandardAreaService.class);
  
  public AomsordTTranslator(Object entity) {
    super();
    this.entity = entity;
  }

  public List<AomsordT> doTranslate(String storeId) {
    if (entity instanceof OrderGet) {
      return parseYougouOrderToAomsordT(storeId, ((OrderGet)entity).getItemDetails());
    } else if (entity instanceof OrderQuery){
      return parseYougouOrderToAomsordT(storeId, ((OrderQuery)entity).getItemDetails());
    } else if (entity instanceof OrderIncrement) {
      return parseYougouOrderToAomsordT(storeId, ((OrderIncrement)entity).getItemDetails());
    }
     else {
      return new ArrayList<AomsordT>();
    }
  }

  private List<AomsordT> parseYougouOrderToAomsordT(String storeId, List<OrderItemDetail> orderItems) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    OrderBase head = (OrderBase) this.entity;
    
    for (OrderItemDetail detail : orderItems) {
      AomsordT aomsordT = new AomsordT();

      aomsordT.setId(CommonUtil.checkNullOrNot(head.getOrderSubNo()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(OrderStatus.getOrderStatusByCode(head.getOrderStatus())));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(head.getCreateTime()));
      aomsordT.setModified(CommonUtil.checkNullOrNot(head.getModifyTime()));
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(head.getMessage()));
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(head.getDiscountAmount() + head.getCouponPrefAmount5())); // modi by mowj 20160504 添加礼品卡金额couponprefamount5（因为是平台优惠，需要给商家）
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(head.getOrderPayTotalAmont() + head.getCouponPrefAmount5())); // modi by mowj 20160504 添加礼品卡金额couponprefamount5（因为是平台优惠，需要给商家）
      aomsordT.setAoms023(CommonUtil.checkNullOrNot(head.getPayment()));
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(head.getOnlinePayTime()));
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(head.getMemberName()));
      aomsordT.setAoms026(CommonUtil.checkNullOrNot(head.getEmail()));
      aomsordT.setAoms034(CommonUtil.checkNullOrNot(head.getLogisticsName()));
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(head.getActualPostage()));
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(head.getConsigneeName()));

      
      AreaResponse standardArea =
          standardAreaService.getStandardAreaNameByKeyWord(head.getProvinceName(),
              head.getCityName(), head.getAreaName());
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
      } else {
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(head.getProvinceName()));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(head.getCityName()));
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(head.getAreaName()));
      }
      
      aomsordT.setAoms040(CommonUtil.checkNullOrNot(head.getConsigneeAddress()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(head.getZipcode()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(head.getMobilePhone()));
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(head.getConstactPhone()));
      
      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(YougouCommonTool.STORE_TYPE);
      
      aomsordT.setAoms060(CommonUtil.checkNullOrNot(detail.getProdNo()));
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(detail.getCommoditySpecificationStr()));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(detail.getCommodityNum()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(detail.getProdUnitPrice()));
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(detail.getSupplierCode()));
      aomsordT.setAoms069(CommonUtil.checkNullOrNot(detail.getProdDiscountAmount()));
      aomsordT.setAoms071(CommonUtil.checkNullOrNot(detail.getProdTotalAmt()));
      
      aomsordT.setAoms078(CommonUtil.checkNullOrNot(head.getCouponPrefAmount5())); // modi by mowj 20160504));
      
      Date now = new Date();
      aomsordT.setAomsstatus("0");
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
      
      aomsordTs.add(aomsordT);
    }

    return aomsordTs;
  }
}
