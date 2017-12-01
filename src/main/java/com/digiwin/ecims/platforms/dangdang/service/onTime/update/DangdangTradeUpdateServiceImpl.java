package com.digiwin.ecims.platforms.dangdang.service.onTime.update;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.platforms.dangdang.service.DangdangApiService;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

@Service("dangdangTradeUpdateServiceImpl")
public class DangdangTradeUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {
	private static final Logger logger = LoggerFactory.getLogger(DangdangTradeUpdateServiceImpl.class);

	@Autowired
	private DangdangApiService dangdangAPIService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private LoginfoOperateService loginfoOperateService;
	
	@Autowired
	private AomsShopService aomsShopService;

	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		logger.info("定时任务开始--DangdangTradeUpdateServiceImpl--取得当当交易数据");
		Date sysDate = new Date();
		TaskScheduleConfig taskScheduleConfig = null;
		String scheduleType = null;
		try {
			/**
			 * 通过分销类型获得分销商的订单信息 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
			 */
			List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
			if (aomsShopList != null && aomsShopList.size() != 0) {
				for (int i = 0; i < aomsShopList.size(); i++) {
					AomsshopT aomsshopT = aomsShopList.get(i);
					/*
					 * 以定时任务功能名+分销商的id+分销商类型为定时任务的类型，到定时任务的配置文件中查找该定时任务的配置信息 此处当当的格式为：DangdangTradeUpdate#DZ0004#5
					 */
					scheduleType = "DangdangTradeUpdate#" + aomsshopT.getAomsshop001() + "#" + aomsshopT.getAomsshop003();
					/*
					 * 通过“DangdangTradeUpdate”当当的定时任务计划类型， 获取定时任务的schedule配置， 此配置内存放了该批次的定时任务的最后更新时间，每笔最大更新条数
					 */
					taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
					taskService.newTransaction4SaveLastRunTime(scheduleType, null); //記錄執行時間, add by xavier on 20150830

					// 如果没有设置更新时间，默认从很久以前开始同步
					if (taskScheduleConfig.getLastUpdateTime() == null || "".equals(taskScheduleConfig.getLastUpdateTime())) {
						taskScheduleConfig.setLastUpdateTime(getInitDateTime());
						taskScheduleConfig.setMaxReadRow(2000);
						taskScheduleConfig.setMaxPageSize(20);
						taskService.saveTaskTaskScheduleConfig(taskScheduleConfig);
					}
					logger.info("当前排程:{}, 店铺ID:{}", scheduleType, aomsshopT.getAomsshop001());
					logger.info("获取同步前的上次同步时间{}", taskScheduleConfig.getLastUpdateTime());
					// modiby lizhi 20150726
					// taskScheduleConfig.setEndDate(sysDate);
					taskScheduleConfig.setEndDateByDate(sysDate, 5);
					dangdangAPIService.syncOrdersData(taskScheduleConfig, aomsshopT);

				}
			}
		} catch (ParseException e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), 
					"DangdangTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), 
					DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), 
					"json数据转换异常", e.getMessage(), taskScheduleConfig.getScheduleType(), "");
			e.printStackTrace();
			logger.error("ParseException = {}", e.getMessage());
			throw e;
		} catch (DataException e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), 
					"DangdangTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
					DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), 
					"保存数据异常", e.getSQLException().getMessage(), scheduleType, "");
			e.printStackTrace();
			logger.error("DataException = {}", e.getSQLException().getMessage());
			throw e;
		} catch (Exception e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), 
					"DangdangTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), 
					DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), 
					"获取数据异常", e.getMessage(), taskScheduleConfig.getScheduleType(), "");
			e.printStackTrace();
			logger.error("Exception = {}", e.getMessage());
			throw e;
		}
		logger.info("定时任务结束-DangdangTradeUpdateServiceImpl--取得当当交易数据---方法");
		return false;
	}
}
