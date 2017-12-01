package com.digiwin.ecims.platforms.icbc.bean.translator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.icbc.bean.base.Product;
import com.digiwin.ecims.platforms.icbc.bean.base.Saleproperty;
import com.digiwin.ecims.platforms.icbc.bean.base.Skuproduct;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail.IcbcProductDetailResponse;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;

public class IcbcAomsitemTTranslator {
	private Object obj;
	private String storeId;

	public IcbcAomsitemTTranslator(IcbcProductDetailResponse response,
			String storeid) {
		this.obj = response;
		this.storeId = storeid;
	}

	public List<AomsitemT> doTranslate() throws ParseException {
		if (obj instanceof IcbcProductDetailResponse) {
			IcbcProductDetailResponse response = (IcbcProductDetailResponse) obj;
			if(response.getBody().getProductList() == null){
				return null;
			}
			
			if (response.getBody().getProductList().getProductList() != null) {
				List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
				for (Product product : response.getBody().getProductList().getProductList()) {
					if (product.getSkuproducts().getSkuproductList() != null) {
						for (Skuproduct skuProduct : product.getSkuproducts().getSkuproductList()) {
							AomsitemT aomsitemT = new AomsitemT();
							aomsitemT.setId(CommonUtil.checkNullOrNot(product.getProductId()));
							aomsitemT.setAoms002(CommonUtil.checkNullOrNot(product.getProductMerchantId()));
							aomsitemT.setAoms003(CommonUtil.checkNullOrNot(product.getProductName()));
							aomsitemT.setAoms004(CommonUtil.checkNullOrNot(skuProduct.getProductSkuId()));
							aomsitemT.setAoms005(CommonUtil.checkNullOrNot(skuProduct.getProductMerchantId()));
							aomsitemT.setAoms006(CommonUtil.checkNullOrNot(null));
							//20150730 modi by Shang Hsuan Hsu: 05轉換成onsale; 01轉換成instock
							if(product.getProductStatus().equals("05")){
								aomsitemT.setAoms007("onsale");
							}else if(product.getProductStatus().equals("01")){
								aomsitemT.setAoms007("instock");
							}

							List<Saleproperty> saleproperties = skuProduct.getSaleproperties().getSalepropertyList();
							if (saleproperties != null) {
								//屬性可能有顏色有尺寸, 有顏色無尺寸, 無顏色有尺寸, 無顏色無尺寸....
								if(saleproperties.size()<2){
									Saleproperty saleproperty = saleproperties.get(0);
									if(saleproperty.getSale_prop_name().equals("尺寸")){
										aomsitemT.setAoms009(CommonUtil.checkNullOrNot(saleproperty.getSale_prop_value()));
									}
									if(saleproperty.getSale_prop_name().equals("颜色")){
										aomsitemT.setAoms008(CommonUtil.checkNullOrNot(saleproperty.getSale_prop_value()));
									}
								}else{
									aomsitemT.setAoms008(CommonUtil.checkNullOrNot(saleproperties.get(1).getSale_prop_value()));
									aomsitemT.setAoms009(CommonUtil.checkNullOrNot(saleproperties.get(0).getSale_prop_value()));
								}
							} else {
								aomsitemT.setAoms008(CommonUtil.checkNullOrNot(null));
								aomsitemT.setAoms009(CommonUtil.checkNullOrNot(null));
							}
							
							Date currentDate = new Date();
							aomsitemT.setStoreId(storeId);
							aomsitemT.setStoreType(IcbcCommonTool.STORE_TYPE);
							aomsitemT.setAoms015(CommonUtil.checkNullOrNot(null));
							aomsitemT.setAoms018(CommonUtil.checkNullOrNot(skuProduct.getProductStorage()));
							aomsitemT.setAoms019(CommonUtil.checkNullOrNot(null));
							aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null));
							aomsitemT.setAoms024(CommonUtil.checkNullOrNot(getMaxPrice(skuProduct))); // add by mowj 20150819 商品价格
							aomsitemT.setAomsstatus(CommonUtil.checkNullOrNot(null));
							aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(currentDate)); // add by mowj 20150819
							aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));
							
							//不能填上架時間，因為推送的時候資料筆數會跟中台DB不一樣
//							Date putonTime = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US)
//									.parse(product.getPutonTime());
//							String putonTimeStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(putonTime);
//							aomsitemT.setModified(putonTimeStr);//商品沒有修改時間，填上架時間
							
							aomsitemT.setModified(DateTimeTool.format(currentDate, "yyyy-MM-dd HH:mm:ss"));//商品沒有修改時間，暫時填當前時間
							
							aomsitemTs.add(aomsitemT);
						}
					} else { // add by mowj 20150804
						AomsitemT aomsitemT = new AomsitemT();
						aomsitemT.setId(CommonUtil.checkNullOrNot(product.getProductId()));
						aomsitemT.setAoms002(CommonUtil.checkNullOrNot(product.getProductMerchantId()));
						aomsitemT.setAoms003(CommonUtil.checkNullOrNot(product.getProductName()));
						aomsitemT.setAoms004(CommonUtil.checkNullOrNot(product.getProductId())); // product.getProductSkuId()
						aomsitemT.setAoms005(CommonUtil.checkNullOrNot(product.getProductMerchantId()));
						aomsitemT.setAoms006(CommonUtil.checkNullOrNot(null));
						//20150730 modi by Shang Hsuan Hsu: 05轉換成onsale; 01轉換成instock
						if(product.getProductStatus().equals("05")){
							aomsitemT.setAoms007("onsale");
						}else if(product.getProductStatus().equals("01")){
							aomsitemT.setAoms007("instock");
						}

						Date currentDate = new Date();
						aomsitemT.setStoreId(storeId);
						aomsitemT.setStoreType(IcbcCommonTool.STORE_TYPE);
						aomsitemT.setAoms015(CommonUtil.checkNullOrNot(null));
						aomsitemT.setAoms018(CommonUtil.checkNullOrNot(product.getProductStorage()));
						aomsitemT.setAoms019(CommonUtil.checkNullOrNot(null));
						aomsitemT.setAoms020(CommonUtil.checkNullOrNot(null));
						aomsitemT.setAoms024(CommonUtil.checkNullOrNot(getMaxPrice(product))); // add by mowj 20150819 商品价格    
						aomsitemT.setAomsstatus(CommonUtil.checkNullOrNot(null));
						aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(currentDate)); // add by mowj 20150819
						aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(new Date()));
						
						//不能填上架時間，因為推送的時候資料筆數會跟中台DB不一樣
//						Date putonTime = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US)
//								.parse(product.getPutonTime());
//						String putonTimeStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(putonTime);
//						aomsitemT.setModified(putonTimeStr);//商品沒有修改時間，填上架時間
						
						aomsitemT.setModified(DateTimeTool.format(currentDate));//商品沒有修改時間，暫時填當前時間 // modify by mowj 20151204
						
						aomsitemTs.add(aomsitemT);
					}
				}
				return aomsitemTs;
			}
		}
		return null;
	}

	/**
	 * @param obj
	 * @return
	 * @author 维杰
	 * @since 2015.08.19
	 */
	private String getMaxPrice(Object obj) {
		if (obj instanceof Product) {
			Double[] priceDoubles = new Double[1];
			Product product = (Product)obj;
			
			//modifybycs at 20170406
			priceDoubles[0] = product.getProductEmallPrice() == null ? 0.0 : product.getProductEmallPrice();
			//priceDoubles[1] = product.getProductMarketPrice() == null ? 0.0 : product.getProductMarketPrice();
			
			int maxPricePos = 0;
			for (int i = 1; i < priceDoubles.length; i++) {
				if (priceDoubles[i] > priceDoubles[maxPricePos]) {
					maxPricePos = i;
				}
			}
			return priceDoubles[maxPricePos] + "";
			
		} else if (obj instanceof Skuproduct) {
			Skuproduct skuProduct = (Skuproduct)obj;
			String[] priceStrings = new String[2];
			priceStrings[0] = skuProduct.getProductEmallPrice() == null ? "0.0" : skuProduct.getProductEmallPrice();
			priceStrings[1] = skuProduct.getProductMarketPrice() == null ? "0.0" : skuProduct.getProductMarketPrice();
			
			double[] priceDoubles = new double[2];
			priceDoubles[0] = Double.parseDouble(priceStrings[0]);
			priceDoubles[1] = Double.parseDouble(priceStrings[1]);
			
			int maxPricePos = 0;
			for (int i = 1; i < priceDoubles.length; i++) {
				if (priceDoubles[i] > priceDoubles[maxPricePos]) {
					maxPricePos = i;
				}
			}
			return priceStrings[maxPricePos];
			
		} else {
			return "";
		}
	}
}
