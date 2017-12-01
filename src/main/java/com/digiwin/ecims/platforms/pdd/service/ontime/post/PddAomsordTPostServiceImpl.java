package com.digiwin.ecims.platforms.pdd.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;

@Service("pddAomsordTPostServiceImpl")
public class PddAomsordTPostServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private BasePostService basePostService;

  @Autowired
  private TaskService taskService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    String scheduleType = PddCommonTool.ORD_POST_SCHEDULE_TYPE;
    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(scheduleType), AomsordT.class);
    
    return false;
  }
  
  
}
