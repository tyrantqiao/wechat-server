package com.tyrantqiao.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class DateUtil {

	public static final SimpleDateFormat yMdSdf = new SimpleDateFormat("yyyyMMdd");

	private static final String NUMBER_REGEX = "[0-9]*";
	/**
	 * yyyy-MM
	 */
	public static final String MONTH = "yyyy-MM";
	/**
	 * yyyy-MM-dd
	 */
	public static final String DATE = "yyyy-MM-dd";
	/**
	 * yyyyMMdd
	 */
	public static final String DATE_yyyyMMDD = "yyyyMMdd";
	/**
	 * MM-dd HH:mm
	 */
	public static final String DATE_MM_DD = "MM-dd HH:mm";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String FULL_DATETIME = "yyyyMMddHHmmssSSS";
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String MSEC = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 无间隔日期格式 yyyyMMddHHmmss
	 */
	public static final String NOSPLIT_MDHM_DATETIME = "yyyyMMddHHmmss";

	/**
	 * 常规日期时间格式，24小时制yyyy-MM-dd HH:mm:ss
	 */
	public static final String NORMAL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 常规日期时间格式，24小时制yyyy年MM月dd日 HH时mm分
	 */
	public static final String NORMAL_DATETIME_FORMAT_CN = "yyyy年MM月dd日 HH时mm分";
	/**
	 * 常规日期时间格式，24小时制MM月dd日 HH时mm分
	 */
	public static final String NORMAL_MONTHDATETIME_FORMAT_CN = "MM月dd日 HH时mm分";
	/**
	 * 常规日期时间格式，24小时制MM月dd日
	 */
	public static final String NORMAL_MONTHDATE_FORMAT_CN = "MM月dd日";

	/**
	 * 常规日期时间格式，24小时制yyyy-MM-dd HH:mm 没有秒
	 */
	public static final String NORMAL_DATETIME_FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";

	/**
	 * yyyy-MM-dd HH
	 */
	public static final String NORMAL_DATETIME_FORMAT_HOUR = "yyyy-MM-dd HH";

	/**
	 * 常规日期，yyyy-MM-dd
	 **/
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 无间隔日期格式，yyyyMMddHHmmssS
	 */
	public static final String NOSPLIT_DATE_FORMAT = "yyyyMMddHHmmssS";

	/**
	 * 斜杠间隔日期格式，yyyy/MM/dd HH:mm
	 */
	public static final String SLASHPLIT_DATE_FORMAT = "yyyy/MM/dd HH:mm";

	/**
	 * yyyy-MM-dd HH:mm:ss java.util.Locale.CHINA
	 */
	public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
			Locale.CHINA);

	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 锁对象
	 */
	private static final Object LOCKOBJ = new Object();

	/**
	 * 存放不同的日期模板格式的sdf的Map
	 */
	private static Map<String, ThreadLocal<SimpleDateFormat>> SDFMAP = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	public static final String NOW_YEAR = "YEAR";

	public static final String NOW_MONTH = "MONTH";

	public static final String NOW_WEEK = "WEEK";

	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";

	/**
	 * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
	 *
	 * @param pattern
	 * @return
	 */
	private static SimpleDateFormat getDateFormat(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = SDFMAP.get(pattern);
		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (LOCKOBJ) {
				tl = SDFMAP.get(pattern);
				if (tl == null) {
					// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
					// System.out.println("put new sdf of pattern " + pattern + " to map");
					// 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							// System.out.println("thread: " + Thread.currentThread() + " init
							// pattern: " + pattern);
							return new SimpleDateFormat(pattern);
						}
					};
					SDFMAP.put(pattern, tl);
				}
			}
		}
		return tl.get();
	}

	/**
	 * @Title: formatNew
	 * @Description: 返回格式化时间
	 * @author: lilsh lilsh@corp.21cn.com
	 * @date: 2014-9-15 下午4:45:35
	 */
	public static String formatNew(Date date, String dateFormat) {
		return DateUtil.getDateFormat(dateFormat).format(date);
	}

	/**
	 * @Title: formatNew
	 * @Description: 返回格式化时间
	 * @author: lilsh lilsh@corp.21cn.com
	 * @date: 2014-9-15 下午4:45:35
	 */
	public static String formatNew(Object date, String dateFormat) {
		return DateUtil.getDateFormat(dateFormat).format(date);
	}

	/**
	 * @Title: parse
	 * @Description: 返回解析时间
	 * @author: lilsh lilsh@corp.21cn.com
	 * @date: 2014-9-15 下午4:45:35
	 */
	public static Date parse(String strDate, String dateFormat) throws ParseException {
		return DateUtil.getDateFormat(dateFormat).parse(strDate);
	}

	/*
	 * 获取当前时间戳,格式:yyyy-MM-dd HH:mm:ss,时区为:东八区(GMT+8)
	 *
	 * @return
	 */
	public static String getCurrentTimestampStr(String sdf, String tz) {
		DateFormat df = new SimpleDateFormat(sdf);
		df.setTimeZone(TimeZone.getTimeZone(tz));
		return df.format(new Date());
	}

	/*
	 * 将日期转化成指定格式字符串
	 */
	public static String getDateStr(Date d, String sdf) {

		DateFormat df = new SimpleDateFormat(sdf);
		return df.format(d);
	}

	/*
	 * 获得输入日期的前后日期
	 */
	public static String addDateStr(Date d, int days, String sdf) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, days);
		DateFormat df = new SimpleDateFormat(sdf);
		return df.format(c.getTime());
	}

	/**
	 * 根据天数得到指定日期的前几天或者后几天的日期
	 *
	 * @param currentDate 当前日期
	 * @param days        天数
	 * @return 返回日期
	 */
	public static Date getDateByByDaysInt(Date currentDate, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static String getDateEndTimeStr(Date d, int days) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d);
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.add(Calendar.DAY_OF_YEAR, days);
		return DateUtil.format(c2.getTime(), NORMAL_DATETIME_FORMAT);
	}

	public static String getDateStartTimeStr(Date d, int days) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.add(Calendar.DAY_OF_YEAR, days);
		return DateUtil.format(c2.getTime(), NORMAL_DATETIME_FORMAT);
	}

	/**
	 * @author:caoshanshan(caoshsh@corp.2cn.com)) description:在原有时间上增加或减少n个小时
	 * @params: String
	 */
	public static String getDateByAddHour(Date d, int hour) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d);
		c2.add(Calendar.HOUR, hour);
		return DateUtil.format(c2.getTime(), NORMAL_DATETIME_FORMAT);
	}

	/**
	 * @param d
	 * @return 返回当前日期的开始时间
	 * @description
	 */
	public static Date getDateStartTime(Date d, int days) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.add(Calendar.DAY_OF_YEAR, days);
		return c1.getTime();
	}

	/**
	 * @param d
	 * @return 返回当前日期的结束时间
	 * @description ...
	 */
	public static Date getDateEndTime(Date d, int days) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d);
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		c2.add(Calendar.DAY_OF_YEAR, days);
		return c2.getTime();
	}

	public static Date getFirstDayOfMonth(Date d) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d);
		c2.set(Calendar.DAY_OF_MONTH, 1);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		return c2.getTime();
	}

	public static Date getLastDayOfMonth(Date d) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d);
		c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		return c2.getTime();
	}

	public static Date addDate(Date d, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}

	public static int getDayOfDate(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static boolean isNumeric1(String str) {
		Pattern pattern = Pattern.compile(NUMBER_REGEX);
		return pattern.matcher(str).matches();
	}

	/**
	 * @throws ParseException
	 * @author:caoshanshan(caoshsh@corp.2cn.com)) description:讲日期字符串转换为日期格式
	 * @params: Date
	 */
	public static Date parseDateIgnoreCase(String dateStr) throws ParseException {
		if (dateStr == null || "".equalsIgnoreCase(dateStr)) {
			return null;
		} else {
			return DateUtil.parse(dateStr, NORMAL_DATE_FORMAT);
		}
	}

	public static Date parseDate(String dateStr, String type) throws Exception {
		if (dateStr == null || "".equalsIgnoreCase(dateStr)) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(type);
			return dateFormat.parse(dateStr);
		}
	}

	public static String format(Date date, String fmt) {
		DateFormat formatter = new SimpleDateFormat(fmt);
		return formatter.format(date);
	}

	/**
	 * @param date
	 * @return String
	 * 返回类型 @throws
	 * @Title: format
	 * @Description: TODO(yyyy - MM - dd)
	 */
	public static String format(Date date) {
		if (date == null) {
			return "";
		}
		return DateUtil.format(date, NORMAL_DATE_FORMAT);
	}

	/**
	 * @return 设定文件 @return String
	 * 返回类型 @throws
	 * @Title: formatCurrentDate
	 * @Description: TODO(yyyy - MM - dd)
	 */
	public static String formatCurrentDate() {
		return DateUtil.format(new Date(System.currentTimeMillis()), NORMAL_DATE_FORMAT);
	}

	/**
	 * 常规日期时间格式，24小时制yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return DateUtil.format(date, NORMAL_DATETIME_FORMAT);
	}

	/**
	 * 常规日期时间格式，24小时制yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String formatCurrentDateTime() {
		return DateUtil.format(new Date(System.currentTimeMillis()), NORMAL_DATETIME_FORMAT);
	}

	// 获取当前时间戳 从1970年到当前的毫秒数
	public static long getTimeInMillis() {
		Calendar calendar = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT");
		calendar.setTimeZone(tz);
		return calendar.getTimeInMillis();// 本次提交当前时间戳。长整型，是从1970年到当前的毫秒数。
	}

	public static Date getNow() {
		return new Date(System.currentTimeMillis());
	}

	// 获取某一天所在那一年的第一天
	public static Date getFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
		return DateUtil.getToday(cal.getTime());
	}

	// 获取某一天所在那一年的最后一天
	public static Date getLastDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		return DateUtil.getTodayLast(cal.getTime());
	}

	// 得到今天凌晨的时间.
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 得到今天凌晨的时间. 字符串
	public static String getTodayStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return DateUtil.format(cal.getTime(), NORMAL_DATETIME_FORMAT);
	}

	// 得到今日23：59 时间
	public static Date getTodayLast() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	// 得到今日23：59 时间 字符串
	public static String getTodayLastStr() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return DateUtil.format(cal.getTime(), NORMAL_DATETIME_FORMAT);
	}

	// 得到某一天的凌晨时间
	public static Date getToday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 得到某一天的23：59 时间
	public static Date getTodayLast(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	// 得到某一天的00:00 时间
	public static Date getTodayBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 000);
		return cal.getTime();
	}

	// 得到几天前当天时间===================================================================
	public static Date getSomeDate(Date date, int dayNum) {
		Calendar cal = Calendar.getInstance();
		long DAY = 1000 * 3600 * 24;
		cal.setTimeInMillis(date.getTime() + DAY * dayNum);
		return cal.getTime();
	}

	// 得到几月前当天时间===================================================================
	public static Date getSomeMonthDate(Date date, int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + monthNum);
		return cal.getTime();
	}

	// 得到24小时内某小时的开始时间
	public static Date getSubsectionHourBegin(Date date, int sub) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, sub);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 得到24小时内某小时的末尾时间
	public static Date getSubsectionHourEnd(Date date, int sub) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, sub);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getTheDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	// 返回的月数是 自然月-1 也就是说返回的月是从 0--11
	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH);
	}

	public static int getDate(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMin(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	// 得到这个星期开始的时间,星期的开始从getFirstDayOfWeek()得到
	public static Date getThisWeekStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -(cal.get(Calendar.DAY_OF_WEEK) - 1));
		return DateUtil.getTheDay(cal.getTime());
	}

	// 本月的开始
	public static Date getThisMonthStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.getTheDay(cal.getTime());
	}

	// 本月的开始
	public static Date getMonthStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return DateUtil.getTheDay(cal.getTime());
	}

	public static Date getTheMonthStart(int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getThisMonthStart());
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}

	// 本月的结束
	public static Date getThisMonthEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateUtil.getTodayLast(cal.getTime());
	}

	// 本月的结束
	public static Date getMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateUtil.getTodayLast(cal.getTime());
	}

	// 当月天数
	public static int getMonthDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	// n分钟前或后 + -
	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return new Date(cal.getTime().getTime());
	}

	// n秒前或后 + -
	public static Date addSecond(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return new Date(cal.getTime().getTime());
	}

	// n小时前或后 + -
	public static Date addHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);
		return new Date(cal.getTime().getTime());
	}

	// n天前或后 + -
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);

		return new Date(cal.getTime().getTime());
	}

	// n月前或后 + -
	public static Date addMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);

		return new Date(cal.getTime().getTime());
	}

	// n年前或后 + -
	public static Date addYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);

		return new Date(cal.getTime().getTime());
	}

	// 计算两个日期之间的天数
	public static int getDays(Date date1, Date date2) {
		int days = 0;
		days = (int) (Math.abs((date2.getTime() - date1.getTime())) / (24 * 60 * 60 * 1000));
		return days;
	}

	public static int getSecond(Date date1, Date date2) {
		int second = 0;
		second = (int) (Math.abs((date1.getTime() - date2.getTime()) / 1000));
		return second;
	}

	public static int getMinute(Date date1, Date date2) {
		int minute = 0;
		minute = (int) (Math.abs((date1.getTime() - date2.getTime()) / (60 * 1000)));
		return minute;
	}

	public static int getCompareMinute(Date date1, Date date2) {
		int minute = 0;
		minute = (int) ((date1.getTime() - date2.getTime()) / (60 * 1000));
		return minute;
	}

	public static int getCompareDate(Date date1, Date date2) {
		int date = 0;
		date = (int) ((date1.getTime() - date2.getTime()) / (24 * 3600 * 1000));
		return date;
	}

	// 计算两个日期之间的时间差 详细到秒 返回类型为String
	public static String getDayDif(Date date1, Date date2) {
		long DAY = 24 * 60 * 60 * 1000;
		long between = Math.abs((date2.getTime() - date1.getTime()));
		long day = between / DAY;
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		return "" + day + "天" + hour + "小时" + min + "分" + s + "秒";
	}

	public static Date[] getMonthStartAndEnd(String dateStr) throws Exception {
		Date[] date = new Date[2];
		if (StringUtils.isNotBlank(dateStr)) {
			Date datePar = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
			date[0] = DateUtil.getFirstDayOfMonth(datePar);
			date[1] = DateUtil.getLastDayOfMonth(datePar);
		}
		return date;
	}

	/**
	 * 取得表示当天的时间对象
	 *
	 * @return
	 */
	public static Date getCurrentDay() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	public static long getDayOfHour(long time) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTimeInMillis();
	}

	public static long addDayOfHour(long time, int hour) {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time);
		ca.add(Calendar.HOUR_OF_DAY, hour);
		return ca.getTimeInMillis();
	}

	/**
	 * 解析简单格式的日期yyyy-MM-dd HH:mm字符串
	 *
	 * @param simpleDateStr
	 * @return
	 */
	public static Date parseSimpleForMinute(String simpleDateStr) {
		if (StringUtils.isBlank(simpleDateStr)) {
			return null;
		}
		try {
			DateFormat simpleDateFormatForMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return simpleDateFormatForMinute.parse(simpleDateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 返回某个时间的"yyyy-MM-dd HH:mm"字符串
	 *
	 * @param time
	 * @return
	 */
	public static String getTimeStringForMinute(Long time) {
		DateFormat simpleDateFormatForMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return simpleDateFormatForMinute.format(new Date(time));
	}

	public static String getDateStringForMinute(Date date) {
		DateFormat simpleDateFormatForMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return simpleDateFormatForMinute.format(date);
	}

	/**
	 * 解析简单格式yyyy-MM-dd HH:mm:ss的日期字符串
	 *
	 * @param simpleDateStr
	 * @return
	 */
	public static Date parseSimple(String simpleDateStr) {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isEmpty(simpleDateStr)) {
			return null;
		}
		try {
			return simpleDateFormat.parse(simpleDateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 返回某个时间的"yyyy-MM-dd HH:mm:ss"字符串
	 *
	 * @param time
	 * @return
	 */
	public static String getTimeString(Long time) {
		final DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date(time));
	}

	/**
	 * 返回日期的微秒数
	 *
	 * @param date
	 * @return
	 */
	public static Long getTimeLong(Date date) {
		return date.getTime();
	}

	/**
	 * 获取当前的时间
	 *
	 * @return
	 */
	public static Date getCurrentTime() {
		return new Date();
	}

	/**
	 * 获取当前的时间
	 *
	 * @return
	 */
	public static String formatCurrentTime(String patten) {
		return DateUtil.formatDateToString(new Date(), patten);
	}

	/**
	 * 将时间按格式转换为字符串，日期为空时转换为空字符串
	 *
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String formatDateToString(Date date, String patten) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sd = new SimpleDateFormat(patten);
		return sd.format(date);
	}

	/**
	 * Description: 把日期转换为字符串
	 *
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String DateToString(Date date) {
		if (date == null) {
			return "";
		}
		return DateUtil.DateToString(date, null);
	}

	/**
	 * Description: 把日期转换为字符串
	 *
	 * @param date
	 * @param format default:yyyy-MM-dd HH:mm:ss
	 * @return default:yyyy-MM-dd HH:mm:ss
	 */
	public static String DateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(format)) {
			return DATE_TIME_FORMAT.format(date);
		} else {
			return DateUtil.format(date, format);
		}
	}

	/**
	 * 将时间按24小时制格式("yyyy-MM-dd HH:mm:ss")转换为字符串，日期为空时转换为空字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateNormal(Date date) {
		if (null == date) {
			return "";
		}
		return DateUtil.format(date, NORMAL_DATETIME_FORMAT);
	}

	/**
	 * 将时间按24小时制格式("yyyy-MM-dd HH:mm:ss")转换为字符串，日期为空时转换为空字符串
	 *
	 * @param date
	 * @return
	 */
	public static String formatDateNormal(Long date) {
		if (null == date) {
			return "";
		}
		return DateUtil.format(new Date(date), NORMAL_DATETIME_FORMAT);
	}

	/**
	 * 功能描述：将字符串按格式转换为时间，字符串为空时转换为null
	 *
	 * @param dateStr 时间字符串
	 * @param patten  格式
	 * @return
	 * @version 1.0.0
	 * @since 1.0.0 create on: 2012-5-2
	 */
	public static Date formatStrToDate(String dateStr, String patten) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		try {
			return DateUtil.parse(dateStr, patten);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将字符串按24小时制格式("yyyy-MM-dd HH:mm:ss")转换为时间
	 *
	 * @param dateStr 时间字符串
	 * @return 转换成的时间，字符串为空时转换为null
	 */
	public static Date formatStrToNormalDate(String dateStr) {
		return DateUtil.formatStrToDate(dateStr, NORMAL_DATETIME_FORMAT);
	}

	/**
	 * 获得按时间字符time(格式 "HH:mm:ss")转换的日期date
	 *
	 * @param date
	 * @param timeString 格式 "HH:mm:ss"
	 * @return 非法返回null
	 */
	public static Date getDateByTimeString(Date date, String timeString) {
		timeString = DateUtil.formatDateToString(date, String.format("yyyy-MM-dd %s", timeString));
		return DateUtil.formatStrToDate(timeString, NORMAL_DATETIME_FORMAT);
	}

	/**
	 * 功能描述：获取对应日期的开始时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		String dateStartString = DateUtil.formatDateToString(date, "yyyy-MM-dd 00:00:00");
		return DateUtil.formatStrToDate(dateStartString, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 功能描述：获取对应日期的结束时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		String dateStartString = DateUtil.formatDateToString(date, "yyyy-MM-dd 23:59:59");
		return DateUtil.formatStrToDate(dateStartString, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getDay(Date date) {
		String dateStartString = DateUtil.formatDateToString(date, "yyyy-MM-dd 00:00:00");
		return DateUtil.formatStrToDate(dateStartString, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getODay(Date date) {
		String dateStartString = DateUtil.formatDateToString(date, "yyyy-MM-dd");
		return DateUtil.formatStrToDate(dateStartString, "yyyy-MM-dd");
	}

	/**
	 * 功能描述：获取昨天
	 *
	 * @return
	 * @version 1.0.0
	 * @since 1.0.0 create on: 2012-8-15
	 */
	public static Date getYesterday() {
		return DateUtil.getDayBefore(1);
	}

	/**
	 * 功能描述：获取前day天的日期
	 *
	 * @param day
	 * @return
	 * @version 1.0.0
	 * @since 1.0.0 create on: 2012-8-15
	 */
	public static Date getDayBefore(Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - day);
		return calendar.getTime();
	}

	public static Date getDayBefore(Date date, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - day);
		return calendar.getTime();
	}

	public static Date getDayAfter(Date date, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
		return calendar.getTime();
	}

	public static Date getMonthBefore(Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
		calendar.add(Calendar.MONTH, -month);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期的几个月前的信息。
	 *
	 * @param month
	 * @return
	 */
	public static Date getCurrentDateMonthBefore(Integer month) {
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
		calendar.add(Calendar.MONTH, -month);
		return calendar.getTime();
	}

	public static Date getDateMonthBefore(Date date, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 跨年有问题??
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
		calendar.add(Calendar.MONTH, -month);
		return calendar.getTime();
	}

	public static Date getMonthBefore(Date date, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 跨年有问题??
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - month);
		calendar.add(Calendar.MONTH, -month);
		return calendar.getTime();
	}

	public static Date getMonthAfter(Date date, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 跨年有问题??
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/***
	 *
	 * 功能描述：获得当月，如2012-08
	 *
	 * @return
	 * @version 1.0.0
	 * @since 1.0.0 create on: 2012-8-17
	 */
	public static String getMonth() {
		String dateStartString = DateUtil.formatDateToString(new Date(), "yyyy-MM");
		return dateStartString;
	}

	public static Date getLastMonth() {
		return DateUtil.getMonthBefore(1);
	}

	public static boolean isMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static boolean isMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static int getMonthDiff(Date bigDate, Date smallDate) {
		if (bigDate.compareTo(smallDate) <= 0) {
			return -1;
		}
		Calendar calInstance = Calendar.getInstance();
		calInstance.setTime(bigDate);
		int year1 = calInstance.get(Calendar.YEAR);
		int month1 = calInstance.get(Calendar.MONTH);
		calInstance.setTime(smallDate);
		int year2 = calInstance.get(Calendar.YEAR);
		int month2 = calInstance.get(Calendar.MONTH);
		return (year1 - year2) * 12 + month1 - month2;
	}

	public static int getDayDiff(Date bigDate, Date smallDate) {
		if (bigDate.compareTo(smallDate) <= 0) {
			return -1;
		}
		Long diff = (bigDate.getTime() - smallDate.getTime()) / 86400000;
		return diff.intValue();
	}

	/**
	 * 返回某个时间的"yyyy-MM-dd 00:00:00"字符串
	 *
	 * @param time
	 * @return
	 */
	public static String getTimeZeroString(Long time) {
		DateFormat simpleDateTimeZeroFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return simpleDateTimeZeroFormat.format(new Date(time));
	}

	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime()) {
			return 1;
		} else if (date1.getTime() < date2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 日期相减
	 *
	 * @param date  日期
	 * @param date1 日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((DateUtil.getMillis(date) - DateUtil.getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 年份相减
	 *
	 * @param date  日期
	 * @param date1 日期
	 * @return 返回相减后的年份
	 */
	public static int diffYear(Date date, Date date1) {
		return (int) (Math.abs((DateUtil.getMillis(date) - DateUtil.getMillis(date1))) / (24 * 3600 * 1000)) / 365;
	}

	/**
	 * 获取两个时间相差的毫秒数
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return end.getTime()-begin.getTime()
	 */
	public static Long diffMillis(Date begin, Date end) {
		return end.getTime() - begin.getTime();
	}

	/**
	 * 返回毫秒
	 *
	 * @param date 日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/***
	 * 判断checkDate时间是否在当前时间之前
	 *
	 * @param checkDate
	 * @return
	 */
	public static boolean isNowBefore(Date checkDate) {

		Calendar checkCal = Calendar.getInstance(Locale.CHINA);
		Calendar nowCal = Calendar.getInstance(Locale.CHINA);

		checkCal.setTime(checkDate);
		nowCal.setTime(new Date());

		return nowCal.before(checkCal);
	}

	/***
	 * 判断checkDate时间是否在当前时间之后
	 *
	 * @param checkDate
	 * @return
	 */
	public static boolean isNowAfter(Date checkDate) {

		Calendar checkCal = Calendar.getInstance(Locale.CHINA);
		Calendar nowCal = Calendar.getInstance(Locale.CHINA);

		checkCal.setTime(checkDate);
		nowCal.setTime(new Date());

		return nowCal.after(checkCal);
	}

	/**
	 * 判断checkDate时间是否在当前时间之后
	 *
	 * @param beginDate 开始时间
	 * @param endDate   结束时间
	 * @return boolean
	 */
	public static boolean isNowBetween(Date beginDate, Date endDate) {

		Calendar beginCal = Calendar.getInstance(Locale.CHINA);
		Calendar nowCal = Calendar.getInstance(Locale.CHINA);
		Calendar endCal = Calendar.getInstance(Locale.CHINA);

		beginCal.setTime(beginDate);
		endCal.setTime(endDate);
		nowCal.setTime(new Date());

		return nowCal.after(beginCal) && nowCal.before(endCal);
	}

	/**
	 * 将字符串转换为日期（包括时间）
	 *
	 * @param dateTimeString "yyyy-MM-dd HH:mm:ss"格式的日期字符串
	 * @return 日期时间
	 */
	public static Date convertToDateTime(String dateTimeString) {
		try {
			return DATE_TIME_FORMAT.parse(dateTimeString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取当前时间距离今日凌晨：00：00：00之间的秒数 〈功能详细描述〉
	 *
	 * @return int 剩余秒数
	 */
	public static int getTodayLeftSecounds() {
		return (int) ((DateUtil.getMillis(DateUtil.getDayEnd(new Date())) - DateUtil.getMillis(new Date())) / (1000));
	}

	public static Date getDateFromTimestamp(String longMillis) {
		if (StringUtils.isBlank(longMillis)) {
			return null;
		}
		if (longMillis.length() < 12) {
			longMillis = longMillis + "000";
			return new Date(Long.parseLong(longMillis));
		}
		return new Date(Long.parseLong(longMillis));
	}

	public static int getTodayLeftMiao() {
		Calendar curDate = Calendar.getInstance();
		Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
				.get(Calendar.DATE) + 1, 0, 0, 0);
		return (int) (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
	}

	/**
	 * 现在到下周一的时间差（秒）
	 *
	 * @param todayDate
	 * @return
	 */
	public static int getNextMonday(Date todayDate) {
		int mondayPlus = 0;
		Calendar todayCalendar = Calendar.getInstance();
		todayCalendar.setTime(todayDate);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = todayCalendar.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}

		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monday);
		Calendar mondaycCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);

		return (int) (mondaycCalendar.getTimeInMillis() - todayCalendar.getTimeInMillis()) / 1000;
	}

	/**
	 * 获取信息流中的每条信息的发布时间.
	 *
	 * <pre>
	 * 	1、发布时间距离当前时间≤1小时，则显示X分钟前；
	 * 	2、1小时＜发布时间距离当前时间≤24小时，则显示X小时前；
	 * 	3、24小时＜发布时间距离当前时间，则按天：
	 * 	1）当前日期-发布日期=1时，则显示昨天；显示1小时前；
	 * 	2）当前-发布日期≥X（X≥2）天时，则显示X天前。
	 * </pre>
	 *
	 * @return
	 * @since kuai 1.0
	 */
	public static String formatTimeStrForFeed(Date date) {
		long curTime = System.currentTimeMillis();
		long dateTime = date.getTime();
		long interval = curTime - dateTime;
		if (interval < 0) {
			return "刚刚";
		} else if (interval <= 3600000) {
			long minutes = interval / 60000;
			return minutes == 0 ? "刚刚" : minutes + "分钟前";
		} else if (interval <= 3600000 * 24) {
			long hours = interval / 3600000;
			return hours + "小时前";
		} else {
			int diffDate = (int) (interval / (3600000 * 24));
			if (diffDate == 1) {
				return "昨天";
			} else {
				return diffDate + "天前";
			}
		}
	}

	/**
	 * 进行中：0<=T<2小时，即话题已经在开播，但是开播不到2个小时，且未结束； XX分钟后：-1(小时)<T<0，即话题未开播，距离开播时间不到1小时，且未结束；
	 * XX小时后：-24(小时)<T=<-1，即话题未开播，距离开播时间超过1小时，不到24小时，且未结束; XX天后：T<=-24(小时)，即话题未开播，距离开播时间超过24小时，且未结束；
	 */
	public static String formatTimeStrForFeedV2(Date date) {
		long curTime = System.currentTimeMillis();
		long dateTime = date.getTime();
		long interval = curTime - dateTime; // 秒
		long beforeBeginTime = dateTime - curTime;
		if (interval >= 0 || beforeBeginTime < 60000) {
			return "进行中";
		} else if (beforeBeginTime < 3600000) {
			long minutes = beforeBeginTime / 60000;
			return minutes + "分钟后";
		} else if (beforeBeginTime < 3600000 * 24) {
			long hours = beforeBeginTime / 3600000;
			return hours + "小时后";
		} else {
			int diffDate = (int) (beforeBeginTime / (3600000 * 24));
			return diffDate + "天后";
		}
	}

	// 根据日期取得星期几
	public static String getChineseWeek(Date date) {
		String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	// 根据日期取得星期几 ，返回数字
	public static String getNumberWeek(Date date) {
		String[] weeks = {"7", "1", "2", "3", "4", "5", "6"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 功能描述：返回分
	 *
	 * @param date 日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 判断是否同一天
	 *
	 * @param date1
	 * @param date2
	 * @return isSameYear && isSameMonth && isSameDate
	 * @since kuai 1.0
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

	/**
	 * 判断是否同一天
	 *
	 * @param date1
	 * @param date2
	 * @return isSameYear && isSameMonth && isSameDate && isSameHour && isSameMinute
	 * @since kuai 1.0
	 */
	public static boolean isSameDateTime(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		boolean isSameHour = isSameDate && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY);
		boolean isSameMinute = isSameHour && cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE);

		return isSameMinute;
	}

	public static Integer getDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DATE);
	}

	public static Integer getDayByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 小时差
	 *
	 * @param date
	 * @param date2
	 * @return
	 */
	public static int diffHour(Date date, Date date2) {
		return (int) ((DateUtil.getMillis(date) - DateUtil.getMillis(date2)) / (60 * 60 * 1000));
	}

	/**
	 * 分钟差
	 *
	 * @param date
	 * @param date2
	 * @return
	 */
	public static int diffMin(Date date, Date date2) {
		return (int) ((DateUtil.getMillis(date) - DateUtil.getMillis(date2)) / (60 * 1000));
	}

	/**
	 * 时间差： 大于24小时：天数 小于24小时大于一小时：小时数 小于一小时大于一分钟：分钟数 小于一分钟：1分钟
	 *
	 * @param date
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String diffDateAfter(Date date, Date date2) {
		String dateAfter = null;
		Date currentDate = new Date();
		int day = DateUtil.diffDate(date, currentDate);
		if (day > 0) { // n天后

			// 小于24小时的也需要+1
			if ((date.getHours() - currentDate.getHours()) <= 0) {
				day++;
			}

			dateAfter = day + "天";
		} else if (day == 0) {
			day = DateUtil.diffHour(date, currentDate);
			if (day > 0) { // n小时后
				dateAfter = day + "小时";
			} else {
				day = DateUtil.diffMin(date, currentDate);
				if (day > 0) {
					dateAfter = day + "分钟";
				} else if (day == 0) {
					dateAfter = "1分钟";
				}
			}
		}
		return dateAfter;
	}

	/**
	 * 获得当前的时间的年月周的开始时间到现在时间
	 *
	 * @param val :YEAR(年),MONTH(月),WEEK(周)
	 * @return
	 */
	public static Map<String, String> getStartAndEndTime(String val) {
		Map<String, String> timeMap = new HashMap<>();
		// 获取当前时间
		Date now = DateUtil.getNow();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATETIME);
		String startTime = "";
		String endTime = getDateStr(getNow(), DATETIME);
		if (DateUtil.NOW_YEAR.equals(val)) {
			cal.clear();
			cal.set(year, Calendar.JANUARY, 1, 0, 0, 0);
			startTime = sdf.format(cal.getTime());
		}
		if (DateUtil.NOW_MONTH.equals(val)) {
			cal.clear();
			cal.set(year, month, 1, 0, 0, 0);
			startTime = sdf.format(cal.getTime());
		}

		if (DateUtil.NOW_WEEK.equals(val)) {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			startTime = sdf.format(cal.getTime());
		}
		timeMap.put(DateUtil.START_TIME, startTime);
		timeMap.put(DateUtil.END_TIME, endTime);

		return timeMap;

	}

	/**
	 * 根据时间与时间格式返回时间字符串
	 *
	 * @param date_format 时间格式 例如:yyyy-MM-dd HH:mm:ss
	 * @param date        时间对象
	 * @return 返回时间字符串
	 */
	public static String getTime(String date_format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(date_format);
		String current_time = formatter.format(date);
		return current_time;
	}

	/**
	 * 根据小时数得到指定日期的前几小时的日期
	 *
	 * @param currentDate 当前日期
	 * @param hours分钟
	 * @return 返回日期
	 */
	public static Date getDateByHoursInt(Date currentDate, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.HOUR, hours);
		return calendar.getTime();
	}

	public static String format(long ms) {
		boolean negative = false;
		if (ms < 0) {
			ms = -ms;
			negative = true;
		}
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		String hourText = "";
		String minuteText = "";
		if (!negative) {
			if (hour > 0) {
				hourText = hour + "小时";
			} else {
				minuteText = minute + "分钟";
			}
		} else {
			if (hour > 0) {
				hourText = hour + 1 + "小时";
			} else {
				minuteText = minute + "分钟";
			}
		}
		return hourText + minuteText;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(DateUtil.getTimeInMillis()-DateUtil.getMillis(DateUtil.getCurrentTime()));
		/*
		 * System.out.println(new Date()); System.out.println(getDateStartTime(new Date(), -10));
		 * String myString = "2014-04-09"; SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd"); try { Date d = sdf.parse(myString);
		 * System.out.println(getCompareDate(d, new Date())); } catch (ParseException e) { // TODO
		 * Auto-generated catch block  }
		 *
		 * System.out.println(DateUtil.parse("2014-04-12 12:15:33", DateUtil.DATETIME));
		 * System.out.println(DateUtil.parse("2014-04-12 12:15:33", DateUtil.DATE));
		 * System.out.println(DateUtil.parse("2014-04-12 12:15:33", DateUtil.MONTH));
		 *
		 * Thread t1 = new Thread() {
		 *
		 * @Override public void run() { System.out.println(DateUtil.formatNew(new Date(),
		 * DateUtil.DATETIME)); } };
		 *
		 * Thread t2 = new Thread() {
		 *
		 * @Override public void run() { System.out.println(DateUtil.formatNew(new Date(),
		 * DateUtil.DATE)); } };
		 *
		 * Thread t3 = new Thread() {
		 *
		 * @Override public void run() { System.out.println(DateUtil.formatNew(new Date(),
		 * DateUtil.MONTH)); } };
		 *
		 * Thread t4 = new Thread() {
		 *
		 * @Override public void run() { System.out.println(DateUtil.formatNew(new Date(),
		 * DateUtil.MSEC)); } };
		 *
		 * Thread t5 = new Thread() {
		 *
		 * @Override public void run() { System.out.println(DateUtil.formatNew(new Date(),
		 * DateUtil.FULL_DATETIME)); } };
		 *
		 * Thread t6 = new Thread() {
		 *
		 * @Override public void run() { System.out.println(DateUtil.formatNew(new Date(),
		 * DateUtil.DATETIME)); } };
		 *
		 * System.out.println("单线程执行: "); ExecutorService exec = Executors.newFixedThreadPool(10);
		 * exec.execute(t1); exec.execute(t2); exec.execute(t3); exec.execute(t4); exec.execute(t5);
		 * exec.execute(t6); exec.shutdown();
		 *
		 *
		 *
		 * // System.out.println("双线程执行: "); // ExecutorService exec2 =
		 * Executors.newFixedThreadPool(2); // exec2.execute(t1); // exec2.execute(t2); //
		 * exec2.execute(t3); // exec2.execute(t4); // exec2.execute(t5); // exec2.execute(t6); //
		 * exec2.shutdown();
		 */
		/*
		 * System.out.println(format(addMonth(new Date(), 999),NORMAL_DATETIME_FORMAT));
		 * System.out.println(isNowAfter(DateUtil.parse("2016-08-12 10:18:59",
		 * DateUtil.NORMAL_DATETIME_FORMAT)));
		 * System.out.println("假期从2016-11-10到"+format(addDay(DateUtil.parse("2016-11-10 00:00:00",
		 * DateUtil.NORMAL_DATETIME_FORMAT),98),NORMAL_DATE_FORMAT));
		 */

		System.out.println(DateUtil.getSecond(DateUtil
				.parse("2016-08-12 07:00:00", DateUtil.NORMAL_DATETIME_FORMAT), DateUtil
				.parse("2016-08-12 06:00:00", DateUtil.NORMAL_DATETIME_FORMAT)));
		long ts = System.currentTimeMillis() / 1000;
		String ts1 = "1481794601";
		System.out.println(ts);
		System.out.println(ts1);
		System.out.println(DateUtil.format(new Date(ts * 1000), NORMAL_DATETIME_FORMAT));
		System.out.println(DateUtil.format(DateUtil.getDateFromTimestamp(ts1), NORMAL_DATETIME_FORMAT));

		System.out.println(DateUtil.isSameDateTime(new Date(), DateUtil.addSecond(new Date(), 60)));

		System.out.println(DateUtil.format(DateUtil.addMonth(DateUtil
				.parse("2017-01-21 08:00:00", DateUtil.NORMAL_DATETIME_FORMAT), 1), NORMAL_DATETIME_FORMAT));

	}

}
