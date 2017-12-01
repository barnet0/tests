package com.digiwin.ecims.platforms.icbc.bean.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.icbc.bean.base.Consignee;
import com.digiwin.ecims.platforms.icbc.bean.base.Discount;
import com.digiwin.ecims.platforms.icbc.bean.base.Giftproduct;
import com.digiwin.ecims.platforms.icbc.bean.base.Order;
import com.digiwin.ecims.platforms.icbc.bean.base.Product;
import com.digiwin.ecims.platforms.icbc.bean.base.Tringproduct;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail.IcbcOrderDetailResponse;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.util.MySpringContext;

public class IcbcAomsordTTranslator {
	private Object obj;
	private String storeId;

	private StandardAreaService standardAreaService = MySpringContext
			.getContext().getBean(StandardAreaService.class);

//	private static final Logger logger = LoggerFactory.getLogger(IcbcAomsordTTranslator.class);
	
	public IcbcAomsordTTranslator(Object obj, String storeId) {
		super();
		this.obj = obj;
		this.storeId = storeId;
	}

	public List<AomsordT> doTranslate() {
		if (obj instanceof IcbcOrderDetailResponse) {
			IcbcOrderDetailResponse response = (IcbcOrderDetailResponse) obj;
			if (response.getBody().getOrderList() == null) {
				return null;
			}
			return orderToAomsordT(response.getBody().getOrderList().getOrderList());
		} else {
			return null;
		}
	}

	public List<AomsordT> orderToAomsordT(List<Order> orders){
		List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
		for (Order order : orders) {
			if (order.getProducts().getProductList() != null) {
				for (Product product : order.getProducts().getProductList()) {
					if (product.getTringproducts().getTringproductList() != null) {
						for (Tringproduct tringproduct : product
								.getTringproducts().getTringproductList()) {
							AomsordT tmp = tringProductToAomsord(order,
									tringproduct);
							aomsordTs.add(tmp);
						}
					}

					if (product.getGiftproducts().getGiftproductList() != null) {
						for (Giftproduct giftproduct : product
								.getGiftproducts().getGiftproductList()) {
							AomsordT tmp = tringProductToAomsord(order,
									giftproduct);
							aomsordTs.add(tmp);
						}
					}

					AomsordT tmp = tringProductToAomsord(order, product);
					aomsordTs.add(tmp);
				}
			}
		}
		return aomsordTs;
	}
	
	private AomsordT tringProductToAomsord(Order order, Object obj) {
		AomsordT aomsordT = new AomsordT();
		aomsordT.setId(CommonUtil.checkNullOrNot(order.getOrderId()));
		aomsordT.setAoms003(CommonUtil.checkNullOrNot(order.getOrderStatus()));
		aomsordT.setAoms004(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms006(CommonUtil.checkNullOrNot(order
				.getOrderCreateTime()));
		aomsordT.setModified(CommonUtil.checkNullOrNot(order
				.getOrderModifyTime()));
		aomsordT.setAoms009(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms010(CommonUtil.checkNullOrNot(order
				.getOrderBuyerRemark()));
		aomsordT.setAoms012(CommonUtil.checkNullOrNot(order
				.getOrderSellerRemark()));

		// aoms020 modi by Shang Hsuan Hsu 20150726
		if (order.getDiscounts() != null) {
			List<Discount> discounts = order.getDiscounts().getDiscountList();
			if (discounts != null) {
				Double sum = 0.0;
				for (Discount dis : discounts) {
					// aoms020 modi by Shang Hsuan Hsu 20150727
					if(!dis.getDiscountType().equals("07")){
						sum += dis.getDiscountAmount().doubleValue();
					}
				}
//				aomsordT.setAoms020(String.valueOf(sum)); // mark by mowj 20160119 避免长度大于表定义
				aomsordT.setAoms020(CommonUtil.checkNullOrNot(sum)); // add by mowj 20160119
			}
		}

		aomsordT.setAoms021(CommonUtil.checkNullOrNot(null));
		
		//20150727 modi by Shang Hsuan Hsu: aoms022 從order.getPayment().getOrderPayAmount() 改成 order.getOrderAmount() 
		aomsordT.setAoms022(CommonUtil.checkNullOrNot(order.getOrderAmount()));
		aomsordT.setAoms023(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms024(CommonUtil.checkNullOrNot(order.getPayment()
				.getOrderPayTime()));
		aomsordT.setAoms025(CommonUtil.checkNullOrNot(order
				.getOrderBuyerUsername()));
		aomsordT.setAoms026(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms028(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms030(CommonUtil.checkNullOrNot(order
				.getOrderCreditAmount()));// modi by Shang Hsuan Hsu 20150726
		aomsordT.setAoms034(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms035(CommonUtil.checkNullOrNot(order.getPayment()
				.getOrderFreight()));// modi by Shang Hsuan Hsu 20150726
		aomsordT.setAoms036(CommonUtil.checkNullOrNot(order.getConsignee()
				.getConsigneeName()));
		// aomsordT.setAoms037(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneeProvince()));
		// aomsordT.setAoms038(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneeCity()));
		// aomsordT.setAoms039(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneeDistrict()));
		
		// modi by mowj 20150726 省市区淘宝标准化
		Consignee consigneeInfo = order.getConsignee();
		AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(
						consigneeInfo.getConsigneeProvince(),
						consigneeInfo.getConsigneeCity(),
						consigneeInfo.getConsigneeDistrict());
		if (standardArea != null) {
			String standardProvince = standardArea.getProvince();
			String standardCity = standardArea.getCity();
			String standardDistrict = standardArea.getDistrict();

			aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
			aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
			aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039

		} else {
			aomsordT.setAoms037(CommonUtil.checkNullOrNot(consigneeInfo.getConsigneeProvince()));
			aomsordT.setAoms038(CommonUtil.checkNullOrNot(consigneeInfo.getConsigneeCity())); // 修正District->City by mowj 20160317
			aomsordT.setAoms039(CommonUtil.checkNullOrNot(consigneeInfo.getConsigneeDistrict()));
		}

		aomsordT.setAoms040(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneeAddress()));
		aomsordT.setAoms041(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneeZipcode()));
		aomsordT.setAoms042(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneeMobile()));
		aomsordT.setAoms043(CommonUtil.checkNullOrNot(order.getConsignee().getConsigneePhone()));
		aomsordT.setAoms053(CommonUtil.checkNullOrNot(order.getInvoice().getInvoiceTitle()));
		aomsordT.setAoms054(CommonUtil.checkNullOrNot(order.getInvoice().getInvoiceContent()));
		aomsordT.setStoreId(CommonUtil.checkNullOrNot(storeId));
		aomsordT.setStoreType(IcbcCommonTool.STORE_TYPE);
		aomsordT.setAoms071(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms076(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms077(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms078(CommonUtil.checkNullOrNot(order
				.getOrderCouponAmount()));// modi by Shang Hsuan Hsu 20150726
		aomsordT.setAoms082(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms089(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms090(CommonUtil.checkNullOrNot(null));
		aomsordT.setAoms091(CommonUtil.checkNullOrNot(null));
		
		aomsordT.setAomscrtdt(DateTimeTool.format(new Date())); // add by mowj 20151204
		aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

		setBody(aomsordT, order, obj);

		return aomsordT;
	}

	/**
	 * 單身處理
	 * 
	 * @param aomsordT
	 * @param order
	 * @param obj
	 */
	private void setBody(AomsordT aomsordT, Order order, Object obj) {
		if (obj instanceof Product) {
			Product product = (Product) obj;
			aomsordT.setAoms060(CommonUtil.checkNullOrNot(product.getProductSkuId()));
			aomsordT.setAoms061(CommonUtil.checkNullOrNot(product.getProductName()));
			aomsordT.setAoms062(CommonUtil.checkNullOrNot(product.getProductNumber()));
			aomsordT.setAoms064(CommonUtil.checkNullOrNot(product.getProductPrice()));
			aomsordT.setAoms067(CommonUtil.checkNullOrNot(product.getProductCode()));

			// modi by Shang Hsuan Hsu 20150726
			Double aoms071 = (Double.parseDouble(product.getProductNumber()) * Double
					.parseDouble(product.getProductPrice()))
					- Double.parseDouble(product.getProductDiscount());
//			// for test
//			String test071 = String.valueOf(aoms071);
//			if (test071.length() > 15) {
//				logger.error("test071 is {}, it's too long for column aomsord071", test071);
//			}
			
//			aomsordT.setAoms071(CommonUtil.checkNullOrNot(String.valueOf(aoms071))); // mark by mowj 20160119 解决直接使用String.valueOf导致长度超出表定义
			aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071)); // add by mowj 20160119
		} else if (obj instanceof Tringproduct) {
			Tringproduct tringproduct = (Tringproduct) obj;
			aomsordT.setAoms060(CommonUtil.checkNullOrNot(tringproduct.getProductSkuId()));
			aomsordT.setAoms061(CommonUtil.checkNullOrNot(tringproduct.getProductName()));
			aomsordT.setAoms062(CommonUtil.checkNullOrNot(tringproduct.getProductNumber()));
			aomsordT.setAoms064(CommonUtil.checkNullOrNot(tringproduct.getProductPrice()));
			aomsordT.setAoms067(CommonUtil.checkNullOrNot(tringproduct.getProductCode()));

			// modi by Shang Hsuan Hsu 20150726
			Double aoms071 = tringproduct.getProductNumber().doubleValue()
					* tringproduct.getProductPrice().doubleValue();
//			// for test
//			String test071 = String.valueOf(aoms071);
//			if (test071.length() > 15) {
//				logger.error("test071 is {}, it's too long for column aomsord071", test071);
//			}
			
//			aomsordT.setAoms071(CommonUtil.checkNullOrNot(String.valueOf(String
//					.valueOf(aoms071))));  // mark by mowj 20160119 解决直接使用String.valueOf导致长度超出表定义
			aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071)); // add by mowj 20160119
		} else if (obj instanceof Giftproduct) {
			Giftproduct giftproduct = (Giftproduct) obj;
			aomsordT.setAoms060(CommonUtil.checkNullOrNot(giftproduct.getProductSkuId()));
			aomsordT.setAoms061(CommonUtil.checkNullOrNot(giftproduct.getProductName()));
			aomsordT.setAoms062(CommonUtil.checkNullOrNot(giftproduct.getProductNumber()));
			aomsordT.setAoms064(CommonUtil.checkNullOrNot("0"));
			aomsordT.setAoms067(CommonUtil.checkNullOrNot(giftproduct.getProductCode()));

			// modi by Shang Hsuan Hsu 20150726
			aomsordT.setAoms071("0");
		}
	}
}
