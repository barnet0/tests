package com.digiwin.ecims.platforms.taobao.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service("taobaoFxAomsordTPostServiceImpl")
public class TaobaoFxAomsordTPostServiceImpl implements OnTimeTaskBusiJob {
  
  @Autowired
  private TaskService taskService;
  
  @Autowired
  private BasePostService basePostService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    basePostService.timeOutExecute(
        taskService.getTaskScheduleConfigInfo(
            TaobaoCommonTool.FX_ORD_POST_SCHEDULE_TYPE), AomsordT.class);
    
    return false;
  }

}
