package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd2.bean.response.refund.RefundGetResponse;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncRefundsService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.pdd2.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

/**
 * 自动同步
 * @author cjp
 *
 */
@Service
public class Pdd2ApiServiceRefundsServiceImpl implements Pdd2ApiSyncRefundsService {

	  @Autowired
	  private LoginfoOperateService loginfoOperateService;

	  @Autowired
	  private TaskService taskService;

	  @Autowired
	  private Pdd2ApiService pdd2ApiService;
	  
	  private static final Logger logger = LoggerFactory.getLogger(Pdd2ApiServiceRefundsServiceImpl.class);
	  
	@Override
	public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate, String endDate)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		syncRefunds(taskScheduleConfig,aomsshopT);
		
	}

	@Override
	public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		 // 参数设定
	    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
	    String sDate = taskScheduleConfig.getLastUpdateTime();
	    String eDate = taskScheduleConfig.getEndDate();
	    int pageSize = taskScheduleConfig.getMaxPageSize();
	    
	    String storeId = aomsshopT.getAomsshop001();
	    String appKey = aomsshopT.getAomsshop004();
	    String appSecret = aomsshopT.getAomsshop005();
	    String accessToken = aomsshopT.getAomsshop006();
	    
	    String start_updated_at=DateTimeTool.parseToDateAndFormatToTimestamp(sDate);
	    String end_update_at=DateTimeTool.parseToDateAndFormatToTimestamp(eDate);
	    logger.info("sdate:{},{}",sDate,start_updated_at);
		logger.info("edate:{},{}",eDate,end_update_at);
		
		//30分钟内
	    String sDate30=DateTimeTool.format(DateTimeTool.getAfterMins(sDate,30),"yyyy-MM-dd HH:mm:ss");	    
	    end_update_at=DateTimeTool.parseToDateAndFormatToTimestamp(DateTimeTool.isBefore(eDate,sDate30)?eDate:sDate30);
	    logger.info("sdate30:{},{}",sDate30,end_update_at);
	    //for test
	 //   start_updated_at=DateTimeTool.parseToDateAndFormatToTimestamp("2017-05-10 13:15:27");
	 //   end_update_at=DateTimeTool.parseToDateAndFormatToTimestamp("2017-05-10 13:35:27");
	
	    logger.info("start_updated_at,{}",start_updated_at);
		logger.info("end_update_at,{}",end_update_at);
	 
	 // 取得时间区间内总资料笔数
	    long totalSize = 0L;
	    totalSize = pdd2ApiService.pddRefundGet(
	        appKey, appSecret, accessToken,"1","1" ,start_updated_at,end_update_at,
	        Pdd2CommonTool.MIN_PAGE_NO, Pdd2CommonTool.MIN_PAGE_SIZE).getTotal_count();
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

	    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
	    // 針對每一頁(倒序)的所有資料新增
	    for (long i = pageNum; i >0; i--) {
	    	RefundGetResponse response = pdd2ApiService.pddRefundGet(
	          appKey, appSecret, accessToken, "1", "1", start_updated_at,end_update_at, (int)i, pageSize);

	      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", 
	          Pdd2CommonTool.STORE_TYPE,
	          "[after_sales_type-1 after_sales_status 1]|pdd.refund.list.increment.get 获得销退资料",
	          JsonUtil.formatByMilliSecond(response),
	          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
	          taskScheduleConfig.getScheduleType());

	      List<AomsrefundT> list =
	          new AomsrefundTTranslator(response).doTranslate(storeId);
	      aomsrefundTs.addAll(list);
	      
	      taskService.newTransaction4Save(aomsrefundTs);
	      // 清空列表，为下一页资料作准备
	      aomsrefundTs.clear();
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
	public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
