package com.digiwin.ecims.platforms.feiniu.service.api.impl;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.refund.MyFeiniuRefundGetDetailInfoResponse;
import com.digiwin.ecims.platforms.feiniu.enums.RefundDateType;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiService;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiSyncRefundsService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.platforms.feiniu.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.feiniu.open.api.sdk.bean.refund.RefundsItemVo;
import com.feiniu.open.api.sdk.response.refund.RefundGetDetailInfoResponse;
import com.feiniu.open.api.sdk.response.refund.RefundsRreceiveNeedGetListResponse;
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
@Service
public class FeiniuApiSyncRefundsServiceImpl implements FeiniuApiSyncRefundsService {

    private static final Logger logger =
        LoggerFactory.getLogger(FeiniuApiSyncRefundsServiceImpl.class);

    @Autowired private LoginfoOperateService loginfoOperateService;

    @Autowired private TaskService taskService;

    @Autowired private FeiniuApiService feiniuApiService;

    @Autowired private AomsShopService aomsShopService;

    @Override public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
        syncRefunds(taskScheduleConfig, aomsshopT);
    }

    @Override public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
        throws Exception {
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
        long totalSize = 0L;
        boolean sizeMoreThanSetting = false;
        do {
            totalSize = getCreatedCount(appKey, appSecret, accessToken, sDate, eDate);

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

        // 區間內有資料， 計算頁數
        long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

        List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
        // 針對每一頁(倒序)的所有資料新增
        for (long i = pageNum; i > 0; i--) {
            RefundsRreceiveNeedGetListResponse listResponse =
                feiniuApiService.fnRefundsReceiveNeedGetList(
                    appKey, appSecret, accessToken,
                    i, (long)pageSize,
                    StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY,
                    sDate, eDate, StringTool.EMPTY, StringTool.EMPTY,
                    RefundDateType.UPDATE_TIME.getCode());

            loginfoOperateService.newTransaction4SaveSource(
                sDate, eDate, FeiniuCommonTool.STORE_TYPE,
                "[" + UseTimeEnum.UPDATE_TIME + "]|fn.refunds.receiveNeed.getList 查询所有需要退款的列表POST",
                JsonUtil.formatByMilliSecond(listResponse),
                SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
                taskScheduleConfig.getScheduleType());

            if (listResponse != null
                && listResponse.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)) {
                for (RefundsItemVo refundsItemVo : listResponse.getData().getPageVoList()) {
                    String rssSeq = refundsItemVo.getRssSeq();
                    Date submitDt = refundsItemVo.getSubmitDate();

                    MyFeiniuRefundGetDetailInfoResponse detailInfoResponse =
                        feiniuApiService.fnRefundGetDetailInfo(
                            appKey, appSecret, accessToken, rssSeq);
                    loginfoOperateService.newTransaction4SaveSource(
                        sDate, eDate, FeiniuCommonTool.STORE_TYPE,
                        "fn.refund.getDetailInfo 查询单笔退款详情POST",
                        JsonUtil.formatByMilliSecond(detailInfoResponse),
                        SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
                        taskScheduleConfig.getScheduleType());

                    List <AomsrefundT> list =
                        new AomsrefundTTranslator(refundsItemVo, detailInfoResponse)
                        .doTranslate(storeId);

                    if (submitDt.after(lastUpdateTime)) {
                        lastUpdateTime = submitDt;
                    }

                    aomsrefundTs.addAll(list);
                }
                taskService.newTransaction4Save(aomsrefundTs);
                // 清空列表，为下一页资料作准备
                aomsrefundTs.clear();
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

    @Override public Boolean syncRefunds(String startDate, String endDate, String storeId)
        throws Exception {
        return null;
    }

    @Override public Long getCreatedCount(String appKey, String appSecret, String accessToken,
        String startDate, String endDate) throws Exception {
        RefundsRreceiveNeedGetListResponse response =
            feiniuApiService.fnRefundsReceiveNeedGetList(
                appKey, appSecret, accessToken,
                FeiniuCommonTool.MIN_PAGE_NO,
                FeiniuCommonTool.MIN_PAGE_SIZE,
                StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY,
                startDate, endDate, StringTool.EMPTY, StringTool.EMPTY,
                RefundDateType.UPDATE_TIME.getCode());
        if (response == null
            || !response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)) {
            loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
                "FeiniuApiSyncRefundsServiceImpl#getCreatedCount",
                LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
                new Date(), "获取数据异常", response.getMsg(), "", "");
            return 0L;
        }
        Long count = (long)response.getData().getTotalRows();

        return count;
    }
}
