package com.digiwin.ecims.platforms.dangdang.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.ItemInfo;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.OperateInfo;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.OrderDetailsGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.SendGoodsInfo;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.util.MySpringContext;

public class AomsordTTranslator {

	private StandardAreaService standardAreaService = MySpringContext.getContext().getBean(StandardAreaService.class);

	public AomsordTTranslator() {

	}

	/**
	 * 单笔订单查询
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public List<AomsordT> doTrans(OrderDetailsGetResponse orderDetailsGetResponse, String storeId) {
		return doTransToAomsordTBean(orderDetailsGetResponse, storeId, null);
	}

	public List<AomsordT> doTransToAomsordTBean(OrderDetailsGetResponse odgResponse, String storeId, String orderTimeStart) {
		List<AomsordT> result = new ArrayList<AomsordT>();
		// Set<String> orderIdSet = new HashSet<String>();
		Map<String, AomsordT> aomsordTMap = new HashMap<String, AomsordT>();
		
		String orderIdKey = "";
		Map<String, String> prodItemInfoMap = new HashMap<String, String>(); // modi by mowj 20151020
		
		// add by mowj 20151020
		String currentItemId = "";
		
		for (ItemInfo itemInfo : odgResponse.getItemsList().getItemInfos()) {
			orderIdKey = odgResponse.getOrderID() + DangdangCommonTool.STORE_TYPE + itemInfo.getItemID();
			// AomsordT aomsordT = new AomsordT();
			AomsordT aomsordT = null;

			// start id + StoreType + ItemID会有重复，以下栏位需特殊加总
			// 062=sum（ItemInfo.orderCount）
			// 064=ItemInfo.orderCount*ItemInfo.marketPrice
			// 071=sum（ItemInfo.orderCount*ItemInfo.unitPrice）
			// 069=sum（ItemInfo.orderCount*ItemInfo.marketPrice）-sum（ItemInfo.orderCount*ItemInfo.unitPrice）
			int aoms062 = Integer.parseInt(itemInfo.getOrderCount());
			double aoms064 = Double.parseDouble(itemInfo.getMarketPrice());
			double aoms069 = Double.parseDouble(itemInfo.getOrderCount()) * (Double.parseDouble(itemInfo.getMarketPrice()) - Double.parseDouble(itemInfo.getUnitPrice()));
			double aoms071 = Double.parseDouble(itemInfo.getOrderCount()) * Double.parseDouble(itemInfo.getUnitPrice());
			if (null == aomsordTMap.get(orderIdKey)) {
				aomsordT = new AomsordT();
				aomsordT.setAoms062(CommonUtil.checkNullOrNot(aoms062));// aomsord062
				aomsordT.setAoms064(CommonUtil.checkNullOrNot(aoms064));// aomsord064
				aomsordT.setAoms069(CommonUtil.checkNullOrNot(aoms069));// aomsord069
				aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));// aomsord071
			} else {
				aomsordT = aomsordTMap.get(orderIdKey);
				aomsordT.setAoms062(CommonUtil.checkNullOrNot(Integer.parseInt(aomsordT.getAoms062()) + aoms062));// aomsord062
				aomsordT.setAoms064(CommonUtil.checkNullOrNot(aoms064));// aomsord064
				aomsordT.setAoms069(CommonUtil.checkNullOrNot(Double.parseDouble(aomsordT.getAoms069()) + aoms069));// aomsord069
				aomsordT.setAoms071(CommonUtil.checkNullOrNot(Double.parseDouble(aomsordT.getAoms071()) + aoms071));// aomsord071
				aomsordTMap.put(orderIdKey, aomsordT);
				continue;
			}
			// end

			aomsordT.setId(CommonUtil.checkNullOrNot(odgResponse.getOrderID())); // aomsord001
			aomsordT.setAoms002(CommonUtil.checkNullOrNot(null)); // aomsord002
			aomsordT.setAoms003(CommonUtil.checkNullOrNot(odgResponse.getOrderState()));// aomsord003
			aomsordT.setAoms004(CommonUtil.checkNullOrNot(null));// aomsord004
			aomsordT.setAoms005(CommonUtil.checkNullOrNot(null));// aomsord005
//			aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderTimeStart));// aomsord006 // mark by mowj 20150818
			
			// add by mowj 20150818 start
			String orderCreateTime = CommonUtil.checkNullOrNot(orderTimeStart);
			orderCreateTime = "".equals(orderCreateTime) ? getOrderCreateOperateTime(odgResponse) : orderCreateTime;
			aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderCreateTime));// aomsord006
			// add by mowj 20150818 end
			
			aomsordT.setModified(CommonUtil.checkNullOrNot(odgResponse.getLastModifyTime()));// aomsord007
			aomsordT.setAoms008(CommonUtil.checkNullOrNot(null));// aomsord008
			aomsordT.setAoms009(CommonUtil.checkNullOrNot(null));// aomsord009
			aomsordT.setAoms010(CommonUtil.checkNullOrNot(odgResponse.getMessage()));// aomsord010
			aomsordT.setAoms011(CommonUtil.checkNullOrNot(null));// aomsord011
			aomsordT.setAoms012(CommonUtil.checkNullOrNot(odgResponse.getRemark()));// aomsord012
			aomsordT.setAoms013(CommonUtil.checkNullOrNot(null));// aomsord013
			aomsordT.setAoms014(CommonUtil.checkNullOrNot(null));// aomsord014
			aomsordT.setAoms015(CommonUtil.checkNullOrNot(null));// aomsord015
			aomsordT.setAoms016(CommonUtil.checkNullOrNot(null));// aomsord016
			aomsordT.setAoms017(CommonUtil.checkNullOrNot(null));// aomsord017
			aomsordT.setAoms018(CommonUtil.checkNullOrNot(null));// aomsord018
			aomsordT.setAoms019(CommonUtil.checkNullOrNot(null));// aomsord019
			// 网银优惠金额+促销优惠金额	modi by mowj 20150817 去除移动端优惠金额，放到aoms078，作为平台优惠（水星提出）
			// /*+移动端优惠金额 */ mark by mowj 20150817
			// +礼品券	add by mowj 20150817 礼品券作为商家优惠，而不是平台优惠，所以移到aomsord020（水星提出）
			aomsordT.setAoms020(
					CommonUtil.checkNullOrNot(Double.parseDouble(odgResponse.getBuyerInfo().getDeductAmount()) 
							+ Double.parseDouble(odgResponse.getBuyerInfo().getPromoDeductAmount()) 
//							+ Double.parseDouble(odgResponse.getBuyerInfo().getActivityDeductAmount()) // mark by mowj 20150817
							+ Double.parseDouble(odgResponse.getBuyerInfo().getGiftCertMoney()) // add by mowj 20150817
							));// aomsord020
			aomsordT.setAoms021(CommonUtil.checkNullOrNot(null));// aomsord021
			// 客户需支付现金+礼品卡
			// /*+礼品劵*/ mark and modi by mowj 20150817 礼品券作为商家优惠，而不是平台优惠，所以移到aomsord020（水星提出）
			// +积分+账户余额支付
			// +移动端优惠 add by mowj 20150818 补上8.17遗漏的。因为移动端变成了 平台优惠，所以是需要支付给商家的
			aomsordT.setAoms022(CommonUtil.checkNullOrNot(
					Double.parseDouble(odgResponse.getBuyerInfo().getTotalBarginPrice()) 
					+ Double.parseDouble(odgResponse.getBuyerInfo().getGiftCardMoney()) 
//					+ Double.parseDouble(odgResponse.getBuyerInfo().getGiftCertMoney()) // mark by mowj 20150817  
					+ Double.parseDouble(odgResponse.getBuyerInfo().getPointDeductionAmount()) 
					+ Double.parseDouble(odgResponse.getBuyerInfo().getAccountBalance()) 
					+ Double.parseDouble(odgResponse.getBuyerInfo().getActivityDeductAmount()) // add by mowj 20150818 补上8.17遗漏的
					));// aomsord022
			aomsordT.setAoms023(CommonUtil.checkNullOrNot(odgResponse.getBuyerInfo().getBuyerPayMode()));// aomsord023
			// modi by mowj 20150923 如果支付时间为空（当当系统延时），则放订单创建时间
			if (odgResponse.getPaymentDate() == null || odgResponse.getPaymentDate().length() == 0) {
				aomsordT.setAoms024(aomsordT.getAoms006());// aomsord024
			} else {
				aomsordT.setAoms024(CommonUtil.checkNullOrNot(odgResponse.getPaymentDate()));// aomsord024
			}
//			aomsordT.setAoms024(CommonUtil.checkNullOrNot(odgResponse.getPaymentDate()));// aomsord024 // mark by mowj 20150923
			
			aomsordT.setAoms025(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getDangdangAccountID()));// aomsord025
			aomsordT.setAoms026(CommonUtil.checkNullOrNot(null));// aomsord026
			aomsordT.setAoms027(CommonUtil.checkNullOrNot(null));// aomsord027
			aomsordT.setAoms028(CommonUtil.checkNullOrNot(null));// aomsord028
			aomsordT.setAoms029(CommonUtil.checkNullOrNot(null));// aomsord029
			aomsordT.setAoms030(CommonUtil.checkNullOrNot(odgResponse.getBuyerInfo().getCustPointUsed()));// aomsord030
			aomsordT.setAoms031(CommonUtil.checkNullOrNot(null));// aomsord031
			aomsordT.setAoms032(CommonUtil.checkNullOrNot(null));// aomsord032
			aomsordT.setAoms033(CommonUtil.checkNullOrNot(null));// aomsord033
			aomsordT.setAoms034(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getSendCompany()));// aomsord034
			// 运费
			aomsordT.setAoms035(CommonUtil.checkNullOrNot(odgResponse.getBuyerInfo().getPostage()));// aomsord035
			aomsordT.setAoms036(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeName()));// aomsord036
			// aomsordT.setAoms037(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeAddr_Province()));// aomsord037
			// aomsordT.setAoms038(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeAddr_City()));// aomsord038
			// aomsordT.setAoms039(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeAddr_Area()));// aomsord039
			// modi by mowj 20150726 省市区淘宝标准化
			SendGoodsInfo sendGoodsInfo = odgResponse.getSendGoodsInfo();
			AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(sendGoodsInfo.getConsigneeAddr_Province(), sendGoodsInfo.getConsigneeAddr_City(), sendGoodsInfo.getConsigneeAddr_Area());
			if (standardArea != null) {
				String standardProvince = standardArea.getProvince();
				String standardCity = standardArea.getCity();
				String standardDistrict = standardArea.getDistrict();

				aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039

			} else {
				aomsordT.setAoms037(CommonUtil.checkNullOrNot(sendGoodsInfo.getConsigneeAddr_Province()));
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(sendGoodsInfo.getConsigneeAddr_City()));
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(sendGoodsInfo.getConsigneeAddr_Area()));
			}
			aomsordT.setAoms040(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeAddr()));// aomsord040
			aomsordT.setAoms041(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneePostcode()));// aomsord041
			aomsordT.setAoms042(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeMobileTel()));// aomsord042
			aomsordT.setAoms043(CommonUtil.checkNullOrNot(odgResponse.getSendGoodsInfo().getConsigneeTel()));// aomsord043
			aomsordT.setAoms044(CommonUtil.checkNullOrNot(null));// aomsord044
			aomsordT.setAoms045(CommonUtil.checkNullOrNot(null));// aomsord045
			aomsordT.setAoms046(CommonUtil.checkNullOrNot(null));// aomsord046
			aomsordT.setAoms047(CommonUtil.checkNullOrNot(null));// aomsord047
			aomsordT.setAoms048(CommonUtil.checkNullOrNot(null));// aomsord048
			aomsordT.setAoms049(CommonUtil.checkNullOrNot(null));// aomsord049
			aomsordT.setAoms050(CommonUtil.checkNullOrNot(null));// aomsord050
			aomsordT.setAoms051(CommonUtil.checkNullOrNot(null));// aomsord051
			aomsordT.setAoms052(CommonUtil.checkNullOrNot(null));// aomsord052
			aomsordT.setAoms053(CommonUtil.checkNullOrNot(odgResponse.getReceiptInfo().getReceiptName()));// aomsord053
			aomsordT.setAoms054(CommonUtil.checkNullOrNot(odgResponse.getReceiptInfo().getReceiptDetails()));// aomsord054
			aomsordT.setAoms055(CommonUtil.checkNullOrNot(null));// aomsord055
			aomsordT.setStoreId(CommonUtil.checkNullOrNot(storeId));// aomsord056
			// 0.淘宝 1.京东 2.一号店 3.中国工商银行 4.苏宁 5.当当
			aomsordT.setStoreType(DangdangCommonTool.STORE_TYPE);// aomsord057
			aomsordT.setAoms058(CommonUtil.checkNullOrNot(itemInfo.getProductItemId()));// aomsord058
			aomsordT.setAoms059(CommonUtil.checkNullOrNot(null));// aomsord059
			aomsordT.setAoms060(CommonUtil.checkNullOrNot(itemInfo.getItemID()));// aomsord060
			aomsordT.setAoms061(CommonUtil.checkNullOrNot(itemInfo.getSpecialAttribute()));// aomsord061
			// aomsordT.setAoms062(CommonUtil.checkNullOrNot(itemInfo.getOrderCount()));// aomsord062
			aomsordT.setAoms063(CommonUtil.checkNullOrNot(null));// aomsord063
			// aomsordT.setAoms064(CommonUtil.checkNullOrNot(Double.parseDouble(itemInfo.getMarketPrice())));// aomsord064
			aomsordT.setAoms065(CommonUtil.checkNullOrNot(null));// aomsord065
			aomsordT.setAoms066(CommonUtil.checkNullOrNot(null));// aomsord066
			aomsordT.setAoms067(CommonUtil.checkNullOrNot(itemInfo.getOuterItemID()));// aomsord067
			aomsordT.setAoms068(CommonUtil.checkNullOrNot(null));// aomsord068
			// aomsordT.setAoms069(CommonUtil.checkNullOrNot(Double.parseDouble(itemInfo.getMarketPrice()) - Double.parseDouble(itemInfo.getUnitPrice())));// aomsord069
			aomsordT.setAoms070(CommonUtil.checkNullOrNot(null));// aomsord070
			// aomsordT.setAoms071(CommonUtil.checkNullOrNot(Double.parseDouble(itemInfo.getOrderCount()) * Double.parseDouble(itemInfo.getUnitPrice())));// aomsord071
			aomsordT.setAoms072(CommonUtil.checkNullOrNot(null));// aomsord072
			aomsordT.setAoms073(CommonUtil.checkNullOrNot(null));// aomsord073
			aomsordT.setAoms074(CommonUtil.checkNullOrNot(null));// aomsord074
			aomsordT.setAoms075(CommonUtil.checkNullOrNot(null));// aomsord075
			aomsordT.setAoms076(CommonUtil.checkNullOrNot(null));// aomsord076
			aomsordT.setAoms077(CommonUtil.checkNullOrNot(null));// aomsord077
			// /*礼品券+*/ mark by mowj 20150817
			// 礼品卡+移动端优惠
			// add by mowj 20150817 添加当当的移动端优惠作为平台优惠（水星提出）
			// modi by mowj 20150817 礼品券作为商家优惠，而不是平台优惠，所以移到aomsord020（水星提出）
			aomsordT.setAoms078(CommonUtil.checkNullOrNot(
//					Double.parseDouble(odgResponse.getBuyerInfo().getGiftCertMoney()) + // mark by mowj 20150817
					Double.parseDouble(odgResponse.getBuyerInfo().getGiftCardMoney()) + 
					Double.parseDouble(odgResponse.getBuyerInfo().getActivityDeductAmount()) // add by mowj 20150817	添加当当的移动端优惠作为平台优惠（水星提出）
					));// aomsord078
			aomsordT.setAoms079(CommonUtil.checkNullOrNot(null));// aomsord079
			aomsordT.setAoms080(CommonUtil.checkNullOrNot(null));// aomsord080
			aomsordT.setAoms081(CommonUtil.checkNullOrNot(null));// aomsord081
			aomsordT.setAoms082(CommonUtil.checkNullOrNot(null));// aomsord082
			aomsordT.setAoms083(CommonUtil.checkNullOrNot(null));// aomsord083
			aomsordT.setAoms084(CommonUtil.checkNullOrNot(null));// aomsord084
			aomsordT.setAoms085(CommonUtil.checkNullOrNot(null));// aomsord085
			aomsordT.setAoms086(CommonUtil.checkNullOrNot(null));// aomsord086
			aomsordT.setAoms087(CommonUtil.checkNullOrNot(null));// aomsord087
			aomsordT.setAoms088(CommonUtil.checkNullOrNot(null));// aomsord088
			aomsordT.setAoms089(CommonUtil.checkNullOrNot(null));// aomsord089
			aomsordT.setAoms090(CommonUtil.checkNullOrNot(null));// aomsord090
			aomsordT.setAoms091(CommonUtil.checkNullOrNot(null));// aomsord091
			aomsordT.setAoms092(CommonUtil.checkNullOrNot(null));// aomsord092

			// add by mowj 20150923
			// 只需要在有赠品的商品单身行上加上多个productItemId,orderCount.没有赠品的就它自己一个

			// add by mowj 20151010
			currentItemId = itemInfo.getItemID();
			
			if (prodItemInfoMap.get(currentItemId) == null) {
				prodItemInfoMap.put(currentItemId, itemInfo.getProductItemId() + "," + itemInfo.getOrderCount());
			}
			else {
				String prodItemInfo = prodItemInfoMap.get(currentItemId);
				prodItemInfo += "|" + itemInfo.getProductItemId() + "," + itemInfo.getOrderCount();
				prodItemInfoMap.put(currentItemId, prodItemInfo);
			}
			
			String date = CommonUtil.checkNullOrNot(new Date());
			aomsordT.setAomsstatus("0");
			aomsordT.setAomscrtdt(date);
			aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

			aomsordTMap.put(orderIdKey, aomsordT);
		}

		if (null != aomsordTMap) {
			for (Map.Entry<String, AomsordT> aomsordT : aomsordTMap.entrySet()) {
				aomsordT.getValue().setAomsundefined003(prodItemInfoMap.get(aomsordT.getValue().getAoms060())); // add by mowj 20150923 // modi by mowj 20151020
				result.add(aomsordT.getValue());
			}
		}

		return result;
	}
	
	/**
	 * 根据当当的OperateInfo节点，返回Operate为下单的时间
	 * @param odgResponse
	 * @return
	 * @author 维杰
	 * @since 2015.08.18
	 */
	private String getOrderCreateOperateTime(OrderDetailsGetResponse odgResponse) {
		String orderCreateOperateTime = "";
		List<OperateInfo> operateInfos = odgResponse.getOrderOperateList().getOperateInfos();
		for (OperateInfo operateInfo : operateInfos) {
			if (operateInfo.getOperateDetails().contains("下单")) {
				orderCreateOperateTime = operateInfo.getOperateTime();
				break;
			}
		}
		return "".equals(orderCreateOperateTime) ? odgResponse.getLastModifyTime() : orderCreateOperateTime;
	}
}
