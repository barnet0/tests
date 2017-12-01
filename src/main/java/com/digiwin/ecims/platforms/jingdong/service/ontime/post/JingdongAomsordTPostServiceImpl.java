package com.digiwin.ecims.platforms.jingdong.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;

@Service("jingdongAomsordTPostServiceImpl")
public class JingdongAomsordTPostServiceImpl implements OnTimeTaskBusiJob {
  @Autowired
  private TaskService taskService;
  
  @Autowired
  private BasePostService basePostService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            JingdongCommonTool.ORD_POST_SCHEDULE_TYPE), AomsordT.class);

    return false;
  }

}
