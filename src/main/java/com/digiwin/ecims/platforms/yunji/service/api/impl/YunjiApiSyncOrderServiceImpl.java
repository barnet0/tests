package com.digiwin.ecims.platforms.yunji.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.yunji.bean.domain.order.OrderInfo;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderListResponse;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiService;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiSyncOrdersService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool.OrderStatus;
import com.digiwin.ecims.platforms.yunji.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class YunjiApiSyncOrderServiceImpl implements YunjiApiSyncOrdersService {

	
	private static final Logger logger = LoggerFactory.getLogger(YunjiApiSyncOrderServiceImpl.class);
	
	  @Autowired
	  private LoginfoOperateService loginfoOperateService;

	  @Autowired
	  private TaskService taskService;

	  @Autowired
	  private YunjiApiService yunjiApiService;
	@Override
	public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
			throws Exception {

		return null;
	}

	@Override
	public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {

		return null;
	}

	@Override
	public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {

		return null;
	}

	@Override
	public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {

		return null;
	}

	@Override
	public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {

		return null;
	}

	@Override
	public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {

		return null;
	}

	@Override
	public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {

		return null;
	}

	@Override
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		syncOrder( taskScheduleConfig,  aomsshopT,  OrderStatus.ALL);
		
	}

	@Override
	public Boolean syncOrder(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, OrderStatus orderStatus)
			throws Exception {
		 // 参数设定
	 //   Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
	    String sDate = taskScheduleConfig.getLastUpdateTime();
	    String eDate = taskScheduleConfig.getEndDate();
	    String sDate2 = DateTimeTool.format(DateTimeTool.getAfterMins(sDate, -10), "yyyy-MM-dd HH:mm:ss");
	  

	    
	    String storeId = aomsshopT.getAomsshop001();
	    String appKey = aomsshopT.getAomsshop004();
	    String appSecret = aomsshopT.getAomsshop005();
	    
	    //云集的版本号参数后续应该维护到aomsshop_t中
	    String version = "1.0";

	    // 取得时间区间内总资料笔数

	    //add by test
		  
	
	    
	    	
	try{
	    	
	    	int count=0;
	    	int pageNo=1;
			do {

				OrderListResponse response = yunjiApiService.yunjiOrderList(appKey, appSecret, version,
						orderStatus.getValueStr(), sDate2, eDate, YunjiCommonTool.MAX_PAGE_SIZE, pageNo, 1);
				int itotal = response.getTotal();

				String data = response.getResponseBody();

				JSONObject jsondata = JSON.parseObject(data);

				if(jsondata.containsKey("list")){
						JSONArray list = jsondata.getJSONArray("list");
						
						if(list.size()>0){
		
								List<OrderInfo> listOrderinfo = JSON.parseObject(JSON.toJSONString(list),
										new ArrayList<OrderInfo>().getClass());
				
								List<AomsordT> aomsorderTs = new ArrayList<AomsordT>();
								response.setList(listOrderinfo);
				
								List<AomsordT> listAomsordT = new AomsordTTranslator(response).doTranslate(storeId);
				
								aomsorderTs.addAll(listAomsordT);
				
								taskService.newTransaction4Save(aomsorderTs);
								
								aomsorderTs.clear();
						}
						count = itotal / 50 + (itotal % 50 == 0 ? 0 : 1);
				}
				pageNo++;
			}while(pageNo <= count);
			 // 更新updateTime 成為下次的sDate
		    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
	    	
	    	
	}catch (Exception e) {
		e.printStackTrace();
	}  	
	    	
	    	
	    	
	    	

	    
	    return true;
	}

}
