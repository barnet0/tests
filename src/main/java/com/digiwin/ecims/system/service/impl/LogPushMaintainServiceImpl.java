package com.digiwin.ecims.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

@Service
public class LogPushMaintainServiceImpl {
	
	@Autowired
	private BaseDAO baseDAO;
	
	private Map<String, String> platFormMap;
	
	public LogPushMaintainServiceImpl() {
		this.platFormMap = new HashMap<String, String>();
		this.platFormMap.put("0", "淘宝");
		this.platFormMap.put("1", "京东");
		this.platFormMap.put("2", "一号店");
		this.platFormMap.put("3", "ICBC");
		this.platFormMap.put("4", "苏宁");
		this.platFormMap.put("5", "当当");
		this.platFormMap.put("E", "网易考拉"); //2017/06/30  by cjp
		this.platFormMap.put("J", "拼多多");
		
		
//		this.storeActionName.put("Dangdang#AomsitemT#5", "[当当][推送]-铺货");
//		this.storeActionName.put("Dangdang#AomsordT#5", "[当当][推送]-订单");
//		this.storeActionName.put("Dangdang#AomsrefundT#5", "[当当][推送]-退货退款");
//		this.storeActionName.put("DangdangItemUpdate#DZ0004#5", "[当当][拉取]-铺货");
//		this.storeActionName.put("DangdangRefundUpdate#DZ0004#5", "[当当][拉取]-退货退款");
//		this.storeActionName.put("DangdangTradeUpdate#DZ0004#5", "[当当][拉取]-订单");
//		this.storeActionName.put("DangdangTradeUpdateCheck#DZ0004#5", "[当当]-效验机制");
//		
//		this.storeActionName.put("Icbc#AomsitemT#3", "[ICBC][推送]-铺货");
//		this.storeActionName.put("Icbc#AomsordT#3", "[ICBC][推送]-订单");
//		this.storeActionName.put("Icbc#AomsrefundT#3", "[ICBC][推送]-退货退款");
//		this.storeActionName.put("IcbcItemUpdate#DZ0025#3", "[ICBC][拉取]-铺货");
//		this.storeActionName.put("IcbcRefundUpdate#DZ0025#3", "[ICBC][拉取]-退货退款");
//		this.storeActionName.put("IcbcTradeUpdate#DZ0025#3", "[ICBC][拉取]-订单");
//		this.storeActionName.put("IcbcTradeUpdateCheck#DZ0025#3", "[ICBC]-效验机制");
//		
//		this.storeActionName.put("Suning#AomsitemT#4", "[苏宁][推送]-铺货");
//		this.storeActionName.put("Suning#AomsordT#4", "[苏宁][推送]-订单");
//		this.storeActionName.put("Suning#AomsrefundT#4", "[苏宁][推送]-退货退款");
//		this.storeActionName.put("SuningItemUpdate#DZ0023#4", "[苏宁][拉取]-铺货");
//		this.storeActionName.put("SuningRefundUpdate#DZ0023#4", "[苏宁][拉取]-退货退款");
//		this.storeActionName.put("SuningTradeUpdate#DZ0023#4", "[苏宁][拉取]-订单");
//		this.storeActionName.put("SuningTradeUpdateCheck#DZ0023#4", "[苏宁]-效验机制");
//		
//		this.storeActionName.put("Jingdong#AomsitemT#1", "[京东][推送]-铺货");
//		this.storeActionName.put("Jingdong#AomsordT#1", "[京东][推送]-订单");
//		this.storeActionName.put("JingdongBoth#AomsrefundT#1", "[京东][推送]-退货退款");
//		this.storeActionName.put("JingdongOnly#AomsrefundT#1", "[京东][推送]-仅退款");
//		this.storeActionName.put("JingdongItemUpdate#DZ0018#1", "[京东][拉取]-铺货");
//		this.storeActionName.put("JingdongRefundBothUpdate#DZ0018#1", "[京东][拉取]-退货退款");
//		this.storeActionName.put("JingdongRefundOnlyUpdate#DZ0018#1", "[京东][拉取]-仅退款");
//		this.storeActionName.put("JingdongTradeUpdate#DZ0018#1", "[京东][拉取]-订单");
//		this.storeActionName.put("JingdongTradeUpdateCheck#DZ0018#1", "[京东]-效验机制");
//		
//		this.storeActionName.put("TaobaoFx#AomsitemT#A", "[淘宝][推送][分销]-铺货");
//		this.storeActionName.put("TaobaoFx#AomsordT#A", "[淘宝][推送][分销]-订单");
//		this.storeActionName.put("TaobaoFx#AomsrefundT#A", "[淘宝][推送][分销]-退货退款");
//		this.storeActionName.put("TaobaoTb#AomsitemT#0", "[淘宝][推送]-铺货");
//		this.storeActionName.put("TaobaoTb#AomsordT#0", "[淘宝][推送]-订单");
//		this.storeActionName.put("TaobaoTb#AomsrefundT#0", "[淘宝][推送]-退货退款");
		
//		this.storeActionName.put("TaobaoFxRefundUpdate", "[淘宝][分销][拉取]-退货退款");
//		this.storeActionName.put("TaobaoFxTradeUpdate", "[淘宝][分销][拉取]-订单");
//		this.storeActionName.put("TaobaoFxProductUpdate#MISC-14#A", "[淘宝][分销][拉取]-铺货");
//		this.storeActionName.put("TaobaoTbItemUpdate", "[淘宝][拉取]-铺货");
//		this.storeActionName.put("TaobaoTbRefundUpdate", "[淘宝][拉取]-退货退款");
//		this.storeActionName.put("TaobaoTbTradeUpdate", "[淘宝][拉取]-订单");
//		this.storeActionName.put("TaobaoTbTradeUpdateCheck", "[淘宝]-效验机制");
//		
//		this.storeActionName.put("Yhd#AomsitemT#2", "[一号店][推送]-铺货");
//		this.storeActionName.put("Yhd#AomsordT#2", "[一号店][推送]-订单");
//		this.storeActionName.put("Yhd#AomsrefundT#2", "[一号店][推送]-退货退款");
//		this.storeActionName.put("YhdSerial#AomsitemT#2", "[一号店][推送]-商品推送(己无使用)");
//		this.storeActionName.put("YhdItemUpdate#DZ0001#2", "[一号店][拉取]-铺货普通商品");
//		this.storeActionName.put("YhdSerialItemUpdate#DZ0001#2", "[一号店][拉取]-系列商品");
//		this.storeActionName.put("YhdRefundAbnormalUpdate#DZ0001#2", "[一号店][拉取]-异常订单退款");
//		this.storeActionName.put("YhdRefundUpdate#DZ0001#2", "[一号店][拉取]-退货退款");
//		this.storeActionName.put("YhdTradeUpdate#DZ0001#2", "[一号店][拉取]-订单");
//		this.storeActionName.put("YhdTradeUpdateCheck#DZ0001#2", "[一号店]-效验机制");
	}
	
	/**
	 * 取得所有的排程設定資料
	 * @param storeType
	 * @return
	 */
	public List<TaskScheduleConfig> findAllScheduleConfig(String storeType) {
		String hql;
		if (StringUtils.isBlank(storeType)) {
			hql = "from TaskScheduleConfig order by run_type, shop_id";
		} else {
			hql = "from TaskScheduleConfig where scheduleType like '" + storeType + "%' order by run_type, shop_id ";
		}
		List<TaskScheduleConfig> data = baseDAO.executeQueryByHql(hql);
		this.changeStoreActionName(data); //更換 schedule type
		return data;
	}
	
	/**
	 * 轉換名稱
	 * @param data
	 */
	private void changeStoreActionName(List<TaskScheduleConfig> data) {
		
		//取得所有的電商資訊
		Map<String, AomsshopT> storeMap = this.getStoreMap();
		
		for (TaskScheduleConfig tsc : data) {
			
			//取得對應的電商資訊
			AomsshopT shopInfo = storeMap.get(tsc.getStoreId());
			
			String newName = null;
			String platFormType = null;
			if (shopInfo == null) {

				if ( this.platFormMap.get(tsc.getStoreId()) == null) {
					//表示該 scheduleType 沒有被管理到
					newName = "[" + tsc.getScheduleType() + "][" + tsc.getRunType() + "]";
				} else {
					/*
					 * 推送是不分店家的, 所以 shopId 會記店商平台編號
					 * 0 --> taobao
					 * 1 --> 京东
					 * 2 --> 一号店
					 * 3 --> ICBC
					 * 4 --> 苏宁
					 * 5 --> 当当
					 */
					newName = "[" + this.platFormMap.get(tsc.getStoreId()) + "][" + tsc.getRunType() + "]";					
				}
				platFormType = tsc.getStoreId();
			} else {
				newName = shopInfo.getAomsshop002() + "[" + tsc.getRunType() + "]";
				platFormType = shopInfo.getAomsshop003();
			}
			
			if ("0".equals(platFormType)) {
				//taobao
				this.parseTaobaoPullActionName(shopInfo, tsc, newName);
			} else if ("1".equals(platFormType)) {
				//京东
				this.parseJingdongPullActionName(shopInfo, tsc, newName);
			} else if ("2".equals(platFormType)) {
				//一号店
				this.parseYhdPullActionName(shopInfo, tsc, newName);
			} else {
				//ICBC, 苏宁, 当当
				this.normalActionName(tsc, newName);
			}
		}
	}
	
	/**
	 * 更換顯示名稱
	 * @param shopInfo
	 * @param tsc
	 */
//	private void normalActionName(AomsshopT shopInfo, TaskScheduleConfig tsc) {
//		String newName = "[" + shopInfo.getAomsshop002() + "][" + tsc.getRunType() + "]";
//		this.normalActionName(tsc, newName);
//	}
	
	/**
	 * 更換顯示名稱
	 * @param shopInfo
	 * @param tsc
	 */
	private void normalActionName(TaskScheduleConfig tsc, String newName) {
		if ("PULL".equals(tsc.getRunType())) {
			if (tsc.getScheduleType().indexOf("ItemUpdate") > -1) {
				newName += "-铺货";
			} else if (tsc.getScheduleType().indexOf("TradeUpdate") > -1) {
				newName += "-订单";
			} else if (tsc.getScheduleType().indexOf("RefundUpdate") > -1) {
				newName += "-退货退款";
			}
		} else if ("PUSH".equals(tsc.getRunType())) {
			if (tsc.getScheduleType().indexOf("AomsitemT") > -1) {
				newName += "-铺货";
			} else if (tsc.getScheduleType().indexOf("AomsordT") > -1) {
				newName += "-订单";
			} else if (tsc.getScheduleType().indexOf("AomsrefundT") > -1) {
				newName += "-退货退款";
			}
		} else if ("CHECK".equals(tsc.getRunType())) {
			newName += "-效验机制";
		}
		tsc.setScheduleType(newName);
	}
	
	/**
	 * 更換顯示名稱 for 京東
	 * @param shopInfo
	 * @param tsc
	 */
	private void parseJingdongPullActionName(AomsshopT shopInfo, TaskScheduleConfig tsc, String newName) {
		if ("PULL".equals(tsc.getRunType())) {
			if (tsc.getScheduleType().indexOf("RefundBothUpdate") > -1) {
				newName += "-退货退款";
			} else if (tsc.getScheduleType().indexOf("RefundOnlyUpdate") > -1) {
				newName += "-仅退款";
			} else {
				this.normalActionName(tsc, newName);
				return;
			}
		} else if ("PUSH".equals(tsc.getRunType())) {
			if (tsc.getScheduleType().indexOf("Both#AomsrefundT") > -1) {
				newName += "-退货退款";
			} else if (tsc.getScheduleType().indexOf("Only#AomsrefundT") > -1) {
				newName += "-仅退款";
			} else {
				this.normalActionName(tsc, newName);
				return;
			}
		} else {
			this.normalActionName(tsc, newName);
			return;
		}
		tsc.setScheduleType(newName);
	}
	
	/**
	 * 更換顯示名稱 for 一號店
	 * @param shopInfo
	 * @param tsc
	 */
	private void parseYhdPullActionName(AomsshopT shopInfo, TaskScheduleConfig tsc, String newName) {
		if ("PULL".equals(tsc.getRunType())) {
			if (tsc.getScheduleType().indexOf("YhdItemUpdate") > -1) {
				newName += "-铺货普通商品";
			} else if (tsc.getScheduleType().indexOf("YhdSerialItemUpdate") > -1) {
				newName += "-系列商品";
			} else if (tsc.getScheduleType().indexOf("YhdRefundAbnormalUpdate") > -1) {
				newName += "-异常订单退款";
			} else if (tsc.getScheduleType().indexOf("YhdRefundUpdate") > -1) {
				newName += "-退货退款";
			} else {
				this.normalActionName(tsc, newName);
				return;
			}
		} else {
			this.normalActionName(tsc, newName);
			return;
		}
		tsc.setScheduleType(newName);
	}
	
	/**
	 * 更換顯示名稱 for taobao
	 * @param shopInfo
	 * @param tsc
	 */
	private void parseTaobaoPullActionName(AomsshopT shopInfo, TaskScheduleConfig tsc, String newName) {
		//檢查是否為分銷
		if (tsc.getScheduleType().indexOf("Fx") > -1) {
			newName += "[分销]";
		}
		
		if ("PULL".equals(tsc.getRunType())) {
			if (tsc.getScheduleType().indexOf("TaobaoFxProductUpdate") > -1) {
				//分銷
				newName += "-铺货";
			}
			this.normalActionName(tsc, newName);
			return;
		} else {
			this.normalActionName(tsc, newName);
			return;
		}
	}
	
	/**
	 * //只有 Taobao 的電商, 才需要把所有的店鋪名稱, 串接起來. 因為是從聚石塔 取資料下來, 所以無法分商店
	 * 取得商店的資料
	 */
	private Map<String, AomsshopT> getStoreMap() {
		
		Map<String, AomsshopT> storeMap = new HashMap<String, AomsshopT>();
		
		//取得所有的商店資料
		String hql = "from AomsshopT";
		List<AomsshopT> data = baseDAO.executeQueryByHql(hql);
		
		for (AomsshopT row : data) {
			if ("0".equals(row.getAomsshop003())) {
				//taobao
				AomsshopT existBean = storeMap.get("MISC");
				String newName = null;
				if (existBean == null) {
					//first process.
					newName = "[" + row.getAomsshop002() + "]";
					row.setAomsshop002(newName);					
					storeMap.put("MISC", row);
				} else {
					newName = existBean.getAomsshop002() + "[" + row.getAomsshop002() + "]";
					existBean.setAomsshop002(newName);
					storeMap.put("MISC", existBean);
				}
			} else {
				storeMap.put(row.getAomsshop001(), row);				
			}
		}		
		return storeMap;
	}

}
