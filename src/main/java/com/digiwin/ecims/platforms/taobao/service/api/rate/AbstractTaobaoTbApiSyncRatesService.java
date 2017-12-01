package com.digiwin.ecims.platforms.taobao.service.api.rate;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.taobao.api.ApiException;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.response.TraderatesGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zaregoto on 2017/1/20.
 */
public abstract class AbstractTaobaoTbApiSyncRatesService implements TaobaoTbApiSyncRatesService {
    private static final Logger logger =
        LoggerFactory.getLogger(AbstractTaobaoTbApiSyncRatesService.class);

    @Override public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
        // 获取排程参数
        Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
        final String sDate = taskScheduleConfig.getLastUpdateTime();
        String eDate = taskScheduleConfig.getEndDate();
        int pageSize = taskScheduleConfig.getMaxPageSize();

        String storeId = aomsshopT.getAomsshop001();
        String appKey = aomsshopT.getAomsshop004();
        String appSecret = aomsshopT.getAomsshop005();
        String accessToken = aomsshopT.getAomsshop006();

        // 取得时间区间内总资料笔数
        long size = 0L;
        boolean sizeMoreThanSetting = false;
        do {
            size = getTaobaoRatesCount(appKey, appSecret, accessToken, sDate, eDate);
            sizeMoreThanSetting = size > taskScheduleConfig.getMaxReadRow();
            if (sizeMoreThanSetting) {
                // eDate变为sDate与eDate的中间时间
                eDate = DateTimeTool.getDivisionEndTime(sDate, eDate);
            }
        } while (sizeMoreThanSetting);

        // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
        if (taskScheduleConfig.isReCycle()) {
            // process empty, 主要是為好閱讀

        } else if (size == 0L) {
            // 区间内没有资料，才更新排程时间
            if (DateTimeTool.parse(eDate)
                .before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
                // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
                updateLastUpdateTimeIfNoData(taskScheduleConfig.getScheduleType(), eDate);
                return;
            }
        }

        // 区间内有资料，计算页数
        long pageNum = (size / pageSize) + (size % pageSize == 0 ? 0 : 1);
        List<AomsordT> results = new ArrayList<>();
        for (long i = pageNum; i > 0; i--) {
            TraderatesGetResponse response = getTaobaoRatesResponse(
                appKey, appSecret, accessToken,
                TaobaoCommonTool.API_TRADE_RATES_FIELDS,
                getRateType(), getRateRole(), i,
                TaobaoCommonTool.DEFAULT_PAGE_SIZE, DateTimeTool.parse(sDate),
                DateTimeTool.parse(eDate), true);

            saveSourceLog(taskScheduleConfig, sDate, eDate, storeId, response);

            List<TradeRate> rates = response.getTradeRates();
            if (rates == null) {
                return;
            }

            for (TradeRate rate : rates) {
                Long tid = rate.getTid();
                Long oid = rate.getOid();
                AomsordT aomsordT = findAomsordTInDb(tid, oid);
                if (aomsordT != null) {
                    if (getRateType().equals(
                        TaobaoCommonTool.TaobaoTradeRateType.GET.getRateType())) {
                        aomsordT.setAomsundefined004(CommonUtil.checkNullOrNot(rate.getContent()));
                        aomsordT.setAomsundefined005(CommonUtil.checkNullOrNot(rate.getResult()));
                    } else if (getRateType().equals(
                        TaobaoCommonTool.TaobaoTradeRateType.GIVE.getRateType())) {
                        aomsordT.setAomsundefined006(CommonUtil.checkNullOrNot(rate.getContent()));
                        aomsordT.setAomsundefined007(CommonUtil.checkNullOrNot(rate.getResult()));
                    }
                    // 获取最大的时间
                    if (rate.getCreated().after(lastUpdateTime)) {
                        lastUpdateTime = rate.getCreated();
                    }
                    results.add(aomsordT);
                }
            }
            if (results.size() > 0) {
                saveAomsordTsWithRates(results);
            }
            results.clear();
        }

        // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
        // 排程. add by xavier on 20150829
        if (taskScheduleConfig.isReCycle()) {
            // process empty, 主要是為好閱讀

        } else {
            // 更新updateTime 成為下次的sDate
            updateLastUpdateTimeIfEnd(taskScheduleConfig.getScheduleType(), sDate,
                DateTimeTool.format(lastUpdateTime));
        }
    }

    /*
     * 以下方法在实际同步评价信息时需要单独实现
     */

    protected abstract void updateLastUpdateTimeIfNoData(String scheduleType, String lastUpdateTime);

    protected abstract long getTaobaoRatesCount(String appKey, String appSecret, String accessToken,
        String sDate, String eDate) throws ApiException, IOException;

    protected abstract String getRateType();

    protected abstract String getRateRole();

    protected abstract TraderatesGetResponse getTaobaoRatesResponse(String appKey, String appSecret,
        String accessToken, String fields, String rateType, String role, Long pageNo, Long pageSize,
        Date startDate, Date endDate, Boolean useHasNext) throws ApiException, IOException;

    protected abstract void saveSourceLog(TaskScheduleConfig taskScheduleConfig, String sDate,
        String eDate, String storeId, Object response);

    protected abstract AomsordT findAomsordTInDb(Long tid, Long oid);

    protected abstract void saveAomsordTsWithRates(List<AomsordT> results);

    protected abstract void updateLastUpdateTimeIfEnd(String scheduleType, String startDate,
        String lastUpdateTime);

}
