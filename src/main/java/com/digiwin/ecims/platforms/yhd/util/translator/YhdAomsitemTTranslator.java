package com.digiwin.ecims.platforms.yhd.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.yhd.object.product.PmPrice;
import com.yhd.object.product.PmStock;
import com.yhd.object.product.Product;
import com.yhd.object.product.SerialAttributeInfo;
import com.yhd.object.product.SerialChildProd;
import com.yhd.object.product.SerialProdAttributeInfo;
import com.yhd.object.product.SerialProduct;
import com.yhd.response.product.ProductsPriceGetResponse;
import com.yhd.response.product.ProductsStockGetResponse;
import com.yhd.response.product.SerialProductAttributeGetResponse;
import com.yhd.response.product.SerialProductGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

public class YhdAomsitemTTranslator {
	private Object obj;
	private String storeId;

	public YhdAomsitemTTranslator(Object obj, String storeId) {
		super();
		this.obj = obj;
		this.storeId = storeId;
	}

	/**
	 * 单个普通产品的Mapping
	 * @param productsPriceGetResponse
	 * @param productsStockGetResponse
	 * @return
	 */
	public AomsitemT doTranslate(ProductsPriceGetResponse productsPriceGetResponse, ProductsStockGetResponse productsStockGetResponse) {
		Product product = (Product) obj;
		AomsitemT aomsitemT = new AomsitemT();

		// 商品在电商平台的编码
		// Product.productId
		aomsitemT.setId(CommonUtil.checkNullOrNot(product.getProductId()));

		aomsitemT.setAoms002(CommonUtil.checkNullOrNot(product.getOuterId()));
		aomsitemT.setAoms003(CommonUtil.checkNullOrNot(product.getProductCname()));

		// 商品在电商平台的规格编码
		// Product.productId
		aomsitemT.setAoms004(CommonUtil.checkNullOrNot(product.getProductId()));

		aomsitemT.setAoms005(CommonUtil.checkNullOrNot(product.getOuterId()));
		aomsitemT.setAoms006(CommonUtil.checkNullOrNot(product.getProductCname()));

		String aoms007 = null;
		if (product.getCanSale() == 0) {
			aoms007 = "instock";
		} else {
			aoms007 = "onsale";
		}

		aomsitemT.setAoms007(CommonUtil.checkNullOrNot(aoms007));
		aomsitemT.setAoms008(CommonUtil.checkNullOrNot(null));
		aomsitemT.setAoms009(CommonUtil.checkNullOrNot(null));

		// 商品URL
		// Product.prodDetailUrl
		aomsitemT.setAoms013(CommonUtil.checkNullOrNot(product.getProdDetailUrl()));

		// aomsitemT.setAoms014(CommonUtil.checkNullOrNot(product.getProdImg()));
		setProductImgUrl(aomsitemT, product.getProdImg()); // add by mowj
															// 20150730

		aomsitemT.setStoreId(storeId);
		aomsitemT.setStoreType(YhdCommonTool.STORE_TYPE);
		aomsitemT.setAoms015(CommonUtil.checkNullOrNot(null));
		
		// add by mowj 20150831 添加商品库存
		for (PmStock pmStock : productsStockGetResponse.getPmStockList().getPmStock()) {
			if (pmStock.getProductId().equals(product.getProductId())) {
				aomsitemT.setAoms018(CommonUtil.checkNullOrNot(pmStock.getVs()));				
			}
		}
		
		aomsitemT.setAoms019(CommonUtil.checkNullOrNot(null));
		aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null));

		// 备用字段一号店系列产品编码
		// Product.productCode
		aomsitemT.setAoms021(CommonUtil.checkNullOrNot(product.getProductCode()));

		// 备用字段一号店子产品编码
		// Product.productCode
		aomsitemT.setAoms022(CommonUtil.checkNullOrNot(product.getProductCode()));

		// add by mowj 20150819 获取商品价格
		for (PmPrice pmPrice : productsPriceGetResponse.getPmPriceList().getPmPrice()) {
			if (pmPrice.getProductId().equals(product.getProductId())) {
					aomsitemT.setAoms024(CommonUtil.checkNullOrNot(getMaxPrice(pmPrice)));  		
				break;
			}
		}
		
		aomsitemT.setModified(DateTimeTool.format(new Date())); // modify by mowj 20151204

		aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date())); // add by mowj 20150818
		aomsitemT.setAomsstatus("0");
		aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));

		return aomsitemT;
	}

	public List<AomsitemT> doTranslate(SerialProductGetResponse serialProduct, 
			SerialProductAttributeGetResponse serialProductAttributes, 
			ProductsPriceGetResponse productsPriceGetResponse,
			ProductsStockGetResponse productsStockGetResponse) {
		SerialProduct prod = (SerialProduct) obj;
		List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
		for (int i = 0; i < serialProduct.getSerialChildProdList().getSerialChildProd().size(); i++) {
			// 取得一個子品
			SerialChildProd childProd = serialProduct.getSerialChildProdList().getSerialChildProd().get(i);

			// 取得該子品的屬性info
			List<SerialAttributeInfo> childProdAttr = null;
			for (SerialProdAttributeInfo serialProdAttributeInfo : serialProductAttributes.getProdSerialAttributeInfo().getSerialProdAttributeInfoList()
					.getSerialProdAttributeInfo()) {
				if (childProd.getProductId().equals(serialProdAttributeInfo.getProductId())) {
					childProdAttr = serialProdAttributeInfo.getSerialAttributeInfoList().getSerialAttributeInfo();
//					break; // add by mowj 20150807 通过子品ID将子品与其属性匹配到后，即可跳出循环
				}
			}

			AomsitemT aomsitemT = new AomsitemT();

			// 商品在电商平台的编码
			// SerialProduct.productId
			aomsitemT.setId(CommonUtil.checkNullOrNot(prod.getProductId()));

			aomsitemT.setAoms002(CommonUtil.checkNullOrNot(prod.getOuterId()));
			aomsitemT.setAoms003(CommonUtil.checkNullOrNot(prod.getProductCname()));

			// 商品在电商平台的规格编码
			// SerialChildProd.productId
			aomsitemT.setAoms004(CommonUtil.checkNullOrNot(childProd.getProductId()));

			aomsitemT.setAoms005(CommonUtil.checkNullOrNot(childProd.getOuterId()));
			String str = childProd.getProductCname().replace(prod.getProductCname(), "");
			aomsitemT.setAoms006(CommonUtil.checkNullOrNot(str));

			String aoms007 = null;
			if (childProd.getCanSale() == 0) {
				aoms007 = "instock";
			} else {
				aoms007 = "onsale";
			}

			aomsitemT.setAoms007(CommonUtil.checkNullOrNot(aoms007));
			if (childProdAttr != null) {
				for (SerialAttributeInfo serialAttributeInfo : childProdAttr) {
//					if (aomsitemT.getId().equals("6520690")) {
//						System.out.println("aaaa");
//					}
					if (serialAttributeInfo.getType() == 1) {// 若屬性是"顏色"
						aomsitemT.setAoms008(CommonUtil.checkNullOrNot(serialAttributeInfo.getItemLabel()));
					} else {// 其他歸類為"尺寸"
						aomsitemT.setAoms009(CommonUtil.checkNullOrNot(serialAttributeInfo.getItemLabel()));
					}
				}
			}
			aomsitemT.setStoreId(storeId);
			aomsitemT.setStoreType(YhdCommonTool.STORE_TYPE);// 2-一號店

			// 商品URL
			// SerialChildProd.prodDetailUrl
			aomsitemT.setAoms013(CommonUtil.checkNullOrNot(childProd.getProdDetailUrl()));

			// aomsitemT.setAoms014(CommonUtil.checkNullOrNot(childProd.getProdImg()));
			setProductImgUrl(aomsitemT, childProd.getProdImg()); // add by mowj
																	// 20150730

			aomsitemT.setAoms015(CommonUtil.checkNullOrNot(null));
			
			// add by mowj 20150831 添加商品库存
			for (PmStock pmStock : productsStockGetResponse.getPmStockList().getPmStock()) {
				if (pmStock.getProductId().equals(childProd.getProductId())) {
					aomsitemT.setAoms018(CommonUtil.checkNullOrNot(pmStock.getVs()));
				}
			}
			
			aomsitemT.setAoms019(CommonUtil.checkNullOrNot(null));
			aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null));
			// aomsitemT.setAomsstatus(null);

			// 备用字段一号店系列产品编码
			// SerialProduct.productCode
			aomsitemT.setAoms021(CommonUtil.checkNullOrNot(prod.getProductCode()));

			// 备用字段一号店子产品编码
			// SerialChildProd.productCode
			aomsitemT.setAoms022(CommonUtil.checkNullOrNot(childProd.getProductCode()));

			// add by mowj 20150819 获取商品价格
			for (PmPrice pmPrice : productsPriceGetResponse.getPmPriceList().getPmPrice()) {
				if (pmPrice.getProductId().equals(childProd.getProductId())) {
						aomsitemT.setAoms024(CommonUtil.checkNullOrNot(getMaxPrice(pmPrice)));		
					break;
				}
			}
			
			aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(new Date())); // add by mowj 20150818
			aomsitemT.setModified(DateTimeTool.format(new Date()));
			aomsitemT.setAomsstatus("0");
			// 2015-07-28 modified by KenTu
			aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));
			if (childProdAttr != null) {
				aomsitemTs.add(aomsitemT);
			}
		}
		return aomsitemTs;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 * @author 维杰
	 * @since 2015.08.19
	 */
	private String getMaxPrice(Object obj) {
		Double[] priceDoubles = new Double[1];
		
		if (obj instanceof PmPrice) {
			PmPrice price = (PmPrice)obj;
			//modifybycs 20170406
			//priceDoubles[0] = price.getInPrice() == null ? 0.0 : price.getInPrice();
			//modifyby cs at 20170425,价格promNonMemberPrice，如果这个等于0，再取nonMemberPrice
			Double PromNonMemberPrice = price.getPromNonMemberPrice() == null ? 0.0 : price.getPromNonMemberPrice();
			Double NonMemberPrice = price.getNonMemberPrice() == null ? 0.0 : price.getNonMemberPrice();
			if(PromNonMemberPrice == 0 || PromNonMemberPrice == 0.0)
			{
				priceDoubles[0] = NonMemberPrice;
			}else{
				priceDoubles[0] = PromNonMemberPrice;
			}
			//priceDoubles[0] = price.getNonMemberPrice() == null ? 0.0 : price.getNonMemberPrice();
			//priceDoubles[0] = price.getPromNonMemberPrice() == null ? 0.0 : price.getPromNonMemberPrice();
			//priceDoubles[3] = price.getProductListPrice() == null ? 0.0 : price.getProductListPrice();
			
		} else {
			return "";
		}
		
		int maxPricePos = 0;
		for (int i = 1; i < priceDoubles.length; i++) {
			if (priceDoubles[i] > priceDoubles[maxPricePos]) {
				maxPricePos = i;
			}
		}
		return priceDoubles[maxPricePos] + "";
	}

	/**
	 * 设定Aomsitem014的图片URL 图片URL的内容：逗号分隔，图片id、图片URL、主图标识之间用竖线分隔；其中1：表示主图，0：表示非主图
	 * e.g. 1404254|http://d1.yihaodian.com/t1/2011/01/06/1404254.jpg|1
	 * 
	 * @param aomsitemT
	 * @param prodImgInfos
	 */
	private void setProductImgUrl(AomsitemT aomsitemT, String prodImgInfos) {
		if (prodImgInfos != null && prodImgInfos.trim().length() > 0) {
			String prodImgUrl = prodImgInfos;
			String[] prodImgUrls = prodImgUrl.split(",");
			for (String imgUrl : prodImgUrls) {
				if (imgUrl.indexOf('|') <= 0) {
					aomsitemT.setAoms014(CommonUtil.checkNullOrNot(""));
					break;
				}
				String[] imgUrlDetails = imgUrl.split("\\|");
				if (imgUrlDetails.length != 3) {
					aomsitemT.setAoms014(CommonUtil.checkNullOrNot(""));
					break;
				}
				if (imgUrlDetails[2].equals("1")) {
					aomsitemT.setAoms014(CommonUtil.checkNullOrNot(imgUrlDetails[1]));
					break;
				}
			}
		} else {
			aomsitemT.setAoms014(CommonUtil.checkNullOrNot(""));
		}
	}

}
