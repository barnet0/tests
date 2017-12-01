package com.digiwin.ecims.platforms.vip.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class VipCommonTool {
	public static final String STORE_TYPE = "D";
	public static final String STORE_NAME = "Vip";
	public static final String STORE_CHN_NAME_IN_DB = "唯品会";
	
	public static final String VENDOR_TYPE = "COMMON";
	public static final int JIT_INVENTORY_TYPE = 462;
	
	public static final String ORD_POST_SCHEDULE_TYPE = 
			STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
			+ OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
	public static final String REF_POST_SCHEDULE_TYPE = 
			STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
			+ OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
	public static final String ITE_POST_SCHEDULE_TYPE = 
			STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
			+ OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;	
	
	public static final String JIT_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = STORE_NAME + "JIT" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
	public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX = STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
	public static final String ITEM_UPDATE_SCHEDULE_NAME_PREFIX = STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
	
	public static final int UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 30;
	
	public static final int MIN_PAGE_NO = 1;
	public static final int MIN_PAGE_SIZE = 1;
	public static final int DEFAULT_PAGE_SIZE = 50;
	public static final int MAX_PAGE_SIZE = 100;
	
//	public static final String VENDOR_ID_PARAM_KEY = "VipVendorId";
	
	public static final String RETURN_MSG_DELIMITER = ";";
	
	public enum VipApiEnum {
		DIGIWIN_VIP_JIT_CREATE_PICK("digiwin.vip.jit.createpick"),
		DIGIWIN_VIP_JIT_GET_PICK("digiwin.vip.jit.getpick"),
		DIGIWIN_VIP_JIT_CREATE_DELIVERY("digiwin.vip.jit.createdelivery"),
		DIGIWIN_VIP_JIT_IMPORT_DELIVERY_DETAIL("digiwin.vip.jit.importdeliverydetail"),
		DIGIWIN_VIP_JIT_CONFIRM_DELIVERY("digiwin.vip.jit.confirmdelivery"),
		DIGIWIN_VIP_JIT_GET_PRINTED_DELIVERY("digiwin.vip.jit.getprinteddelivery"),
		DIGIWIN_VIP_JIT_SHIPPING_SEND("digiwin.vip.jit.shipping.send");
		
		private String apiName;
		VipApiEnum(String apiName) {
			this.apiName = apiName;
		}
		public String toString() {
			return this.apiName;
		}
	}
	
	public enum VipJitPickStatusEnum {
		
		UNDELIVERIED("0"),
		DELIVERIED("1");
		
		private String pickStatus;
		
		private VipJitPickStatusEnum(String pickStatus) {
			this.pickStatus = pickStatus;
		}

		public String getPickStatus() {
			return pickStatus;
		}
	}
	
}
