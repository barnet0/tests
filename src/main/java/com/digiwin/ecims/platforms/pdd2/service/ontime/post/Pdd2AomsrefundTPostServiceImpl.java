package com.digiwin.ecims.platforms.pdd2.service.ontime.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
/**
 * 退货
 * @author by cjp
 *
 */
@Service("pdd2AomsrefundTPostServiceImpl")
public class Pdd2AomsrefundTPostServiceImpl implements OnTimeTaskBusiJob {
	
	@Autowired
	  private BasePostService basePostService;

	  @Autowired
	  private TaskService taskService;
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		String scheduleType = Pdd2CommonTool.REF_POST_SCHEDULE_TYPE;
	    basePostService.timeOutExecute(
	        taskService.getTaskScheduleConfigInfo(scheduleType), AomsrefundT.class);
		return false;
	}

}
