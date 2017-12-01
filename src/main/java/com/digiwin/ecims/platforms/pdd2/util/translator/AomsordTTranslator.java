package com.digiwin.ecims.platforms.pdd2.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.ItemList;
import com.digiwin.ecims.platforms.pdd2.bean.domain.order.OrderInfo;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderInfoGetResponse;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;

public class AomsordTTranslator {

	private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);

	private Object object;

	private StandardAreaService standardAreaService = MySpringContext.getContext().getBean(StandardAreaService.class);

	public AomsordTTranslator(Object orderEntity) {
		super();
		this.object = orderEntity;
	}

	public List<AomsordT> doTranslate(String storeId) {
		if (object instanceof OrderInfoGetResponse) {
			return parsePddOrderInfoToAomsordT(storeId);
		}
		{
			return new ArrayList<AomsordT>();
		}
	}

	private List<AomsordT> parsePddOrderInfoToAomsordT(String storeId) {
		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		OrderInfoGetResponse orderInfoResponse = (OrderInfoGetResponse) this.object;
		OrderInfo order = orderInfoResponse.getOrder_info();
		for (ItemList orderItem : order.getItem_list()) {

			AomsordT aomsordT = new AomsordT();

			aomsordT.setId(CommonUtil.checkNullOrNot(order.getOrder_sn()));
			aomsordT.setAoms003(order.getOrder_status().toString());
			switch (order.getOrder_status())

			{

			case 1:
				aomsordT.setAoms003("WAIT_SELLER_SEND_GOODS");
				break;
			case 2:
				aomsordT.setAoms003("WAIT_GOODS_RECEIVE_CONFIRM");
				break;
			case 3:
				aomsordT.setAoms003("TRADE_FINISHED");
				break;
			default:
				aomsordT.setAoms003(order.getOrder_status().toString());
				break;
			}
			
			aomsordT.setAoms006(CommonUtil.checkNullOrNot(order.getCreated_time()));
			aomsordT.setAoms009(CommonUtil.checkNullOrNot(order.getRemark()));

			aomsordT.setAoms020(CommonUtil.checkNullOrNot(order.getSeller_discount())); // 商家优惠
			aomsordT.setAoms022(CommonUtil.checkNullOrNot(order.getPay_amount()+order.getPlatform_discount()));
			aomsordT.setAoms023(CommonUtil.checkNullOrNot(order.getPay_type()));
			aomsordT.setAoms024(CommonUtil.checkNullOrNot(order.getConfirm_time()));
			aomsordT.setAoms025(CommonUtil.checkNullOrNot(order.getReceiver_name()));
			aomsordT.setAoms034(CommonUtil.checkNullOrNot(order.getLogistics_id()));
			aomsordT.setAoms035(CommonUtil.checkNullOrNot(order.getPostage()));
			aomsordT.setAoms036(CommonUtil.checkNullOrNot(order.getReceiver_name()));

			AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(order.getProvince(),
					order.getCity(), order.getTown());
			if (standardArea != null) {
				String standardProvince = standardArea.getProvince();
				String standardCity = standardArea.getCity();
				String standardDistrict = standardArea.getDistrict();

				aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
			} else {
				aomsordT.setAoms037(CommonUtil.checkNullOrNot(order.getProvince()));
				aomsordT.setAoms038(CommonUtil.checkNullOrNot(order.getCity()));
				aomsordT.setAoms039(CommonUtil.checkNullOrNot(order.getTown()));
			}

			aomsordT.setAoms040(CommonUtil.checkNullOrNot(order.getAddress()));
			aomsordT.setAoms042(CommonUtil.checkNullOrNot(order.getReceiver_phone()));
			aomsordT.setAoms044(CommonUtil.checkNullOrNot(order.getShipping_time()));

			aomsordT.setStoreId(storeId);
			aomsordT.setStoreType(Pdd2CommonTool.STORE_TYPE);

			aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderItem.getGoods_id()));

			aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderItem.getSku_id()));

			aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderItem.getGoods_spec()));
			aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderItem.getGoods_count()));
			aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderItem.getGoods_name()));
			aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderItem.getGoods_price()));

			aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderItem.getOuter_id()));

			Double aoms071 = (double) (orderItem.getGoods_count() * orderItem.getGoods_price());
			aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));

			aomsordT.setAoms078(CommonUtil.checkNullOrNot(order.getPlatform_discount()));

			Date now = new Date();
			aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
			aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

			aomsordTs.add(aomsordT);
		}

		return aomsordTs;
	}
}
