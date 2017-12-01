package com.digiwin.ecims.ontime.service.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Scheduler 会为每一次执行创建新的 Job 实例
 * 
 * 注：我们的系统不能使用 StatefulJob (这代表不能并发执行)
 * 
 * @author aibozeng
 *
 */

public class JobExecuter implements Job {

  public JobExecuter() {}

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {}

}
