package com.digiwin.ecims.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.service.impl.LogPushMaintainServiceImpl;
import net.sf.json.JSONArray;

/**
 * 
 * @author Xavier
 *
 */
@Controller
@RequestMapping("/logPushMaintain")
public class LogPushMaintainController {

  @Autowired
  private LogPushMaintainServiceImpl logPushMaintainService;

  @Autowired
  private BaseDAO baseDAO;

  public LogPushMaintainController() {}

  @RequestMapping("/showPushLogMaintain.do")
  public String showLogInfo() {
    return "/logpushmaintain/logpushmaintain";
  }

  /**
   * 查询定时作业配置信息
   * 
   * @param storeType
   * @return
   */
  @RequestMapping("/queryPushLogMaintain.do")
  @ResponseBody
  public String queryLogMaintain(String storeType) {
    // 取得查詢的資料
    List<TaskScheduleConfig> data = logPushMaintainService.findAllScheduleConfig(storeType);

    PageBean<TaskScheduleConfig> pageBean = new PageBean<TaskScheduleConfig>();
    final int totalCount = data.size();
    pageBean.setTotal(totalCount);
    pageBean.setRoot(data);
    String taskJson = JSONArray.fromObject(pageBean.getRoot()).toString();

    String str = "{\"totalProperty\":" + pageBean.getTotal() + ",\"root\":" + taskJson + "}";
    // System.out.println(str);
    return str;
  }
}
