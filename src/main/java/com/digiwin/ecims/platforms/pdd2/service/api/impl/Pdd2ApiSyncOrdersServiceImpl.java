package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.order_sn_list;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderIncrementSearchResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderInfoGetResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.LuckyFlag;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.OrderStatus;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.RefundStatus;
import com.digiwin.ecims.platforms.pdd2.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class Pdd2ApiSyncOrdersServiceImpl implements Pdd2ApiSyncOrdersService {

	private static final Logger logger = LoggerFactory.getLogger(Pdd2ApiSyncOrdersServiceImpl.class);

	@Autowired
	private LoginfoOperateService loginfoOperateService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private Pdd2ApiService pdd2ApiService;

	@Autowired
	private AomsShopService aomsShopService;

	@Override
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		syncOrdersByIncremental(taskScheduleConfig, aomsshopT);

	}

	@Override
	public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
			throws Exception {
		// 参数设定
		Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
		String sDate = taskScheduleConfig.getLastUpdateTime();
		String eDate = taskScheduleConfig.getEndDate();
		int pageSize = taskScheduleConfig.getMaxPageSize();

		String storeId = aomsshopT.getAomsshop001();
		String appKey = aomsshopT.getAomsshop004();
		String appSecret = aomsshopT.getAomsshop005();
		String accessToken = aomsshopT.getAomsshop006();

		String start_updated_at = DateTimeTool.parseToDateAndFormatToTimestamp(sDate);
		String end_update_at = DateTimeTool.parseToDateAndFormatToTimestamp(eDate);

		// 30分钟内
		String sDate30 = DateTimeTool.format(DateTimeTool.getAfterMins(sDate, 30), "yyyy-MM-dd HH:mm:ss");
		end_update_at = DateTimeTool
				.parseToDateAndFormatToTimestamp(DateTimeTool.isBefore(eDate, sDate30) ? eDate : sDate30);

		// 取得时间区间内总资料笔数
		long totalSize = 0L;
		totalSize = pdd2ApiService.pddOrderIncrementSearch(appKey, appSecret, accessToken, LuckyFlag.COMMON.getValue(),
				OrderStatus.PAIED.getValue(), RefundStatus.ALL.getValue(), start_updated_at, end_update_at,
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

		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		// 針對每一頁(倒序)的所有資料新增
		for (long i = pageNum; i > 0; i--) {
			OrderIncrementSearchResponse response = pdd2ApiService.pddOrderIncrementSearch(appKey, appSecret,
					accessToken, LuckyFlag.COMMON.getValue(), OrderStatus.PAIED.getValue(), RefundStatus.ALL.getValue(),
					start_updated_at, end_update_at, (int) i, pageSize);

			loginfoOperateService.newTransaction4SaveSource(sDate, eDate, Pdd2CommonTool.STORE_TYPE,
					"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.number.list.increment.get 订单增量查询接口",
					JsonUtil.formatByMilliSecond(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
					taskScheduleConfig.getScheduleType());

			for (order_sn_list orderSearchResult : response.getOrder_sn_list()) {
				String orderSN = orderSearchResult.getOrder_sn();
				OrderInfoGetResponse orderInfoGetResponse = pdd2ApiService.pddOrderGet(appKey, appSecret, accessToken,
						orderSN);

				loginfoOperateService.newTransaction4SaveSource(sDate, eDate, Pdd2CommonTool.STORE_TYPE,
						"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.information.get 订单详细信息接口",
						JsonUtil.formatByMilliSecond(orderInfoGetResponse),
						SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId, taskScheduleConfig.getScheduleType());

				List<AomsordT> list = new AomsordTTranslator(orderInfoGetResponse).doTranslate(storeId);

				if (DateTimeTool.parse(orderInfoGetResponse.getOrder_info().getCreated_time()).after(lastUpdateTime)) {
					lastUpdateTime = DateTimeTool.parse(orderInfoGetResponse.getOrder_info().getCreated_time());
				}

				aomsordTs.addAll(list);
			}
			taskService.newTransaction4Save(aomsordTs);
			// 清空列表，为下一页资料作准备
			aomsordTs.clear();
		}

		// 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
		// 排程.
		if (taskScheduleConfig.isReCycle()) {
			// process empty, 主要是為好閱讀

		} else {
			// 更新updateTime 成為下次的sDate
			logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
			taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
					DateTimeTool.format(lastUpdateTime));
		}

		return true;
	}

	@Override
	public Long syncOrdersByCreated(String sDate, String eDate, String storeId, String scheduleType) throws Exception {
		// 参数设定
		int pageSize = Pdd2CommonTool.DEFAULT_PAGE_SIZE;
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);
		String appKey = esv.getAppKey();
		String appSecret = esv.getAppSecret();
		String accessToken = esv.getAccessToken();

		// 30分钟内
		String sDate30 = DateTimeTool.format(DateTimeTool.getAfterMins(sDate, 30), "yyyy-MM-dd HH:mm:ss");
		String end_update_at = DateTimeTool
				.parseToDateAndFormatToTimestamp(DateTimeTool.isBefore(eDate, sDate30) ? eDate : sDate30);
		String start_updated_at = DateTimeTool.parseToDateAndFormatToTimestamp(sDate);

		// 取得时间区间内总资料笔数
		long totalSize = 0L;
		totalSize = pdd2ApiService.pddOrderIncrementSearch(appKey, appSecret, accessToken, LuckyFlag.COMMON.getValue(),
				OrderStatus.PAIED.getValue(), RefundStatus.ALL.getValue(), start_updated_at, end_update_at,
				Pdd2CommonTool.MIN_PAGE_NO, Pdd2CommonTool.MIN_PAGE_SIZE).getTotal_count();

		// 區間內有資料， 計算頁數
		long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		// 針對每一頁(倒序)的所有資料新增
		for (long i = pageNum; i >0; i--) {
			OrderIncrementSearchResponse response = pdd2ApiService.pddOrderIncrementSearch(appKey, appSecret,
					accessToken, LuckyFlag.COMMON.getValue(), OrderStatus.PAIED.getValue(), RefundStatus.ALL.getValue(),
					start_updated_at, end_update_at, (int) i, pageSize);

			loginfoOperateService.newTransaction4SaveSource(sDate, eDate, Pdd2CommonTool.STORE_TYPE,
					"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.number.list.increment.get 订单增量查询接口",
					JsonUtil.formatByMilliSecond(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
					scheduleType);

			// 防止在第一次统计订单总数后，翻页过程中订单数量有变化，导致翻页超过实际数量
			if (response.getTotal_count() == 0) {
				break;
			}

			for (order_sn_list orderSearchResult : response.getOrder_sn_list()) {
				String orderSN = orderSearchResult.getOrder_sn();
				OrderInfoGetResponse orderInfoGetResponse = pdd2ApiService.pddOrderGet(appKey, appSecret, accessToken,
						orderSN);

				loginfoOperateService.newTransaction4SaveSource(sDate, eDate, Pdd2CommonTool.STORE_TYPE,
						"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.information.get 订单详细信息接口",
						JsonUtil.formatByMilliSecond(orderInfoGetResponse),
						SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId, scheduleType);

				List<AomsordT> list = new AomsordTTranslator(orderInfoGetResponse).doTranslate(storeId);

				aomsordTs.addAll(list);
			}
			taskService.newTransaction4Save(aomsordTs);
			// 清空列表，为下一页资料作准备
			aomsordTs.clear();
		}

		return totalSize;
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
	public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		// 参数设定
		Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
		String sDate = taskScheduleConfig.getLastUpdateTime();
		String eDate = taskScheduleConfig.getEndDate();
		int pageSize = taskScheduleConfig.getMaxPageSize();

		String storeId = aomsshopT.getAomsshop001();
		String appKey = aomsshopT.getAomsshop004();
		String appSecret = aomsshopT.getAomsshop005();
		String accessToken = aomsshopT.getAomsshop006();

		String start_updated_at = DateTimeTool.parseToDateAndFormatToTimestamp(sDate);
		String end_update_at = DateTimeTool.parseToDateAndFormatToTimestamp(eDate);

		// 30分钟内
		String sDate30 = DateTimeTool.format(DateTimeTool.getAfterMins(sDate, 30), "yyyy-MM-dd HH:mm:ss");
		end_update_at = DateTimeTool
				.parseToDateAndFormatToTimestamp(DateTimeTool.isBefore(eDate, sDate30) ? eDate : sDate30);

		// 取得时间区间内总资料笔数
		long totalSize = 0L;
		totalSize = pdd2ApiService.pddOrderIncrementSearch(appKey, appSecret, accessToken, LuckyFlag.COMMON.getValue(),
				OrderStatus.PAIED.getValue(), RefundStatus.ALL.getValue(), start_updated_at, end_update_at,
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

		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		// 針對每一頁(倒序)的所有資料新增
		for (long i = pageNum; i >0; i--) {
			OrderIncrementSearchResponse response = pdd2ApiService.pddOrderIncrementSearch(appKey, appSecret,
					accessToken, LuckyFlag.COMMON.getValue(), OrderStatus.PAIED.getValue(), RefundStatus.ALL.getValue(),
					start_updated_at, end_update_at, (int) i, pageSize);

			loginfoOperateService.newTransaction4SaveSource(sDate, eDate, Pdd2CommonTool.STORE_TYPE,
					"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.number.list.increment.get 订单增量查询接口",
					JsonUtil.formatByMilliSecond(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
					taskScheduleConfig.getScheduleType());

			for (order_sn_list orderSearchResult : response.getOrder_sn_list()) {
				String orderSN = orderSearchResult.getOrder_sn();
				OrderInfoGetResponse orderInfoGetResponse = pdd2ApiService.pddOrderGet(appKey, appSecret, accessToken,
						orderSN);

				loginfoOperateService.newTransaction4SaveSource(sDate, eDate, Pdd2CommonTool.STORE_TYPE,
						"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.information.get 订单详细信息接口",
						JsonUtil.formatByMilliSecond(orderInfoGetResponse),
						SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId, taskScheduleConfig.getScheduleType());

				List<AomsordT> list = new AomsordTTranslator(orderInfoGetResponse).doTranslate(storeId);

				if (DateTimeTool.parse(orderInfoGetResponse.getOrder_info().getCreated_time()).after(lastUpdateTime)) {
					lastUpdateTime = DateTimeTool.parse(orderInfoGetResponse.getOrder_info().getCreated_time());
				}

				aomsordTs.addAll(list);
			}
			taskService.newTransaction4Save(aomsordTs);
			// 清空列表，为下一页资料作准备
			aomsordTs.clear();
		}

		// 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
		// 排程.
		if (taskScheduleConfig.isReCycle()) {
			// process empty, 主要是為好閱讀

		} else {
			// 更新updateTime 成為下次的sDate
			logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
			taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
					DateTimeTool.format(lastUpdateTime));
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digiwin.ecims.core.api.EcImsApiSyncOrdersService#
	 * getCreatedOrdersCount(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 * 拼多多目前统计时间段内订单数量，只能一页一页去请求，然后做加法来统计。 另外，在第一次统计完后，订单数量可能会改变。
	 * 所以在后面翻页请求时，需要添加对数量为0的判断，如果为0，表示页数大于实际数量，就应该退出循环。
	 */
	@Override
	public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		
		return null;
	}

	@Override
	public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
		// 參數設定
		/*
		 * ESVerification esVerification =
		 * aomsShopService.getAuthorizationByStoreId(storeId); String appKey =
		 * esVerification.getAppKey(); String appSecret =
		 * esVerification.getAppSecret(); String accessToken =
		 * esVerification.getAccessToken();
		 * 
		 * OrderGetResponse response = pdd2ApiService.pddOrderGet( appKey,
		 * appSecret, accessToken, orderId);
		 * 
		 * loginfoOperateService.newTransaction4SaveSource( "N/A", "N/A",
		 * Pdd2CommonTool.STORE_TYPE, "mGetOrder 订单详细信息接口",
		 * JsonUtil.format(response),
		 * SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId, null);
		 * 
		 * List<AomsordT> aomsordTs = new
		 * AomsordTTranslator(response).doTranslate(storeId);
		 * 
		 * taskService.newTransaction4Save(aomsordTs);
		 */

		return true;
	}

}
