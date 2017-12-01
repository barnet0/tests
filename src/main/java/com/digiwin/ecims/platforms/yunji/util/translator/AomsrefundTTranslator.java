package com.digiwin.ecims.platforms.yunji.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

import com.digiwin.ecims.platforms.yunji.bean.domain.refund.ReturnOrder;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundDetailResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundListResponse;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;



public class AomsrefundTTranslator {
	private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);

	  private Object object;
	
	   
	  public AomsrefundTTranslator(Object orderEntity) {
	    super();
	    this.object = orderEntity;
	  }
	  
	  public List<AomsrefundT> doTranslate(String storeId) {
	    if (object instanceof OrderRefundListResponse) {
	      return parseYunjiRefundToAomsrefundT(storeId);
	    }else if (object instanceof OrderRefundDetailResponse) {
	    	return parseYunjiRefundDetailToAomsrefundT(storeId);
		}else {
	      return new ArrayList<AomsrefundT>();
	    }
	  }
	  
	  private List<AomsrefundT> parseYunjiRefundToAomsrefundT(String storeId) {
	    List<AomsrefundT> AomsrefundTs = new ArrayList<AomsrefundT>();
	    
	    OrderRefundListResponse orderRefundListRsp = (OrderRefundListResponse)this.object;
	    
	    
	    
	    
	    for (Object  objectItem : orderRefundListRsp.getReturnorderlist()) {
	    	
	    	
	    	ReturnOrder refundItem = JSON.parseObject(JSON.toJSONString(objectItem), ReturnOrder.class);
	    	
	    	AomsrefundT aomsrefundT = new AomsrefundT();
	    	 // aomsrefund001
	         aomsrefundT.setId(CommonUtil.checkNullOrNot(refundItem.getRefundId()));
	         
	         
	         aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(refundItem.getReturnMoney()));
	         
	         if(3==refundItem.getReturnType()){
	        	 aomsrefundT.setAoms009("true");
	         }else{
	        	 aomsrefundT.setAoms009("false"); 
	         }
	         
	     //    aomsrefundT.setAoms017("tlb");   //(串订单获取 oms_ord036)
	         
	         aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(refundItem.getCreateTime()));
	         aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(refundItem.getReturnQty()));
	         aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(refundItem.getPrice()));
	         aomsrefundT.setAoms034(CommonUtil.checkNullOrNot(refundItem.getLogisticsCompanyName()));
	         aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(refundItem.getLogisticsNumber()));
	         aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(refundItem.getSkuNo()));
	        
	         if(3==refundItem.getReturnStatus()){
	        	 aomsrefundT.setAoms037("WAIT_BUYER_RETURN_GOODS");
	         }else if(5==refundItem.getReturnStatus()){
	        	 aomsrefundT.setAoms037("WAIT_SELLER_CONFIRM_GOODS");
	         }else if(1==refundItem.getReturnStatus()){
	        	 aomsrefundT.setAoms037("WAIT_SELLER_AGREE");
	         }else if(4==refundItem.getReturnStatus()){
	        	 aomsrefundT.setAoms037("SUCCESS");
	         }
	         
	         
	         aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundItem.getOrderId()));
	         aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refundItem.getBuerDesc()));
	         aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refundItem.getReturnReason()));
	        // aomsrefundT.setAoms044("M");
	         aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundItem.getSkuNo()));
	        
	         aomsrefundT.setModified(CommonUtil.checkNullOrNot(refundItem.getCreateTime()));
	         aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundItem.getCreateTime()));
	         
	         
	         
	         aomsrefundT.setAoms046("1");
	         aomsrefundT.setAoms048("N");
	         aomsrefundT.setStoreId(storeId);
	         aomsrefundT.setStoreType(YunjiCommonTool.STORE_TYPE);
	         
	         Date now = new Date();
	         aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
	         aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
	         
	         AomsrefundTs.add(aomsrefundT);
	    }
	    
	   return AomsrefundTs; 
	  }
	  private List<AomsrefundT> parseYunjiRefundDetailToAomsrefundT(String storeId) {
		 List<AomsrefundT> AomsrefundTs = new ArrayList<AomsrefundT>();
		    
		 OrderRefundDetailResponse orderRefundDetailRsp = (OrderRefundDetailResponse)this.object;
		 logger.info("refund res code:" + orderRefundDetailRsp.getCode() + "-----"); 
		 for (ReturnOrder retrunOrder : orderRefundDetailRsp.getData()){
	    	 AomsrefundT aomsrefundT = new AomsrefundT();
	         aomsrefundT.setId(CommonUtil.checkNullOrNot(retrunOrder.getRefundId()));
	         aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(retrunOrder.getOrderId()));
	         
	         aomsrefundT.setAoms007(CommonUtil.checkNullOrNot(retrunOrder.getReturnType()));
	         logger.info("return status:---" + retrunOrder.getReturnStatus());
	         if (retrunOrder.getReturnStatus() == 1) {
	        	 aomsrefundT.setAoms008("WAIT_SELLER_AGREE");
	        	 aomsrefundT.setAoms037("WAIT_SELLER_AGREE");
			}else if (retrunOrder.getReturnStatus() == 3) {
				aomsrefundT.setAoms008("WAIT_BUYER_RETURN_GOODS");
				aomsrefundT.setAoms037("WAIT_BUYER_RETURN_GOODS");
			}else if (retrunOrder.getReturnStatus() == 4) {
				aomsrefundT.setAoms008("SUCCESS");
				aomsrefundT.setAoms037("SUCCESS");
			}else if (retrunOrder.getReturnStatus() == 5) {
				aomsrefundT.setAoms008("WAIT_SELLER_CONFIRM_GOODS");
				aomsrefundT.setAoms037("WAIT_SELLER_CONFIRM_GOODS");
			}
	         
	         if (retrunOrder.getReturnType() == 1 | retrunOrder.getReturnType() == 2) {
				aomsrefundT.setAoms009("false");
			}else {
				aomsrefundT.setAoms009("true");
			}
	         
	         aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(retrunOrder.getCreateTime()));
	         aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(retrunOrder.getReturnQty()));
	         aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(retrunOrder.getSkuNo()));     
	         aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(retrunOrder.getPrice()));
	         aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(retrunOrder.getReturnMoney()));
	         
	         aomsrefundT.setAoms034(CommonUtil.checkNullOrNot(retrunOrder.getLogisticsCompanyName()));
	         aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(retrunOrder.getLogisticsNumber()));
	         aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(retrunOrder.getSkuNo()));
	         
	         aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(retrunOrder.getOrderId()));
	         aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(retrunOrder.getName()));
	         
	         
	         aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(retrunOrder.getCreateTime()));
	         aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(retrunOrder.getBuerDesc()));
	         aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(retrunOrder.getReturnReason()));
	         
	         aomsrefundT.setAoms046("1");
	         aomsrefundT.setAoms048("N");
	         aomsrefundT.setStoreId(storeId);
	         aomsrefundT.setStoreType(YunjiCommonTool.STORE_TYPE);
	         
	         Date now = new Date();
	         aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
	         aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
	         
	         AomsrefundTs.add(aomsrefundT);	    
			    
		 }
		 return AomsrefundTs;
	  }
}
