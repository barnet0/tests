package com.digiwin.ecims.platforms.dangdang.service.onTime.updatecheck;

import java.text.ParseException;
//import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJobForDataCycleRun;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;

@Service("dangdangTradeUpdateCheckServiceImpl")
@Scope("prototype")
public class DangdangTradeUpdateCheckServiceImpl extends UpdateTask implements OnTimeTaskBusiJobForDataCycleRun {

	private static final Logger logger = LoggerFactory.getLogger(DangdangTradeUpdateCheckServiceImpl.class);

//	@Autowired
//	private DangdangAPIService dangdangAPIService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private LoginfoOperateService loginfoOperateService;

	@Autowired
	private CheckUpdateService checkUpdateService;

	@Autowired
	private AomsShopService aomsShopService;

	/*
	 * 校验实现类
	 */
	@Resource(name = "syncDangDangOrderDataByHandServiceImpl")
	private SyncOrderDataByHandService syncDangDangOrderDataByHandServiceImpl;

	@Resource(name = "pushDangDangOrderDataByHandServiceImpl")
	private PushOrderDataByHandService pushDangDangOrderDataByHandServiceImpl;
	
//	private TaskScheduleConfig taskScheduleConfig = null;
//	private TaskScheduleConfig taskScheduleUpdateCheckConfig = null;
//	private String scheduleType = null;
	private String scheduleUpdateCheckType = null;
	
	@Override
	public void setScheduleUpdateCheckType(String scheduleUpdateCheckType) {
		this.scheduleUpdateCheckType = scheduleUpdateCheckType;
	}
	
//	private String startDate;
//	private String endDate;

	private String[] reCycleDate;

	public void setCheckDate(String[] reCycleDate) {
		this.reCycleDate = reCycleDate;
	}

	public void executeUpdateCheckForDataCycleRun(AomsshopT aomsshopT) throws NumberFormatException, Exception {
		// mark by mowj 20151118 start
//		/*
//		 * 以定时任务功能名+分销商的id+分销商类型为定时任务的类型，到定时任务的配置文件中查找该定时任务的配置信息
//		 * 此处淘宝的格式为：TaobaoFxProductUpdate#MISC-14#A
//		 */
//		String storeId = aomsshopT.getAomsshop001();
//		storeType = aomsshopT.getAomsshop003();
//
//		scheduleType = "DangdangTradeUpdate#" + storeId + "#" + storeType;
//		scheduleUpdateCheckType = "DangdangTradeUpdateCheck#" + storeId + "#" + storeType;
//
//		taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
//
//		/*
//		 * 通过“DangdangTradeUpdate”当当的定时任务计划类型， 获取定时任务的schedule配置，
//		 * 此配置内存放了该批次的定时任务的最后更新时间，每笔最大更新条数
//		 */
//		taskScheduleConfigCheck = taskService.getTaskScheduleConfigInfo(scheduleUpdateCheckType);
//
//		// 檢查是不是 reCycle 的 schedule 所調用的, 若是, 則不更新 lastRunTime, 因為在上一層己有更新. add
//		// by xavier on 20150830
//		if (reCycleDate == null) {
//			// 舊的 check logic 才需要更新 lastRunTime
//			taskService.newTransaction4SaveLastRunTime(scheduleUpdateCheckType, null); // 記錄執行時間, // add by xavier on 20150830
//		}
//
//		// 如果没有设置更新时间，默认从很久以前开始同步
//		if (taskScheduleConfigCheck.getLastUpdateTime() == null
//				|| "".equals(taskScheduleConfigCheck.getLastUpdateTime())) {
//			taskScheduleConfigCheck.setLastUpdateTime(getInitDateTime());
//			taskScheduleConfigCheck.setMaxReadRow(2000);
//			taskScheduleConfigCheck.setMaxPageSize(20);
//			taskService.saveTaskTaskScheduleConfig(taskScheduleConfigCheck);
//		}
//
//		// 開始時間為檢查資料的最後更新時間
//		startDate = taskScheduleConfigCheck.getLastUpdateTime();
//		// 結束時間為拉取資料的最後更新時間
//		endDate = taskScheduleConfig.getLastUpdateTime();
//
//		// updateCheckService的endDate 不能大於
//		// updateService的startDate(可能會存在覆蓋其他新的單據狀態的情況)
//		taskScheduleConfigCheck.setEndDateByMonth(DateTimeTool.parse(taskScheduleConfigCheck.getLastUpdateTime()), 1);
//
//		// 檢查是不是 reCycle 的 schedule 所調用的, 若是, 則調整 taskScheduleUpdateCheckConfig
//		// 裡的資訊. add by xavier on 20150829
//		if (reCycleDate != null) {
//			// 調整 相關資訊
//			startDate = reCycleDate[0];
//			endDate = reCycleDate[1];
//			taskScheduleConfigCheck.setReCycle(Boolean.TRUE);
//			taskScheduleConfigCheck.setLastUpdateTime(startDate);
//			taskScheduleConfigCheck.setEndDate(endDate);
//		}
//		logger.info("获取同步前的上次校验的时间{}", taskScheduleConfigCheck.getLastUpdateTime());
		// mark by mowj 20151118 end

		// add by mowj 20151118 start
//		taskScheduleUpdateCheckConfig = taskService.getTaskScheduleConfigInfo(scheduleUpdateCheckType);
//		if (taskScheduleUpdateCheckConfig.getLastRunTime() == null) {
//			taskService.saveTaskTaskScheduleConfig(taskScheduleUpdateCheckConfig);
//		}
		String startDate = "";
		String endDate = "";
		if (reCycleDate != null) {
			// 取得執行區間
			startDate = this.reCycleDate[0];
			endDate = this.reCycleDate[1];
//			// 調整 相關資訊
//			taskScheduleUpdateCheckConfig.setReCycle(Boolean.TRUE);
		}
		// add by mowj 20151118 end
		
//		String state = "9999";
//		String dateType = "1";
		
//		// 取得時間區間內總資料筆數
//		int totalSize = Integer.parseInt(dangdangAPIService.digiwinOrdersGet(aomsshopT, startDate, endDate, state, 1, 5,
//				dateType, taskScheduleUpdateCheckConfig.getScheduleType()).getTotalInfo().getOrderCount());

//		// 整理搜尋區間，直到區間數目<=最大讀取數目
//		// 整理方式，採二分法
//		while (totalSize > taskScheduleUpdateCheckConfig.getMaxReadRow()) {
//			// eDate變為sDate與eDate的中間時間
//			endDate = DateTimeTool
//					.format(new Date((DateTimeTool.parse(startDate).getTime() + DateTimeTool.parse(endDate).getTime()) / 2));
//			// System.out.println("eDate" + eDate);
//			totalSize = Integer.parseInt(dangdangAPIService.digiwinOrdersGet(aomsshopT, startDate, endDate, state, 1, 5,
//					dateType, taskScheduleUpdateCheckConfig.getScheduleType()).getTotalInfo().getOrderCount());
//		}

		// 根据创建时间捞取数据
//		long ecCount = totalSize;
		// long ecCount =
		// syncDangDangOrderDataByHandServiceImpl.findOrderDataFromECByCreateDate(storeId,
		// sDate, eDate);
		// Integer ecCount =
		// syncDangDangOrderDataByHandServiceImpl.findOrderDataFromECByCreateDate(taskScheduleConfigCheck,
		// aomsshopT);

//		long dbCount = pushDangDangOrderDataByHandServiceImpl.findOrderDataCountFromDBByCreateDate(storeId, startDate,
//				endDate);
		// Integer dbCount =
		// pushDangDangOrderDataByHandServiceImpl.findOrderDataCountFromDBByCreateDate(taskScheduleConfigCheck,
		// aomsshopT);

//		checkUpdateService.put(startDate, endDate, scheduleUpdateCheckType,
//				loginfoOperateService.newTransaction4SaveCheckServiceRecord(this.getClass().getSimpleName(), startDate,
//						endDate, ecCount - dbCount, storeType, storeId, scheduleUpdateCheckType));
//
//		if (ecCount != dbCount) {
//			syncDangDangOrderDataByHandServiceImpl.syncOrderDataByCreateDate(storeId, startDate, endDate);
//			// dangdangAPIService.syncOrdersData(taskScheduleConfigCheck,
//			// aomsshopT);
//		} 

//		// 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
//		checkUpdateService.remove(startDate, endDate, scheduleUpdateCheckType);
		
		// add by mowj 20151118 start
		executeCheckLogic(aomsshopT, startDate, endDate, UseTimeEnum.CREATE_TIME);
		executeCheckLogic(aomsshopT, startDate, endDate, UseTimeEnum.UPDATE_TIME);
		// add by mowj 20151118 end
		
	}

	private void executeCheckLogic(AomsshopT aomsshopT, String startDate, String endDate, UseTimeEnum orderUseTime) throws Exception {
		// 取得時間區間內總資料筆數
		String storeId = aomsshopT.getAomsshop001();
		long ecCount = 0l;
		long dbCount = 0l;
		if (orderUseTime == UseTimeEnum.CREATE_TIME) {
			ecCount = syncDangDangOrderDataByHandServiceImpl.findOrderCountFromEcByCreateDate(this.scheduleUpdateCheckType,
					storeId,
					startDate, endDate);
			dbCount = pushDangDangOrderDataByHandServiceImpl.findOrderDataCountFromDBByCreateDate(storeId, startDate,
					endDate);
		} else if (orderUseTime == UseTimeEnum.UPDATE_TIME) {
			ecCount = syncDangDangOrderDataByHandServiceImpl.findOrderCountFromEcByModifyDate(this.scheduleUpdateCheckType,
					storeId, 
					startDate, endDate);
			dbCount = pushDangDangOrderDataByHandServiceImpl.findOrderDataCountFromDbByModifyDate(storeId, startDate,
					endDate);
		} else {
			;
		}

		// 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
		checkUpdateService.put(startDate, endDate, scheduleUpdateCheckType,
				loginfoOperateService.newTransaction4SaveCheckServiceRecord(this.getClass().getSimpleName() + "|" + orderUseTime, startDate,
						endDate, ecCount - dbCount, DangdangCommonTool.STORE_TYPE, storeId, scheduleUpdateCheckType));

//		String mappingCode = checkUpdateService.get(startDate, endDate, this.scheduleUpdateCheckType);
		if (ecCount != dbCount) {
			if (orderUseTime == UseTimeEnum.CREATE_TIME) {
				syncDangDangOrderDataByHandServiceImpl.syncOrderDataByCreateDate(storeId, startDate, endDate);
			} else if (orderUseTime == UseTimeEnum.UPDATE_TIME) {
				syncDangDangOrderDataByHandServiceImpl.syncOrderDataByModifyDate(storeId, startDate, endDate);
			} else {
				;
			}
		} 

		// 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
		checkUpdateService.remove(startDate, endDate, scheduleUpdateCheckType);
	}
	
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
//		logger.info("定时任务开始--进入-DangdangTradeUpdateCheckServiceImpl--当当校验数据---方法");
//		try {
//			/**
//			 * 通过分销类型获得分销商的铺货信息
//			 * 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
//			 */
//
//			List<AomsshopT> aomsShopList = aomsShopService.getShopDataByEcType(DangdangCommonTool.STORE_TYPE);
//			if (aomsShopList != null && aomsShopList.size() != 0) {
//				for (int i = 0; i < aomsShopList.size(); i++) {
//					executeUpdateCheckForDataCycleRun(aomsShopList.get(i));
//
//					// 將最晚的訂單更新時間當作下一次的起始時間
//					taskService.updateLastUpdateTime(taskScheduleUpdateCheckConfig.getScheduleType(), endDate);
//				}
//			}
//		} catch (ParseException e) {
//			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//					"DangdangTradeUpdateCheckServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST_CHECK.getValueString(),
//					DateTimeTool.parse(taskScheduleUpdateCheckConfig.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"),
//					"json数据转换异常", e.getMessage(), scheduleUpdateCheckType, "");
//			e.printStackTrace();
//			logger.error("ParseException = {}", e.getMessage());
//			throw e;
//		} catch (Exception e) {
//			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//					"DangdangTradeUpdateCheckServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST_CHECK.getValueString(),
//					DateTimeTool.parse(taskScheduleUpdateCheckConfig.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"), "获取数据异常",
//					e.getMessage(), scheduleUpdateCheckType, "");
//			e.printStackTrace();
//			logger.error("Exception = {}", e.getMessage());
//			throw e;
//		}
//		logger.info("定时任务结束-DangdangTradeUpdateCheckServiceImpl--当当校验数据---方法");
//		return false;
		
		logger.info("定时任务开始--进入-DangdangTradeUpdateCheckServiceImpl--当当校验数据---方法");
		try {
			/**
			 * 通过分销类型获得分销商的铺货信息
			 * 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
			 */
			List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
			if (aomsShopList != null && aomsShopList.size() != 0) {
				for (int i = 0; i < aomsShopList.size(); i++) {
					executeUpdateCheckForDataCycleRun(aomsShopList.get(i));
				}
			}
		} catch (ParseException e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
					"DangdangTradeUpdateCheckServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST_CHECK.getValueString(),
					DateTimeTool.parse(reCycleDate[0]),
					"json数据转换异常", e.getMessage(), scheduleUpdateCheckType, "");
			e.printStackTrace();
			logger.error("ParseException = {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
					"DangdangTradeUpdateCheckServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST_CHECK.getValueString(),
					DateTimeTool.parse(reCycleDate[0]), 
					"获取数据异常", e.getMessage(), scheduleUpdateCheckType, "");
			e.printStackTrace();
			logger.error("Exception = {}", e.getMessage());
			throw e;
		}
		logger.info("定时任务结束-DangdangTradeUpdateCheckServiceImpl--当当校验数据---方法");
		return false;
	}

}
