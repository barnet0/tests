package com.digiwin.ecims.platforms.yunji.service.ontime.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;


@Service("yunjiAomsrefundTPostServiceImpl")
public class YunjiAomsrefundTPostServiceImpl implements OnTimeTaskBusiJob {
	@Autowired
	  private BasePostService basePostService;

	  @Autowired
	  private TaskService taskService;
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		String scheduleType = YunjiCommonTool.REF_POST_SCHEDULE_TYPE;
	    basePostService.timeOutExecute(
	        taskService.getTaskScheduleConfigInfo(scheduleType), AomsrefundT.class);
		return false;
	}
}
