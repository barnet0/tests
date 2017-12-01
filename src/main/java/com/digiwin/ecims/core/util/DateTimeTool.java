package com.digiwin.ecims.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 放置常用的与日期时间处理有关的功能
 * 
 * @author aibo zeng,mowj
 *
 */

public class DateTimeTool {

	public static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String MILLI_SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String SHORT_SECOND_FORMAT = "HH:mm:ss";

	public static final DateTimeFormatter SECOND_DATETIME_FORMATTER = DateTimeFormat.forPattern(SECOND_FORMAT);
	public static final DateTimeFormatter MILLISECOND_DATETIME_FORMATTER = DateTimeFormat
			.forPattern(MILLI_SECOND_FORMAT);

	public static boolean isWindow() {
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 使用指定格式将java.util.Date转换为java.lang.String
	 * 
	 * @param date
	 *            java.util.Date实例
	 * @param format
	 *            指定格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		return DateTimeFormat.forPattern(format).print(new DateTime(date));
		// return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 以精确到毫秒的格式，将java.util.Date转换为java.lang.String
	 * <p>
	 * 格式为：yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToMillisecond(Date date) {
		return MILLISECOND_DATETIME_FORMATTER.print(new DateTime(date));
	}

	/**
	 * 使用默认格式将java.util.Date转换为java.lang.String
	 * <p>
	 * 默认格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            java.util.Date实例
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return SECOND_DATETIME_FORMATTER.print(new DateTime(date));
	}

	/**
	 * 使用默认格式将org.joda.DateTime转换为java.lang.String
	 * <p>
	 * 默认格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param org.joda.DateTime实例
	 * @return 日期字符串
	 */
	public static String format(DateTime dateTime) {
		return SECOND_DATETIME_FORMATTER.print(dateTime);
	}

	public static String toTime(String date) {
		return parseToMillisecond(date).getTime() + "";
	}

	public static DateTime parseToDateTime(String date, String format) {
		return DateTimeFormat.forPattern(format).parseDateTime(date);
	}

	/**
	 * 使用指定格式将java.lang.String 转换为java.util.Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String format) {
		return parseToDateTime(date, format).toDate();
	}

	/**
	 * 以精确到毫秒的格式，将java.lang.String转换为java.util.Date
	 * <p>
	 * 格式为：yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseToMillisecond(String date) {
		return MILLISECOND_DATETIME_FORMATTER.parseDateTime(date).toDate();
	}

	/**
	 * 使用默认格式将java.lang.String 转换为java.util.Date。
	 * <p>
	 * 默认格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {
		return SECOND_DATETIME_FORMATTER.parseDateTime(date).toDate();
	}

	/**
	 * 以yyyy-MM-dd HH:mm:ss格式化当前Date，返回字符串
	 * 
	 * @return 当前日期时间字符串
	 */
	public static String getTodayBySecond() {
		return SECOND_DATETIME_FORMATTER.print(new DateTime(new Date()));
	}

	/**
	 * 以yyyy-MM-dd HH:mm:ss.SSS格式化当前Date，返回字符串
	 * 
	 * @return 当前日期时间字符串
	 */
	public static String getTodayByMilliSecond() {
		return MILLISECOND_DATETIME_FORMATTER.print(new DateTime(new Date()));
	}

	/**
	 * 返回当前时间的时间戳（10位，精确到秒）
	 * 
	 * @return
	 */
	public static String getTodayByTimestamp() {
		return (new Date().getTime() + "").substring(0, 10);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的时间字符串转换为对应的格林尼治标准时间戳
	 * 
	 * @param dateString
	 * @return
	 */
	public static String parseToDateAndFormatToTimestamp(String dateString) {
		return (parse(dateString).getTime() + "").substring(0, 10);
	}

	/**
	 * 将指定format的时间字符串转换为对应的格林尼治标准时间戳
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static String parseToDateAndFormatToTimestamp(String dateString, String format) {
		return (parse(dateString, format).getTime() + "").substring(0, 10);
	}

	/**
	 * 以指定format格式化当前Date，返回字符串
	 * 
	 * @param format
	 *            时间格式。例如：<code>yyyy-MM-dd HH:mm:ss</code>
	 * @return 当前日期时间字符串
	 */
	public static String getToday(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static Date getTodayDate() {
		return new Date();
	}

	/**
	 * 取出日期中，今天凌晨的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginTime(Date date) {
		if (date == null) {
			return getMinDate();
		}
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS").parse(dateStr + " 00:00:00 000");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return date;
		}
	}

	/**
	 * 取出日期中，今天结束的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndTime(Date date) {
		if (date == null) {
			return getMaxDate();
		}
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS").parse(dateStr + " 23:59:59 999");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return date;
		}
	}

	/**
	 * 取出今天日期中，今天凌晨的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTodayBeginTime() {
		return getBeginTime(new Date());
	}

	/**
	 * 取出今天日期中，今天结束的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTodayEndTime() {
		return getEndTime(new Date());
	}

	public static Date getMinDate() {
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("19000101 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}

	public static Date getMaxDate() {
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("29000101 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 取出某个日期的多少个月之后的日期
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getAfterMonths(Date date, int months) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public static String timeAddOneMillisecond(String inTime) {
		// Calendar calendar = Calendar.getInstance();
		//
		// Date date = null;
		//
		// date = DateTimeTool.parseToMillisecond(currentDate);
		//
		// calendar.setTime(date);
		//
		// calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)
		// + 1);
		//
		// return DateTimeTool.formatToMillisecond(calendar.getTime());

		DateTime dateTime = MILLISECOND_DATETIME_FORMATTER.parseDateTime(inTime);

		return dateTime.plusMillis(1).toString(MILLISECOND_DATETIME_FORMATTER);

	}

	public static String timeAddOneSecond(String inTime) {
		DateTime dateTime = SECOND_DATETIME_FORMATTER.parseDateTime(inTime);

		return dateTime.plusSeconds(1).toString(SECOND_DATETIME_FORMATTER);
	}

	public static boolean isBefore(String aDate, String bDate) {
		DateTime aDateTime = SECOND_DATETIME_FORMATTER.parseDateTime(aDate);
		DateTime bDateTime = SECOND_DATETIME_FORMATTER.parseDateTime(bDate);

		return aDateTime.isBefore(bDateTime);
	}

	/**
	 * 取出某个日期的多少天之后的日期
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getAfterDays(Date date, int days) {
		return new DateTime(date).plusDays(days).toDate();
	}

	public static String getAfterDays(String sDate, int days) {
		return SECOND_DATETIME_FORMATTER.parseDateTime(sDate).plusDays(days).toString(SECOND_DATETIME_FORMATTER);
	}

	/**
	 * 取出某个日期的多少小時之前的日期
	 * 
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getBeforeHours(Date date, int hours) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -hours);
		return cal.getTime();
	}

	public static Date getBeforeHours(String dateString, int hours) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(DateTimeTool.parse(dateString));
		cal.add(Calendar.HOUR, -hours);
		return cal.getTime();
	}

	/**
	 * 取出某个日期多少天之前的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getBeforeDays(Date date, int days) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

	/**
	 * 計算兩天的差異天數
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 * @author Xavier
	 */
	public static int getDiffDay(Date sDate, Date eDate) {
		long quot = eDate.getTime() - sDate.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return (int) quot;
	}

	public static double getDiffPrecisionDay(Date sDate, Date eDate) {
		long quot = eDate.getTime() - sDate.getTime();
		double q = quot / 1000.0 / 60.0 / 60.0 / 24.0; // 這邊要全部都是小數計算, 否則會有誤差
		return q;
	}

	public static Period getPeriod(long startMillis, long endMillis) {
		Interval interval = new Interval(startMillis, endMillis);
		Period period = interval.toPeriod(PeriodType.days());

		return period;
	}

	public static Period getPeriod(Date startDate, Date endDate) {
		return getPeriod(startDate.getTime(), endDate.getTime());
	}

	public static Period getPeriod(String startDate, String endDate) {
		return getPeriod(parse(startDate), parse(endDate));
	}

	public static Period getPeriod(DateTime startDateTime, DateTime endDateTime) {
		return getPeriod(startDateTime.getMillis(), endDateTime.getMillis());
	}

	public static int getDiffDays(String startDate, String endDate) {
		return getPeriod(startDate, endDate).getDays();
	}

	/**
	 * 等分sDate与eDate，并返回第一个等分时间点。
	 * 
	 * @param sDate
	 * @param eDate
	 * @param divisionCount
	 *            等分数量
	 * @return
	 */
	public static String getDivisionEndTime(String sDate, String eDate, int divisionCount) {
		Period period = getPeriod(sDate, eDate);
		Period newPeriod = period.toStandardDuration().dividedBy(divisionCount).toPeriod();

		DateTime newEdateTime = SECOND_DATETIME_FORMATTER.parseDateTime(sDate).plus(newPeriod);

		return format(newEdateTime);
	}

	/**
	 * 二等分sDate与eDate，返回中间时间点
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static String getDivisionEndTime(String sDate, String eDate) {
		return getDivisionEndTime(sDate, eDate, 2);
	}

	/**
	 * add by cjp 20170510 取出某个日期的多少分之后的日期
	 * 
	 * @param date
	 * @param min
	 * @return
	 */
	public static Date getAfterMins(Date date, int min) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, min);
		return cal.getTime();
	}

	public static Date getAfterMins(String dateString, int min) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(DateTimeTool.parse(dateString));
		cal.add(Calendar.MINUTE, min);
		return cal.getTime();
	}


	
	
	
	
	/**
	 * 时间戳转化为默认的格式
	 * add by cjp 20170605
	 * @param s
	 * @return
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SECOND_FORMAT);
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/*public static void main(String[] args) {
		// DateTimeFormatter fmt = DateTimeFormat.forPattern(SECOND_FORMAT);
		// fmt.withLocale(Locale.SIMPLIFIED_CHINESE);
		// String inputDateTime = "2013-05-24 06:02:20";
		// DateTime dt = fmt.parseDateTime(inputDateTime);
		// String outputDateTime = fmt.print(dt);
		// System.out.println(outputDateTime);
		// System.out.println();

		// DateTimeFormatter fmt2 = new DateTimeFormatterBuilder()
		// .appendDayOfMonth(2)
		// .appendLiteral('-')
		// .appendMonthOfYearShortText()
		// .appendLiteral('-')
		// .appendTwoDigitYear(1956) // pivot = 1956
		// .toFormatter();
		//
		// System.out.println(fmt2.getPrinter().estimatePrintedLength());
		//
		// DateTime now = DateTime.now();
		// String nowStr = now.toString(SECOND_FORMAT);
		// System.out.println(nowStr);
		//// System.out.println(timeAddOneSecond(now));
		// String pushTimeSpan = "01:53:12";
		// DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss");
		// DateTime dt = fmt.parseDateTime(pushTimeSpan);
		// System.out.println(dt.getHourOfDay() + " " + dt.getMinuteOfHour() + "
		// " + dt.getSecondOfMinute());
		//
		// System.out.println(now.plusHours(dt.getHourOfDay())
		// .plusMinutes(dt.getMinuteOfHour())
		// .plusSeconds(dt.getSecondOfMinute())
		// .toString(SECOND_FORMAT));
		// DateTime now = DateTime.now();
		// DateTime past = now.minusDays(1).minusHours(7);
		//
		// System.out.println(getDivisionEndTime(format(past), format(now)));

		System.out.println(getDiffDays("2016-07-19 17:11:18", "2016-10-17 17:27:19"));

	}*/
}
