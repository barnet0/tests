package com.digiwin.ecims.ontime.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.exception.GenericBusinessException;
import com.digiwin.ecims.ontime.bean.OnTimeTaskQueryBean;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.enumvar.StatusEnum;
import com.digiwin.ecims.ontime.enumvar.TaskRunStatusEnum;
import com.digiwin.ecims.ontime.model.LoginUser;
import com.digiwin.ecims.ontime.model.OntimeTask;
import com.digiwin.ecims.ontime.service.OnTimeTaskService;
import com.digiwin.ecims.ontime.service.SchedulerFactoryIntf;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 定时作业调度器的管理、以及配置信息的管理
 * 2015-07-04 lizhi 定时作业，可以放在  对接服务器,通过  spring http invoke 进行远程调用读取  运行状态、重启、立即调用服务等。
 * @author lizhi
 *
 */

@Controller
@RequestMapping("/ontimeTask")
public class OnTimeTaskController{
	
	public OnTimeTaskController() {  
    }
	@Autowired 
	private OnTimeTaskService onTimeTaskService;
	
	@Autowired
	SchedulerFactoryIntf schedulerFactoryIntf;
	//SchedulerFactoryDynamic schedulerFactoryDynamic;
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(FormatConstants.DATE_FORMAT_PATTERN), true));
//	}
	@RequestMapping("/showOnTimeTask.do")
	public String showOnTimeTask(){
		return "/ontimetask/ontimetask";
	}
	@RequestMapping("/showCronHelp.do")
	public String showCronHelp(){
		return "/ontimetask/quartz_cron_help";
	}
	
	@RequestMapping("/getLocalIP.do")
	@ResponseBody
	public String getLocalIP(){
		return schedulerFactoryIntf.getRunIP();
	}
	
	/**
	 * 查询定时作业配置信息 
	 * @param map
	 * @param user
	 * @param onTimeTaskQC
	 * @param nowPage
	 * @param doAction
	 * @param totalPages
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/queryOntimeTask.do")
	@ResponseBody
	public String queryOntimeTask(ModelMap map, LoginUser user, String name,String code,String status,String type,String start,String limit) {
		PageBean<OntimeTask> pageBean = null;
		try {
			OnTimeTaskQueryBean queryBean = new OnTimeTaskQueryBean(code,name,status);
			pageBean = onTimeTaskService.findOntimeTask(user, queryBean, start,limit);
		}
		catch (GenericBusinessException e) {
			e.printStackTrace();
		}
		String statusTMP = "";
		//2011-04-24 补充在调度器的状态
		try{
			for(OntimeTask task : pageBean.getRoot()){
				statusTMP = schedulerFactoryIntf.findTriggerStatus(task);
				task.setRunStatus( TaskRunStatusEnum.getName(statusTMP));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String taskJson = JSONArray.fromObject(pageBean.getRoot()).toString();
		return "{totalProperty:"+pageBean.getTotal()+",root:"+taskJson+"}";
	}

	@RequestMapping(value = "/editOnTimeTask.do", method = RequestMethod.POST)
	@ResponseBody
	public String editontimeTask(ModelMap map, Long ontimeTaskId, LoginUser user) {
		String taskJson = null;
		try {
			OntimeTask ontimeTask = onTimeTaskService.findontimeTask(user, ontimeTaskId);
			taskJson = JSONObject.fromObject(ontimeTask).toString();
		}
		catch (GenericBusinessException e) {
			e.printStackTrace();
		}
		return taskJson;
	}
	/**
	 * 2011-04-17 根据状态,同步定时调度器(start or  stop)
	 * @param map
	 * @param ontimeTask
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/addOnTimeTask.do", method = RequestMethod.POST)
	@ResponseBody
	public String editontimeTask(ModelMap map,String jsonTask,OntimeTask ontimeTask, LoginUser user,HttpServletRequest request) {
		try {
			JSONObject taskJsonObject = JSONObject.fromObject(jsonTask);
			int id = taskJsonObject.getInt("ontimeTask.id");
			ontimeTask.setId(id);
			String name = taskJsonObject.getString("ontimeTask.name");
			ontimeTask.setName(name);
			String code = taskJsonObject.getString("ontimeTask.code");
			ontimeTask.setCode(code);
			String status = taskJsonObject.getString("ontimeTask.status");
			ontimeTask.setStatus(status);
			String exceType = taskJsonObject.getString("ontimeTask.exceType");
			ontimeTask.setExceType(exceType);
			String exceName = taskJsonObject.getString("ontimeTask.exceName");
			ontimeTask.setExceName(exceName);
			String cronVal = taskJsonObject.getString("ontimeTask.cronVal");
			ontimeTask.setCronVal(cronVal);
			String dataSource = taskJsonObject.getString("ontimeTask.dataSource");
			if(dataSource == null || "".equals(dataSource)){
				//业务系统，对应job的group
				dataSource ="1";
			}
			ontimeTask.setDataSource(dataSource);
			String runIp = taskJsonObject.getString("ontimeTask.runIp");
			if(runIp == null || "".equals(runIp)){
				ontimeTask.setRunIp(InetAddressTool.getLocalIpv4());
			}else{
				ontimeTask.setRunIp(runIp);
			}
			String remark = taskJsonObject.getString("ontimeTask.remark");
			ontimeTask.setRemark(remark);
			ontimeTask.setApplicationId(0);
			
			if (ontimeTask.getStatus() == null || ontimeTask.getStatus().equals("")) {
				ontimeTask.setStatus(StatusEnum.VALID.getCode());
			}
			ontimeTask = onTimeTaskService.saveOntimeTask(user, ontimeTask);
			//if(StatusEnum.VALID.getCode().equals(ontimeTask.getStatus())){
			//	schedulerFactoryDynamic.resumeTask(ontimeTask);
			/* 出错
			java.lang.NullPointerException
	        at org.quartz.simpl.RAMJobStore.resumeTrigger(RAMJobStore.java:938)
	        at org.quartz.core.QuartzScheduler.resumeTrigger(QuartzScheduler.java:953)
	        at org.quartz.impl.StdScheduler.resumeTrigger(StdScheduler.java:410)
	        */
			//}else{
			//	schedulerFactoryDynamic.pauseTask(ontimeTask);
			//}
			
		}
		catch (GenericBusinessException e) {
			e.printStackTrace();
		}
		return "{success: true, msg: '保存成功!'}";
	}
	
	/**
	 * 
	 * @param id   如果 new ,id=0
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkontimeTaskCode.do")
	@ResponseBody
	public boolean checkontimeTaskCode(Long id , String oldCode , String code) {
		if(code==null){
			return false;
		}		
		try {
			if (id >0 && code.equals(oldCode) ) {
				return true;
			}
			else{
			   boolean notExist = onTimeTaskService.getOntimeTaskByCode(code) == null;
			   return notExist;
			}
		}
		catch (GenericBusinessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping("/checkCronVal.do")
	@ResponseBody
	public boolean checkCronVal(String cronVal) {
		if(cronVal==null || cronVal.length()<=1){
			return false;
		}				
		return schedulerFactoryIntf.checkCronExpression(cronVal);
	}
	
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public String deleteOntimeTask(Long id, LoginUser user) {
		String retStr = "0";
		try {
			OntimeTask ontimeTask = onTimeTaskService.findontimeTask(user, id);
			onTimeTaskService.deleteByPK(user, OntimeTask.class, id);
			schedulerFactoryIntf.deleteTrigger(ontimeTask.getCode(), ontimeTask.getDataSource());
			retStr="{success: true, msg: '删除成功!'}";
		}
		catch (GenericBusinessException e) {
			e.printStackTrace();
			retStr="{success: true, msg: '删除失败!'}";
		}
		return retStr;
	}	

	@RequestMapping(value = "/callNow.do")
	@ResponseBody
	public String callNow(Long ontimeTaskId, LoginUser user) throws Exception {
		String retStr = "0";
		try {
			OntimeTask ontimeTask = onTimeTaskService.findontimeTask(user, ontimeTaskId);
			//数据库配置不可用，调度器不加载，也能调用
			if(schedulerFactoryIntf.callJob(ontimeTask)){
				retStr="1";
			}
		} catch (Exception e) {
			System.out.println("+999+");
			e.printStackTrace();
		}
		return retStr;
	}	

	
	//org.quartz.SchedulerException: The Scheduler cannot be restarted after shutdown() has been called.
	@RequestMapping(value = "/restartDispatcher.do")
	@ResponseBody
	public String restartDispatcher(LoginUser user) {
		String retStr = "0";
		//停止
		if(schedulerFactoryIntf.shutdownScheduler()){
			//重新加载
			schedulerFactoryIntf.initOnTimeTask();
			schedulerFactoryIntf.startScheduler();
			retStr="1";
		}
		return retStr;
	}		

	/**
	 * 查看调度器状态  一个JVM里   --- 暂时不可用，因为跨服务器, 需要构造可序列化的 VO
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/queryDispatcher.do")
	public String queryDispatcher(ModelMap map,LoginUser user) {
		String retUrl = "ontimetask/query_dispatcher";
		Collection<Scheduler> schedulers = schedulerFactoryIntf.findAllSchedulers();
		map.addAttribute("schedulers", schedulers);
		map.addAttribute("triggers", schedulerFactoryIntf.findAllTrigger());
		return retUrl;
	}		

}
