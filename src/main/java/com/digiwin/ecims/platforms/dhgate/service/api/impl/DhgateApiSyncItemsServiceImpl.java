package com.digiwin.ecims.platforms.dhgate.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhgate.open.client.CompositeResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemList;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemListResponse;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiService;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiSyncItemsService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.dhgate.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class DhgateApiSyncItemsServiceImpl implements DhgateApiSyncItemsService {

  private static final Logger logger = LoggerFactory.getLogger(DhgateApiSyncItemsServiceImpl.class);

  @Autowired
  private DhgateApiService dhgateApiService;

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

    ItemListRequest itemListRequest = new ItemListRequest();
    itemListRequest.setPages(DhgateCommonTool.MIN_PAGE_NO + "");
    itemListRequest.setPageSize(DhgateCommonTool.DEFAULT_PAGE_SIZE + "");
    itemListRequest
        .setOrderBy(DhgateCommonTool.ItemOrderByColumnEnum.OPERATE_DATE_START.getColumnName());
    itemListRequest.setOrderByAsc(DhgateCommonTool.ItemOrderByAscEnum.DESCEND.getValue());

    // 由于API定义，需要为每个产品状态进行查找
    for (String state : DhgateCommonTool.ITEM_STATES) {
      itemListRequest.setState(state);

      CompositeResponse<ItemListResponse> listResponse = null;
      int totalSize = 0;
      boolean sizeMoreThanSetting = false;

      do {
        itemListRequest.setOperateDateStart(sDate);
        itemListRequest.setOperateDateEnd(eDate);
        listResponse = dhgateApiService.dhItemList(itemListRequest, aomsshopT.getAomsshop006());
        if (listResponse == null || !listResponse.isSuccessful()
            || listResponse.getSuccessResponse().getStatus() == null) {
          continue;
        } else {
          totalSize = Integer.parseInt(listResponse.getSuccessResponse().getTotal());
        }

        // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
        if (taskScheduleConfig.isReCycle()) {
          // process empty, 主要是為好閱讀
        } else if (totalSize == 0) {
          // 區間內沒有資料
          if (DateTimeTool.parse(eDate)
              .before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
            // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
            taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
            return true;
          }
        }

        sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
        if (sizeMoreThanSetting) {
          // eDate變為sDate與eDate的中間時間
          eDate = DateTimeTool.format(new Date(
              (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
        }
      } while (sizeMoreThanSetting);

      // 計算頁數
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

      ItemGetRequest itemGetRequest = new ItemGetRequest();

      // TODO 需要验证是否是最新的资料在第一页
      for (int i = pageNum; i > 0; i--) {
        List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();

        itemListRequest.setPages(i + "");

        listResponse = dhgateApiService.dhItemList(itemListRequest, aomsshopT.getAomsshop006());
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DhgateCommonTool.STORE_TYPE,
            "[" + state + "]|dh.item.list$2.0 卖家获取产品列表信息接口",
            JsonUtil.format(listResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
            taskScheduleConfig.getScheduleType());

        List<ItemList> itemLists = listResponse.getSuccessResponse().getItemList();

        for (ItemList item : itemLists) {
          String itemCode = item.getItemCode() + "";

          itemGetRequest.setItemCode(itemCode);

          CompositeResponse<ItemGetResponse> itemGetResponse =
              dhgateApiService.dhItemGet(itemGetRequest, aomsshopT.getAomsshop006());
          loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", DhgateCommonTool.STORE_TYPE,
              "dh.item.get$2.0 卖家获取产品详情", JsonUtil.format(itemGetResponse),
              SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
              taskScheduleConfig.getScheduleType());
          List<AomsitemT> list = new AomsitemTTranslator(itemGetResponse.getSuccessResponse())
              .doTranslate(aomsshopT.getAomsshop001());

          aomsitemTs.addAll(list);

          if (DateTimeTool.parse(item.getOperateDate()).after(lastUpdateTime)) {
            lastUpdateTime = DateTimeTool.parse(item.getOperateDate());
          }
        } // end for one order

        taskService.newTransaction4Save(aomsitemTs);
      } // end for one page

    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

}
