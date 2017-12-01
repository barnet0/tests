package com.digiwin.ecims.platforms.icbc.service.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail.IcbcProductDetailResponse;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist.IcbcProductListResponse;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsitemTTranslator;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiSyncItemsService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class IcbcApiSyncItemsServiceImpl implements IcbcApiSyncItemsService {

  @Autowired
  private IcbcApiService icbcApiService;

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
    // 參數設定
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    IcbcProductListResponse response = icbcApiService.icbcb2cProductList(appKey, appSecret,
        accessToken, null, null, sDate, eDate, null, null, null);

    // add by mowj 20150824
    // modi by mowj 20150825 增加biztype
    loginfoOperateService.newTransaction4SaveSource(sDate, eDate, IcbcCommonTool.STORE_TYPE,
        "icbcb2c.product.list 商品列表查询接口", response, SourceLogBizTypeEnum.AOMSITEMT.getValueString(),
        storeId, taskScheduleConfig.getScheduleType());

    // 若區間內沒有資料，將結束時間設為下一次的起始時間，結束
    if (response == null || response.getBody() == null
        || response.getBody().getProductList() == null) {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
      return false;
    }

    int totalSize = response.getBody().getProductList().getProductList().size();// 資料總筆數
    int maxSizePerPage = taskScheduleConfig.getMaxPageSize(); // 每一頁最大資料量
    int totalPage = (totalSize / maxSizePerPage) + (totalSize % maxSizePerPage == 0 ? 0 : 1);// 總頁數
    int finalPageIndex = totalPage - 1;// 最後一頁的索引值

    // 以頁為單位做商品的同步(因為工商批量取商品的API並無分頁觀念，無需考慮漏單問題，因此不用倒序同步)
    for (int i = 0; i < totalPage; i++) {
      // 取出該頁所有鋪貨資料編號，組成商品編號字串. ex:123,124,...,134(字串長度不可超過512)
      String productIds = "";
      int startIndex = i * maxSizePerPage;// 起始索引值
      int length =
          (i == finalPageIndex) ? (totalSize - maxSizePerPage * finalPageIndex) : maxSizePerPage;// 長度
      for (int j = startIndex; j < startIndex + length; j++) {
        productIds += response.getBody().getProductList().getProductList().get(j).getProductId();
        if (j < startIndex + length - 1) {
          productIds += ",";
        }
      }

      // 取得商品詳情
      IcbcProductDetailResponse icbcProductDetailResponse =
          icbcApiService.icbcb2cProductDetail(appKey, appSecret, accessToken, productIds);

      // add by mowj 20150824
      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", IcbcCommonTool.STORE_TYPE,
          "icbcb2c.product.detail 商品详情查询", icbcProductDetailResponse,
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      // 將商品詳情轉換成中台POJO
      List<AomsitemT> aomsitemTs =
          new IcbcAomsitemTTranslator(icbcProductDetailResponse, aomsshopT.getAomsshop001())
              .doTranslate();

      // 存檔
      taskService.newTransaction4Save(aomsitemTs);
    }

    // 將截止時間當作下一次的起始時間(商品詳情中沒有更新時間的欄位...所以不用取最晚更新時間)
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate, eDate);
    
    return true;
  }

}
