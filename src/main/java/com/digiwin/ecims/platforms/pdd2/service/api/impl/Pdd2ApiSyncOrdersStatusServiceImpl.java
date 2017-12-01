package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.OrderSearchResult;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.order_sn_list;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderInfoGetResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersStatusService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.OrderStatus;
import com.digiwin.ecims.platforms.pdd2.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class Pdd2ApiSyncOrdersStatusServiceImpl implements Pdd2ApiSyncOrdersStatusService {

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
	public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
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
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		syncOrdersByStatus(taskScheduleConfig, aomsshopT);

	}

	@Override
	public void syncOrdersByStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		// 参数设定
		int pageSize = taskScheduleConfig.getMaxPageSize();

		String storeId = aomsshopT.getAomsshop001();
		String appKey = aomsshopT.getAomsshop004();
		String appSecret = aomsshopT.getAomsshop005();
		String accessToken = aomsshopT.getAomsshop006();

		// 取得时间区间内总资料笔数
		//long totalSize = 0L;
		/*totalSize = pdd2ApiService.pddOrderSearch(appKey, appSecret, accessToken, OrderStatus.PAIED.getValue(),
				Pdd2CommonTool.MIN_PAGE_NO, Pdd2CommonTool.MIN_PAGE_SIZE).getOrderCount();*/
		//totalSize = pdd2ApiService.pddOrderSearch(appKey, appSecret, accessToken, OrderStatus.PAIED.getValue(),
		//null, null).getTotal_count();

		// 區間內有資料， 計算頁數
		//long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		// 針對每一頁(倒序)的所有資料新增
		Integer i=1;
		while(i>0){
			OrderSearchResponse response = pdd2ApiService.pddOrderSearch(appKey, appSecret, accessToken,
					OrderStatus.PAIED.getValue(), (int) i, pageSize);

			loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", Pdd2CommonTool.STORE_TYPE,
					"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.number.list.increment.get 订单查询接口",
					JsonUtil.formatByMilliSecond(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
					taskScheduleConfig.getScheduleType());

			for (order_sn_list orderSnList : response.getOrder_sn_list()) {
				String orderSN = orderSnList.getOrder_sn();
				OrderInfoGetResponse orderInfoGetResponse = pdd2ApiService.pddOrderGet(appKey, appSecret, accessToken,
						orderSN);

				loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", Pdd2CommonTool.STORE_TYPE,
						"[" + UseTimeEnum.CREATE_TIME + "]|pdd.order.information.get 订单详细信息接口",
						JsonUtil.formatByMilliSecond(orderInfoGetResponse),
						SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId, taskScheduleConfig.getScheduleType());

				List<AomsordT> list = new AomsordTTranslator(orderInfoGetResponse).doTranslate(storeId);

				aomsordTs.addAll(list);
			}
			i++;
            if(response.getTotal_count()==0)
            	i=0;
			taskService.newTransaction4Save(aomsordTs);
			// 清空列表，为下一页资料作准备
			aomsordTs.clear();
		}

	}

}
