package com.digiwin.ecims.platforms.yhd.util.translator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhd.object.order.OrderDetail;
import com.yhd.object.order.OrderInfo;
import com.yhd.object.order.OrderItem;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

/**
 * 
 * @author ShangHsuan Hsu 20150121 create
 *
 */
public class YhdAomsordTTranslator {
	private Object obj;
	private String storeId;

	private StandardAreaService standardAreaService = MySpringContext.getContext().getBean(StandardAreaService.class);
	
	public YhdAomsordTTranslator(Object obj, String storeId) {
		super();
		this.obj = obj;
		this.storeId = storeId;
	}

	public List<AomsordT> doTranslate() {
		OrderInfo orderInfo = (OrderInfo) obj;
		OrderDetail head = orderInfo.getOrderDetail();// 單頭
		List<OrderItem> items = orderInfo.getOrderItemList().getOrderItem();// 單身
		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		Map<String ,AomsordT> onlyOrdMap = new HashMap<String,AomsordT>();
		Map<String ,List<AomsordT>> sumMap = new HashMap<String,List<AomsordT>>();
		for (OrderItem item : items) {
			AomsordT aomsordT = new AomsordT();
			aomsordT.setId(CommonUtil.checkNullOrNot(head.getOrderId()));
			aomsordT.setAoms003(CommonUtil.checkNullOrNot(head.getOrderStatus()));
			aomsordT.setAoms004(null);
			aomsordT.setAoms006(CommonUtil.checkNullOrNot(head.getOrderCreateTime()));
			aomsordT.setModified(CommonUtil.checkNullOrNot(head.getUpdateTime()));
			aomsordT.setAoms009(null);
			aomsordT.setAoms010(CommonUtil.checkNullOrNot(head.getDeliveryRemark()));
			aomsordT.setAoms012(CommonUtil.checkNullOrNot(head.getMerchantRemark()));

			// 折扣金額Aoms020
			// orderPromotionDiscount+orderCouponDiscount
			Double aoms020 = (head.getOrderPromotionDiscount() + head.getOrderCouponDiscount());
			aomsordT.setAoms020(CommonUtil.checkNullOrNot(aoms020));

			aomsordT.setAoms021(null);

			// 支付金額Aoms022
			// OrderInfo.orderDetail.realAmount-OrderInfo.orderDetail.orderPlatformDiscount
//			Double aoms022 = (head.getRealAmount() - head.getOrderPlatformDiscount()); //modi by Shang Hsuan Hsu 20150726
			aomsordT.setAoms022(CommonUtil.checkNullOrNot(head.getRealAmount())); //modi by Shang Hsuan Hsu 20150726

			aomsordT.setAoms023(CommonUtil.checkNullOrNot(head.getPayServiceType()));
			aomsordT.setAoms024(CommonUtil.checkNullOrNot(head.getOrderPaymentConfirmDate()));
			aomsordT.setAoms025(CommonUtil.checkNullOrNot(head.getEndUserId()));
			aomsordT.setAoms026(null);
			aomsordT.setAoms028(null);
			aomsordT.setAoms034(CommonUtil.checkNullOrNot(head.getDeliverySupplierId()));

			// 物流費用
			// OrderInfo.orderDetail.orderDeliveryFee
			aomsordT.setAoms035(CommonUtil.checkNullOrNot(head.getOrderDeliveryFee()));
			aomsordT.setAoms036(CommonUtil.checkNullOrNot(head.getGoodReceiverName()));
//			aomsordT.setAoms037(CommonUtil.checkNullOrNot(head.getGoodReceiverProvince()));
//			aomsordT.setAoms038(CommonUtil.checkNullOrNot(head.getGoodReceiverCity()));
//			aomsordT.setAoms039(CommonUtil.checkNullOrNot(head.getGoodReceiverCounty()));
			
			// modi by mowj 20150726 省市区淘宝标准化
			AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(
					head.getGoodReceiverProvince(), 
					head.getGoodReceiverCity(),
					head.getGoodReceiverCounty());
			if (standardArea != null) {
				String standardProvince = standardArea.getProvince();
				String standardCity = standardArea.getCity();
				String standardDistrict = standardArea.getDistrict();
				
				aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039	
				
			} else {
				aomsordT.setAoms037(CommonUtil.checkNullOrNot(head.getGoodReceiverProvince()));
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(head.getGoodReceiverCity()));
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(head.getGoodReceiverCounty()));
			}
			
			aomsordT.setAoms040(CommonUtil.checkNullOrNot(head.getGoodReceiverAddress()));
			aomsordT.setAoms041(CommonUtil.checkNullOrNot(head.getGoodReceiverPostCode()));
			aomsordT.setAoms042(CommonUtil.checkNullOrNot(head.getGoodReceiverMoblie()));
			aomsordT.setAoms043(CommonUtil.checkNullOrNot(head.getGoodReceiverPhone()));
			aomsordT.setAoms053(CommonUtil.checkNullOrNot(head.getInvoiceTitle()));
			aomsordT.setAoms054(CommonUtil.checkNullOrNot(head.getInvoiceContent()));
			aomsordT.setStoreId(storeId);
			aomsordT.setStoreType(YhdCommonTool.STORE_TYPE);
			aomsordT.setAoms058(CommonUtil.checkNullOrNot(item.getProductId()+"#"+item.getId())); // add by mowj 20150726 添加子订单号
			aomsordT.setAoms060(CommonUtil.checkNullOrNot(item.getProductId()));// 临时修改为：SKU_ID#子订单号
			aomsordT.setAoms061("");
			aomsordT.setAoms062(CommonUtil.checkNullOrNot(item.getOrderItemNum()));
			aomsordT.setAoms064(CommonUtil.checkNullOrNot(item.getOriginalPrice()));//單價改成原價 modi by Shang Hsuan Hsu 20150726
			aomsordT.setAoms067(CommonUtil.checkNullOrNot(item.getOuterId()));

			// 子订单级订单优惠金额
			// OrderInfo.orderItem.originalPrice-OrderInfo.orderItem.orderItemPrice
			DecimalFormat df1 = new DecimalFormat("###############.00");
			Double aoms069 = item.getOriginalPrice() - item.getOrderItemPrice();
			aomsordT.setAoms069(CommonUtil.checkNullOrNot(df1.format(aoms069)));

			// 支付金額
			// OrderInfo.orderItem.orderItemAmount
			aomsordT.setAoms071(CommonUtil.checkNullOrNot(item.getOrderItemAmount()));

			aomsordT.setAoms076(null);
			aomsordT.setAoms077(null);

			// 平台優惠
			// OrderInfo.orderDetail.orderPlatformDiscount
			aomsordT.setAoms078(CommonUtil.checkNullOrNot(head.getOrderPlatformDiscount()));

			aomsordT.setAoms082(null);
			aomsordT.setAoms089(null);

			// 分攤優惠
			// OrderInfo.orderItem.promotionAmount+OrderInfo.orderItem.couponAmountMerchant
			Double aoms091 = item.getPromotionAmount() + item.getCouponAmountMerchant();
			aomsordT.setAoms091(CommonUtil.checkNullOrNot(aoms091));

			// 分摊之后的实付金额
			// aomsord071-aomsord091
//			Double aoms090 = Double.parseDouble(aomsordT.getAoms071()) - Double.parseDouble(aomsordT.getAoms091());
//			aomsordT.setAoms090(CommonUtil.checkNullOrNot(aoms090));

			// 备用字段（一号店运费分摊金额）
			// orderItem.deliveryFeeAmount
			aomsordT.setAoms092(CommonUtil.checkNullOrNot(item.getDeliveryFeeAmount()));

			// 平台优惠分摊
			// OrderInfo.orderItem.couponPlatformDiscount
			aomsordT.setAoms094(CommonUtil.checkNullOrNot(item.getCouponPlatformDiscount()));

			aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date())); // add by mowj 20150818
			aomsordT.setAomsstatus("0");
			aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

//			aomsordTs.add(aomsordT);
			String key=aomsordT.getId()+aomsordT.getStoreType()+aomsordT.getAoms060();
			if(onlyOrdMap.containsKey(key)){
				sumMap.get(key).add(aomsordT);
			}else{
				List<AomsordT> aomsordTList=new ArrayList<AomsordT>();
				aomsordTList.add(aomsordT);
				sumMap.put(key, aomsordTList);
				onlyOrdMap.put(key, aomsordT);
			}
			
		}
		
		//比對 && 加總
		for(String key:sumMap.keySet()){
			int last062 = 0;
			
			Double last091 = 0.0;//相加即可
			Double last090 = 0.0;//last71-last91
			Double last092 = 0.0;//相加即可
			Double last094 = 0.0;//相加即可
			
			
			Double last071 = 0.0;
			Double last064 = 0.0;
			Double mult062x064 = 0.0;
			Double last069 = 0.0; 
			for(AomsordT ordT:sumMap.get(key)){
				last062 += Integer.parseInt(ordT.getAoms062());
				mult062x064 += Double.parseDouble(ordT.getAoms064())*Integer.parseInt(ordT.getAoms062());
				last071 += Double.parseDouble(ordT.getAoms071());
				last091 += Double.parseDouble(ordT.getAoms091());
				last092 += Double.parseDouble(ordT.getAoms092());
				last094 += Double.parseDouble(ordT.getAoms094());
			}
			last064 = mult062x064/last062;
			last069 = mult062x064-last071;
			last090 = last071-last091;
			
			AomsordT ordT = onlyOrdMap.get(key);
			ordT.setAoms062(CommonUtil.checkNullOrNot(last062));
			ordT.setAoms064(CommonUtil.checkNullOrNot(last064));
			ordT.setAoms069(CommonUtil.checkNullOrNot(last069));
			ordT.setAoms071(CommonUtil.checkNullOrNot(last071));
			
			ordT.setAoms090(CommonUtil.checkNullOrNot(last090));
			ordT.setAoms091(CommonUtil.checkNullOrNot(last091));
			ordT.setAoms092(CommonUtil.checkNullOrNot(last092));
			ordT.setAoms094(CommonUtil.checkNullOrNot(last094));
			
			
			
			//062  => 相加就好
			//064  => 自己的數量(062)成自己的價格(064) sum and / 加完的062
			//071  => 相加就好
			//069  => 自己的數量(062)成自己的價格(064) sum - 加完的071
			
		}
		
		
		if (null != onlyOrdMap) {
			for (Map.Entry<String, AomsordT> aomsordT : onlyOrdMap.entrySet()) {
				aomsordTs.add(aomsordT.getValue());
			}
		}
		
		
		
		return aomsordTs;
	}

	// private void setTimeAndStatus(AomsordT aomsordT) {
	// String key = aomsordT.getId() + "_" + aomsordT.getStoreType() + "_"
	// + aomsordT.getAoms060();
	// JedisUtils jedisUtils = new JedisUtils();
	// if (jedisUtils.isExist(key)) {
	// aomsordT.setAomscrtdt(jedisUtils.getRedis(key));
	// // aomsordT.setAomsordstatus("2");// 存在更新
	// } else {
	// aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date()));
	// // aomsordT.setAomsordstatus("0");// 不存在新增
	// jedisUtils.setRedis(key, DateTime.toString(new Date()));
	// }
	// aomsordT.setAomsstatus("0");
	// aomsordT.setAomsmoddt(CommonUtil.checkNullOrNot(new Date()));
	// }
}
