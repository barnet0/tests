package com.digiwin.ecims.platforms.pdd2.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.pdd2.bean.domain.refund.RefundList;
import com.digiwin.ecims.platforms.pdd2.bean.response.refund.RefundGetResponse;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;

/**
 * 退当转义
 * @author cjp 2017.05.10
 *
 */
public class AomsrefundTTranslator {
	private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);

	  private Object object;
	
	   
	  public AomsrefundTTranslator(Object orderEntity) {
	    super();
	    this.object = orderEntity;
	  }
	  
	  public List<AomsrefundT> doTranslate(String storeId) {
	    if (object instanceof RefundGetResponse) {
	      return parsePdd2RefundToAomsrefundT(storeId);
	    } else {
	      return new ArrayList<AomsrefundT>();
	    }
	  }
	  
	  private List<AomsrefundT> parsePdd2RefundToAomsrefundT(String storeId) {
	    List<AomsrefundT> AomsrefundTs = new ArrayList<AomsrefundT>();
	    RefundGetResponse refundGetResponse = (RefundGetResponse)this.object;
	    for (RefundList refundItem : refundGetResponse.getRefund_list()) {
	         int sales_type=refundItem.getAfter_sales_type();
	    	 AomsrefundT aomsrefundT = new AomsrefundT();
	         aomsrefundT.setId(CommonUtil.checkNullOrNot(refundItem.getId()));
	         aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(refundItem.getOrder_sn()));

	         aomsrefundT.setAoms007(CommonUtil.checkNullOrNot(sales_type));
	         aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(refundItem.getAfter_sales_status()));
	         if(sales_type==2){
	        	 aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.FALSE));
	         }
	         if(sales_type==3){
	        	 aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));
	         }

	         aomsrefundT.setModified(CommonUtil.checkNullOrNot(refundItem.getUpdated_time()));
	         
	         
	         aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(refundItem.getGoods_number()));
	         aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundItem.getSku_id()));
	         aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refundItem.getOrder_sn()));
	         aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(refundItem.getOuter_id()));
	         aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(refundItem.getGoods_pricd()));
	         
	         aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(refundItem.getRefund_amount()));
	         aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(refundItem.getTracking_number()));
	         //aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(refundItem.getSku_id()));
	         aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refundItem.getAfter_sales_status()));
	         aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundItem.getOrder_sn()));
	         aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(refundItem.getGoods_name()));
	         
	         aomsrefundT.setAoms040(CommonUtil.checkNullOrNot(refundItem.getOrder_amount()));
	         
	         aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundItem.getCreated_time()));
	
	         aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refundItem.getAfter_sale_reason()));
	         
	         aomsrefundT.setStoreId(storeId);
	         aomsrefundT.setStoreType(Pdd2CommonTool.STORE_TYPE);
	         
	         Date now = new Date();
	         aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
	         aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
	         
	         AomsrefundTs.add(aomsrefundT);
	    }
	    
	   return AomsrefundTs; 
	  }
}
