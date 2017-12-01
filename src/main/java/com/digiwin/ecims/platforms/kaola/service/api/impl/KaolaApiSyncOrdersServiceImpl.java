package com.digiwin.ecims.platforms.kaola.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiService;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncOrdersService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.OrderStatus;
import com.digiwin.ecims.platforms.kaola.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class KaolaApiSyncOrdersServiceImpl implements KaolaApiSyncOrdersService {
	private static final Logger logger = LoggerFactory.getLogger(KaolaApiSyncOrdersServiceImpl.class);
	
	  @Autowired
	  private LoginfoOperateService loginfoOperateService;

	  @Autowired
	  private TaskService taskService;

	  @Autowired
	  private KaolaApiService kaolaApiService;
	  
	@Override  
	public Boolean syncOrder(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,OrderStatus orderStatus) throws Exception{
		 // 参数设定
	    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
	    String sDate = taskScheduleConfig.getLastUpdateTime();
	    String eDate = taskScheduleConfig.getEndDate();
	    int pageSize = taskScheduleConfig.getMaxPageSize();
	    
	    String storeId = aomsshopT.getAomsshop001();
	    String appKey = aomsshopT.getAomsshop004();
	    String appSecret = aomsshopT.getAomsshop005();
	    String accessToken = aomsshopT.getAomsshop006();

	    // 取得时间区间内总资料笔数
	    long totalSize = 0L;
	    totalSize = kaolaApiService.kaolaOrderSearch(
	        appKey, appSecret, accessToken, orderStatus.getValue(), sDate, eDate, 
	        KaolaCommonTool.MIN_PAGE_NO, KaolaCommonTool.MIN_PAGE_SIZE).getTotal_count(); 
	    
	    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
	    if (taskScheduleConfig.isReCycle()) {
	      // process empty, 主要是為好閱讀

	    } else if (totalSize == 0L) {
	      // 区间内没有资料 
	      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
	        // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
	        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
	        return true;
	      }
	    }

	    // 區間內有資料， 計算頁數
	    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

	    List<AomsordT> aomsorderTs = new ArrayList<AomsordT>();
	    // 針對每一頁(倒序)的所有資料新增
	    for (long i = pageNum; i > 0; i--) {
	    	OrderSearchResponse response = kaolaApiService.kaolaOrderSearch(
	          appKey, appSecret, accessToken, 
	          orderStatus.getValue(), sDate, eDate, (int)i, pageSize);
	    	  logger.info("请求项次：" + i);

	      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", 
	          KaolaCommonTool.STORE_TYPE,
	          "[" + orderStatus + "]|mSearchOrders 获取订单详情列表",
	          JsonUtil.formatByMilliSecond(response),
	          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
	          taskScheduleConfig.getScheduleType());

	      List<AomsordT> list =
	          new AomsordTTranslator(response).doTranslate(storeId);
	      aomsorderTs.addAll(list);
	      	
	      taskService.newTransaction4Save(aomsorderTs);
	      // 清空列表，为下一页资料作准备
	      aomsorderTs.clear();
	    }

	    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
	    if (taskScheduleConfig.isReCycle()) {
	      // process empty, 主要是為好閱讀

	    } else {
	      // 更新updateTime 成為下次的sDate
	      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
	    }
	    
	    return true;
	  }
	@Override
	public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		// TODO Auto-generated method stub
		
	}



}
