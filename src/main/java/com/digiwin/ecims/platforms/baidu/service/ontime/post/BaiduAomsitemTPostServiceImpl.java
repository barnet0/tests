package com.digiwin.ecims.platforms.baidu.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;

@Service("baiduAomsitemTPostServiceImpl")
public class BaiduAomsitemTPostServiceImpl implements OnTimeTaskBusiJob {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private BasePostService basePostService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            BaiduCommonTool.ITE_POST_SCHEDULE_TYPE), AomsitemT.class);

    return false;
  }

}
