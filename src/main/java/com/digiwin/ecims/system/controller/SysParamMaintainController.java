package com.digiwin.ecims.system.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.SystemParam;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.SchedulerFactoryIntf;
import com.digiwin.ecims.system.bean.ParamBean;
import com.digiwin.ecims.system.bean.SysParamMaintainQueryBean;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.service.SysParamMaintainService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 定时作业调度器的管理、以及配置信息的管理 
 * 2015-07-04 lizhi 定时作业，可以放在 对接服务器,通过 spring http invoke 进行远程调用读取
 * 运行状态、重启、立即调用服务等。
 * 
 * @author lizhi
 *
 */

@Controller
@RequestMapping("/sysParamMaintain")
public class SysParamMaintainController {

  private static final Logger logger = LoggerFactory.getLogger(SysParamMaintainController.class);

  @Autowired
  LoginfoOperateService interceptorService;

  @Autowired
  private SysParamMaintainService sysParamMaintainService;

  @Autowired
  private ParamSystemService paramSystemService;

  @Autowired
  SchedulerFactoryIntf schedulerFactoryIntf;

  @Autowired
  BaseDAO baseDao;

  @RequestMapping("/showSysParamMaintain.do")
  public String showSysParamInfo() {
    return "/sysparammaintain/sysparammaintain";
  }

  @RequestMapping("/querySysParamMaintain.do")
  @ResponseBody
  public String querySysParamMaintain(String pKey, String status, String type, String start,
      String limit) {
    PageBean<SystemParam> pageBean = null;
    SysParamMaintainQueryBean queryBean = new SysParamMaintainQueryBean(pKey, status);
    pageBean = sysParamMaintainService.findSysParamInfo(queryBean, start, limit);

    String taskJson = JSONArray.fromObject(pageBean.getRoot()).toString();
    // System.out.println("===================================================================");
    return "{totalProperty:" + pageBean.getTotal() + ",root:" + taskJson + "}";
  }

  @RequestMapping(value = "/showSysParamDetail.do", method = RequestMethod.POST)
  @ResponseBody
  public String showSysParamDetail(Long sysParamMaintainId) {
    String taskJson = "";
    SystemParam sysParam = sysParamMaintainService.findOneRecord(sysParamMaintainId);
    sysParam
        .setCreatedDateStr(DateTimeTool.format(sysParam.getCreatedDate(), "yyyy-MM-dd HH:mm:ss"));
    sysParam.setModiDateStr(DateTimeTool.format(sysParam.getModiDate(), "yyyy-MM-dd HH:mm:ss"));
    taskJson = JSONObject.fromObject(sysParam).toString();
    return taskJson;
  }

  @RequestMapping(value = "/deleteSysParam.do", method = RequestMethod.POST)
  @ResponseBody
  public String deleteSysParam(String sysParamIds) {
    ParamBean paramBean = new ParamBean(LogInfoBizTypeEnum.SYS_PARAM_DELETE.getValueString(),
        "deleteSysParam()", sysParamIds, new Date());
    sysParamIds = sysParamIds.trim().substring(0, sysParamIds.length() - 1);

    String sysParamKeyName = "";
    SystemParam sysParam = sysParamMaintainService.findOneRecord(Long.valueOf(sysParamIds));
    sysParamKeyName = sysParam.getpKey();

    String sql = "DELETE FROM T_SYSTEM_PARAM WHERE id IN(" + sysParamIds + ")";

    int status = 0;
    String showInfo = "";
    try {
      status = sysParamMaintainService.executeSql(sql);
      showInfo = "成功删除" + status + "笔资料|id=" + sysParamIds + ",paramKey=" + sysParamKeyName;
      interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(), paramBean,
          Boolean.TRUE, showInfo, null);
    } catch (Exception e) {
      showInfo = "删除失败" + status + " 笔资料|id=" + sysParamIds + ",paramKey=" + sysParamKeyName;
      interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(), paramBean,
          Boolean.TRUE, showInfo, e);
    }

    return showInfo;
  }

  @RequestMapping(value = "/saveSysParam.do", method = RequestMethod.POST)
  @ResponseBody
  public String saveSysParam(String jsonSysParam) {
    ParamBean paramBean = new ParamBean("saveSysParam()", jsonSysParam, new Date());
    JSONObject sysParamJsonObject = JSONObject.fromObject(jsonSysParam);
    String queryType = "";
    String sql = "";
    String showJson = "";
    int affectedRows = 0;
    if (!sysParamJsonObject.get("sys.id").equals("")) {
      sql = "UPDATE T_SYSTEM_PARAM SET p_key = '" + sysParamJsonObject.get("sys.pKey")
          + "',p_val = '" + sysParamJsonObject.get("sys.pVal") + "',p_desc = '"
          + sysParamJsonObject.get("sys.pDesc") + "',status = '"
          + sysParamJsonObject.get("sys.status").toString().split("-")[0] + "' WHERE id = '"
          + sysParamJsonObject.get("sys.id") + "'";
      queryType = "更新";
      paramBean.setProcessType(LogInfoBizTypeEnum.SYS_PARAM_UPDATE.getValueString());

      String oldSysParamKeyName = "";
      String oldSysParamKeyValue = "";
      String oldSysParamKeyStatus = "";
      SystemParam oldSysParam = sysParamMaintainService
          .findOneRecord(Long.valueOf((String) sysParamJsonObject.get("sys.id")));
      oldSysParamKeyName = oldSysParam.getpKey();
      oldSysParamKeyValue = oldSysParam.getpVal();
      oldSysParamKeyStatus = oldSysParam.getStatus();

      String showInfo = "";
      try {
        affectedRows = sysParamMaintainService.executeSql(sql);
        showJson = "{success: true, msg: '" + queryType + "成功 " + affectedRows + " 笔资料 !'}";
        showInfo = showJson + "|id=" + sysParamJsonObject.get("sys.id") + ",oldKey="
            + oldSysParamKeyName + ",oldValue=" + oldSysParamKeyValue + ",oldStatus="
            + oldSysParamKeyStatus + ",newKey=" + sysParamJsonObject.get("sys.pKey") + ",newValue="
            + sysParamJsonObject.get("sys.pVal") + ",newStatus="
            + sysParamJsonObject.get("sys.status");
        // interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(),paramBean,
        // Boolean.TRUE, showJson, null);
        interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(), paramBean,
            Boolean.TRUE, showInfo, null);
      } catch (Exception e) {
        showJson = "{success: false, msg: '" + queryType + "失败 !'}";
        // interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(),paramBean,
        // Boolean.TRUE, showJson, e);
        interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(), paramBean,
            Boolean.TRUE, showInfo, e);
      }

      return showJson;

    } else {
      sql = "INSERT INTO T_SYSTEM_PARAM (p_key,p_val,p_desc,creator_id,creator_name,"
          + "modifier_id,modifier_name,STATUS) " + "VALUES ('" + sysParamJsonObject.get("sys.pKey")
          + "', '" + sysParamJsonObject.get("sys.pVal") + "'," + " '"
          + sysParamJsonObject.get("sys.pDesc") + "', '1', 'sysadmin'," + " '1', 'sysadmin', '"
          + sysParamJsonObject.get("sys.status").toString().split("-")[0] + "');";
      queryType = "新增";
      paramBean.setProcessType(LogInfoBizTypeEnum.SYS_PARAM_SAVE.getValueString());

      try {
        affectedRows = sysParamMaintainService.executeSql(sql);
        showJson = "{success: true, msg: '" + queryType + "成功 " + affectedRows + " 笔资料 !'}";
        interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(), paramBean,
            Boolean.TRUE, showJson, null);
      } catch (Exception e) {
        showJson = "{success: false, msg: '" + queryType + "失败 !'}";
        interceptorService.saveSysParamActionLog(schedulerFactoryIntf.getRunIP(), paramBean,
            Boolean.TRUE, showJson, e);
      }

      return showJson;
    }


  }

  @RequestMapping(value = "/restartSysParam.do")
  @ResponseBody
  public String reloadSysParam() {
    String retStr = "1";
    logger.info("systemParam:系统参数开始加载。。。。。");

    boolean isSuccess = paramSystemService.initParamToCache();
    if (!isSuccess) {
      logger.error("systemParam:系统参数加载失败");
      retStr = "0";
    }
    logger.info("systemParam:系统参数加载完毕。。。。。");
    return retStr;
  }

}
