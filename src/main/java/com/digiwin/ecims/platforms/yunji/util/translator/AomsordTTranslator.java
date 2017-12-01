package com.digiwin.ecims.platforms.yunji.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.OrderItem;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.platforms.yunji.bean.domain.order.OrderDetailInfo;
import com.digiwin.ecims.platforms.yunji.bean.domain.order.OrderInfo;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderDetailResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderListResponse;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;

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
	    if (object instanceof OrderListResponse) {
	    	return parseYunjiOrderToAomsordT(storeId);
		}else if (object instanceof OrderDetailResponse) {
			return parseYunjiOrderDetailToAomsordT(storeId);
		}else {
			return new ArrayList<AomsordT>();
	    }
	  }
	  
	  private List<AomsordT> parseYunjiOrderToAomsordT(String storeId) {
	    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
	    OrderListResponse order = (OrderListResponse)this.object;
	 //   List<OrderInfo> orderList = order.getList();
	    for (Object jsonOrder : order.getList()) {
	//   for (OrderInfo orderItem : order.getList()) {
	   
	    //	OrderInfo orderItem = JSON.parseObject(JSON.toJSONString(jsonOrder) , OrderInfo.class);
	    	//for (OrderDetailInfo orderDetail : orderItem.getOrderDetail()){
	  	 
		      
	  	    JSONObject    jOrder= (JSONObject)jsonOrder;
	  	 
	  	      JSONArray  array = jOrder.getJSONArray("orderDetail");
	  	    
	  	      for (int i=0 ; i<array.size();i++ ){
	  	    	  
	  	    	  JSONObject	orderDetail = array.getJSONObject(i);
	  	    	  
			  	      AomsordT aomsordT = new AomsordT();
			  	      //根据订单中心 订单状态给不同的状态
				      aomsordT.setId(CommonUtil.checkNullOrNot(jOrder.getString("orderId")));
				      if (jOrder.getInteger("status") == 50) {
				    	  aomsordT.setAoms003("WAIT_BUYER_CONFIRM_GOODS");
				      }else if (jOrder.getInteger("status") == 40) {
				    	  aomsordT.setAoms003("WAIT_SELLER_SEND_GOODS");
				      }else if (jOrder.getInteger("status") == 45 ) {
				    	  aomsordT.setAoms003("WAIT_BUYER_CONFIRM_GOODS");
				      }
				      aomsordT.setAoms004("yunji");
				      
				      aomsordT.setAoms005(CommonUtil.checkNullOrNot(jOrder.getDouble("totalPrice")));
				      
				      aomsordT.setAoms006(CommonUtil.checkNullOrNot(jOrder.getString("createTime")));
				     
				      aomsordT.setModified(CommonUtil.checkNullOrNot(jOrder.getString("modifyTime")));
				      
				      aomsordT.setAoms010(CommonUtil.checkNullOrNot(jOrder.getString("buyerComment")));
				      
				      String BuyerComment = CommonUtil.checkNullOrNot(jOrder.getString("buyerComment"));
				     
				     if(BuyerComment!=null){ 
				      if(BuyerComment.getBytes().length>=90){
				    	  byte[] bbb = new byte[90];
				    	  System.arraycopy(jOrder.getString("buyerComment").getBytes(),
				    			  			0, bbb, 0, 90);
				    	  
				    	  BuyerComment = new String(bbb);
				      }
				     }
				      aomsordT.setAoms011(BuyerComment);
				      
				      aomsordT.setAoms017(CommonUtil.checkNullOrNot(orderDetail.getInteger("qty")));
				      
				      aomsordT.setAoms022(CommonUtil.checkNullOrNot(jOrder.getDouble("totalPrice")));
				      
				      aomsordT.setAoms024(CommonUtil.checkNullOrNot(jOrder.getString("payTime")));
				      
				      aomsordT.setAoms025(CommonUtil.checkNullOrNot(jOrder.getString("receiverName")));
				      
		
				      
				      aomsordT.setAoms035(CommonUtil.checkNullOrNot(jOrder.getInteger("logisticsPrice")));
				      aomsordT.setAoms036(CommonUtil.checkNullOrNot(jOrder.getString("receiverName")));
				      
				      AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(jOrder.getString("receiverProvince"),
				    		  jOrder.getString("receiverCity") , jOrder.getString("receiverArea"));		      
					  if (standardArea != null) {
						String standardProvince = standardArea.getProvince();
						String standardCity = standardArea.getCity();
						String standardDistrict = standardArea.getDistrict();
		
						aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
						aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
						aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
					  }else {
						aomsordT.setAoms037(CommonUtil.checkNullOrNot(jOrder.getString("receiverProvince")));
						aomsordT.setAoms038(CommonUtil.checkNullOrNot(jOrder.getString("receiverCity")));
						aomsordT.setAoms039(CommonUtil.checkNullOrNot(jOrder.getString("receiverArea")));
					  }
				      
		
				      aomsordT.setAoms040(CommonUtil.checkNullOrNot(jOrder.getString("receiverAddress")));
				      aomsordT.setAoms041(CommonUtil.checkNullOrNot(jOrder.getString("receiverZipCode")));
				      aomsordT.setAoms042(CommonUtil.checkNullOrNot(jOrder.getString("receiverMobile")));
				      aomsordT.setAoms043(CommonUtil.checkNullOrNot(jOrder.getString("receiverMobile")));
				      
		
				      
				     /* aomsordT.setAoms053(CommonUtil.checkNullOrNot(orderItem.getInvoiceHead()));
				      aomsordT.setAoms054(CommonUtil.checkNullOrNot(orderItem.getInvoiceContent()));
				      */
				      aomsordT.setStoreId(storeId);
				      aomsordT.setStoreType(YunjiCommonTool.STORE_TYPE);
				      
				      aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderDetail.getString("id")));
				      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderDetail.getString("skuNo")));
				      
				      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderDetail.getInteger("qty")));
				      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderDetail.getString("name")));
				      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderDetail.getDouble("itemPrice")));
				      
		
				      aomsordT.setAoms071(CommonUtil.checkNullOrNot(orderDetail.getDouble("totalPrice")));
				      
				      aomsordT.setAoms073(CommonUtil.checkNullOrNot(orderDetail.getInteger("returnStatus")));
				      
				      aomsordT.setAoms079(CommonUtil.checkNullOrNot(orderDetail.getString("tradeOrderDetailId")));
				      
				      Date now = new Date();
				      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
				      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
				      
				      aomsordTs.add(aomsordT);
				      
	  	      }   
				      
	    	}
	      
	   return aomsordTs; 
	  }
	  private List<AomsordT> parseYunjiOrderDetailToAomsordT(String storeId) {
		    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		    OrderDetailResponse order = (OrderDetailResponse)this.object;
		      
	    	for (OrderDetailInfo orderDetail : order.getOrderDetail()){
	  	      AomsordT aomsordT = new AomsordT();
		      
		      aomsordT.setId(CommonUtil.checkNullOrNot(order.getOrderId()));

		      if (order.getStatus() == 40) {
		    	  aomsordT.setAoms003("WAIT_SELLER_SEND_GOODS");
		      }else if (order.getStatus() == 50) {
		    	  aomsordT.setAoms003("WAIT_BUYER_CONFIRM_GOODS");
		      }else if (order.getStatus() == 45 ) {
		    	  aomsordT.setAoms003("WAIT_BUYER_CONFIRM_GOODS");
		      }
		      aomsordT.setAoms004("yunji");
		      aomsordT.setAoms005(CommonUtil.checkNullOrNot(order.getTotalPrice()));
		      
		      aomsordT.setAoms006(CommonUtil.checkNullOrNot(order.getCreateTime()));
		      aomsordT.setModified(CommonUtil.checkNullOrNot(order.getModifyTime()));
		      
		      aomsordT.setAoms010(CommonUtil.checkNullOrNot(order.getBuyerComment()));
		      aomsordT.setAoms011(CommonUtil.checkNullOrNot(order.getBuyerComment()));
		      
		      aomsordT.setAoms017(CommonUtil.checkNullOrNot(orderDetail.getQty()));
		      aomsordT.setAoms022(CommonUtil.checkNullOrNot(order.getTotalPrice()));
		      aomsordT.setAoms024(CommonUtil.checkNullOrNot(order.getPayTime()));
		      aomsordT.setAoms025(CommonUtil.checkNullOrNot(order.getReceiverName()));
		      
		      aomsordT.setAoms035(CommonUtil.checkNullOrNot(order.getLogisticsPrice()));
		      aomsordT.setAoms036(CommonUtil.checkNullOrNot(order.getReceiverName()));
		      
		      
		      AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(order.getReceiverProvince(),
		    		  order.getReceiverCity(), order.getReceiverArea());		      
			  if (standardArea != null) {
				String standardProvince = standardArea.getProvince();
				String standardCity = standardArea.getCity();
				String standardDistrict = standardArea.getDistrict();

				aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
			  }else {
				aomsordT.setAoms037(CommonUtil.checkNullOrNot(order.getReceiverProvince()));
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(order.getReceiverCity()));
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(order.getReceiverArea()));
			  }

		      aomsordT.setAoms040(CommonUtil.checkNullOrNot(order.getReceiverAddress()));
		      aomsordT.setAoms041(CommonUtil.checkNullOrNot(order.getReceiverZipCode()));
		      aomsordT.setAoms042(CommonUtil.checkNullOrNot(order.getReceiverMobile()));
		      aomsordT.setAoms043(CommonUtil.checkNullOrNot(order.getReceiverPhone()));
		      

		      
		      aomsordT.setAoms053(CommonUtil.checkNullOrNot(order.getInvoiceHead()));
		      aomsordT.setAoms054(CommonUtil.checkNullOrNot(order.getInvoiceContent()));
		      
		      aomsordT.setStoreId(storeId);
		      aomsordT.setStoreType(YunjiCommonTool.STORE_TYPE);
		      
		      aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderDetail.getId()));
		      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderDetail.getSkuNo()));
		      
		      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderDetail.getQty()));
		      aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderDetail.getName()));
		      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderDetail.getItemPrice()));
		      

		      aomsordT.setAoms071(CommonUtil.checkNullOrNot(orderDetail.getTotalPrice()));
		      aomsordT.setAoms073(CommonUtil.checkNullOrNot(orderDetail.getReturnStatus()));
		      
		      aomsordT.setAoms079(CommonUtil.checkNullOrNot(orderDetail.getTradeOrderDetailId()));
		      Date now = new Date();
		      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
		      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
		      
		      aomsordTs.add(aomsordT);
	    	}
    
		   return aomsordTs; 
		  }
}
