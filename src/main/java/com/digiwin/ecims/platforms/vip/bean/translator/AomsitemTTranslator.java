package com.digiwin.ecims.platforms.vip.bean.translator;

import java.util.ArrayList;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import vipapis.product.MultiGetProductSkuResponse;
import vipapis.product.ProductSkuInfo;
import vipapis.product.ProductSpuInfo;

public class AomsitemTTranslator<T> {

	private Object head;
	private Object detailResponse;
	private List<T> freezeNums;
	
	public AomsitemTTranslator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AomsitemTTranslator(Object head, Object detailResponse, 
			List<T> freezeNums) {
		super();
		this.head = head;
		this.detailResponse = detailResponse;
		this.freezeNums = freezeNums;
	}

	public List<AomsitemT> doTranslate(String storeId) {
		if (head instanceof ProductSpuInfo) {
			return parseProductSpuSkuInfoToAomsitemT(storeId);
		}
		return null;
	}
	
	private List<AomsitemT> parseProductSpuSkuInfoToAomsitemT(String storeId) {
		List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
		ProductSpuInfo spuInfo = (ProductSpuInfo) this.head;
		MultiGetProductSkuResponse skuResponse = (MultiGetProductSkuResponse) this.detailResponse;
		
		StringBuffer aoms006Sb = new StringBuffer();
		for (int i = 0; i < skuResponse.getProducts().size(); i++) {
			ProductSkuInfo skuInfo = skuResponse.getProducts().get(i);
			T freezeNum = this.freezeNums.get(i);
			AomsitemT aomsitemT = new AomsitemT();
			
			aomsitemT.setId(CommonUtil.checkNullOrNot(spuInfo.getSn()));
			aomsitemT.setAoms003(CommonUtil.checkNullOrNot(spuInfo.getProduct_name()));
			aomsitemT.setAoms004(CommonUtil.checkNullOrNot(skuInfo.getBarcode()));
			// TODO 颜色与尺码无法从文档获得信息，需要实际测试过
//			aomsitemT.setAoms006(
//					aoms006Sb.append(CommonUtil.checkNullOrNot(skuInfo.getColor()))
//						.append(";")
//						.append(CommonUtil.checkNullOrNot(skuInfo.getSize()))
//					.toString());
			aomsitemT.setAoms007(CommonUtil.checkNullOrNot(spuInfo.getStatus().getValue() == 13 ? "onsale" : "instock"));
			// TODO 颜色与尺码无法从文档获得信息，需要实际测试过
//			aomsitemT.setAoms008(CommonUtil.checkNullOrNot(skuInfo.getColor()));
//			aomsitemT.setAoms009(CommonUtil.checkNullOrNot(skuInfo.getSize()));
			// TODO 商品主图URL无法从文档获得信息，需要实际测试
//			aomsitemT.setAoms013(CommonUtil.checkNullOrNot(spuInfo.getProduct_image()));
			
			aomsitemT.setAoms014(CommonUtil.checkNullOrNot(spuInfo.getSmall_image()));
			aomsitemT.setAoms018(CommonUtil.checkNullOrNot(freezeNum));
			
			aomsitemTs.add(aomsitemT);
			
			aoms006Sb.setLength(0);
		}
		
		return aomsitemTs;
	}
}
