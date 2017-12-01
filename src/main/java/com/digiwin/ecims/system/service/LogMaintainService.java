package com.digiwin.ecims.system.service;

import java.util.Date;
import java.util.List;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.LogMaintainQueryBean;


public interface LogMaintainService {
	public List<LogInfoT> findAll();
	
	public List<LogInfoT> findCondition(Date reqType,String businessType);
	
	public PageBean<LogInfoT> findLogInfo(LogMaintainQueryBean queryBean,String start,String limit);
	
	public LogInfoT findOneRecord(Long logMaintainId);
	
	public int updateBySQL(String sql);
}
