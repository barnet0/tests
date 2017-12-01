package com.digiwin.ecims.platforms.yunji.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;

@Service("yunjiAomsordTPostServiceImpl")
public class YunjiAomsordTPostServiceImpl implements OnTimeTaskBusiJob {

	  @Autowired
	  private BasePostService basePostService;

	  @Autowired
	  private TaskService taskService;
	
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
	    String scheduleType = YunjiCommonTool.ORD_POST_SCHEDULE_TYPE;
	    basePostService.timeOutExecute(
	        taskService.getTaskScheduleConfigInfo(scheduleType), AomsordT.class);
	    
	    return false;
	}

}
