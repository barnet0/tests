package com.digiwin.ecims.core.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author ken Tu 2015/07/17
 * 
 *         update任務基本功能
 *
 */
public class UpdateTask {

	/**
	 * 將時間往前挪一秒
	 * 
	 * @param currentDate
	 *            當前時間
	 * @return 當前時間往前一秒
	 * @throws ParseException
	 */
	protected String timeAddOneSecond(String currentDate) throws ParseException {

		Calendar calendar = Calendar.getInstance();

		Date date = null;

		date = DateTimeTool.parse(currentDate);

		calendar.setTime(date);

		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 1);

		return DateTimeTool.format(calendar.getTime());
	}

	protected String getInitDateTime() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 90);

		return DateTimeTool.format(calendar.getTime());
	}
	/**
	 * 获取指定时间前beforeDate天的日期
	 * @param checkedTaskTime
	 * @param beforeDate
	 * @return
	 */
	protected String getInitDateTime(String checkedTaskTime,int beforeDate) {
		Calendar calendar = Calendar.getInstance();
		Date date = DateTimeTool.parse(checkedTaskTime);
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - beforeDate);
		return DateTimeTool.format(calendar.getTime());
	}
}
