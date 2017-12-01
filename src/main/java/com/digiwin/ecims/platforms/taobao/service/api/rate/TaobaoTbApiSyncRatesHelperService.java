package com.digiwin.ecims.platforms.taobao.service.api.rate;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

import java.util.List;

/**
 * 辅助Service,解决在抽象类中调用标记为@Service的子类方法，且子类成员使用@Autowired注入其他组件，
 * 在使用组件时会返回无事务开启的异常
 * Created by zaregoto on 2017/1/23.
 */
public interface TaobaoTbApiSyncRatesHelperService {

    void saveSourceLog(TaskScheduleConfig taskScheduleConfig, String sDate, String eDate,
        String storeId, Object response);

    AomsordT findAomsordTInDb(Long tid, Long oid);

    void saveAomsordTsWithRates(List<AomsordT> results);

}
