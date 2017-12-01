package com.digiwin.ecims.platforms.dangdang.service.onTime.update;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.platforms.dangdang.bean.AomsrefundTTranslator;
import com.digiwin.ecims.platforms.dangdang.bean.response.DangdangErrorResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list.OrdersRefundListResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet.OrdersExchangeReturnListGetResponse;
import com.digiwin.ecims.platforms.dangdang.service.DangdangApiService;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangClient;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

@Service("dangdangRefundUpdateServiceImpl")
public class DangdangRefundUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {
	private static final Logger logger = LoggerFactory.getLogger(DangdangRefundUpdateServiceImpl.class);

	@Autowired
	private DangdangApiService dangdangAPIService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private LoginfoOperateService loginfoOperateService;

	@Autowired
	private AomsShopService aomsShopService;

	XmlUtils xu = XmlUtils.getInstance();
	
	@Autowired
	private ParamSystemService paramSystemService; // add by mowj 20150928

	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		logger.info("定时任务开始--DangdangRefundUpdateServiceImpl--获取当当退货数据");
		Date sysDate = new Date();
		TaskScheduleConfig taskScheduleConfig = null;
		String scheduleType = null;
		try {
			/**
			 * 通过分销类型获得分销商的退货退款信息 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
			 */
			List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
			if (aomsShopList != null && aomsShopList.size() != 0) {
				for (int i = 0; i < aomsShopList.size(); i++) {
					AomsshopT aomsshopT = aomsShopList.get(i);
					/*
					 * 以定时任务功能名+分销商的id+分销商类型为定时任务的类型，到定时任务的配置文件中查找该定时任务的配置信息 此处当当的格式为：DangdangRefundUpdate#DZ0004#5
					 */
					scheduleType = "DangdangRefundUpdate#" + aomsshopT.getAomsshop001() + "#" + aomsshopT.getAomsshop003();
					/*
					 * 通过“DangdangRefundUpdate”当当的定时任务计划类型， 获取定时任务的schedule配置， 此配置内存放了该批次的定时任务的最后更新时间，每笔最大更新条数
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
					taskScheduleConfig.setEndDateByDate(sysDate, 15);
					dangdangAPIService.syncReturnListAndRefundsData(taskScheduleConfig, aomsshopT);

					/* 处理退货单的更新 */
					// 處理退换货订单狀態不為最終狀態訂單
					doOrdersExchangeReturnListGet(taskScheduleConfig, aomsshopT);// 查询退换货订单列表信息
					// 處理退款订单態不為最終狀態訂單
					doOrdersRefundList(aomsshopT);// 查询商家退款订单列表信息

				}
			}
		} catch (ParseException e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), 
					"DangdangRefundUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), 
					DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), 
					"json数据转换异常", e.getMessage(), taskScheduleConfig.getScheduleType(), "");
			e.printStackTrace();
			logger.error("ParseException = {}", e.getMessage());
			throw e;
		} catch (DataException e) {
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), 
					"DangdangRefundUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
					DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), 
					"保存数据异常", e.getSQLException().getMessage(), scheduleType, "");
			e.printStackTrace();
			logger.error("DataException = {}", e.getSQLException().getMessage());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), 
					"DangdangRefundUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), 
					DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), 
					"获取数据异常", e.getMessage(), taskScheduleConfig.getScheduleType(), "");
			logger.error("Exception = {}", e.getMessage());
			throw e;
		}
		logger.info("定时任务结束--DangdangRefundUpdateServiceImpl--获取当当退货数据");
		return false;
	}

	// 处理退换货订单狀態不為最终状态订单
	private void doOrdersExchangeReturnListGet(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		// 从中台DB中取出退换货状态不为：2——审核不通过、11——审核通过同意退款、12——审核通过拒绝退款
		List<Object> orderIds = taskService.executeQueryByHql(" SELECT DISTINCT t.aoms038 FROM AomsrefundT t WHERE t.storeType = '5' AND t.aoms009 = 'TRUE' AND t.aoms008 != '2' AND t.aoms008 != '11' AND t.aoms008 != '12'");

		// 每筆中台退貨單..
		String modiDateStr = DateTimeTool.format(new Date());// 若資料有修改過，以此為修改時間
		OrdersExchangeReturnListGetResponse oerlgResponse = null;
		try {
			for (Object obj : orderIds) {
				// 調用API
				DangdangClient client = new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API), aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
				Map<String, String> formMap = new HashMap<String, String>();
				formMap.put("o", obj.toString());// 订单编号，一次一个订单信息
				formMap.put("isNeedReverseReason", "1");// 如果为“1”则表示会返回商品的退换货原因，如果为0或者其他值则表示不返回
				String resultClient = client.execute("dangdang.orders.exchange.return.list.get", formMap);
				if (resultClient.contains("errorResponse")) {
					DangdangErrorResponse errorResponse = (DangdangErrorResponse) xu.xml2JavaBean(resultClient, DangdangErrorResponse.class);
					throw new Exception("处理批次退货单不为最终状态时异常，ErrorCode=" + errorResponse.getErrorCode() + "，ErrorMessage=" + errorResponse.getErrorMessage());
				} else {
					oerlgResponse = (OrdersExchangeReturnListGetResponse) xu.xml2JavaBean(resultClient, OrdersExchangeReturnListGetResponse.class);
					List<AomsrefundT> AomsrefundTList = new AomsrefundTTranslator().doTransToReturnListBean(oerlgResponse, aomsshopT.getAomsshop001(), taskScheduleConfig);
					for (AomsrefundT aomsrefundT : AomsrefundTList) {
						aomsrefundT.setModified(modiDateStr);
					}
					taskService.newTransaction4Save(AomsrefundTList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// 处理退款订单状态不为最终状态订单
	private void doOrdersRefundList(AomsshopT aomsshopT) throws Exception {
		// 仅退款 从中台DB中取出退款状态为：1
		List<Object[]> orderIds = taskService.executeQueryByHql(" select distinct t.aoms038, t.aoms041 FROM AomsrefundT t WHERE t.storeType = '5' AND t.aoms009 = 'FALSE' AND t.aoms008 = '1' ");

		// 每笔中台退款单
		OrdersRefundListResponse orlResponse = null;
		try {
			for (Object[] objArray : orderIds) {
				// 调用API
				DangdangClient client = new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API), aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
				Map<String, String> formMap = new HashMap<String, String>();
				formMap.put("o", objArray[0].toString());
				formMap.put("osd", objArray[1].toString());// 退款单开始日期
				formMap.put("oed", objArray[1].toString());// 退款单结束日期
				String resultClient = client.execute("dangdang.orders.refund.list", formMap);
				orlResponse = (OrdersRefundListResponse) xu.xml2JavaBean(resultClient, OrdersRefundListResponse.class);
				if ("0".equals(orlResponse.getResultCode()) && !"0".equals(orlResponse.getRefundInfos().getTotalSize())) {
					// 操作成功
					List<AomsrefundT> AomsrefundTList = new AomsrefundTTranslator().doTransToRefundListBean(orlResponse, aomsshopT.getAomsshop001());
					taskService.newTransaction4Save(AomsrefundTList);
				} else {
					throw new Exception("处理批次退款单不为最终状态时异常，ResultCode=" + orlResponse.getResultCode() + "，ResultMessage=" + orlResponse.getResultMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
