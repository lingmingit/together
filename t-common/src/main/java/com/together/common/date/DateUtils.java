package com.together.common.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map.Entry;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.base.CommonConstants;
import com.together.common.base.RegularExpressionStatics;


/**
 * 基于日期类型的通用工具类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public final class DateUtils {
	
	
	
	/**日期  格式化字符串***/
	private static String DateFormatter = "yyyy-MM-dd";
	/**日期 时间 格式化字符串***/
	private static String DateTimeFormatter = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 构造函数<p>
	 */
	private DateUtils() {}
	
	
	
	
	/***
	 * 获取当前日期的中文星期几
	 * @param date日期
	 * @return 中文星期几
	 */
	public static String getDateToWeek(Date date){
		return (new SimpleDateFormat("EEEE")).format(date);
	}
	
	
	/****
	 * 获取 当前日期字符串 返回 最大日期+时分秒
	 * 如： yyyy-MM-dd 00:00:00
	 * @param date 日期对象
	 * @return 返回日期当天最小日期+时间 Date
	 */
	public static java.util.Date getTodayMinDateTime(java.util.Date date){
		if(CommonUtils.isEmptyObject(date)){
			return date;
		}
		return getTodayMinDateTime(DateUtils.getDateBySpecifiedFormatter(date, DateFormatter));
	}
	
	
	/****
	 * 获取 当前日期字符串 返回 最大日期+时分秒
	 * 如： yyyy-MM-dd 23:59:59
	 * @param dateStr 日期字符串 yyyy-MM-dd
	 * @return 返回日期当天最小日期+时间 Date
	 */
	public static java.util.Date getTodayMinDateTime(String dateStr){
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		dateStr += " 00:00:00";
    	return getDateFromSpecifiedString(dateStr, DateTimeFormatter);
	}
	
	

	/****
	 * 获取 当前日期字符串 返回 最大日期+时分秒
	 * 如： yyyy-MM-dd 23:59:59
	 * @param date 日期对象
	 * @return 返回日期当天最大日期+时间 Date
	 */
	public static java.util.Date getTodayMaxDateTime(java.util.Date date){
		if(CommonUtils.isEmptyObject(date)){
			return date;
		}
		return getTodayMaxDateTime(DateUtils.getDateBySpecifiedFormatter(date, DateFormatter));
	}
	
	
	/****
	 * 获取 当前日期字符串 返回 最大日期+时分秒
	 * 如： yyyy-MM-dd 23:59:59
	 * @param dateStr 日期字符串 yyyy-MM-dd
	 * @return 返回日期当天最大日期+时间 Date
	 */
	public static java.util.Date getTodayMaxDateTime(String dateStr){
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		dateStr += " 23:59:59";
    	return getDateFromSpecifiedString(dateStr, DateTimeFormatter);
	}
	
	
	
	/**
	 * 获取当前日期年份信息<p>
	 * @return 当前日期年份信息
	 */
	public static String getCurrentYear() {
		return String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
	}
	
	/**
	 * 获取指定日期的农历字符串<p>
	 * @param date 日期字符串<br>
	 * @return 农历字符串<br>
	 */
	public static String getLunarDate(String date) {
		return new LunarCalendar(getDateFromSpecifiedString(date)).getLunarDateString();
	}
	
	/**
	 * 获取当前月的总天数<p>
	 * @return 当前月总天数<br>
	 */
	public static int getDaysFromCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.DATE);
	}
	
	/**
	 * 获取当前系统时间的毫秒数<p>
	 * @return 毫秒数<br>
	 */
	public static String getMillsFromCurrentTime() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 判断日期字符串是否合法<p>
	 * @param str 日期字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean isLegalDateString(String str) {
		boolean rtnB = false;
		if (StringUtils.isNotEmpty(str)) {
			// 初始化日期转换对象
			java.text.SimpleDateFormat formatter = null;
			try {
				java.util.Iterator<?> iterator = CommonConstants.DATEMAP.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
					if (str.matches(entry.getKey().toString())) {
						formatter = new SimpleDateFormat(entry.getValue().toString());
						break;
					}
				}
				rtnB = (formatter != null && str.equals(formatter.format(formatter.parse(str))));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return rtnB;
	}
	
	/**
	 * 判断指定年份是否闰年<p>
	 * @param year 指定年份<br>
	 * @return true:是 false:非<br>
	 */
	public static boolean isLeapYearForInteger(int year) {
		return year != 0 && ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
	}
	
	/**
	 * 获取当前日期的数据库日期对象<p>
	 * @return 数据库日期对象<br>
	 */
	public static java.sql.Date getSQLDateFromCurrentTime() {
		return new java.sql.Date(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前月的第一天<p>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getFirstDayFromCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(java.util.GregorianCalendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取日期的星期字符串<p>
	 * @param date 日期对象<br>
	 * @return 星期字符串<br>
	 */
	public static String getWeekString(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0) {
			dayOfWeek = 0;
		}
		return CommonConstants.WEEK_STR_ARRAY[dayOfWeek];
	}
	
	/**
	 * 判断指定年份是否闰年<p>
	 * @param str 日期字符串<br>
	 * @return true:是 false:非<br>
	 */
	public static boolean isLeapYearForString(String str) {
		return StringUtils.isNotEmpty(str)
				&& isLeapYearForDate(getDateFromSpecifiedString(str));
	}
	
	/**
	 * 获取当前月的最后一天<p>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getLastDayFromCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前周最后一天的日期<p>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getLastWeekDayFromCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(java.util.GregorianCalendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}
	
	/**
	 * 判断指定日期对象中年份是否闰年<p>
	 * @param date 指定日期<br>
	 * @return true:是 false:非<br>
	 */
	public static boolean isLeapYearForDate(java.util.Date date) {
		return CommonUtils.isNotEmptyObject(date)
				&& isLeapYearForInteger(getYearFromSpecifiedDate(date));
	}
	
	/**
	 * 获取当前周第一天的日期<p>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getFirstWeekDayFromCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前日期的数据库日期对象<p>
	 * @return 数据库日期对象<br>
	 */
	public static java.sql.Timestamp getTimeStampFromCurrentTime() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 获取指定日期的当月总天数<p>
	 * @param str 日期字符串<br>
	 * @return 总天数<br>
	 */
	public static int getDaysFromSpecifiedString(String str) {
		return getDaysFromSpecifiedDate(getDateFromSpecifiedString(str));
	}
	
	/**
	 * 当前系统日期增加|减少指定天数<p>
	 * @param days 指定天数<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date addDaysToCurrentTime(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	/**
	 * 当前日期增加|减少指定年数<p>
	 * @param years 指定年数<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date addYearsToCurrentTime(int years) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();
	}
	
	/**
	 * 当前日期增加|减少指定月数<p>
	 * @param months 指定月数<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date addMonthsToCurrentTime(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期的当月总天数<p>
	 * @param date 日期对象<br>
	 * @return 总天数<br>
	 */
	public static int getDaysFromSpecifiedDate(java.util.Date date) {
		int rtnInt = 0;
		if (CommonUtils.isNotEmptyObject(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			rtnInt = calendar.getActualMaximum(Calendar.DATE);
		}
		return rtnInt;
	}
	
	/**
	 * 将毫秒数据转换成日期对象<p>
	 * @param mill 毫秒<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getDateFromSpecifiedMill(long mill) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mill);
		return calendar.getTime();
	}
	
	/**
	 * 将毫秒数据转换成日期对象<p>
	 * @param mill 毫秒<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getDateFromSpecifiedMill(String mill) {
		return DataCheckUtils.checkLong(mill) ? getDateFromSpecifiedMill(Long.parseLong(mill)) : null;
	}
	
	/**
	 * 将数据库日期对象转换为系统日期对象<p>
	 * @param date 数据库日期对象<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getDateFormSQLDate(java.sql.Date date) {
		return CommonUtils.isNotEmptyObject(date) ? getDateFromSpecifiedMill(date.getTime()) : null;
	}
	
	/**
	 * 将DOUBLE数据类型数据转换为日期对象<p>
	 * @param number DOUBLE类型数据<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getDateFromSpecifiedDouble(double number) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Double.valueOf(number).longValue());
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期月份的最后一天<p>
	 * @param str 日期字符串<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getLastDayFromSpecifiedString(String str) {
		return getLastDayFromSpecifiedDate(getDateFromSpecifiedString(str));
	}
	
	/**
	 * 获取指定日期月份的第一天<p>
	 * @param str 日期字符串<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getFirstDayFromSpecifiedString(String str) {
		return getFirstDayFromSpecifiedDate(getDateFromSpecifiedString(str));
	}
	
	/**
	 * 将数据库日期对象转换为系统日期对象<p>
	 * @param ts 数据库日期对象<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getDateFromTimestamp(java.sql.Timestamp ts) {
		return CommonUtils.isNotEmptyObject(ts) ? getDateFromSpecifiedMill(ts.getTime()) : null;
	}
	
	/**
	 * 获取指定日期月份的第一天<p>
	 * @param date 日期对象<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getFirstDayFromSpecifiedDate(java.util.Date date) {
		java.util.Date rtnDate = null;
		if (CommonUtils.isNotEmptyObject(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, 1);
			rtnDate = calendar.getTime();
		}
		return rtnDate;
	}
	
	/**
	 * 获取指定日期月份的最后一天<p>
	 * @param date 日期对象<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getLastDayFromSpecifiedDate(java.util.Date date) {
		java.util.Date rtnDate = null;
		if (CommonUtils.isNotEmptyObject(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, 1);
			calendar.roll(Calendar.DATE, -1);
			rtnDate = calendar.getTime();
		}
		return rtnDate;
	}
	
	/***
	 * 字符串转化为日期类型对象<p>
	 * @param str 日期字符串
	 * @param formater 日期格式字符串
	 * @param str 日期字符串<br>
	 * @return Date 日期对象<br>
	 */
	public static java.util.Date getDateFromSpecifiedString(String str , String formater) {
		if (StringUtils.isNotEmpty(str)) {
			// 初始化日期转换对象
			java.text.SimpleDateFormat formatter = null;
			try {
				formatter = new java.text.SimpleDateFormat(formater);
				return formatter != null ? formatter.parse(str) : null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 字符串转化为日期类型对象<p>
	 * 支持的日期格式:[yyyyMMdd] [yyyy-MM-dd] [yyyy/MM/dd] [yyyy年MM月dd日]<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * :[yyyyMMddHHmmss] [yyyy-MM-dd HH:mm:ss] [yyyy/MM/dd HH:mm:ss]<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * :[yyyy年MM月dd日 HH时mm分ss秒]<br>
	 * @param str 日期字符串<br>
	 * @return Date 日期对象<br>
	 */
	public static java.util.Date getDateFromSpecifiedString(String str) {
		if (StringUtils.isNotEmpty(str)) {
			// 初始化日期转换对象
			java.text.SimpleDateFormat formatter = null;
			try {
				java.util.Iterator<?> iterator = CommonConstants.DATEMAP.entrySet().iterator();
				while (iterator.hasNext()) {
					java.util.Map.Entry<?, ?> entry = (java.util.Map.Entry<?, ?>) iterator.next();
					if(str.matches(entry.getKey().toString())) {
						formatter = new java.text.SimpleDateFormat(entry.getValue().toString());
						break;
					}
				}
				return formatter != null ? formatter.parse(str) : null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 根据自定义日期格式格式化日期对象<p>
	 * @param date 日期对象
	 * @param formater 格式化字符串<br>
	 * @return 日期字符串<br>
	 */
	public static String getDateBySpecifiedFormatter(Date date , String formater) {
		return formatDateBySpecifiedFormatter(date , formater);
	}
	
	/**
	 * 根据自定义日期格式格式化当前日期对象<p>
	 * @param formater 格式化字符串<br>
	 * @return 日期字符串<br>
	 */
	public static String getCurrentDateBySpecifiedFormatter(String formater) {
		return formatDateBySpecifiedFormatter(new java.util.Date(), formater);
	}
	
	/**
	 * 计算两日期之间的间隔天数<p>
	 * @param begin 开始日期字符串<br>
	 * @param end   结束日期字符串<br>
	 * @return 间隔天数<br>
	 */
	public static double getDaysBetweenSpecifiedString(String begin, String end) {
		return getDaysBetweenSpecifiedDate(
				getDateFromSpecifiedString(begin),
				getDateFromSpecifiedString(end));
	}
	
	/**
	 * 获取指定日期所在周第一天的日期<p>
	 * @param date 日期对象<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getFirstWeekDayFromSpecifiedDate(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(java.util.GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}
	
	/**
	 * 获取指定日期所在周最后一天的日期<p>
	 * @param date 日期对象<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date getLastWeekDayFromSpecifiedDate(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(java.util.GregorianCalendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}
	
	/**
	 * 指定日期增加|减少指定天数<p>
	 * @param date 日期对象<br>
	 * @param days 指定天数<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date addDaysToSpecifiedDate(java.util.Date date, int days) {
		java.util.Date rtnDate = null;
		if (CommonUtils.isNotEmptyObject(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			rtnDate = calendar.getTime();
		}
		return rtnDate;
	}
	
	/**
	 * 指定日期增加|减少指定月数<p>
	 * @param date 日期对象<br>
	 * @param months 指定月数<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date addMonthsToSpecifiedTime(java.util.Date date, int months) {
		java.util.Date rtnDate = null;
		if (CommonUtils.isNotEmptyObject(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
			rtnDate = calendar.getTime();
		}
		return rtnDate;
	}
	
	/**
	 * 指定日期增加|减少指定年数<p>
	 * @param date 日期对象<br>
	 * @param years 指定年数<br>
	 * @return 日期对象<br>
	 */
	public static java.util.Date addYearsToSpecifiedTime(java.util.Date date, int years) {
		java.util.Date rtnDate = null;
		if (CommonUtils.isNotEmptyObject(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, years);
			rtnDate = calendar.getTime();
		}
		return rtnDate;
	}
	
	/**
	 * 将系统日期转换为数据库日期对象<p>
	 * @param date 日期对象<br>
	 * @return 数据库日期对象<br>
	 */
	public static java.sql.Date getSQLDateFromSpecifiedDate(java.util.Date date) {
		return CommonUtils.isNotEmptyObject(date) ? new java.sql.Date(date.getTime()) : null;
	}
	
	/**
	 * 将系统日期转换为数据库日期对象<p>
	 * @param date 日期对象<br>
	 * @return 数据库日期对象<br>
	 */
	public static java.sql.Timestamp getTimestampFromSpecifiedDate(java.util.Date date) {
		return CommonUtils.isNotEmptyObject(date) ? new java.sql.Timestamp(date.getTime()) : null;
	}
	
	/**
	 * 根据自定义日期样式格式化日期对象<p>
	 * @param date 日期对象<br>
	 * @param formater 日期格式样式<br>
	 * @return 日期字符串<br>
	 */
	public static String formatDateBySpecifiedFormatter(java.util.Date date, String formater) {
		return CommonUtils.isNotEmptyObject(date) && StringUtils.isNotEmpty(formater)
				&& formater.matches(RegularExpressionStatics.DATE_FORMATER_RULER) ? new SimpleDateFormat(formater).format(date) : "";
	}
	
	/**
	 * 根据自定义日期样式格式化数据库日期对象<p>
	 * @param date 数据库日期对象<br>
	 * @param formater 日期格式样式<br>
	 * @return 日期字符串<br>
	 */
	public static String formatDateBySpecifiedFormatter(java.sql.Date date, String formater) {
		return CommonUtils.isNotEmptyObject(date) ? formatDateBySpecifiedFormatter(
				getDateFromSpecifiedMill(date.getTime()), formater) : "";
	}
	
	/**
	 * 计算两日期之间的间隔天数<p>
	 * @param begin 开始日期<br>
	 * @param end   结束日期<br>
	 * @return 间隔天数<br>
	 */
	public static double getDaysBetweenSpecifiedDate(java.util.Date begin, java.util.Date end) {
		return CommonUtils.isNotEmptyObject(begin)
				&& CommonUtils.isNotEmptyObject(end) ? (end.getTime() - begin.getTime()) / 86400000.0 : 0.0d;
	}
	
	/**
	 * 获取指定系统日期对象的年份<p>
	 * @param date 日期对象<br>
	 * @return 年份<br>
	 */
	private static int getYearFromSpecifiedDate(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
}
