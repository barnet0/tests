package com.digiwin.ecims.platforms.yunji.service.ontime.updateCheck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.OnTimeTaskCheckBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.yunji.bean.domain.order.OrderInfo;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderListResponse;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool.OrderStatus;
import com.digiwin.ecims.platforms.yunji.util.translator.AomsordTTranslator;

/*
 * 云集订单检查逻辑
 */
@Service("yunjiTradeUpdateCheckServiceImpl")
public class YunjiTradeUpdateCheckServiceImpl implements OnTimeTaskCheckBusiJob{

	
	  @Autowired
	  private YunjiApiService yunjiApiService;
	
	  @Autowired
	  private TaskService taskService;
	  
	
/*	private static final Logger logger = 
			LoggerFactory.getLogger(YunjiTradeUpdateCheckServiceImpl.class);
	*/
	 @Autowired
	 private AomsShopService aomsShopService;
	
/*	 @Autowired
	 private BaseUpdateCheckService baseCheckUpdateService;
	  
	 @Autowired
	 private YunjiApiSyncOrdersService yunjiApiSyncOrdersService;*/
	
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		
		
		return timeOutExecuteCheck(args);
	}

	
	/**
	 * 
	 * 
	 * 说明： lastupdateTime ，向前推2小时 。lastupdateTime 是手动设定
	 */
	
	@Override
	public boolean timeOutExecuteCheck(String... args)  {
		

		
		String info = "yunjiTradeUpdateCheck#DZ0058#Hour#1";
		String[] arg = info.split("#");
		//baseCheckUpdateService.saveTaskScheduleConfigIfNotExist(args[0]);
		
		/*
	     * 格式:
	     * 排程名称#平台编号#时间类型#检验时间范围 
	     * service#storeType#timeType#reCycleTime 
	     * ex: taobaoTbTradeUpdateCheck#0#Hour#1，表示淘宝普通订单，检验前一小时至当前时间的订单
	     * 
	     * inputData[0] = serviceName 
	     * inputData[1] = storeType 
	     * inputData[2] = timeType 
	     * inputData[3] = reCycleTime
	     */
		if(arg == null || arg.length<1){
			throw new IllegalArgumentException("云集补单需要排名名称");
		}
//		String scheduleType = arg[0];

		String storeId = arg[1];
		
//		final int beforeHours = Integer.parseInt(arg[3]);   //推迟时间

		// 获取该平台的所有店铺
		AomsshopT aomsshopT= aomsShopService.getStoreByStoreId(storeId);
		

		TaskScheduleConfig taskScheduleConfig =	taskService.getTaskScheduleConfigInfo(info);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String sDate2 =null;
		String lastUpdateTime =null;
		String saveLastUpdateTime = null;

		//记录执行时间
		try{
			lastUpdateTime =	taskScheduleConfig.getLastUpdateTime();
			
			Date  dlastUpdateTime = format.parse(lastUpdateTime);
		
		
		sDate2 = DateTimeTool.format(DateTimeTool.getBeforeHours(dlastUpdateTime, 2), "yyyy-MM-dd HH:mm:ss"); //两小时获取  开始时间
		
		saveLastUpdateTime =  DateTimeTool.format(DateTimeTool.getBeforeHours(dlastUpdateTime, -2), "yyyy-MM-dd HH:mm:ss"); 
		
		}catch(Exception e){
			e.printStackTrace();
			
			
			Date  dlastUpdateTime = new Date();
			lastUpdateTime =	format.format(dlastUpdateTime);
			lastUpdateTime = format.format(new Date());
			sDate2 = DateTimeTool.format(DateTimeTool.getBeforeHours(dlastUpdateTime, 2), "yyyy-MM-dd HH:mm:ss"); //两小时获取  开始时间
			saveLastUpdateTime =  DateTimeTool.format(DateTimeTool.getBeforeHours(dlastUpdateTime, -2), "yyyy-MM-dd HH:mm:ss"); 
			
		}
			
		

			
			try{
			    	
			    	int count=0;
			    	int pageNo=1;
					do {
						String 	version = "1.0";
						
						String appKey=aomsshopT.getAomsshop004();
						String appSecret=aomsshopT.getAomsshop005();
						
						OrderStatus	orderStatus = OrderStatus.ALL;
						
						OrderListResponse response = yunjiApiService.yunjiOrderList(appKey, appSecret, version,
								orderStatus.getValueStr(),sDate2,lastUpdateTime , YunjiCommonTool.MAX_PAGE_SIZE, pageNo, 1);
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
				    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), saveLastUpdateTime);
			    	
				    return true;
			}catch (Exception e) {
				e.printStackTrace();
				 return false;
			}  
		
	}

}
