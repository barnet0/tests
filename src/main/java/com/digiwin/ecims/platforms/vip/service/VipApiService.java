package com.digiwin.ecims.platforms.vip.service;

import java.util.List;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import vipapis.common.Warehouse;
import vipapis.delivery.GetPickListResponse;
import vipapis.delivery.GetPoSkuListResponse;
import vipapis.delivery.PickDetail;

public interface VipApiService extends EcImsApiService {
	
	public CmdRes digiwinVipJitShippingSend(CmdReq cmdReq) throws Exception;
	
	public GetPickListResponse jitDeliveryServiceGetPickList(
			String appKey, String appSecret, String accessToken,
			String vendorId,
			String poNo,
			String pickNo,
			Warehouse warehouse,
			String coMode,
			String orderCate,
			String stCreateTime,
			String etCreateTime,
			String stSellTimeFrom,
			String etSellTimeFrom,
			String stSellTimeTo,
			String etSellTimeTo,
			int isExport,
			int page,
			int limit,
			String storeSn) throws Exception;
	
	public PickDetail jitDeliveryServiceGetPickDetail(
			String appKey, String appSecret, String accessToken,
			String poNo,
			String vendorId,
			String pickNo,
			int page,
			int limit,
			String coMode) throws Exception;
	
	public List<GetPoSkuListResponse> getPoSkuListResponses (
			String scheduleType, int pageSize, AomsshopT aomsshopT, String poNo, String pickNo) throws Exception;
	
	@Deprecated
	public void syncOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, String dateType) throws Exception;

	public void syncJitOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception;
	
	@Deprecated
	public void syncReturnsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, String dateType) throws Exception;
	
	@Deprecated
	public void syncGoodsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, String dateType) throws Exception;

}
