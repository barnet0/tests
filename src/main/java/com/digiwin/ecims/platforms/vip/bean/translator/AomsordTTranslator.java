package com.digiwin.ecims.platforms.vip.bean.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;
import vipapis.delivery.DvdOrder;
import vipapis.delivery.GetPoSkuListResponse;
import vipapis.delivery.Pick;
import vipapis.delivery.PickDetail;
import vipapis.delivery.PickProduct;
import vipapis.delivery.PurchaseOrderSku;

public class AomsordTTranslator {

	private Object head; // 单头
	private Object detailResponse; // 单身
	private Object externalInfoResponse; // 额外信息

	public AomsordTTranslator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AomsordTTranslator(Object head, Object detailResponse, Object externalInfoResponse) {
		this.head = head;
		this.detailResponse = detailResponse;
		this.externalInfoResponse = externalInfoResponse;
	}
	
	public List<AomsordT> doTranslate(String storeId) {
		if (this.head instanceof Pick) {
			return parseJitOrderToAomsordT(storeId);
		} else if (this.head instanceof DvdOrder) {
			return parseOrderToAomsordT(storeId);
		} else {
			return new ArrayList<AomsordT>();
		}
	}
	
	private List<AomsordT> parseJitOrderToAomsordT(String storeId) {
		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		Pick pick = (Pick) this.head;
		PickDetail pickDetail = (PickDetail) this.detailResponse;
		List<GetPoSkuListResponse> poSkuListResponses = (List<GetPoSkuListResponse>) this.externalInfoResponse;
		
		double d022 = 0;
		for (PickProduct product : pickDetail.getPick_product_list()) {
			AomsordT aomsordT = new AomsordT();
			
			aomsordT.setId(CommonUtil.checkNullOrNot(pick.getPick_no()));
			aomsordT.setAoms003(CommonUtil.checkNullOrNot(pick.getDelivery_status()));
			aomsordT.setAoms004(CommonUtil.checkNullOrNot(pick.getOrder_cate()));
			aomsordT.setAoms006(CommonUtil.checkNullOrNot(pick.getCreate_time()));
			aomsordT.setModified(CommonUtil.checkNullOrNot(pick.getCreate_time()));
			aomsordT.setAoms024(CommonUtil.checkNullOrNot(pick.getCreate_time()));
			
			aomsordT.setAoms025(CommonUtil.checkNullOrNot(pick.getSell_site()));
			aomsordT.setStoreId(storeId);
			aomsordT.setStoreType(VipCommonTool.STORE_TYPE);
			
			aomsordT.setAoms059(CommonUtil.checkNullOrNot(product.getArt_no()));
			aomsordT.setAoms060(CommonUtil.checkNullOrNot(product.getBarcode()));
			aomsordT.setAoms061(CommonUtil.checkNullOrNot(product.getSize()));
			aomsordT.setAoms062(CommonUtil.checkNullOrNot(product.getStock()));
			aomsordT.setAoms063(CommonUtil.checkNullOrNot(product.getProduct_name()));
			String price = this.getActualMarketPriceFromPoSkuList(
					product.getBarcode(), poSkuListResponses);
			if (price == null || price.length() == 0) {
				price = getActualMarketPriceFromPickProductList(
						product.getBarcode(), pickDetail.getPick_product_list());
			}
			if (price == null || price.length() == 0) {
				price = "0.0";
			}
			aomsordT.setAoms064(CommonUtil.checkNullOrNot(price));
			
			Integer i062 = product.getStock();
			Double d064 = Double.parseDouble(aomsordT.getAoms064());
			Double d071 = i062 * d064;
			aomsordT.setAoms071(CommonUtil.checkNullOrNot(d071));
			
			d022 += d071;
			
			Date now = new Date();
			aomsordT.setAomsstatus("0");
			aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
			aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
			
			aomsordTs.add(aomsordT);
		}
		
		for (AomsordT aomsordT : aomsordTs) {
			aomsordT.setAoms022(CommonUtil.checkNullOrNot(d022));
		}
		
		return aomsordTs;
	}
	
	private List<AomsordT> parseOrderToAomsordT(String storeId) {
		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		// TODO
		
		return aomsordTs;
	}
	
	private String getActualMarketPriceFromPoSkuList(String barCode, List<GetPoSkuListResponse> list) {
		String actualMarketPrice = StringUtils.EMPTY;
		for (GetPoSkuListResponse getPoSkuListResponse : list) {
			for (PurchaseOrderSku purchaseOrderSku : getPoSkuListResponse.getPurchase_order_sku_list()) {
				if (purchaseOrderSku.getBarcode().equals(barCode)) {
					actualMarketPrice = purchaseOrderSku.getActual_market_price();
					break;
				}
			}
		}
		return actualMarketPrice;
	}
	
	private String getActualMarketPriceFromPickProductList(String barCode, List<PickProduct> list) {
		String actualMarketPrice = StringUtils.EMPTY;
		for (PickProduct pickProduct : list) {
			if (pickProduct.getBarcode().equals(barCode)) {
				actualMarketPrice = pickProduct.getActual_market_price();
				break;
			}
		}
		return actualMarketPrice;
	}
}
