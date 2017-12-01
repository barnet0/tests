package com.digiwin.ecims.platforms.jingdong.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.address.PromiseOperationConfigService.Map;
import com.jd.open.api.sdk.domain.mall.http.PriceChange;
import com.jd.open.api.sdk.domain.ware.Sku;
//import com.jd.open.api.sdk.domain.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.response.mall.WarePriceGetResponse;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncItemsService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class JingdongApiSyncItemsServiceImpl implements JingdongApiSyncItemsService {

  private static final Logger logger = LoggerFactory.getLogger(JingdongApiSyncItemsServiceImpl.class);

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private JingdongApiService jingdongApiService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws JdException, IOException {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());

    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取得时间区间内总资料笔数
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    do {
      totalSize = jingdongApiService.jingdongWaresSearch(
          appKey, appSecret, accessToken, 
          null, null, null, 
          JingdongCommonTool.MIN_PAGE_NO, pageSize, 
          null, null, 
          null, null, 
          sDate, eDate, 
          null, null, 
          null, null, null).getTotal();
      
      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);
    
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else if (totalSize == 0L) {
      // 区间内没有资料 
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return true;
      }
    }
    
    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      WareInfoByInfoSearchResponse response =
          jingdongApiService.jingdongWaresSearch(
              appKey, appSecret, accessToken, 
              null, null, null, 
              i, pageSize, 
              null, null, 
              null, null, 
              sDate, eDate, 
              null, null, 
              null, null, null);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, JingdongCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]" + "360buy.wares.search 检索商品信息", 
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());
      
      if (response == null || response.getWareInfos() == null) {
        logger.error("++++++++++++++++++++ERROR++++++++++++++++++++" + "PageNo Now is " + i);
        logger.error(response.getMsg());
        throw new RuntimeException("++++++++++++++++++++ERROR++++++++++++++++++++"
            + "PageNo Now is " + i + "|||" + response.getMsg());
      }
      
      for (Ware ware : response.getWareInfos()) {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // 取得每个ware单品的sku资讯
        WareGetResponse wareGetResponse = jingdongApiService.jingdongWareGet(
            appKey, appSecret, accessToken, 
            ware.getWareId() + StringTool.EMPTY, JingdongCommonTool.WARE_FIELDS);       
         
        Ware wareGoods = wareGetResponse.getWare();
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
            JingdongCommonTool.STORE_TYPE,
            "360buy.ware.get 根据商品ID查询单个商品的详细信息", 
            JsonUtil.formatByMilliSecond(wareGetResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
            taskScheduleConfig.getScheduleType());
        
        
        //addbycs at 20170412
        //取得每个ware单品的商品价格 ,根据sku资讯，再将价格信息存入一个list
        List<HashMap<String, String>> priceList = new ArrayList<HashMap<String, String>>();
        if(wareGoods.getSkus() != null){
        	  	
        	for(Sku sku : wareGoods.getSkus()){
        		String skuId = "J_"+ sku.getSkuId().toString();
        		
        		HashMap<String, String> map = new HashMap<String,String>();
        		
        		map.put("skuId", sku.getSkuId().toString());	
        		
        		 //根据skuid获取每个sku商品的价格
        		WarePriceGetResponse warePriceGetResponse = jingdongApiService.jingdongWarePriceGet(
        				  appKey, appSecret, accessToken,
        				  skuId);
        		
        		 loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
        		            JingdongCommonTool.STORE_TYPE,
        		            "jingdong.ware.price.get 根据SKU ID获取商品价格信息", 
        		            JsonUtil.formatByMilliSecond(warePriceGetResponse),
        		            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
        		            taskScheduleConfig.getScheduleType());
        		
        		List<PriceChange> priceChange = warePriceGetResponse.getPriceChanges();
        		
        		map.put("price", priceChange.get(0).getPrice());
        		map.put("marketPrice", priceChange.get(0).getMarketPrice());
        		
        		priceList.add(map);
        		
        	}
        }
        

        List<AomsitemT> list = null;
        if (wareGoods != null) {
          // 会有一种商品多种sku,modifybycs at 20170412 传入sku商品的价格
          list = new AomsitemTTranslator(ware, wareGoods.getSkus(), priceList).doTranslator(storeId);
        } else {
          // 无sku单品 一头一身
          list = new AomsitemTTranslator(ware, null, null).doTranslator(storeId);
        }
        aomsitemTs.addAll(list);
        
        // 資料最大的更新時間
        if (DateTimeTool.parse(ware.getModified()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(ware.getModified());
        }
      }
      taskService.newTransaction4Save(aomsitemTs);
      // 清空列表，为下一页资料作准备
      aomsitemTs.clear();
    }
    
    logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));
    
    return true;
  }
}
