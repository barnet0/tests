package com.digiwin.ecims.platforms.dangdang.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list.OrdersRefundListResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list.RefundInfo;
import com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet.ItemInfo;
import com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet.OrderInfo;
import com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet.OrdersExchangeReturnListGetResponse;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public class AomsrefundTTranslator {

	public AomsrefundTTranslator() {

	}

	/**
	 * 单笔退換貨查询
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public List<AomsrefundT> doTrans(OrdersExchangeReturnListGetResponse oerlgResponse, String storeId) {
		return doTransToReturnListBean(oerlgResponse, storeId, null);
	}

	/**
	 * 批次退換貨
	 * 
	 * @param
	 * @return
	 */
	public List<AomsrefundT> doTransToReturnListBean(OrdersExchangeReturnListGetResponse oerlgResponse, String storeId, TaskScheduleConfig taskScheduleConfig) {
		List<AomsrefundT> result = new ArrayList<AomsrefundT>();
		for (OrderInfo orderInfo : oerlgResponse.getOrdersList().getOrderInfos()) {
			List<ItemInfo> itemInfos = orderInfo.getItemsList().getItemInfos();
			for (ItemInfo itemInfo : itemInfos) {
				AomsrefundT aomsrefundT = new AomsrefundT();
				aomsrefundT.setId(CommonUtil.checkNullOrNot(orderInfo.getReturnExchangeCode()));
				aomsrefundT.setAoms002(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms003(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms004(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(orderInfo.getOrderMoney()));
				aomsrefundT.setAoms007(CommonUtil.checkNullOrNot(null));
				// 返回值：0--未审核
				// 1——审核通过待处理
				// 2——审核不通过
				// 11——审核通过同意退款
				// 12——审核通过拒绝退款
				// 13——审核通过延期退款
				aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(orderInfo.getReturnExchangeOrdersApprStatus() + orderInfo.getOrderResult()));// aomsord008
				aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));// 是否退货，TRUE=退換貨、FALSE=退款
				aomsrefundT.setAoms010(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms011(CommonUtil.checkNullOrNot(null));
				if (null == taskScheduleConfig) {
					// 單筆查詢用
					aomsrefundT.setModified(CommonUtil.checkNullOrNot(null));
				} else {
					aomsrefundT.setModified(CommonUtil.checkNullOrNot(taskScheduleConfig.getEndDate()));
				}
				aomsrefundT.setAoms013(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms014(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms015(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms016(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms019(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms021(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(itemInfo.getOrderCount()));
				aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(itemInfo.getItemID()));
				aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms025(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms026(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(itemInfo.getOuterItemID()));
				aomsrefundT.setAoms028(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(itemInfo.getUnitPrice()));
				aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(Double.parseDouble(itemInfo.getOrderCount()) * Double.parseDouble(itemInfo.getUnitPrice())));
				aomsrefundT.setAoms031(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms032(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms033(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms034(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(orderInfo.getReturnExchangeOrdersApprStatus() + orderInfo.getOrderResult()));
				aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(orderInfo.getOrderID()));
				aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms040(CommonUtil.checkNullOrNot(null));
				aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(orderInfo.getOrderTime()));
				aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(itemInfo.getReverseDetailReason()));
//				aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(itemInfo.getOneLevelReverseReason() + "&" + itemInfo.getTwoLevelReverseReason())); // mark by mowj 20150812
				// add by mowj 20150812 start
				String aoms043 = "";
				if (itemInfo.getOneLevelReverseReason() != null && itemInfo.getOneLevelReverseReason().trim().length() > 0) {
					aoms043 += itemInfo.getOneLevelReverseReason();
				}
				if (itemInfo.getTwoLevelReverseReason() != null && itemInfo.getTwoLevelReverseReason().trim().length() > 0) {
					aoms043 += "&" + itemInfo.getTwoLevelReverseReason();
				}
				aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(aoms043));
				// add by mowj 20150812 end
				aomsrefundT.setStoreType(DangdangCommonTool.STORE_TYPE);
				aomsrefundT.setStoreId(storeId);
				aomsrefundT.setAoms048(CommonUtil.checkNullOrNot(null));

				String date = CommonUtil.checkNullOrNot(new Date());
				aomsrefundT.setAomsstatus("0");
				aomsrefundT.setAomscrtdt(date);
				aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

				result.add(aomsrefundT);
			}
		}
		return result;
	}

	/**
	 * 單筆、批次退款
	 * 
	 * @param
	 * @return
	 */
	public List<AomsrefundT> doTransToRefundListBean(OrdersRefundListResponse orlResponse, String storeId) {
		List<AomsrefundT> result = new ArrayList<AomsrefundT>();
		for (RefundInfo refundInfo : orlResponse.getRefundInfos().getRefundInfoList().getRefundInfo()) {
			// 退款只寫入退款原因為：1部分发、2:配送失败、7:逆向退运费
			if (!("1".equals(refundInfo.getRefundSource()) || "2".equals(refundInfo.getRefundSource()) || "7".equals(refundInfo.getRefundSource()))) {
				continue;
			}

			AomsrefundT aomsrefundT = new AomsrefundT();
			aomsrefundT.setId(CommonUtil.checkNullOrNot(refundInfo.getOrderId() + "#" + DateTimeTool.format(DateTimeTool.parse(refundInfo.getCreationDate(), "yyyy-MM-dd HH:mm:ss"), "yyyyMMddHHmmss")));
			aomsrefundT.setAoms002(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms003(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms004(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(refundInfo.getRefundAmount()));
			aomsrefundT.setAoms007(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(refundInfo.getIsAgree()));// aomsord008
			aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.FALSE));// 是否退货，TRUE=退換貨、FALSE=退款
			aomsrefundT.setAoms010(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms011(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setModified(CommonUtil.checkNullOrNot(refundInfo.getLastModifiedDate()));
			aomsrefundT.setAoms013(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms014(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms015(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms016(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms019(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms020(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms021(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refundInfo.getOrderId()));
			aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms025(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms026(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms028(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms031(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms032(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms033(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms034(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refundInfo.getIsAgree()));
			aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refundInfo.getOrderId()));
			aomsrefundT.setAoms039(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms040(CommonUtil.checkNullOrNot(null));
			aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refundInfo.getCreationDate()));
			aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refundInfo.getRemark()));
			aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refundInfo.getRefundSource()));
			aomsrefundT.setStoreType(DangdangCommonTool.STORE_TYPE);
			aomsrefundT.setStoreId(storeId);
			aomsrefundT.setAoms048(CommonUtil.checkNullOrNot(null));

			String date = CommonUtil.checkNullOrNot(new Date());
			aomsrefundT.setAomsstatus("0");
			aomsrefundT.setAomscrtdt(date);
			aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

			result.add(aomsrefundT);
		}
		return result;
	}

}
