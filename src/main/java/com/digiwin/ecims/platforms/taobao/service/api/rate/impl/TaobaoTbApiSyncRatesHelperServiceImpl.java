package com.digiwin.ecims.platforms.taobao.service.api.rate.impl;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.taobao.service.api.rate.TaobaoTbApiSyncRatesHelperService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zaregoto on 2017/1/23.
 */
@Service public class TaobaoTbApiSyncRatesHelperServiceImpl
    implements TaobaoTbApiSyncRatesHelperService {

    @Autowired private BaseDAO baseDAO;

    @Autowired private LoginfoOperateService loginfoOperateService;

    @Override
    public void saveSourceLog(TaskScheduleConfig taskScheduleConfig, String sDate, String eDate,
        String storeId, Object response) {
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, TaobaoCommonTool.STORE_TYPE,
            "[" + UseTimeEnum.CREATE_TIME + "]|taobao.traderates.get (搜索评价信息)",
            JsonUtil.format(response), SourceLogBizTypeEnum.AOMSRATET.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
    }

    @Override public AomsordT findAomsordTInDb(Long tid, Long oid) {
        return baseDAO.findUniqueResultBySQL(
            "FROM AomsordT t WHERE id='" + tid + "' AND aoms058='" + oid + "'");
    }

    @Override public void saveAomsordTsWithRates(List<AomsordT> results) {
        baseDAO.saveOrUpdateByCollectionWithLog(results);
    }
}
