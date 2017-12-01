package com.digiwin.ecims.platforms.icbc.bean.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.icbc.bean.base.Product;
import com.digiwin.ecims.platforms.icbc.bean.base.Refund;
import com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery.IcbcRefundQueryResponse;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;

public class IcbcAomsrefundTTranslator {
	private Object obj;
	private String storeId;
	
	public IcbcAomsrefundTTranslator(Object obj,
			String storeid) {
		this.obj = obj;
		this.storeId = storeid;
	}
	
	public List<AomsrefundT> doTranslate(){
		if(obj instanceof IcbcRefundQueryResponse){
			IcbcRefundQueryResponse response = (IcbcRefundQueryResponse)obj;
			if(response == null ||
				response.getBody() == null || 
				response.getBody().getRefundList() == null ||
				response.getBody().getRefundList().getRefundList() == null){
				return null;
			}
			return refundToAomsrefundT(response.getBody().getRefundList().getRefundList());
		}
		
		return null;
	}

	public List<AomsrefundT> refundToAomsrefundT(List<Refund> refundList) {
		List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
		for(Refund refund: refundList){
			if(refund.getProducts().getProductList() != null){
				for(Product product: refund.getProducts().getProductList()){
					AomsrefundT aomsrefundT = new AomsrefundT();
					aomsrefundT.setId(CommonUtil.checkNullOrNot(refund.getRefundId()));
					aomsrefundT.setAoms005(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms006(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms008(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms009(CommonUtil.checkNullOrNot(refund.getRefundProdFlag().equals("1") ? Boolean.TRUE: Boolean.FALSE));//20150728 modi by Shang Hsuan Hsu: 1改true, 0改false
					aomsrefundT.setAoms017(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms018(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms022(CommonUtil.checkNullOrNot(product.getProductNumber()));
					aomsrefundT.setAoms023(CommonUtil.checkNullOrNot(product.getProductSkuId()));
					aomsrefundT.setAoms024(CommonUtil.checkNullOrNot(refund.getOrderId()));
					aomsrefundT.setAoms027(CommonUtil.checkNullOrNot(product.getProductCode()));
					aomsrefundT.setAoms029(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms030(CommonUtil.checkNullOrNot(product.getProductRefundAmount()));
					aomsrefundT.setAoms035(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAoms036(CommonUtil.checkNullOrNot(product.getProductId()));
					aomsrefundT.setAoms037(CommonUtil.checkNullOrNot(refund.getRefundStatus()));
					aomsrefundT.setAoms038(CommonUtil.checkNullOrNot(refund.getOrderId()));
					aomsrefundT.setAoms041(CommonUtil.checkNullOrNot(refund.getRefundTs()));
					aomsrefundT.setAoms042(CommonUtil.checkNullOrNot(product.getRefundDesc()));//20150728 modi by Shang Hsuan Hsu: 和aoms043調換位置
					aomsrefundT.setAoms043(CommonUtil.checkNullOrNot(product.getRefundReason()));//20150728 modi by Shang Hsuan Hsu: 和aoms042調換位置
					aomsrefundT.setStoreId(storeId);
					aomsrefundT.setStoreType(IcbcCommonTool.STORE_TYPE);
					aomsrefundT.setAoms048(CommonUtil.checkNullOrNot(null));
					aomsrefundT.setAomscrtdt(DateTimeTool.format(new Date())); // modify by mowj 20151204
					aomsrefundT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));
					aomsrefundT.setAomsstatus("0");
					
					aomsrefundT.setModified(refund.getRefundConfirmTs()); //因為退貨單沒有修改時間，填審核時間
					
					aomsrefundTs.add(aomsrefundT);
				}
			}
		}
		return aomsrefundTs;
	}

}
