package com.digiwin.ecims.ontime.service;

import java.util.List;

import com.digiwin.ecims.core.exception.GenericBusinessException;
import com.digiwin.ecims.ontime.bean.OnTimeTaskQueryBean;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.LoginUser;
import com.digiwin.ecims.ontime.model.OntimeTask;

public interface OnTimeTaskService {
	/**
	 * 找出所有定时任务(不管什么状态)
	 * @param loginUser
	 * @return
	 * @throws GenericBusinessException
	 */
	public List<OntimeTask> findAllTask(LoginUser loginUser) throws GenericBusinessException;

	/**
	 * 按分组找出所有定时任务(可激活的 status='1')
	 * 按分组划分多个运行环境 -- 任务执行者所依赖的环境
	 * @param loginUser
	 * @param group  运行环境的分组标识,来源于 spring xml 配置定时器的参数
	 * @return
	 * @throws GenericBusinessException
	 */
	public List<OntimeTask> findAllActiveTaskByGroup(LoginUser loginUser,String group) throws GenericBusinessException;

	/**
	 * 按分组和和IP 找出所有定时任务(可激活的 status='1')
	 * 按分组划分多个运行环境 -- 任务执行者所依赖的环境
	 * @param loginUser
	 * @param group  运行环境的分组标识,来源于 spring xml 配置定时器的参数
	 * @param ip     运行主机的ip。JVM 自动获取本机ip，不依赖于参数
	 * @return
	 * @throws GenericBusinessException
	 */
	public List<OntimeTask> findAllActiveTaskByGroupAndIP(LoginUser loginUser,String group,String ip) throws GenericBusinessException;
	
	/**
	 * 激活定时任务
	 * 注:由客户端的 调度器去加载启动
	 * @param loginUser
	 * @param taskId
	 * @return
	 * @throws GenericBusinessException
	 */
	public boolean activeTask(LoginUser loginUser , long taskId)  throws GenericBusinessException;

	/**
	 * 取消定时任务
	 * 注：这里只管数据，由客户端的 调度器去停止作业
	 * @param loginUser
	 * @param taskId
	 * @return
	 * @throws GenericBusinessException
	 */
	public boolean inactiveTask(LoginUser loginUser , long taskId)  throws GenericBusinessException;
	
	public OntimeTask findontimeTask(LoginUser user, Long ontimeTaskId) throws GenericBusinessException;
	
	public OntimeTask saveOntimeTask(LoginUser user, OntimeTask ontimeTask) throws GenericBusinessException;	
	
	public OntimeTask getOntimeTaskByCode(String ontimeTaskCode) throws GenericBusinessException;
	
	public boolean deleteByPK(LoginUser user, Class entityClass, long pkId)throws GenericBusinessException;
	/**
	 * 查询 所有
	 * 
	 * @param loginUser
	 * @param taskId
	 * @return
	 * @throws GenericBusinessException
	 */
	public PageBean<OntimeTask> findAllOntimeTask(LoginUser user)throws GenericBusinessException;
	/**
	 * 分页查询
	 * 
	 * @param loginUser
	 * @param taskId
	 * @return
	 * @throws GenericBusinessException
	 */
	public PageBean<OntimeTask> findOntimeTask(LoginUser user,OnTimeTaskQueryBean queryBean,String start,String limit)throws GenericBusinessException;
}
