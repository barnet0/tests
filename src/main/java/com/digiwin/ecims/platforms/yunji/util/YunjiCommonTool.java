package com.digiwin.ecims.platforms.yunji.util;


import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class YunjiCommonTool {
	  public static final String STORE_TYPE = "M";
	  public static final String STORE_NAME = "Yunji";
	  public static final String STORE_CHN_NAME_IN_DB = "云集";
	  
	  public static final Integer RESPONSE_SUCCESS_CODE = 1;
	  public static final Integer RESPONSE_FAILURE_CODE = 0;
	  
	  public static final Integer MIN_PAGE_NO = 1;     
	  public static final Integer MIN_PAGE_SIZE = 1;
	  public static final Integer DEFAULT_PAGE_SIZE = 20;
	  public static final Integer MAX_PAGE_SIZE = 50;
	  
	//  public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
//	      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
	  
	  // 已取消订单更新排程的设定前缀名
	  /*public static final String WAIT_TO_DELIVER_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "WaitToDeliver" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
	  */

	  // 获取订单排程的设定前缀名
	  public static final String DELIVERED_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "Delivered" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
	  
	  
	  //已确认收货更新排程的设定前缀名
	 /* public static final String NON_REFUND_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "NonRefund" + OntimeCommonUtil.RETURN_UPDATE_SCHEDULE_NAME_SUFFIX;
	 */
	  /*//获取售后排程的设定前缀名
	  public static final String APPLY_REFUND_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "ApplyRefund" + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
	  */
	  //已确认收货更新排程的设定前缀名
	  public static final String ACCEPT_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "Accept" + OntimeCommonUtil.RETURN_UPDATE_SCHEDULE_NAME_SUFFIX;
	  /*
	  //已确认收货更新排程的设定前缀名
	  public static final String REFUNDED_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "Refunded" + OntimeCommonUtil.RETURN_UPDATE_SCHEDULE_NAME_SUFFIX;
	  
	  //已确认收货更新排程的设定前缀名
	  public static final String CLOSED_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
			  STORE_NAME + "Closed" + OntimeCommonUtil.RETURN_UPDATE_SCHEDULE_NAME_SUFFIX;
	  */
	  public static final String ORD_POST_SCHEDULE_TYPE =
	      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
	          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

	  
	  public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 25;
	  
	  public static final String SKU_SPEC_DELIMITER = " ";
	  
	//add by cjp 20170509 REF_POST_SCHEDULE_TYPE
		public static final String REF_POST_SCHEDULE_TYPE = STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER
				+ AomsrefundT.BIZ_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
		// add by cjp 20170510 REFUND_UPDATE_SCHEDULE_NAME_PREFIX
		public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX = STORE_NAME
				+ OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
		
	  public enum OrderStatus {
		
		 ALL("40,50");
	    
	    private String value;
	    OrderStatus(String value) {
	      this.value = value;
	    }

	    public String getValue() {
	      return value;
	    }
	  
	    // 云集订单 未付款、已付款一起获取   2017-09-13 wjl
	    public String getValueStr(){
	    	return value;
	    }
	  }
	  
	  
	  public enum RefundStatus {
		// 云集订单 未付款、已付款一起获取   2017-09-13 wjl
		     ALL("0,1,3,4,5");
		
		    private String value;

		    RefundStatus(String value) {
		      this.value = value;
		    }

		    public String getValue() {
		      return value;
		    }

		    public String getValueStr(){
		    	return value;
		    }
		  }
	  
	/*  public enum OrderDataType {
		  WAIT_TO_DELIVER(40),DELIVERED(50);
		    
		    private Integer value;

		    OrderDataType(Integer value) {
		      this.value = value;
		    }

		    public Integer getValue() {
		      return value;
		    }
		  }*/
	  
}
