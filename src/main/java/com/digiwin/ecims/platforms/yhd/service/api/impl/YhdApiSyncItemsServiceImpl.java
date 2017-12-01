package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhd.object.product.Product;
import com.yhd.response.product.GeneralProductsSearchResponse;
import com.yhd.response.product.ProductsPriceGetResponse;
import com.yhd.response.product.ProductsStockGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncItemsService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsitemTTranslator;

@Service
public class YhdApiSyncItemsServiceImpl implements YhdApiSyncItemsService {

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
 // 參數設定
    String endDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取得區間內總資料筆數
//  modify by mowj 20151215 按凤顺要求先完整同步一次资料，去掉更新时间，再等东根问一号店结果
    int totalSize = yhdApiService.yhdGeneralProductsSearch(
        appKey, appSecret, accessToken, null, null, 
        ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue(),
        null, null, null, null, null, null, null, null, null, null).getTotalCount(); 

    if (totalSize == 0) {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), endDate);
      return true;
    }

    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<Object> aomsitemTs = new ArrayList<Object>();
   // modify by mowj 20151215 按凤顺要求先完整同步一次资料，去掉更新时间，再等东根问一号店结果
      GeneralProductsSearchResponse response =
          yhdApiService.yhdGeneralProductsSearch(appKey, appSecret, accessToken, 
              null, null, 
              i, pageSize,
              null, null, null, null, null, null, null, null, null, null); 
      
      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
          "yhd.general.products.search 查询普通产品信息", JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());
      // modify by mowj 20151208 减少API调用次数
      // 生成普通商品ID列表(每一项有多个id)
      List<String> productIdList = generateProductIdList(response);
      // 商品ID列表对应的价格列表
      List<ProductsPriceGetResponse> productsPriceGetResponseList =
          new ArrayList<ProductsPriceGetResponse>();
      // 商品ID列表对应的库存列表
      List<ProductsStockGetResponse> productsStockGetResponseList =
          new ArrayList<ProductsStockGetResponse>();
      for (String productIds : productIdList) {
        // 商品价格 add by mowj 20150819
        ProductsPriceGetResponse productsPriceGetResponse =
            yhdApiService.yhdProductsPriceGet(appKey, appSecret, accessToken, productIds, null);
        // add by mowj 20150824
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.products.price.get 批量获取产品价格信息", JsonUtil.format(productsPriceGetResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        productsPriceGetResponseList.add(productsPriceGetResponse);

        // 商品库存 add by mowj 20150831
        ProductsStockGetResponse productsStockGetResponse =
            yhdApiService.yhdProductsStockGet(appKey, appSecret, accessToken, productIds, null, null);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.products.stock.get 批量获取产品库存信息", JsonUtil.format(productsStockGetResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        productsStockGetResponseList.add(productsStockGetResponse);
      }
      // 將商品轉換成中台POJO
      for (int j = 0, k = 0; j < response.getProductList().getProduct().size(); j++) {
        Product product = response.getProductList().getProduct().get(j);
        AomsitemT aomsitemT = new YhdAomsitemTTranslator(product, storeId)
            .doTranslate(productsPriceGetResponseList.get(k), productsStockGetResponseList.get(k));
        aomsitemTs.add(aomsitemT);
        endDate = aomsitemT.getModified();
        // 每MAX_PRODUCT_CODE_LENGTH为一组
        if ((j + 1) % YhdCommonTool.MAX_PRODUCT_CODE_LENGTH == 0) {
          k++;
        }
      }
      taskService.newTransaction4Save(aomsitemTs);
    }
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), endDate);
    
    return true;
  }

  private List<String> generateProductIdList(GeneralProductsSearchResponse response) {
    List<String> productIdList = new ArrayList<String>();
    int productIdSize = response.getProductList().getProduct().size();
    String str = "";
    for (int i = 1; i <= productIdSize; i++) {
      Product product = response.getProductList().getProduct().get(i - 1);
      str += product.getProductId() + YhdCommonTool.LIST_DELIMITER;
      if (i % YhdCommonTool.MAX_PRODUCT_CODE_LENGTH == 0) {
        productIdList.add(str.substring(0, str.length() - 1));
        str = "";
      }
    }
    // 如果无法被除尽，则最后一组（肯定小于length）不会在上方的循环中被添加进list，所以在这里单独添加
    if (productIdSize % YhdCommonTool.MAX_PRODUCT_CODE_LENGTH != 0) {
      productIdList.add(str.substring(0, str.length() - 1));
    }

    return productIdList;
  }

}
