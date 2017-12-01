package com.digiwin.ecims.ontime.service.impl;

import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;


/**
 * 继承  JobDetail, 增加放置 真正执行业务的  OnTimeTaskBusiJob 实例
 * 
 * OnTimeTaskBusiJob 实例 是来源于 Spring管理 的 对象。
 * 
 * @author aibozeng
 *
 */

@SuppressWarnings("serial")
public class JobDetailImpl extends org.quartz.impl.JobDetailImpl{
	
	private OnTimeTaskBusiJob busiJob ;
	
	public JobDetailImpl(){
		super();
	}
	public JobDetailImpl(String name, String group, Class jobClass){
		super(name,group,jobClass);
	}
	public OnTimeTaskBusiJob getBusiJob() {
		return busiJob;
	}
	public void setBusiJob(OnTimeTaskBusiJob busiJob) {
		this.busiJob = busiJob;
	}
	
}
