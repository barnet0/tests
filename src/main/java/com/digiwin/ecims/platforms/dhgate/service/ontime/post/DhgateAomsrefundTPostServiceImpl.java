package com.digiwin.ecims.platforms.dhgate.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;

@Service("dhgateAomsrefundTPostServiceImpl")
public class DhgateAomsrefundTPostServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private BasePostService basePostService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    
    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            DhgateCommonTool.REF_POST_SCHEDULE_TYPE), AomsrefundT.class);

    return false;
  }

}
