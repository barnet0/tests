package com.digiwin.ecims.system.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.LogMaintainQueryBean;
import com.digiwin.ecims.system.dao.LogMaintainDao;
import com.digiwin.ecims.system.service.LogMaintainService;
@Service
public class LogMaintainServiceImpl implements LogMaintainService {

	@Autowired
	LogMaintainDao logDao;
	
	@Override
	public List<LogInfoT> findAll() {
		// TODO Auto-generated method stub
		return logDao.findAll(LogInfoT.class);
	}

	@Override
	public List<LogInfoT> findCondition(Date reqType, String businessType) {		
		// TODO Auto-generated method stub		
		Map<String,Object> paramMap=new HashMap<String,Object>();;
		paramMap.put("reqTime",DateTimeTool.format(reqType, "yyyy-MM-dd"));
		paramMap.put("businessType",businessType);
		return logDao.findCondition(LogInfoT.class, paramMap);
	}

	@Override
	public PageBean<LogInfoT> findLogInfo(LogMaintainQueryBean queryBean,String start, String limit) {
		PageBean pageBean  = logDao.findAllByCondition(queryBean, start, limit);
		return pageBean;
	}
	
	@Override
	public LogInfoT findOneRecord(Long logMaintainId){
		LogInfoT logInfoOnlyRecord = logDao.get(LogInfoT.class, BigInteger.valueOf(logMaintainId));
		return logInfoOnlyRecord;
	}

	@Override
	public int updateBySQL(String sql) {
		return logDao.update(sql);
	}
	
}
