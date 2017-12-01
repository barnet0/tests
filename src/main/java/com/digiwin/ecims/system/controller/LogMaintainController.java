package com.digiwin.ecims.system.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.LogMaintainQueryBean;
import com.digiwin.ecims.system.service.LogMaintainService;


/**
 * 定时作业调度器的管理、以及配置信息的管理
 * 2015-07-04 lizhi 定时作业，可以放在  对接服务器,通过  spring http invoke 进行远程调用读取  运行状态、重启、立即调用服务等。
 * @author lizhi
 *
 */

@Controller
@RequestMapping("/logMaintain")
public class LogMaintainController{
	
	public LogMaintainController() {  
    }
	@Autowired 
	private LogMaintainService logMaintainService;

	@RequestMapping("/showLogMaintain.do")
	public String showLogInfo(){
		return "/logmaintain/logmaintain";
	}
	
//	@RequestMapping("/showLogMaintainDetail.do")
//	public String showCronHelp(){
//		return "/ontimetask/quartz_cron_help";
//	}
	
	/**
	 * 查询定时作业配置信息 
	 * @param map
	 * @param user
	 * @param onTimeTaskQC
	 * @param nowPage
	 * @param doAction
	 * @param totalPages
	 * @param pageSize
	 * 
	 * exceptionType 异常类型，0系统数据异常，1，业务数据异常
	 * @return
	 */
	@RequestMapping("/queryLogMaintain.do")
	@ResponseBody
	public String queryLogMaintain(String reqTime, String businessType, String type, String isSuccess,String exceptionType, String start, String limit) {
		PageBean<LogInfoT> pageBean = null;
		if(reqTime!=null && !reqTime.equals("")){
			reqTime=DateTimeTool.format(DateTimeTool.parse(reqTime, "yyyy-MM-dd"),"yyyy-MM-dd");
		}
		else{
			reqTime=null;
		}
		LogMaintainQueryBean queryBean = new LogMaintainQueryBean(reqTime, businessType, isSuccess,exceptionType);
		pageBean = logMaintainService.findLogInfo(queryBean, start,limit);

//		JsonConfig config = new JsonConfig();  
//		config.registerJsonBeanProcessor(java.util.Date.class, new JsDateJsonBeanProcessor());  
		
		String taskJson = JSONArray.fromObject(pageBean.getRoot()).toString();
		return "{totalProperty:"+pageBean.getTotal()+",root:"+taskJson+"}";
	}
	
	@RequestMapping(value = "/showLogDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public String showLogDetail(Long logMaintainId) {
		String taskJson = null;
		LogInfoT logInfo = logMaintainService.findOneRecord(logMaintainId);
		logInfo.setReqTimeTrasient(DateTimeTool.format(logInfo.getReqTime(), "yyyy-MM-dd HH:mm:ss"));
		logInfo.setResTimeTrasient(DateTimeTool.format(logInfo.getResTime(), "yyyy-MM-dd HH:mm:ss"));
		taskJson = JSONObject.fromObject(logInfo).toString();
		return taskJson;
	}
	
	@RequestMapping(value = "/batchUpdateStatus.do", method = RequestMethod.POST)
	@ResponseBody
	public String batchUpdateStatus(String logIds) {
		logIds=logIds.trim().substring(0,logIds.length()-1);
//		String taskJson = null;
		String sql="update log_info_t set final_status='1' where log_id in("+logIds+")";
		String outInfo="成功處理："+logMaintainService.updateBySQL(sql)+" 筆資料!";
//		taskJson = JSONObject.fromObject(outInfo).toString();
		return outInfo;
	}

}
