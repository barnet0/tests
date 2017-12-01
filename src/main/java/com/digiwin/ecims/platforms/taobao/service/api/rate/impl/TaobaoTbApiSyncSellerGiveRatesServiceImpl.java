package com.digiwin.ecims.platforms.taobao.service.api.rate.impl;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.api.rate.AbstractTaobaoTbApiSyncRatesService;
import com.digiwin.ecims.platforms.taobao.service.api.rate.TaobaoTbApiSyncRatesHelperService;
import com.digiwin.ecims.platforms.taobao.service.api.rate.TaobaoTbApiSyncSellerGiveRatesService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.taobao.api.ApiException;
import com.taobao.api.response.TraderatesGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by zaregoto on 2017/1/18.
 */
@Service public class TaobaoTbApiSyncSellerGiveRatesServiceImpl
    extends AbstractTaobaoTbApiSyncRatesService implements TaobaoTbApiSyncSellerGiveRatesService {

    private static final Logger logger =
        LoggerFactory.getLogger(TaobaoTbApiSyncSellerGiveRatesServiceImpl.class);

    @Autowired private TaobaoApiService taobaoApiService;

    @Autowired private TaobaoTbApiSyncRatesHelperService taobaoTbApiSyncRatesHelperService;

    @Autowired private TaskService taskService;

    @Autowired private LoginfoOperateService loginfoOperateService;

    @Override public void updateLastUpdateTimeIfNoData(String scheduleType, String lastUpdateTime) {
        taskService.updateLastUpdateTime(scheduleType, lastUpdateTime);
    }

    @Override public long getTaobaoRatesCount(String appKey, String appSecret, String accessToken,
        String sDate, String eDate) throws ApiException, IOException {
        TraderatesGetResponse response = getTaobaoRatesResponse(appKey, appSecret, accessToken,
            TaobaoCommonTool.API_TRADE_RATES_MIN_FILEDS, getRateType(), getRateRole(),
            TaobaoCommonTool.MIN_PAGE_NO, TaobaoCommonTool.MIN_PAGE_SIZE, DateTimeTool.parse(sDate),
            DateTimeTool.parse(eDate), false);
        Long totalSize = 0L;
        if (response == null || response.getSubCode() != null) {
            loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
                "TaobaoTbApiSyncSellerGiveRatesServiceImpl#getTaobaoRatesCount",
                LogInfoBizTypeEnum.ECI_REQUEST.getValueString(), new Date(), "获取数据异常",
                response.getSubMsg(), "", "");
        } else {
            totalSize = response.getTotalResults();
        }

        return totalSize;
    }

    @Override public String getRateType() {
        return TaobaoCommonTool.TaobaoTradeRateType.GIVE.getRateType();
    }

    @Override public String getRateRole() {
        return TaobaoCommonTool.TaobaoTradeRateRole.SELLER.getRole();
    }

    @Override public TraderatesGetResponse getTaobaoRatesResponse(String appKey, String appSecret,
        String accessToken, String fields, String rateType, String role, Long pageNo, Long pageSize,
        Date startDate, Date endDate, Boolean useHasNext) throws ApiException, IOException {
        return taobaoApiService
            .taobaoTraderatesGet(appKey, appSecret, accessToken, fields, rateType, role,
                StringTool.EMPTY, pageNo, pageSize, startDate, endDate, null, useHasNext, null);
    }

    @Override
    public void saveSourceLog(TaskScheduleConfig taskScheduleConfig, String sDate, String eDate,
        String storeId, Object response) {
        taobaoTbApiSyncRatesHelperService
            .saveSourceLog(taskScheduleConfig, sDate, eDate, storeId, response);
    }

    @Override public AomsordT findAomsordTInDb(Long tid, Long oid) {
        return taobaoTbApiSyncRatesHelperService.findAomsordTInDb(tid, oid);
    }

    @Override public void saveAomsordTsWithRates(List<AomsordT> results) {
        taobaoTbApiSyncRatesHelperService.saveAomsordTsWithRates(results);
    }

    @Override public void updateLastUpdateTimeIfEnd(String scheduleType, String startDate,
        String lastUpdateTime) {
        taskService.updateLastUpdateTime(scheduleType, startDate, lastUpdateTime);
    }
}
