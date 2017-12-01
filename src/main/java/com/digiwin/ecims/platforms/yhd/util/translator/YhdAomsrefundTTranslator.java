package com.digiwin.ecims.platforms.yhd.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yhd.object.order.RefundOrderInfo;
import com.yhd.object.order.RefundOrderInfoList;
import com.yhd.object.refund.Refund;
import com.yhd.object.refund.RefundDetail;
import com.yhd.object.refund.RefundInfoMsg;
import com.yhd.object.refund.RefundItem;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTime;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

/**
 * 
 * @author ShangHsuan Hsu 20150721 create
 *
 */
public class YhdAomsrefundTTranslator {
	private Object obj;
	private String storeId;
	private Refund refund;

	public YhdAomsrefundTTranslator(Object obj, String storeId) {
		super();
		this.obj = obj;
		this.storeId = storeId;
	}

	public YhdAomsrefundTTranslator(Object obj, Refund refund, String storeId) {
		super();
		this.obj = obj;
		this.storeId = storeId;
		this.refund = refund;
	}

	public List<AomsrefundT> doTranslate() {
		RefundInfoMsg res = (RefundInfoMsg) obj;
		RefundDetail head = res.getRefundDetail();
		List<RefundItem> items = res.getRefundItemList().getRefundItem();
		List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();

		for (RefundItem item : items) {
			AomsrefundT aomsrefundT = new AomsrefundT();
			aomsrefundT.setId(CommonUtil.checkNullOrNot(head.getRefundCode()));
			aomsrefundT.setAoms005(null);
			Double aoms006 = head.getProductAmount() + head.getDeliveryFee();
			aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(aoms006));
						
			aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(head.getRefundStatus()));
			aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.TRUE));
			aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(head.getReceiverName()));
			aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(head.getExpressName()));
			aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(item.getOriginalRefundNum()));
			/*
			 * modify by lizhi 20150725
			 * 此字段改为联合主键,044,023,001,三个必须唯一确定一条记录
			 */
			aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(item.getProductId()));
			aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(item.getOrderItemId()));
			aomsrefundT.setAoms027(null);
			aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(item.getOrderItemPrice()));
			
			aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(head.getExpressNbr()));
			aomsrefundT.setAoms036(null);
			aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(head.getRefundStatus()));
			aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(head.getOrderId()));
			aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(head.getApplyDate()));
			aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(head.getRefundProblem()));
			aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(head.getReasonMsg()));
			aomsrefundT.setStoreType(YhdCommonTool.STORE_TYPE);// 2-一號店
			aomsrefundT.setStoreId(storeId);
			aomsrefundT.setAoms048(null);
//			aomsrefundT.setModified(refund.getUpdateTime());
			aomsrefundT.setModified(getModifiedTime(head));

			// setTimeAndStatus(aomsrefundT);

			aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date())); // add by mowj 20150818
			aomsrefundT.setAomsstatus("0");
			aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

			aomsrefundTs.add(aomsrefundT);
		}

		return aomsrefundTs;
	}
	
	public List<AomsrefundT> doTranslateAbNormal(){
		List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
		
		RefundOrderInfoList res = (RefundOrderInfoList) obj;
		List<RefundOrderInfo> refunds = res.getRefundOrderInfo();

		for (RefundOrderInfo refund : refunds) {
			AomsrefundT aomsrefundT = new AomsrefundT();
			aomsrefundT.setId(CommonUtil.checkNullOrNot(refund.getRefundOrderCode()));
//			System.out.println(refund.getRefundOrderCode());
			aomsrefundT.setAoms005(null);
			aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(refund.getRefundAmount()));
			
			// add by mowj 20150828
			String refundStatus = refund.getRefundStatus();
			if (refundStatus.equals(YhdCommonTool.RefundOrderStatus.CANCELLED.getDescription())) {
				refundStatus = YhdCommonTool.RefundOrderStatus.CANCELLED.getCode() + "";
			} else if (refundStatus.equals(YhdCommonTool.RefundOrderStatus.REFUNDED.getDescription())) {
				refundStatus = YhdCommonTool.RefundOrderStatus.REFUNDED.getCode() + "";
			} else if (refundStatus.equals(YhdCommonTool.RefundOrderStatus.REFUSED.getDescription())) {
				refundStatus = YhdCommonTool.RefundOrderStatus.REFUSED.getCode() + "";
			} else if (refundStatus.equals(YhdCommonTool.RefundOrderStatus.TO_BE_CHECKED.getDescription())) {
				refundStatus = YhdCommonTool.RefundOrderStatus.TO_BE_CHECKED.getCode() + "";
			} else {
				;
			}
			
			aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(refundStatus)); // modi by mowj 20150828
			aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(Boolean.FALSE));

			/*
			 * modify by lizhi 20150725
			 * 此字段改为联合主键,044,023,001,三个必须唯一确定一条记录
			 */
			aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(refund.getRefundOrderCode()));
			aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refundStatus)); // modi by mowj 20150828
			aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refund.getOrderCode()));
			aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refund.getApplyDate()));
			aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(refund.getRemarkes()));
			aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(refund.getReasonMsg()));
			aomsrefundT.setStoreType(YhdCommonTool.STORE_TYPE);// 2-一號店
			aomsrefundT.setStoreId(storeId);
			
//			aomsrefundT.setModified(refund.getUpdateTime());
			aomsrefundT.setModified(CommonUtil.checkNullOrNot(refund.getApprovalDate()));

			// setTimeAndStatus(aomsrefundT);

			aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date())); // add by mowj 20150818
			aomsrefundT.setAomsstatus("0");
			aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

			aomsrefundTs.add(aomsrefundT);
		}

		
		return aomsrefundTs;
	}

	/**获取退款详情中的最新时间
	 * @param refundHead
	 * @return
	 * @author 维杰
	 * @since 2015.07.25
	 */
	private String getModifiedTime(RefundDetail refundHead) {
		String[] dateArrays = { refundHead.getApplyDate(),
				refundHead.getApproveDate(), refundHead.getSendBackDate(),
				refundHead.getRejectDate(), refundHead.getCancelTime() };
		String latestDateString = dateArrays[0];
		Date latestDate = DateTimeTool.parse(latestDateString);
		for (int i = 1; i < dateArrays.length; i++) {
			String tmpDateString = dateArrays[i];
			if (dateArrays[i] != null && !tmpDateString.trim().equals("")) {
				Date tmpDate = DateTimeTool.parse(dateArrays[i]);
				if (tmpDate.after(latestDate)) {
					latestDate = tmpDate;
				}
			}
		}
		
		return DateTime.toString(latestDate);
	}
	
	// private void setTimeAndStatus(AomsrefundT aomsrefundT) {
	// String key = aomsrefundT.getId() + "_" + aomsrefundT.getStoreType();
	// JedisUtils jedisUtils = new JedisUtils();
	// if (jedisUtils.isExist(key)) {
	// aomsrefundT.setAomscrtdt(jedisUtils.getRedis(key));
	// // aomsordT.setAomsordstatus("2");// 存在更新
	// } else {
	// aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date()));
	// // aomsordT.setAomsordstatus("0");// 不存在新增
	// jedisUtils.setRedis(key, DateTime.toString(new Date()));
	// }
	// aomsrefundT.setAomsstatus("0");
	// aomsrefundT.setAomsmoddt(CommonUtil.checkNullOrNot(new Date()));
	//
	// }
}
