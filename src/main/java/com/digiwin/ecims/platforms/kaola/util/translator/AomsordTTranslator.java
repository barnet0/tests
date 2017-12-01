package com.digiwin.ecims.platforms.kaola.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.kaola.bean.domain.order.GetOrder;
import com.digiwin.ecims.platforms.kaola.bean.domain.order.GetOrderSku;
import com.digiwin.ecims.platforms.kaola.bean.domain.order.Order;
import com.digiwin.ecims.platforms.kaola.bean.domain.order.OrderSku;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.OrderStatus;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;


public class AomsordTTranslator {
	  private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);

	  private Object object;
	  
	  private StandardAreaService standardAreaService =
	      MySpringContext.getContext().getBean(StandardAreaService.class);
	  
	  public AomsordTTranslator(Object orderEntity) {
	    super();
	    this.object = orderEntity;
	  }
	  
	  public List<AomsordT> doTranslate(String storeId) {
	    if (object instanceof OrderSearchResponse) {
	      return parseKaolaOrderToAomsordT(storeId);
	    }else if (object instanceof OrderGetResponse) {
			return parseKaolaOrderToAomsordTGet(storeId);
		} else {
	      return new ArrayList<AomsordT>();
	    }
	  }
	  
	  private List<AomsordT> parseKaolaOrderToAomsordT(String storeId) {
	    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
	    OrderSearchResponse order = (OrderSearchResponse)this.object;
	    for (Order orderItem : order.getOrders()) {
	      
	    	for (OrderSku orderSku : orderItem.getOrder_skus()){
	  	      AomsordT aomsordT = new AomsordT();
		      
		      aomsordT.setId(CommonUtil.checkNullOrNot(orderItem.getOrder_id()));
		      aomsordT.setAoms002(CommonUtil.checkNullOrNot(orderItem.getPresale_order()));
		      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderItem.getOrder_status()));
		      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderItem.getOrder_time()));
		      aomsordT.setAoms008(CommonUtil.checkNullOrNot(orderItem.getFinish_time()));
		      aomsordT.setAoms020(CommonUtil.checkNullOrNot(orderItem.getCoupon_amount()));
		      aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderItem.getOrder_real_price()));
		      aomsordT.setAoms023(CommonUtil.checkNullOrNot(orderItem.getPay_method_name()));
		      aomsordT.setAoms024(CommonUtil.checkNullOrNot(orderItem.getPay_success_time()));
		      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderItem.getBuyer_account()));
		      
		      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderItem.getExpress_fee()));
		      aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderItem.getReceiver_name()));
		      aomsordT.setAoms037(CommonUtil.checkNullOrNot(orderItem.getReceiver_province_name()));
		      aomsordT.setAoms038(CommonUtil.checkNullOrNot(orderItem.getReceiver_city_name()));
		      aomsordT.setAoms039(CommonUtil.checkNullOrNot(orderItem.getReceiver_district_name()));
		      aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderItem.getReceiver_address_detail()));
		      aomsordT.setAoms041(CommonUtil.checkNullOrNot(orderItem.getReceiver_post_code()));
		      aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderItem.getReceiver_phone()));
		      
		      aomsordT.setAoms044(CommonUtil.checkNullOrNot(orderItem.getDeliver_time()));
		      aomsordT.setAoms045(CommonUtil.checkNullOrNot(orderItem.getTax_fee()));
		      
		      aomsordT.setAoms053(CommonUtil.checkNullOrNot(orderItem.getInvoice_title()));
		      
		      aomsordT.setStoreId(storeId);
		      aomsordT.setStoreType(KaolaCommonTool.STORE_TYPE);
		      
		      aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderSku.getOrder_serial_num()));
		      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderSku.getSku_key()));
		      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderSku.getCount()));
		      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderSku.getProduct_name()));
		      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderSku.getOrigin_price()));
		      
		      aomsordT.setAoms066(CommonUtil.checkNullOrNot(orderSku.getGoods_no()));
		      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderSku.getBarcode()));
		      aomsordT.setAoms071(CommonUtil.checkNullOrNot(orderSku.getReal_totle_price()));
		      
		      Date now = new Date();
		      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
		      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
		      
		      aomsordTs.add(aomsordT);
	    	}
	    }	    
	   return aomsordTs; 
	  }
	  
	  private List<AomsordT> parseKaolaOrderToAomsordTGet(String storeId) {
		    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

		    OrderGetResponse orderRes = (OrderGetResponse)this.object; 
		    GetOrder order = orderRes.getOrder();
	    	for (GetOrderSku orderSku : order.getOrder_skus()){
	    		
	  	      AomsordT aomsordT = new AomsordT();
		      
		      aomsordT.setId(CommonUtil.checkNullOrNot(order.getOrder_id()));
		      aomsordT.setAoms002(CommonUtil.checkNullOrNot(order.getPresale_order()));
		      aomsordT.setAoms003(CommonUtil.checkNullOrNot(order.getOrder_status()));
		      aomsordT.setAoms006(CommonUtil.checkNullOrNot(order.getOrder_time()));
		      aomsordT.setAoms008(CommonUtil.checkNullOrNot(order.getFinish_time()));
		      logger.info("OrderSku:" + orderSku.toString());
		      aomsordT.setAoms020(CommonUtil.checkNullOrNot(order.getCoupon_amount()));
		      aomsordT.setAoms022(CommonUtil.checkNullOrNot(order.getOrder_real_price()));
		      aomsordT.setAoms023(CommonUtil.checkNullOrNot(order.getPay_method_name()));
		      aomsordT.setAoms024(CommonUtil.checkNullOrNot(order.getPay_success_time()));
		      aomsordT.setAoms025(CommonUtil.checkNullOrNot(order.getBuyer_account()));
		      
		      aomsordT.setAoms035(CommonUtil.checkNullOrNot(order.getExpress_fee()));
		      aomsordT.setAoms036(CommonUtil.checkNullOrNot(order.getReceiver_name()));
		      aomsordT.setAoms037(CommonUtil.checkNullOrNot(order.getReceiver_province_name()));
		      aomsordT.setAoms038(CommonUtil.checkNullOrNot(order.getReceiver_city_name()));
		      aomsordT.setAoms039(CommonUtil.checkNullOrNot(order.getReceiver_district_name()));
		      aomsordT.setAoms040(CommonUtil.checkNullOrNot(order.getReceiver_address_detail()));
		      aomsordT.setAoms041(CommonUtil.checkNullOrNot(order.getReceiver_post_code()));
		      aomsordT.setAoms042(CommonUtil.checkNullOrNot(order.getReceiver_phone()));
		      
		      aomsordT.setAoms044(CommonUtil.checkNullOrNot(order.getDeliver_time()));
		      aomsordT.setAoms045(CommonUtil.checkNullOrNot(order.getTax_fee()));
		      
		      aomsordT.setAoms053(CommonUtil.checkNullOrNot(order.getInvoice_title()));
		      
		      aomsordT.setStoreId(storeId);
		      aomsordT.setStoreType(KaolaCommonTool.STORE_TYPE);
		      
		      aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderSku.getOrder_serial_num()));
		      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderSku.getSku_key()));

		      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderSku.getCount()));
		      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderSku.getProduct_name()));
		      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderSku.getOrigin_price()));
		      
		      aomsordT.setAoms066(CommonUtil.checkNullOrNot(orderSku.getGoods_no()));
		      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderSku.getBarcode()));
		      aomsordT.setAoms071(CommonUtil.checkNullOrNot(orderSku.getReal_totle_price()));
		      
		      Date now = new Date();
		      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
		      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
		      
		      aomsordTs.add(aomsordT);

		    }	   
		   return aomsordTs; 
		  }
}
