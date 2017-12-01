package com.digiwin.ecims.system.service.impl.pushhand.refund;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.service.PushRefundDataByHandService;

@Service("pushJingDongRefundDataByHandServiceImpl")
public class PushJingDongRefundDataByHandServiceImpl implements PushRefundDataByHandService {
	
	@Autowired
	private TaskService taskService;

	@Override
	public Long findRefundDataCountFromDBByCreateDate(String storeId, String startDate, String endDate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pojo", "AomsrefundT");
		params.put("checkCol", "modified"); //退款修改时间
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("storeType", "1");
		params.put("storeId", storeId);
		Long count = taskService.getSelectPojoCount(params);
		return count;
	}

	@Override
	public <T> Long findRefundDataCountFromDBByCreateDate(HashMap<String, String> params, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
