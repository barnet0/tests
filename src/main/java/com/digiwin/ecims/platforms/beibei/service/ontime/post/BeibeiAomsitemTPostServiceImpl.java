package com.digiwin.ecims.platforms.beibei.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;

@Service("beibeiAomsitemTPostServiceImpl")
public class BeibeiAomsitemTPostServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private BasePostService basePostService;

  @Autowired
  private TaskService taskService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    String scheduleType = BeibeiCommonTool.ITE_POST_SCHEDULE_TYPE;
    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(scheduleType), AomsitemT.class);
    
    return false;
  }

}
