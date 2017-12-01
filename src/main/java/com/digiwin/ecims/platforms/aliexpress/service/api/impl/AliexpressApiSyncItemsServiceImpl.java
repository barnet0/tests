package com.digiwin.ecims.platforms.aliexpress.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.category.AeopAttributeDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.item.AeopAEProductDisplaySampleDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.request.category.GetChildAttributesResultByPostCateIdAndPathRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.FindAeProductByIdRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.FindProductInfoListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.category.GetChildAttributesResultByPostCateIdAndPathResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindAeProductByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindProductInfoListQueryResponse;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiSyncItemsService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressCacheService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool.ItemStatusTypeEnum;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class AliexpressApiSyncItemsServiceImpl implements AliexpressApiSyncItemsService {

  private static final Logger logger = LoggerFactory.getLogger(AliexpressApiSyncItemsServiceImpl.class);

  @Autowired
  private AliexpressApiService aliexpressApiService;

  @Autowired
  private AliexpressCacheService aliexpressCacheService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    FindProductInfoListQueryRequest listRequest = new FindProductInfoListQueryRequest();
    listRequest.setCurrentPage(AliexpressCommonTool.MIN_PAGE_NO);
    listRequest.setPageSize(AliexpressCommonTool.DEFAULT_PAGE_SIZE);

    for (ItemStatusTypeEnum itemStatusEnum : ItemStatusTypeEnum.values()) {
      listRequest.setProductStatusType(itemStatusEnum.getStatus());

      FindProductInfoListQueryResponse listResponse = null;
      int totalSize = 0;

      listResponse = aliexpressApiService.ApiFindProductInfoListQuery(listRequest, appKey,
          appSecret, accessToken);
      if (listResponse == null || listResponse.getProductCount() == 0) {
        return false;
      } else {
        totalSize = listResponse.getProductCount();
      }

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀
      } else if (totalSize == 0) {
        // 區間內沒有資料
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return true;
        }
      }

      // 計算頁數
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

      // TODO 需要验证是否是最新的资料在第一页
      List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
      for (int i = pageNum; i > 0; i--) {
        listRequest.setCurrentPage(i);

        listResponse = aliexpressApiService.ApiFindProductInfoListQuery(listRequest, appKey,
            appSecret, accessToken);
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
            AliexpressCommonTool.STORE_TYPE, "api.findProductInfoListQuery -- version: 1 商品列表查询接口",
            JsonUtil.format(listResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        FindAeProductByIdRequest detailRequest = new FindAeProductByIdRequest();
        FindAeProductByIdResponse detailResponse = null;
        GetChildAttributesResultByPostCateIdAndPathRequest childAttrResultRequest =
            new GetChildAttributesResultByPostCateIdAndPathRequest();
        GetChildAttributesResultByPostCateIdAndPathResponse childAttrResultResponse = null;
        List<AeopAttributeDTO> attributeDTOs = null;
        for (AeopAEProductDisplaySampleDTO itemBaseInfo : listResponse
            .getAeopAEProductDisplayDTOList()) {
          Long productId = itemBaseInfo.getProductId();

          detailRequest.setProductId(productId);
          detailResponse = aliexpressApiService.ApiFindAeProductById(detailRequest, appKey,
              appSecret, accessToken);
          loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
              AliexpressCommonTool.STORE_TYPE,
              "api.findAeProductById  -- version: 1 根据商品id查询单个商品的详细信息",
              JsonUtil.format(listResponse),
              SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
              taskScheduleConfig.getScheduleType());
          int categoryId = detailResponse.getCategoryId();
          if (aliexpressCacheService.isKeyExist(categoryId + "")) {
            attributeDTOs = (List<AeopAttributeDTO>) aliexpressCacheService.get(categoryId + "");
          } else {
            childAttrResultRequest.setCateId(categoryId);
            childAttrResultResponse =
                aliexpressApiService.ApiGetChildAttributesResultByPostCateIdAndPath(
                    childAttrResultRequest, appKey, appSecret, accessToken);
            attributeDTOs = childAttrResultResponse.getAttributes();
            aliexpressCacheService.put(categoryId + "", attributeDTOs);
          }

          List<AomsitemT> list =
              new AomsitemTTranslator(detailResponse, attributeDTOs).doTranslate(storeId);

          aomsitemTs.addAll(list);

        }
        taskService.newTransaction4Save(aomsitemTs);
        aomsitemTs.clear();
      } // end-for one page

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀

      } else {
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
      }
    }
    return true;
  }

}
