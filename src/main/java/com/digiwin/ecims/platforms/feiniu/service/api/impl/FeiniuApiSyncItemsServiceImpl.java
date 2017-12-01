package com.digiwin.ecims.platforms.feiniu.service.api.impl;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.feiniu.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiService;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiSyncItemsService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.feiniu.open.api.sdk.bean.item.ItemInventoryListVo;
import com.feiniu.open.api.sdk.response.item.ItemGetResponse;
import com.feiniu.open.api.sdk.response.item.ItemInventoryGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaregoto on 2017/1/26.
 */
@Service
public class FeiniuApiSyncItemsServiceImpl implements FeiniuApiSyncItemsService {

    private static final Logger logger =
        LoggerFactory.getLogger(FeiniuApiSyncItemsServiceImpl.class);

    @Autowired private LoginfoOperateService loginfoOperateService;

    @Autowired private TaskService taskService;

    @Autowired private FeiniuApiService feiniuApiService;

    @Autowired private AomsShopService aomsShopService;

    @Override public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
        syncItems(taskScheduleConfig, aomsshopT);
    }

    @Override public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
        // 参数设定
        //    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
        //    String sDate = taskScheduleConfig.getLastUpdateTime();
        String eDate = taskScheduleConfig.getEndDate();
        int pageSize = taskScheduleConfig.getMaxPageSize();

        String storeId = aomsshopT.getAomsshop001();
        String appKey = aomsshopT.getAomsshop004();
        String appSecret = aomsshopT.getAomsshop005();
        String accessToken = aomsshopT.getAomsshop006();

        // 取得时间区间内总资料笔数
        long totalSize = 0L;
        totalSize = feiniuApiService.fnItemInventoryGet(
            appKey, appSecret, accessToken,
            StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY,
            FeiniuCommonTool.MIN_PAGE_NO, FeiniuCommonTool.MIN_PAGE_SIZE).getData().getTotalRows();

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

        // 區間內有資料， 計算頁數
        long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

        List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
        // 針對每一頁(倒序)的所有資料新增
        for (long i = pageNum; i > 0; i--) {
            ItemInventoryGetResponse listResponse =
                feiniuApiService.fnItemInventoryGet(
                    appKey, appSecret, accessToken,
                    StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY,
                    i, (long)pageSize);
            loginfoOperateService.newTransaction4SaveSource(
                "N/A", "N/A",
                FeiniuCommonTool.STORE_TYPE,
                "fn.item.inventory.get 获取当前会话用户的商品列表",
                JsonUtil.formatByMilliSecond(listResponse),
                SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
                taskScheduleConfig.getScheduleType());

            for (ItemInventoryListVo itemInventoryListVo : listResponse.getData().getList()) {
                String goodsId = itemInventoryListVo.getGoodsId();

                ItemGetResponse detailResponse =
                    feiniuApiService.fnItemGet(
                        appKey, appSecret, accessToken, FeiniuCommonTool.ITEM_FIELDS,
                        goodsId);
                loginfoOperateService.newTransaction4SaveSource(
                    "N/A", "N/A",
                    FeiniuCommonTool.STORE_TYPE,
                    "fn.item.get 查询一个商品POST",
                    JsonUtil.formatByMilliSecond(detailResponse),
                    SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
                    taskScheduleConfig.getScheduleType());

                List<AomsitemT> list =
                    new AomsitemTTranslator(itemInventoryListVo, detailResponse)
                        .doTranslate(storeId);

                aomsitemTs.addAll(list);
            }
            taskService.newTransaction4Save(aomsitemTs);
            // 清空列表，为下一页资料作准备
            aomsitemTs.clear();
        }

        // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
        if (taskScheduleConfig.isReCycle()) {
            // process empty, 主要是為好閱讀

        } else {
            // 更新updateTime 成為下次的sDate
            taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        }

        return true;
    }
}
