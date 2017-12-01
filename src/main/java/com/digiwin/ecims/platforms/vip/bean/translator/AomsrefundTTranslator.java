package com.digiwin.ecims.platforms.vip.bean.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;
import vipapis.vreturn.GetReturnDetailResponse;
import vipapis.vreturn.ReturnDeliveryDetail;
import vipapis.vreturn.ReturnDeliveryInfo;
import vipapis.vreturn.ReturnInfo;

public class AomsrefundTTranslator {

	private Object head;
	private Object detailResponse;
	
	public AomsrefundTTranslator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AomsrefundTTranslator(Object head, Object detailResponse) {
		this.head = head;
		this.detailResponse = detailResponse;
	}
	
	public List<AomsrefundT> doTranslate(String storeId) {
		if (this.head instanceof ReturnInfo) {
			return parseReturnInfoAndDetailToAomsrefundT(storeId);
		} else {
			return new ArrayList<AomsrefundT>();
		}
	}
	
	private List<AomsrefundT> parseReturnInfoAndDetailToAomsrefundT(String storeId) {
		List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
		ReturnInfo returnInfo = (ReturnInfo) this.head;
		GetReturnDetailResponse returnDetailResponse = (GetReturnDetailResponse) this.detailResponse;
		
		for (ReturnDeliveryInfo returnDeliveryInfo : returnDetailResponse.getReturnDeliveryInfos()) {
			for (ReturnDeliveryDetail returnDeliveryDetail : returnDeliveryInfo.getDelivery_list()) {
				AomsrefundT aomsrefundT = new AomsrefundT();
				
				aomsrefundT.setId(CommonUtil.checkNullOrNot(returnInfo.getReturn_sn()));
				aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(returnDeliveryDetail.getPo_no()));
				aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(returnInfo.getSelf_reference()));
				aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(returnDeliveryDetail.getQty()));
				aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(returnDeliveryDetail.getBarcode()));
				
				aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(returnDeliveryDetail.getPo_no()));
				aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(returnDeliveryInfo.getOut_time()));
				aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(returnInfo.getReturn_type()));
				
				aomsrefundT.setStoreId(storeId);
				aomsrefundT.setStoreType(VipCommonTool.STORE_TYPE);
				
				Date now = new Date();
				aomsrefundT.setAomsstatus("0");
				aomsrefundT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
				aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
			}
		}
		
		return aomsrefundTs;
	}
}
