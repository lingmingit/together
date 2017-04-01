package com.together.common.date;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.GregorianCalendar;

import com.together.common.base.CommonConstants;
import com.together.common.number.NumberUtils;


/**
 * 农历日历工具类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public class LunarCalendar {
	private final static Pattern sFreg = Pattern.compile("^(\\d{2})(\\d{2})([\\s\\*])(.+)$");
	private final static Pattern wFreg = Pattern.compile("^(\\d{2})(\\d)(\\d)([\\s\\*])(.+)$");
	
	/**
	 * 根据当前日期进行节日寻找<p>
	 */
	private synchronized void findFestival() {
		int sM = this.getSolarMonth();
		int sD = this.getSolarDay();
		int lM = this.getLunarMonth();
		int lD = this.getLunarDay();
		int sy = this.getSolarYear();
		Matcher m;
		for (int i = 0; i < CommonConstants.LUNAR_NF.length; i++) {
			m = sFreg.matcher(CommonConstants.LUNAR_NF[i]);
			if (m.find()) {
				if (sM == NumberUtils.getIntegerFromString(m.group(1)) && sD == NumberUtils.getIntegerFromString(m.group(2))) {
					this.isSFestival = true;
					this.sFestivalName = m.group(4);
					if ("*".equals(m.group(3)))
						this.isHoliday = true;
					break;
				}
			}
		}
		for (int i = 0; i < CommonConstants.LUNAR_LF.length; i++) {
			m = sFreg.matcher(CommonConstants.LUNAR_LF[i]);
			if (m.find()) {
				if (lM == NumberUtils.getIntegerFromString(m.group(1)) && lD == NumberUtils.getIntegerFromString(m.group(2))) {
					this.isLFestival = true;
					this.lFestivalName = m.group(4);
					if ("*".equals(m.group(3)))
						this.isHoliday = true;
					break;
				}
			}
		}
		// 月周节日
		int w, d;
		for (int i = 0; i < CommonConstants.LUNAR_WF.length; i++) {
			m = wFreg.matcher(CommonConstants.LUNAR_WF[i]);
			if (m.find()) {
				if (this.getSolarMonth() == NumberUtils.getIntegerFromString(m.group(1))) {
					w = NumberUtils.getIntegerFromString(m.group(2));
					d = NumberUtils.getIntegerFromString(m.group(3));
					if (this.solar.get(Calendar.WEEK_OF_MONTH) == w && this.solar.get(Calendar.DAY_OF_WEEK) == d) {
						this.isSFestival = true;
						this.sFestivalName += "|" + m.group(5);
						if ("*".equals(m.group(4)))
							this.isHoliday = true;
					}
				}
			}
		}
		if (sy > 1874 && sy < 1909)
			this.description = "光绪" + (((sy - 1874) == 1) ? "元" : "" + (sy - 1874));
		if (sy > 1908 && sy < 1912)
			this.description = "宣统" + (((sy - 1908) == 1) ? "元" : String.valueOf(sy - 1908));
		if (sy > 1911 && sy < 1950)
			this.description = "民国" + (((sy - 1911) == 1) ? "元" : String.valueOf(sy - 1911));
		if (sy > 1949)
			this.description = "共和国" + (((sy - 1949) == 1) ? "元" : String.valueOf(sy - 1949));
		this.description += "年";
		this.sFestivalName = this.sFestivalName.replaceFirst("^\\|", "");
		this.isFinded = true;
	}

	private boolean isFinded = false;
	private boolean isSFestival = false;
	private boolean isLFestival = false;
	private String sFestivalName = "";
	private String lFestivalName = "";
	private String description = "";
	private boolean isHoliday = false;

	/**
	 * 返回农历年闰月月份<p>
	 * @param lunarYear 指定农历年份(数字)<br>
	 * @return 该农历年闰月的月份(数字,没闰返回0)<br>
	 */
	private static int getLunarLeapMonth(int lunarYear) {
		int leapMonth = CommonConstants.LUNAR_CC[lunarYear - 1900] & 0xf;
		leapMonth = (leapMonth == 0xf ? 0 : leapMonth);
		return leapMonth;
	}

	/**
	 * 返回农历年闰月的天数<p>
	 * @param lunarYear 指定农历年份(数字)<br>
	 * @return 该农历年闰月的天数(数字)<br>
	 */
	private static int getLunarLeapDays(int lunarYear) {
		return getLunarLeapMonth(lunarYear) > 0 ? ((CommonConstants.LUNAR_CC[lunarYear - 1899] & 0xf) == 0xf ? 30 : 29) : 0;
	}

	/**
	 * 返回农历年的总天数<p>
	 * @param lunarYear 指定农历年份(数字)<br>
	 * @return 该农历年的总天数(数字)<br>
	 */
	private static int getLunarYearDays(int lunarYear) {
		int daysInLunarYear = 348;
		// 循环加上非闰月
		for (int i = 0x8000; i > 0x8; i >>= 1) {
			daysInLunarYear += ((CommonConstants.LUNAR_CC[lunarYear - 1900] & i) != 0) ? 1 : 0;
		}
		// 加上闰月天数
		daysInLunarYear += getLunarLeapDays(lunarYear);
		return daysInLunarYear;
	}

	/**
	 * 返回农历年正常月份的总天数<p>
	 * @param lunarYear 指定农历年份(数字)<br>
	 * @param lunarMonth 指定农历月份(数字)<br>
	 * @return 该农历年闰月的月份(数字,没闰返回0)<br>
	 */
	private static int getLunarMonthDays(int lunarYear, int lunarMonth) {
		return ((CommonConstants.LUNAR_CC[lunarYear - 1900] & (0x10000 >> lunarMonth)) != 0) ? 30 : 29;
	}

	/**
	 * 取 Date 对象中用全球标准时间 (UTC) 表示的日期<p>
	 * @param date 指定日期<br>
	 * @return UTC 全球标准时间 (UTC) 表示的日期<br>
	 */
	public static synchronized int getUTCDay(Date date) {
		// 初始化全球标准时间对象
		if (utcCal == null) {
			utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		}
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.setTimeInMillis(date.getTime());
			return utcCal.get(Calendar.DAY_OF_MONTH);
		}
	}

	private static GregorianCalendar utcCal = null;

	/**
	 * 返回全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数。<p>
	 * @param y 指定年份<br>
	 * @param m 指定月份<br>
	 * @param d 指定日期<br>
	 * @param h 指定小时<br>
	 * @param min 指定分钟<br>
	 * @param sec 指定秒数<br>
	 * @return 全球标准时间 (UTC) (或 GMT) 的 1970 年 1 月 1 日到所指定日期之间所间隔的毫秒数<br>
	 */
	public static synchronized long UTC(int y, int m, int d, int h, int min, int sec) {
		// 初始化全球标准时间对象
		if (utcCal == null) {
			utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		}
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.set(y, m, d, h, min, sec);
			return utcCal.getTimeInMillis();
		}
	}

	/**
	 * 返回公历年节气的日期<p>
	 * @param solarYear 指定公历年份(数字)<br>
	 * @param index 指定节气序号(数字,0从小寒算起)<br>
	 * @return 日期(数字,所在月份的第几天)<br>
	 */
	private static int getSolarTermDay(int solarYear, int index) {
		return getUTCDay(new Date((long) 31556925974.7 * (solarYear - 1900) + CommonConstants.LUNAR_FI[index] * 60000L + UTC(1900, 0, 6, 2, 5, 0)));
	}

	private Calendar solar;
	private int lunarYear;
	private int lunarMonth;
	private int lunarDay;
	private boolean isLeap;
	private boolean isLeapYear;
	private int solarYear;
	private int solarMonth;
	private int solarDay;
	private int cyclicalYear = 0;
	private int cyclicalMonth = 0;
	private int cyclicalDay = 0;
	private int maxDayInMonth = 29;

	/**
	 * 通过 Date 对象构建农历信息<p>
	 * @param date 指定日期对象<br>
	 */
	public LunarCalendar(Date date) {
		if (date == null)
			date = new Date();
		this.init(date.getTime());
	}

	/**
	 * 通过 TimeInMillis 构建农历信息<p>
	 * @param TimeInMillis<br>
	 */
	public LunarCalendar(long TimeInMillis) {
		this.init(TimeInMillis);
	}
	
	/**
	 * 初始化日期环境<p>
	 * @param TimeInMillis 日期长整型<br>
	 */
	private void init(long TimeInMillis) {
		this.solar = Calendar.getInstance();
		this.solar.setTimeInMillis(TimeInMillis);
		Calendar baseDate = new GregorianCalendar(1900, 0, 31);
		long offset = (TimeInMillis - baseDate.getTimeInMillis()) / 86400000;
		// 按农历年递减每年的农历天数，确定农历年份
		this.lunarYear = 1900;
		int daysInLunarYear = getLunarYearDays(this.lunarYear);
		while (this.lunarYear < 2100 && offset >= daysInLunarYear) {
			offset -= daysInLunarYear;
			daysInLunarYear = getLunarYearDays(++this.lunarYear);
		}
		// 农历年数字
		// 按农历月递减每月的农历天数，确定农历月份
		int lunarMonth = 1;
		// 所在农历年闰哪个月,若没有返回0
		int leapMonth = getLunarLeapMonth(this.lunarYear);
		// 是否闰年
		this.isLeapYear = leapMonth > 0;
		// 闰月是否递减
		boolean leapDec = false;
		boolean isLeap = false;
		int daysInLunarMonth = 0;
		while (lunarMonth < 13 && offset > 0) {
			if (isLeap && leapDec) { // 如果是闰年,并且是闰月
				// 所在农历年闰月的天数
				daysInLunarMonth = getLunarLeapDays(this.lunarYear);
				leapDec = false;
			} else {
				// 所在农历年指定月的天数
				daysInLunarMonth = getLunarMonthDays(this.lunarYear, lunarMonth);
			}
			if (offset < daysInLunarMonth) {
				break;
			}
			offset -= daysInLunarMonth;
			if (leapMonth == lunarMonth && isLeap == false) {
				// 下个月是闰月
				leapDec = true;
				isLeap = true;
			} else {
				// 月份递增
				lunarMonth++;
			}
		}
		this.maxDayInMonth = daysInLunarMonth;
		// 农历月数字
		this.lunarMonth = lunarMonth;
		// 是否闰月
		this.isLeap = (lunarMonth == leapMonth && isLeap);
		// 农历日数字
		this.lunarDay = (int) offset + 1;
		// 取得干支历
		this.getCyclicalData();
		// 根据当前日期进行节日寻找
		this.findFestival();
	}

	/**
	 * 取干支历 不是历年，历月干支，而是中国的从立春节气开始的节月，是中国的太阳十二宫，阳历的。<p>
	 * @param cncaData 日历对象(Tcnca)<br>
	 */
	private void getCyclicalData() {
		this.solarYear = this.solar.get(Calendar.YEAR);
		this.solarMonth = this.solar.get(Calendar.MONTH);
		this.solarDay = this.solar.get(Calendar.DAY_OF_MONTH);
		// 干支历
		int cyclicalYear = 0;
		int cyclicalMonth = 0;
		int cyclicalDay = 0;

		// 干支年 1900年立春後为庚子年(60进制36)
		int term2 = getSolarTermDay(solarYear, 2); // 立春日期
		// 依节气调整二月分的年柱, 以立春为界
		if (solarMonth < 1 || (solarMonth == 1 && solarDay < term2)) {
			cyclicalYear = (solarYear - 1900 + 36 - 1) % 60;
		} else {
			cyclicalYear = (solarYear - 1900 + 36) % 60;
		}
		// 干支月 1900年1月小寒以前为 丙子月(60进制12)
		int firstNode = getSolarTermDay(solarYear, solarMonth * 2); // 传回当月「节」为几日开始
		// 依节气月柱, 以「节」为界
		if (solarDay < firstNode) {
			cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 12) % 60;
		} else {
			cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 13) % 60;
		}
		// 当月一日与 1900/1/1 相差天数 1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
		cyclicalDay = (int) (UTC(solarYear, solarMonth, solarDay, 0, 0, 0) / 86400000 + 25567 + 10) % 60;
		this.cyclicalYear = cyclicalYear;
		this.cyclicalMonth = cyclicalMonth;
		this.cyclicalDay = cyclicalDay;
	}

	/**
	 * 取农历年生肖<p>
	 * @return 农历年生肖(例:龙)<br>
	 */
	public String getAnimalString() {
		return CommonConstants.LUNAR_AN[(this.lunarYear - 4) % 12];
	}

	/**
	 * 返回公历日期的节气字符串<p>
	 * @return 二十四节气字符串,若不是节气日,返回空串(例:冬至)<br>
	 */
	public String getTermString() {
		String rtnS = "";
		if (getSolarTermDay(solarYear, solarMonth * 2) == solarDay) {
			rtnS = CommonConstants.LUNAR_FS[solarMonth * 2];
		} else if (getSolarTermDay(solarYear, solarMonth * 2 + 1) == solarDay) {
			rtnS = CommonConstants.LUNAR_FS[solarMonth * 2 + 1];
		}
		return rtnS;
	}

	/**
	 * 取得干支历字符串<p>
	 * @return 干支历字符串(例:甲子年甲子月甲子日)<br>
	 */
	public String getCyclicalDateString() {
		return this.getCyclicaYear() + "年" + this.getCyclicaMonth() + "月" + this.getCyclicaDay() + "日";
	}

	/**
	 * 年份天干<p>
	 * @return 年份天干<br>
	 */
	public int getTiananY() {
		return this.cyclicalYear % 10;
	}

	/**
	 * 月份天干<p>
	 * @return 月份天干<br>
	 */
	public int getTiananM() {
		return this.cyclicalMonth % 10;
	}

	/**
	 * 月份地支<p>
	 * @return 月份地支<br>
	 */
	public int getDeqiM() {
		return this.cyclicalMonth % 12;
	}

	/**
	 * 日期地支<p>
	 * @return 日期地支<br>
	 */
	public int getDeqiD() {
		return this.cyclicalDay % 12;
	}

	/**
	 * 取得干支年字符串<p>
	 * @return 干支年字符串<br>
	 */
	public String getCyclicaYear() {
		return getCyclicalString(this.cyclicalYear);
	}

	/**
	 * 取得干支月字符串<p>
	 * @return 干支月字符串<br>
	 */
	public String getCyclicaMonth() {
		return getCyclicalString(this.cyclicalMonth);
	}

	/**
	 * 取得干支日字符串<p>
	 * @return 干支日字符串<br>
	 */
	public String getCyclicaDay() {
		return getCyclicalString(this.cyclicalDay);
	}

	/**
	 * 返回农历日期字符串<p>
	 * @return 农历日期字符串<br>
	 */
	public String getLunarDayString() {
		return getLunarDayString(this.lunarDay);
	}

	/**
	 * 返回农历日期字符串<p>
	 * @return 农历日期字符串<br>
	 */
	public String getLunarMonthString() {
		return (this.isLeap() ? "闰" : "") + getLunarMonthString(this.lunarMonth);
	}

	/**
	 * 返回农历日期字符串<p>
	 * @return 农历日期字符串<br>
	 */
	public String getLunarYearString() {
		return getLunarYearString(this.lunarYear);
	}

	/**
	 * 返回农历表示字符串<p>
	 * @return 农历字符串(例:甲子年正月初三)<br>
	 */
	public String getLunarDateString() {
		return this.getLunarYearString() + "年 " + this.getLunarMonthString() + "月" + this.getLunarDayString() + " " + this.getLFestivalName();
	}

	/**
	 * 农历年是否是闰月<p>
	 * @return 农历年是否是闰月<br>
	 */
	public boolean isLeap() {
		return isLeap;
	}

	/**
	 * 农历年是否是闰年<p>
	 * @return 农历年是否是闰年<br>
	 */
	public boolean isLeapYear() {
		return isLeapYear;
	}

	/**
	 * 当前农历月是否是大月<p>
	 * @return 当前农历月是大月<br>
	 */
	public boolean isBigMonth() {
		return this.getMaxDayInMonth() > 29;
	}

	/**
	 * 当前农历月有多少天<p>
	 * @return 当前农历月有多少天<br>
	 */
	public int getMaxDayInMonth() {
		return this.maxDayInMonth;
	}

	/**
	 * 农历日期<br>
	 * @return 农历日期<br>
	 */
	public int getLunarDay() {
		return lunarDay;
	}

	/**
	 * 农历月份<p>
	 * @return 农历月份<br>
	 */
	public int getLunarMonth() {
		return lunarMonth;
	}

	/**
	 * 农历年份<p>
	 * @return 农历年份<br>
	 */
	public int getLunarYear() {
		return lunarYear;
	}

	/**
	 * 公历日期<p>
	 * @return 公历日期<br>
	 */
	public int getSolarDay() {
		return solarDay;
	}

	/**
	 * 公历月份<p>
	 * @return 公历月份 (不是从0算起)<br>
	 */
	public int getSolarMonth() {
		return solarMonth + 1;
	}

	/**
	 * 公历年份<p>
	 * @return 公历年份<br>
	 */
	public int getSolarYear() {
		return solarYear;
	}

	/**
	 * 星期几<p>
	 * @return 星期几(星期日为:1, 星期六为:7)<br>
	 */
	public int getDayOfWeek() {
		return this.solar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 黑色星期五<p>
	 * @return 是否黑色星期五<br>
	 */
	public boolean isBlackFriday() {
		return (this.getSolarDay() == 13 && this.solar.get(Calendar.DAY_OF_WEEK) == 6);
	}

	/**
	 * 是否是今日<p>
	 * @return 是否是今日<br>
	 */
	public boolean isToday() {
		Calendar clr = Calendar.getInstance();
		return clr.get(Calendar.YEAR) == this.solarYear && clr.get(Calendar.MONTH) == this.solarMonth && clr.get(Calendar.DAY_OF_MONTH) == this.solarDay;
	}

	/**
	 * 取得公历节日名称<p>
	 * @return 公历节日名称,如果不是节日返回空串<br>
	 */
	public String getSFestivalName() {
		return this.sFestivalName;
	}

	/**
	 * 取得农历节日名称<p>
	 * @return 农历节日名称,如果不是节日返回空串<br>
	 */
	public String getLFestivalName() {
		return this.lFestivalName;
	}

	/**
	 * 是否是农历节日<p>
	 * @return 是否是农历节日<br>
	 */
	public boolean isLFestival() {
		return this.isLFestival;
	}

	/**
	 * 是否是公历节日<p>
	 * @return 是否是公历节日<br>
	 */
	public boolean isSFestival() {
		return this.isSFestival;
	}

	/**
	 * 是否是节日<p>
	 * @return 是否是节日<br>
	 */
	public boolean isFestival() {
		return this.isSFestival() || this.isLFestival();
	}

	/**
	 * 是否是放假日<p>
	 * @return 是否是放假日<br>
	 */
	public boolean isHoliday() {
		if (!this.isFinded)
			this.findFestival();
		return this.isHoliday;
	}

	/**
	 * 获取日期说明<p>
	 * @return 日期说明(如:民国2年)<br>
	 */
	public String getDescription() {
		if (!this.isFinded)
			this.findFestival();
		return this.description;
	}

	/**
	 * 根据指定干支位置获取对应的干支字符串<p>
	 * @param cyclicalNumber 指定干支位置(数字,0为甲子)<br>
	 * @return 干支字符串<br>
	 */
	private static String getCyclicalString(int cyclicalNumber) {
		return CommonConstants.LUNAR_TG[cyclicalNumber % 10] + CommonConstants.LUNAR_DZ[cyclicalNumber % 12];
	}

	/**
	 * 返回指定数字的农历年份字符串<p>
	 * @param lunarYear 农历年份(数字,0为甲子)<br>
	 * @return 农历年份字符串<br>
	 */
	private static String getLunarYearString(int lunarYear) {
		return getCyclicalString(lunarYear - 1900 + 36);
	}

	/**
	 * 返回指定数字的农历月份表示字符串<p>
	 * @param lunarMonth 农历月份(数字)<br>
	 * @return 农历月份字符串 (例:正)<br>
	 */
	private static String getLunarMonthString(int lunarMonth) {
		String rtnS = "";
		if (lunarMonth == 1) {
			rtnS = CommonConstants.LUNAR_HD[4];
		} else {
			if (lunarMonth > 9)
				rtnS += CommonConstants.LUNAR_HD[1];
			if (lunarMonth % 10 > 0)
				rtnS += CommonConstants.LUNAR_NM[lunarMonth % 10];
		}
		return rtnS;
	}

	/**
	 * 返回指定数字的农历日表示字符串<p>
	 * @param lunarDay 农历日(数字)<br>
	 * @return 农历日字符串 (例: 廿一)<br>
	 */
	private static String getLunarDayString(int lunarDay) {
		if (lunarDay < 1 || lunarDay > 30)
			return "";
		int i1 = lunarDay / 10;
		int i2 = lunarDay % 10;
		String c1 = CommonConstants.LUNAR_HD[i1];
		String c2 = CommonConstants.LUNAR_NM[i2];
		if (lunarDay < 11)
			c1 = CommonConstants.LUNAR_HD[0];
		if (i2 == 0)
			c2 = CommonConstants.LUNAR_HD[1];
		return c1 + c2;
	}
}