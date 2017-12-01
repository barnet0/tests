package com.digiwin.ecims.ontime.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.dao.OnTimeDAO;
import com.digiwin.ecims.ontime.service.ScheduleLastUpdateTimeAdjustService;

/**
 * 负责在系统启动时向前调整各个排程的lastUpdateTime,以避免每次重启或者版更的时候有漏单
 * 
 * @author 维杰
 * @since 2015.09.07
 */
@Service
public class ScheduleLastUpdateTimeAdjustServiceImpl
    implements ScheduleLastUpdateTimeAdjustService {

  @Autowired
  private OnTimeDAO onTimeDao;

  @Override
  public int updateLastUpdateTime(String timeAhead) {
    // 此处的SQL为
//    	UPDATE task_schedule_config 
//    	SET 
//    	    lastUpdateTime = SUBSTR(DATE_FORMAT(DATE_ADD(STR_TO_DATE(lastUpdateTime, '%Y-%m-%d %H:%i:%s.%f'),
//    	                    INTERVAL - 1 MINUTE),
//    	                '%Y-%m-%d %H:%i:%s.%f'),
//    	        1,
//    	        23);
    // 将原来的lastUpdateTime往过去调一段时间（单位：分钟），避免每次重启，或者版更的时候有漏单
    // 如果原来的排程没有设定lastUpdateTime，此SQL是不会更新那个排程的lastUpdateTime的，会保持原来的NULL值
    // 往前调的时间是可以在系统参数中设定的
		
    String updateHqlString = "";
    updateHqlString = "UPDATE task_schedule_config "
        + "SET lastUpdateTime = "
        + "SUBSTR("
        + " DATE_FORMAT("
        + "     DATE_ADD("
        + "         STR_TO_DATE(lastUpdateTime,'%Y-%m-%d %H:%i:%s.%f'), "
        + "     INTERVAL -" + timeAhead + " MINUTE), "
        + " '%Y-%m-%d %H:%i:%s.%f'), "
        + "1, "
        + "19) "
        + "WHERE length(lastUpdateTime) > 0 ";
    int affectedRows = 0;
    affectedRows = onTimeDao.updateByNativeSql(updateHqlString);

    return affectedRows;
  }

}
