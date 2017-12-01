package com.digiwin.ecims.platforms.feiniu.service.api.impl;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.feiniu.enums.OrderDateType;
import com.digiwin.ecims.platforms.feiniu.enums.OrderStatus;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiService;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiSyncOrdersService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.platforms.feiniu.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.feiniu.open.api.sdk.bean.order.OrderVo;
import com.feiniu.open.api.sdk.response.order.TradeSoldGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zaregoto on 2017/1/26.
 */
@Service public class FeiniuApiSyncOrdersServiceImpl implements FeiniuApiSyncOrdersService {

    private static final Logger logger =
        LoggerFactory.getLogger(FeiniuApiSyncOrdersServiceImpl.class);

    @Autowired private LoginfoOperateService loginfoOperateService;

    @Autowired private TaskService taskService;

    @Autowired private FeiniuApiService feiniuApiService;

    @Autowired private AomsShopService aomsShopService;

    @Override public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
        syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
    }

    @Override public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig,
        AomsshopT aomsshopT) throws Exception {
        // 参数设定
        Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
        String sDate = taskScheduleConfig.getLastUpdateTime();
        String eDate = taskScheduleConfig.getEndDate();
        int pageSize = taskScheduleConfig.getMaxPageSize();

        String storeId = aomsshopT.getAomsshop001();
        String appKey = aomsshopT.getAomsshop004();
        String appSecret = aomsshopT.getAomsshop005();
        String accessToken = aomsshopT.getAomsshop006();

        for (OrderStatus orderStatus : OrderStatus.values()) {
            // 取得时间区间内总资料笔数
            long totalSize = 0L;
            boolean sizeMoreThanSetting = false;
            do {
                totalSize = getOrdersCount(
                    OrderDateType.UPDATE_TIME, orderStatus, appKey, appSecret, accessToken,
                    sDate, eDate);

                sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
                if (sizeMoreThanSetting) {
                    // eDate變為sDate與eDate的中間時間
                    eDate = DateTimeTool.format(new Date(
                        (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime())
                            / 2));
                }
            } while (sizeMoreThanSetting);

            // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
            if (taskScheduleConfig.isReCycle()) {
                // process empty, 主要是為好閱讀

            } else if (totalSize == 0L) {
                // 区间内没有资料
                if (DateTimeTool.parse(eDate)
                    .before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
                    // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
                    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
                    return true;
                }
            } else if (totalSize == FeiniuCommonTool.NO_COUNT_RETURNED) {
                // API调用异常，则把sdate+1天回写到排程
                String tomorrow = DateTimeTool.getAfterDays(sDate, 1);
                if (DateTimeTool.isBefore(tomorrow, taskScheduleConfig.getSysDate())) {
                    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), tomorrow);
                    return false;
                }
            }

            // 區間內有資料， 計算頁數
            long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

            List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
            // 針對每一頁(倒序)的所有資料新增
            for (long i = pageNum; i > 0; i--) {
                TradeSoldGetResponse response =
                    feiniuApiService.fnTradesSoldGet(
                        appKey, appSecret, accessToken, orderStatus.getCode(),
                        sDate, eDate, OrderDateType.UPDATE_TIME.getCode(),
                        i, (long)pageSize);

                loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
                    FeiniuCommonTool.STORE_TYPE,
                    "[" + UseTimeEnum.UPDATE_TIME + "]|" + orderStatus + "|fn.trades.sold.get 查询订单列表POST",
                    JsonUtil.formatByMilliSecond(response),
                    SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
                    taskScheduleConfig.getScheduleType());

                if (response.getData() != null) {
                    for (OrderVo orderVo : response.getData().getPageVoList()) {
                        List<AomsordT> list = new AomsordTTranslator(orderVo).doTranslate(storeId);

                        if (orderVo.getUpdateDt().after(lastUpdateTime)) {
                            lastUpdateTime = orderVo.getUpdateDt();
                        }

                        aomsordTs.addAll(list);
                    }
                    taskService.newTransaction4Save(aomsordTs);
                    // 清空列表，为下一页资料作准备
                    aomsordTs.clear();
                } // end-if

            }
        }


        // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
        if (taskScheduleConfig.isReCycle()) {
            // process empty, 主要是為好閱讀

        } else {
            // 更新updateTime 成為下次的sDate
            logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
            taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
                DateTimeTool.format(lastUpdateTime));
        }

        return true;
    }

    @Override public Long syncOrdersByIncremental(String startDate, String endDate, String storeId,
        String scheduleType) throws Exception {
        return syncOrders(OrderDateType.UPDATE_TIME, startDate, endDate, storeId, scheduleType);
    }

    @Override
    public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken,
        String startDate, String endDate) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override public Long syncOrdersByCreated(String startDate, String endDate, String storeId,
        String scheduleType) throws Exception {
        return syncOrders(OrderDateType.CREATE_TIME, startDate, endDate, storeId, scheduleType);
    }

    @Override public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken,
        String startDate, String endDate) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
        return null;
    }

    private Long syncOrders(OrderDateType dateType, String startDate, String endDate, String storeId,
        String scheduleType) throws Exception {
        // 参数设定
        Long pageSize = FeiniuCommonTool.DEFAULT_PAGE_SIZE;
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);
        String appKey = esv.getAppKey();
        String appSecret = esv.getAppSecret();
        String accessToken = esv.getAccessToken();

        long totalSize = 0L;
        for (OrderStatus orderStatus : OrderStatus.values()) {
            // 取得时间区间内总资料笔数
            totalSize = getOrdersCount(dateType, orderStatus,
                appKey, appSecret, accessToken,
                startDate, endDate);

            // 區間內有資料， 計算頁數
            long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

            List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
            // 針對每一頁(倒序)的所有資料新增
            for (long i = pageNum; i > 0; i--) {
                TradeSoldGetResponse response =
                    feiniuApiService.fnTradesSoldGet(
                        appKey, appSecret, accessToken, orderStatus.getCode(),
                        startDate, endDate, dateType.getCode(),
                        i, (long)pageSize);

                loginfoOperateService.newTransaction4SaveSource(startDate, endDate, FeiniuCommonTool.STORE_TYPE,
                    "[" + dateType + "]|" + orderStatus + "|fn.trades.sold.get 查询订单列表POST",
                    JsonUtil.formatByMilliSecond(response),
                    SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
                    scheduleType);

                if (response.getData() != null) {
                    for (OrderVo orderVo : response.getData().getPageVoList()) {
                        List<AomsordT> list = new AomsordTTranslator(orderVo).doTranslate(storeId);

                        aomsordTs.addAll(list);
                    }
                    taskService.newTransaction4Save(aomsordTs);
                    // 清空列表，为下一页资料作准备
                    aomsordTs.clear();
                } // end-if

            }
        }


        return totalSize;
    }

    private Long getOrdersCount(OrderDateType dateType, OrderStatus orderStatus,
        String appKey, String appSecret, String accessToken,
        String startDate, String endDate) throws Exception {
        TradeSoldGetResponse response =
            feiniuApiService.fnTradesSoldGet(
                appKey, appSecret, accessToken, orderStatus.getCode(),
                startDate, endDate, dateType.getCode(),
                FeiniuCommonTool.MIN_PAGE_NO, FeiniuCommonTool.MIN_PAGE_SIZE);
        if (response == null
            || (!response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE))) {
            loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
                "FeiniuApiSyncOrdersServiceImpl#getOrdersCount",
                LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
                new Date(), JsonUtil.format(response), response.getMsg(), "", "");
            return FeiniuCommonTool.NO_COUNT_RETURNED;
        }
        Long count = (long)response.getData().getTotalRows();

        return count;
    }
}
