package com.digiwin.ecims.platforms.yhd.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service("yhdAomsordTPostServiceImpl")
public class YhdAomsordTPostServiceImpl implements OnTimeTaskBusiJob {
  
  @Autowired
  private TaskService taskService;
  
  @Autowired
  private BasePostService basePostService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            YhdCommonTool.ORD_POST_SCHEDULE_TYPE), AomsordT.class);
    
    return false;
  }

}
