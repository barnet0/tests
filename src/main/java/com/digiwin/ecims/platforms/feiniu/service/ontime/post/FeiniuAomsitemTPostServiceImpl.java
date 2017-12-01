package com.digiwin.ecims.platforms.feiniu.service.ontime.post;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zaregoto on 2017/1/25.
 */
@Service("feiniuAomsitemTPostServiceImpl") public class FeiniuAomsitemTPostServiceImpl
    implements OnTimeTaskBusiJob {

    @Autowired private BasePostService basePostService;

    @Autowired private TaskService taskService;

    @Override public boolean timeOutExecute(String... args) throws Exception {
        String scheduleType = FeiniuCommonTool.ITE_POST_SCHEDULE_TYPE;
        basePostService
            .timeOutExecute(taskService.getTaskScheduleConfigInfo(scheduleType), AomsitemT.class);

        return false;
    }
}
