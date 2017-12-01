package com.digiwin.ecims.platforms.yunji.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.yunji.bean.domain.refund.ReturnOrder;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundListResponse;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiService;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiSyncRefundsAcceptService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool.RefundStatus;
import com.digiwin.ecims.platforms.yunji.util.translator.AomsrefundTTranslator;
@Service
public class YunjiApiSyncRefundsAcceptServiceImpl implements YunjiApiSyncRefundsAcceptService{
	
	


	  @Autowired
	  private TaskService taskService;

	  @Autowired
	  private YunjiApiService yunjiApiService;
	
	
	
	@Override
	public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		return syncRefundsAcceptRefund(taskScheduleConfig,aomsshopT,RefundStatus.ALL);
	}

	@Override
	public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
		return null;
	}

	@Override
	public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate, String endDate)
			throws Exception {
		return null;
	}

	@Override
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		syncRefunds(taskScheduleConfig, aomsshopT);
		
	}
	public boolean syncRefundsAcceptRefund(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,RefundStatus	refundStatus) throws Exception {
		 // 参数设定
	   // Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
	    String sDate = taskScheduleConfig.getLastUpdateTime();
	    String eDate = taskScheduleConfig.getEndDate();
	    String sDate2 = DateTimeTool.format(DateTimeTool.getAfterMins(sDate, -2), "yyyy-MM-dd HH:mm:ss");
//	    int pageSize = taskScheduleConfig.getMaxPageSize();
	    
	    String storeId = aomsshopT.getAomsshop001();
	    String appKey = aomsshopT.getAomsshop004();
	    String appSecret = aomsshopT.getAomsshop005();
	    

	    //云集的版本号参数后续应该维护到aomsshop_t中
	    String version = "1.0";

	    
	    
	    
try{
	    	
	    	int count=0;
	    	int pageNo=1;
			do {

				
				OrderRefundListResponse response = yunjiApiService.yunjiOrderRefundList(appKey, appSecret, version,
						refundStatus.getValueStr(), sDate2, eDate, YunjiCommonTool.MIN_PAGE_NO,
						YunjiCommonTool.MAX_PAGE_SIZE, 1);
				
				
			
				int itotal = response.getTotal();

				String data = response.getResponseBody();

				JSONObject jsondata = JSON.parseObject(data);

				JSONArray jlist = jsondata.getJSONArray("returnorderlist");
				
				if(jlist.size()>0){

					
					
					List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
					
					
					List<ReturnOrder>  listReturnOrder = JSON.parseObject(JSON.toJSONString(jlist),
							new ArrayList<ReturnOrder>().getClass()
							);
					
					
					response.setReturnorderlist(listReturnOrder);
					
					List<AomsrefundT> list =
					          new AomsrefundTTranslator(response).doTranslate(storeId);
					      
						  aomsrefundTs.addAll(list);
					      	
					      taskService.newTransaction4Save(aomsrefundTs);
					      // 清空列表，为下一页资料作准备
					      aomsrefundTs.clear();
					
					
					
					
					
					/*	List<OrderInfo> listOrderinfo = JSON.parseObject(JSON.toJSONString(list),
								new ArrayList<OrderInfo>().getClass());
		
						List<AomsordT> aomsorderTs = new ArrayList<AomsordT>();
						response.setList(listOrderinfo);
		
						List<AomsordT> listAomsordT = new AomsordTTranslator(response).doTranslate(storeId);
		
						aomsorderTs.addAll(listAomsordT);
		
						taskService.newTransaction4Save(aomsorderTs);
						
						aomsorderTs.clear();*/
				}
				count = itotal / 50 + (itotal % 50 == 0 ? 0 : 1);
				pageNo++;
			}while(pageNo <= count);
			 // 更新updateTime 成為下次的sDate
		    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
	    	
	    	
	}catch (Exception e) {
		e.printStackTrace();
	}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	/*    
	   // 取得时间区间内总资料笔数
	    long totalSize = 0L;
	    //add by test
	    logger.info("\r\n appKey:" + appKey + "\r\n appSecret:" + appSecret + "\r\n orderStatus:" + refundStatus.getValueStr() +"\r\n sDate:"+ sDate + "\r\n eDate:" + eDate);
	    totalSize = yunjiApiService.yunjiOrderRefundList(
	        appKey, appSecret, version,refundStatus.getValueStr(), sDate2, eDate, 
	        YunjiCommonTool.MIN_PAGE_NO, YunjiCommonTool.MIN_PAGE_SIZE,1).getTotal(); 
	    
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
	    for (long i = pageNum; i > 0; i--) {
	    	OrderRefundListResponse response = yunjiApiService.yunjiOrderRefundList(
	          appKey, appSecret, version, 
	          refundStatus.getValueStr(), sDate2, eDate, (int)i, pageSize,1);
	    	  logger.info("请求项次：" + i);

	      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", 
	          YunjiCommonTool.STORE_TYPE,
	          "[" + refundStatus + "]|mListOrders 获取退货详情列表",
	          JsonUtil.formatByMilliSecond(response),
	          SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
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
	    }*/
	    
	    return true;
	}
}
