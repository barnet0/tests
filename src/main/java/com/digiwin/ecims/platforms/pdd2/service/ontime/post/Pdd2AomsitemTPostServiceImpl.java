package com.digiwin.ecims.platforms.pdd2.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;

@Service("pdd2AomsitemTPostServiceImpl")
public class Pdd2AomsitemTPostServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private BasePostService basePostService;

  @Autowired
  private TaskService taskService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    String scheduleType = Pdd2CommonTool.ITE_POST_SCHEDULE_TYPE;
    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(scheduleType), AomsitemT.class);
    
    return false;
  }
  
}
