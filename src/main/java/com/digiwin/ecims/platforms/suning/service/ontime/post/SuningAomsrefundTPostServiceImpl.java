package com.digiwin.ecims.platforms.suning.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;

@Service("suningAomsrefundTPostServiceImpl")
public class SuningAomsrefundTPostServiceImpl implements OnTimeTaskBusiJob {
  
  @Autowired
  private TaskService taskService;

  @Autowired
  private BasePostService basePostService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            SuningCommonTool.REF_POST_SCHEDULE_TYPE), AomsrefundT.class);

    return Boolean.TRUE;
  }
}
