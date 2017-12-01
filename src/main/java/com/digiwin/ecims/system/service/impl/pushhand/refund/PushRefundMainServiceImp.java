package com.digiwin.ecims.system.service.impl.pushhand.refund;

import java.util.HashMap;

public class PushRefundMainServiceImp {
	
	public enum PlateformType {
		TAOBAO("0"), //淘寶
		JING_DONG("1"), // 京東
		YHD("2"), // 一號店
		ICBC("3"), // 中國工商
		SUNING("4"), // 蘇寧
		DONG_DONG("5"); //當當
		
		String type;
		private PlateformType(String type) {
			this.type = type;
		}
	}
	
	public Long findRefundDataCountFromDBByCreateDate(PlateformType type, String storeId, String startDate, String endDate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pojo", "AomsrefundT");
		params.put("checkCol", "modified"); //退款修改时间
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("storeType", type.toString());
		params.put("storeId", storeId);
//		Long count = taskService.getSelectPojoCount(params);
		return null;
	}

}
