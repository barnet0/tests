package com.digiwin.ecims.system.service.ontime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.PostTaskDefaultParamEnum;
import com.digiwin.ecims.system.service.ReLogProcessService;


/**
 * 日志中的错误记录，定时任务重新推送
 * 
 * @author hopelee
 *
 */
@Service("aomsRePushServiceImpl")
public class LogOnTimeRePushServiceImpl implements OnTimeTaskBusiJob {

  private static final String ORDER_REPOST_TASK_SCHEDULE = "Re" + AomsordT.BIZ_NAME + "Post";

  private static final String REFUND_REPOST_TASK_SCHEDULE = "Re" + AomsrefundT.BIZ_NAME + "Post";

  private static final String ITEM_REPOST_TASK_SCHEDULE = "Re" + AomsitemT.BIZ_NAME + "Post";

  @Autowired
  private TaskService taskService;

  @Autowired
  private ParamSystemService paramSystemService;
  
  @Autowired
  private ReLogProcessService rePushService;

  @Autowired
  private BasePostService basePostService;
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    Class clazz = null;
    String bizName = "";
    String scheduleType = "";
    
    clazz = AomsordT.class;
    bizName = AomsordT.BIZ_NAME;
    scheduleType = ORDER_REPOST_TASK_SCHEDULE;
    doWork(bizName, clazz, scheduleType);
    

    clazz = AomsrefundT.class;
    bizName = AomsrefundT.BIZ_NAME;
    scheduleType = REFUND_REPOST_TASK_SCHEDULE;
    doWork(bizName, clazz, scheduleType);

    clazz = AomsitemT.class;
    bizName = AomsitemT.BIZ_NAME;
    scheduleType = ITEM_REPOST_TASK_SCHEDULE;
    doWork(bizName, clazz, scheduleType);

    return false;
  }

  private <T> void doWork(String bizName, Class<T> clazz, String scheduleType) throws RejectedExecutionException, NullPointerException, IOException, InterruptedException {
    List<T> repostDataList = rePushService.getReAomsData(bizName, clazz);
    TaskScheduleConfig repostTask = taskService.getTaskScheduleConfigInfo(scheduleType);
    prepareTaskIfNotExist(repostTask, clazz);
    if (repostDataList != null && repostDataList.size() != 0) {
      taskService.newTransaction4SaveLastRunTime(scheduleType, null);
      basePostService.doRePost(taskService.getTaskScheduleConfigInfo(scheduleType), repostDataList, clazz);
    }
  }
  
  private <T> void prepareTaskIfNotExist(TaskScheduleConfig repostTask, Class<T> clazz) {
    if (StringUtils.isEmpty(repostTask.getPostUrl())
        || StringUtils.isEmpty(repostTask.getPostIp())
        || StringUtils.isEmpty(repostTask.getPlant())
        || StringUtils.isEmpty(repostTask.getUser())
        || StringUtils.isEmpty(repostTask.getService())
        || StringUtils.isEmpty(repostTask.getPlant())
        || StringUtils.isEmpty(repostTask.getEntId())
        || StringUtils.isEmpty(repostTask.getCompanyId())) {
      repostTask.setPlant((String) paramSystemService
          .getSysParamByKey(PostTaskDefaultParamEnum.PLANT.getParamKeyName()));
      repostTask.setEntId((String) paramSystemService
          .getSysParamByKey(PostTaskDefaultParamEnum.ENT_ID.getParamKeyName()));
      repostTask.setCompanyId((String) paramSystemService
          .getSysParamByKey(PostTaskDefaultParamEnum.COMPANY_ID.getParamKeyName()));
      repostTask.setUser((String) paramSystemService
          .getSysParamByKey(PostTaskDefaultParamEnum.USER.getParamKeyName()));
      String postUrlPath = (String) paramSystemService
          .getSysParamByKey(PostTaskDefaultParamEnum.POST_URL_PATH.getParamKeyName());
      String postIp = (String) paramSystemService
          .getSysParamByKey(PostTaskDefaultParamEnum.POST_IP.getParamKeyName());
      repostTask.setPostUrl("http://" + postIp + postUrlPath);
      repostTask.setPostIp(postIp);
      switch (clazz.getSimpleName()) {
        case AomsordT.BIZ_NAME:
          repostTask.setService((String) paramSystemService
              .getSysParamByKey(PostTaskDefaultParamEnum.ORDER_SERVICE.getParamKeyName()));
          break;
        case AomsrefundT.BIZ_NAME:
          repostTask.setService((String) paramSystemService
              .getSysParamByKey(PostTaskDefaultParamEnum.REFUND_SERVICE.getParamKeyName()));
          break;
        case AomsitemT.BIZ_NAME:
          repostTask.setService((String) paramSystemService
              .getSysParamByKey(PostTaskDefaultParamEnum.ITEM_SERVICE.getParamKeyName()));
          break;
        default:
          break;
      }
      taskService.saveTaskTaskScheduleConfig(repostTask);
    }
  }
}
