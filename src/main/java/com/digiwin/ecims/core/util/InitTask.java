package com.digiwin.ecims.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Ken Tu 2015/07/17
 *
 *         init任務基本功能
 */
public class InitTask {

	/**
	 * 取得搜尋範圍(默認往前一天)
	 * 
	 * @param currentDate
	 *            當前時間
	 * @return 默認為當前時間一天前的日期
	 * @throws ParseException
	 */
	protected String getQueryCondition(String currentDate) throws ParseException {

		Calendar calendar = Calendar.getInstance();

		Date date = null;

		date = DateTimeTool.parse(currentDate, "yyyy-MM-dd HH:mm:ss");

		calendar.setTime(date);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);

		return DateTimeTool.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 將時間往後挪一秒
	 * 
	 * @param currentDate
	 *            當前時間
	 * @return 當前時間往後一秒
	 * @throws ParseException
	 */
	protected String timeLessOneSecond(String currentDate) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();

		Date date = null;

		date = simpleDateFormat.parse(currentDate);

		calendar.setTime(date);

		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);

		return simpleDateFormat.format(calendar.getTime());
	}

}
