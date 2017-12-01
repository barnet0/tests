package com.digiwin.ecims.platforms.pdd.service.manual.push;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;

/**
 * 2017/06/29
 * @author cjp
 *
 */
@Service("pushPddOrderDataByHandServiceImpl")
public class PushPddOrderDataByHandServiceImpl implements PushOrderDataByHandService {

	@Autowired
	  private TaskService taskService;

	  @Autowired
	  private LoginfoOperateService loginfoOperateService;

	  @Autowired
	  private AomsShopService aomsShopService;
	  
	  @Autowired
	  private BasePostService basePostService;
	  
	  private static final Logger logger =
	      LoggerFactory.getLogger(PushPddOrderDataByHandServiceImpl.class);

	  private static final String SCHEDULE_TYPE = "Pdd#AomsordT#1";
	
  @Override
  public String pushOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByOrderId(String storeId, String orderId) {
	  TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(SCHEDULE_TYPE);
	    SyncResOrderHandBean syncResOrderHandBean = null;
	    try {
	      basePostService.timeOutExecuteById(taskScheduleConfig, orderId, AomsordT.class);
	      syncResOrderHandBean = new SyncResOrderHandBean(true, "1");
	    } catch (IOException e) {
	      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
	          "pushPddOrderDataByHandServiceImpl#pushOrderDataByOrderId",
	          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
	          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
	          "AomsordT", "");
	      e.printStackTrace();
	      logger.error("IOException = {}", e.getMessage());
	      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
	    } catch (Exception e) {
	      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
	          "pushPddOrderDataByHandServiceImpl#pushOrderDataByOrderId",
	          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
	          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
	          "AomsordT", "");
	      e.printStackTrace();
	      logger.error("Exception = {}" + e.getMessage());
	      syncResOrderHandBean = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
	    }

	    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String pushOrderDataByPayDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String pushOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderDataCountFromDBByCreateDate(String storeId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params,
      Class<T> clazz) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

}
