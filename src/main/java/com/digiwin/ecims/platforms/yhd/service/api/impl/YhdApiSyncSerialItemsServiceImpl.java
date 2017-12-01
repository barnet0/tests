package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhd.object.product.SerialChildProd;
import com.yhd.object.product.SerialProduct;
import com.yhd.response.product.ProductsPriceGetResponse;
import com.yhd.response.product.ProductsStockGetResponse;
import com.yhd.response.product.SerialProductAttributeGetResponse;
import com.yhd.response.product.SerialProductGetResponse;
import com.yhd.response.product.SerialProductsSearchResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncSerialItemsService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsitemTTranslator;

@Service
public class YhdApiSyncSerialItemsServiceImpl implements YhdApiSyncSerialItemsService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private YhdApiService yhdApiService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
 // 取得區間內總資料筆數
    int pageSize = taskScheduleConfig.getMaxPageSize();
    String endDate = taskScheduleConfig.getEndDate();
    
    //System.out.println(taskScheduleConfig.getScheduleType()+"---"+endDate);

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
//  modify by mowj 20151215 按凤顺要求先完整同步一次资料，去掉更新时间，再等东根问一号店结果
    int totalSize = yhdApiService.yhdSerialProductsSearch(appKey, appSecret, accessToken, 
        null, null, 
        ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue(),
        null, null, null, null, null, null, null, null, null, null).getTotalCount();
    
    if (totalSize == 0) {  	
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), endDate);
      return true;
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增系列商品
    for (int i = pageNum; i > 0; i--) {
      List<Object> aomsitemTs = new ArrayList<Object>();
   // modify by mowj 20151215 按凤顺要求先完整同步一次资料，去掉更新时间，再等东根问一号店结果
      SerialProductsSearchResponse response =
          yhdApiService.yhdSerialProductsSearch(appKey, appSecret, accessToken, 
              null, null, 
              i, pageSize,
              null, null, null, null, null, null, null, null, null, null);
      
      List<SerialProduct> serialProducts = new ArrayList<SerialProduct>();
      
      //addby cs at 20170427  查询返回结果中，抓取只包含productId的object，并赋给serialProducts
      for (SerialProduct serialProduct : response.getSerialProductList().getSerialProduct()) {
    	  
    	  if(serialProduct.getProductId() != null){
    		  serialProducts.add(serialProduct);
    	  }
    	  
      }

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
          "yhd.serial.products.search 查询系列产品信息", JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      // 針對每一筆系列產品..
      //for (SerialProduct serialProduct : response.getSerialProductList().getSerialProduct()) {
      //modifyby cs 从包含productId的资料中查询详情
      for (SerialProduct serialProduct : serialProducts) {
        // 取得該系列產品的所有子品
    	  
    	//modifyby cs 新增传入outerid，京东返回的serialProduct数组里面的json格式不一样
        SerialProductGetResponse childProds =
            yhdApiService.yhdSerialProductGet(appKey, appSecret, accessToken, serialProduct.getProductId(), null ,null , null);

        // add by mowj 20150824
        // modi by mowj 20150825 增加biztype
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.serial.product.get 获取单个系列产品的子品信息", JsonUtil.format(childProds),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        // 取得所有子品中的屬性
       //modifyby cs 新增传入outerid，京东返回的serialProduct数组里面的json格式不一样
        SerialProductAttributeGetResponse childProdsAttr =
            yhdApiService.yhdSerialProductAttributeGet(appKey, appSecret, accessToken, serialProduct.getProductId(), null);

        // add by mowj 20150824
        // modi by mowj 20150825 增加biztype
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.serial.product.attribute.get 获取商品系列属性", JsonUtil.format(childProdsAttr),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        // 商品价格 add by mowj 20150819
        String serialChildProdIdString = "";
        for (SerialChildProd scProd : childProds.getSerialChildProdList().getSerialChildProd()) {
          serialChildProdIdString += scProd.getProductId() + ",";
        }
        serialChildProdIdString =
         // modi by mowj 20150824 length()-2->length()-1
            serialChildProdIdString.substring(0, serialChildProdIdString.length() - 1); 

        ProductsPriceGetResponse productsPriceGetResponse =
            yhdApiService.yhdProductsPriceGet(appKey, appSecret, accessToken, serialChildProdIdString, null);
        // add by mowj 20150824
        // modi by mowj 20150825 增加biztype
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.products.price.get 批量获取产品价格信息", JsonUtil.format(productsPriceGetResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        // add by mowj 20150831 添加商品库存
        ProductsStockGetResponse productsStockGetResponse =
            yhdApiService.yhdProductsStockGet(appKey, appSecret, accessToken, serialChildProdIdString, null, null);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.products.stock.get 批量获取产品库存信息", JsonUtil.format(productsStockGetResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        List<AomsitemT> list = new YhdAomsitemTTranslator(serialProduct, storeId)
            .doTranslate(childProds, childProdsAttr,
                // modi by mowj 20150819 商品价格
                productsPriceGetResponse,
             // modi by mowj 20150831 商品库存
                productsStockGetResponse); 
        aomsitemTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsitemTs);
    }
    
    //System.out.println("2有更新资料更新时间");
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), endDate);
    //System.out.println("3有更新资料更新时间");
    
    return true;
  }

}
