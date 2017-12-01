package com.digiwin.ecims.ontime.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

/**
 * 该作业实例的作用是，如果存在已经执行的相同的任务，直接跳过，不继续执行
 *
 */

public class JobExecuter1 extends JobExecuter {
  private volatile static Map<String, Boolean> map = new HashMap<String, Boolean>();
  
  private static final Logger logger = LoggerFactory.getLogger(JobExecuter1.class);

  public JobExecuter1() {}

  @Override
  public void execute(JobExecutionContext jobExecCtx) throws JobExecutionException {
    String jobName = jobExecCtx.getTrigger().getKey().getName();
    try {
      JobDetailImpl detail = (JobDetailImpl) jobExecCtx.getJobDetail();
      if (map.get(jobName) != null && map.get(jobName)) {
        logger.info("正在运行：{}", jobName);
        return;
      } else {
        map.put(jobName, true);
      }
      logger.info(detail.getDescription());
      logger.info("begin to call busi job {}.", jobName);
      OnTimeTaskBusiJob busiJob = detail.getBusiJob();
//      busiJob.timeOutExecute();
      busiJob.timeOutExecute(detail.getName());
      logger.info("end to call busi job {}.", jobName);
      if (map.get(jobName) != null) {
        map.put(jobName, false);
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
      if (map.get(jobName) != null) {
        map.put(jobName, false);
        return;
      }
    }
  }
}
