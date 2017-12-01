package com.digiwin.ecims.platforms.icbc.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;

@Service("icbcAomsitemTPostServiceImpl")
public class IcbcAomsitemTPostServiceImpl implements OnTimeTaskBusiJob {
  
  @Autowired
  private TaskService taskService;

  @Autowired
  private BasePostService basePostService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            IcbcCommonTool.ITE_POST_SCHEDULE_TYPE), AomsitemT.class);

    return false;
  }
}
