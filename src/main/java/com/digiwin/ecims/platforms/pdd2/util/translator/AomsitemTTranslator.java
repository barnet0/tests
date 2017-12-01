package com.digiwin.ecims.platforms.pdd2.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.SkuColorSizeAttribute;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.GoodsInfo;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.Item;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.ItemSku;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.SkuList;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.StockItem;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetGoodsInfoResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetSkuStockResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.CheckOrdersAfterSaleResponse;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.ItemStatus;

public class AomsitemTTranslator {

	private Object object;

	public AomsitemTTranslator(Object itemList) {
		super();
		this.object = itemList;
	}

	public List<AomsitemT> doTranslate(String storeId) {
		if (object instanceof GetGoodsResponse) {
			return parsePddItemToAomsitemT(storeId);
		} else if (object instanceof GetGoodsInfoResponse) {
			return parsePddGoodInfoToAomsitemT(storeId);
		} else if (object instanceof GetSkuStockResponse) {
			return parsePddSkuStockToAomsitemT(storeId);
		}else {
			return new ArrayList<AomsitemT>();
		}
	}

	private List<AomsitemT> parsePddItemToAomsitemT(String storeId) {
		List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
		GetGoodsResponse getGoodsResponse = (GetGoodsResponse) this.object;
		for (Item item : getGoodsResponse.getGoods_list()) {
			for (ItemSku itemSku : item.getSku_list()) {
				AomsitemT aomsitemT = new AomsitemT();

				aomsitemT.setId(CommonUtil.checkNullOrNot(item.getGoods_id()));
				aomsitemT.setAoms003(CommonUtil.checkNullOrNot(item.getGoods_name()));
				aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getSku_id()));
				aomsitemT.setAoms005(CommonUtil.checkNullOrNot(itemSku.getOuter_id()));
				// aomsitemT.setAoms007(itemstatus == ItemStatus.ONSALE ?
				// "onsale" : (itemstatus == null ? "" : "instock"));
				aomsitemT.setAoms007(item.getIs_onsale() == 1 ? "onsale" : "instock");
				// System.out.println(item.getGoodsID() + " " +
				// itemSku.getSpec());
				if (StringTool.isNotEmpty(itemSku.getSpec())) {
					SkuColorSizeAttribute skuColorSize = getSkuColorSizeAttribute(itemSku.getSpec().trim());

					aomsitemT.setAoms008(skuColorSize.getColor());
					aomsitemT.setAoms009(skuColorSize.getSize());
				}

				aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSku.getSku_quantity()));

				// aomsitemT.setAoms024(CommonUtil.checkNullOrNot(item.getGoodsPrice()));

				aomsitemT.setStoreId(storeId);
				aomsitemT.setStoreType(Pdd2CommonTool.STORE_TYPE);

				Date now = new Date();
				aomsitemT.setAomsstatus("0");
				aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
				aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

				aomsitemTs.add(aomsitemT);
			}
		}

		return aomsitemTs;
	}

	private List<AomsitemT> parsePddGoodInfoToAomsitemT(String storeId) {
		List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
		GetGoodsInfoResponse getGoodsInfoResponse = (GetGoodsInfoResponse) this.object;

		GoodsInfo item = getGoodsInfoResponse.getGoods_info();
		for (SkuList itemSku : item.getSku_list()) {
			AomsitemT aomsitemT = new AomsitemT();

			aomsitemT.setId(CommonUtil.checkNullOrNot(item.getGoods_id()));
			aomsitemT.setAoms003(CommonUtil.checkNullOrNot(item.getGoods_name()));
			aomsitemT.setAoms004(CommonUtil.checkNullOrNot(itemSku.getSku_id()));
			aomsitemT.setAoms005(CommonUtil.checkNullOrNot(itemSku.getOuter_id()));
			aomsitemT.setAoms007(item.getIs_onsale().equals("1") ? "onsale" : "instock");
			if (StringTool.isNotEmpty(itemSku.getSpec())) {
				SkuColorSizeAttribute skuColorSize = getSkuColorSizeAttribute(itemSku.getSpec().trim());

				aomsitemT.setAoms008(skuColorSize.getColor());
				aomsitemT.setAoms009(skuColorSize.getSize());
			}

			aomsitemT.setAoms018(CommonUtil.checkNullOrNot(itemSku.getSku_quantity()));

			aomsitemT.setAoms024(CommonUtil.checkNullOrNot(itemSku.getSingle_price()));

			aomsitemT.setStoreId(storeId);
			aomsitemT.setStoreType(Pdd2CommonTool.STORE_TYPE);

			Date now = new Date();
			aomsitemT.setAomsstatus("0");
			aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
			aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

			aomsitemTs.add(aomsitemT);
		}

		return aomsitemTs;
	}

	/**
	 * 库存
	 * @param storeId
	 * @return
	 */
	private List<AomsitemT> parsePddSkuStockToAomsitemT(String storeId) {
		List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
		GetSkuStockResponse getSkuStockResponse = (GetSkuStockResponse) this.object;

		for (StockItem stockItem : getSkuStockResponse.getSku_stock_list()) {
			AomsitemT aomsitemT = new AomsitemT();

			aomsitemT.setAoms004(CommonUtil.checkNullOrNot(stockItem.getSku_id()));
			//aomsitemT.setAoms005(CommonUtil.checkNullOrNot(stockItem.getOuter_id()));

			aomsitemT.setAoms018(CommonUtil.checkNullOrNot(stockItem.getQuantity()));

			aomsitemT.setStoreId(storeId);
			aomsitemT.setStoreType(Pdd2CommonTool.STORE_TYPE);

			aomsitemTs.add(aomsitemT);
		}

		return aomsitemTs;
	}
	/**
	 * 根据SKU属性的值，从中获取颜色和尺寸
	 * 
	 * @param skuSpec
	 *            sku属性字符串
	 * @return
	 */
	private Pdd2SkuColorSizeAttribute getSkuColorSizeAttribute(String skuSpec) {
		String color = "";
		String size = "";
		String[] splittedSkuSpec = skuSpec.split(Pdd2CommonTool.SKU_SPEC_DELIMITER);
		for (String skuSpecSplitted : splittedSkuSpec) {
			if (StringTool.isEmpty(skuSpecSplitted)) {
				continue;
			}
			// 数字开头的属性字符串为尺寸，否则为颜色
			if (skuSpecSplitted.charAt(0) >= '0' && skuSpecSplitted.charAt(0) <= '9') {
				size = skuSpecSplitted;
			} else {
				color = skuSpecSplitted;
			}
		}

		return new Pdd2SkuColorSizeAttribute(color, size);
	}

}

// class SkuColorSizeAttribute {
//
// private String color;
//
// private String size;
//
// public String getColor() {
// return color;
// }
//
// public void setColor(String color) {
// this.color = color;
// }
//
// public String getSize() {
// return size;
// }
//
// public void setSize(String size) {
// this.size = size;
// }
//
// public SkuColorSizeAttribute() {}
//
// public SkuColorSizeAttribute(String color, String size) {
// this.color = color;
// this.size = size;
// }
//
// @Override
// public String toString() {
// if (color == null || color.length() == 0) {
// return size;
// }
// if (size == null || size.length() == 0) {
// return color;
// }
// return color + ";" + size;
// }
//
// }
