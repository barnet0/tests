package com.digiwin.ecims.ontime.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

public class JobExecuter2 extends JobExecuter {
  public JobExecuter2() {}

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {
    String jobNm = arg0.getTrigger().getKey().getName();
    try {
      JobDetailImpl detail = (JobDetailImpl) arg0.getJobDetail();
      System.out.println(detail.getDescription());
      System.out.println("begin to call busi job." + jobNm);
      OnTimeTaskBusiJob busiJob = detail.getBusiJob();
      busiJob.timeOutExecute();
      System.out.println("end");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
