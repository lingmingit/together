package com.together.common.date;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.base.CommonConstants;
import com.together.common.base.RegularExpressionStatics;



/**
 * 基于数据信息合法性验证的通用工具操作类<p>
 * @author LingMin
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public final class DataCheckUtils {
	/**
	 * 构造函数<p>
	 */
	private DataCheckUtils() {}
	
	/**
	 * 判断输入时间格式是否合法<p>
	 * @param str 时间字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkTime(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.TIME_STRING_RULER);
	}
	
	/**
	 * 验证数字类型字符串的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkNumeral(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.NUMERAL_STRING_RULER);
	}
	
	/**
	 * 验证百分数的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkPercent(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.PERCENT_NUMBER_RULER);
	}
	
	/**
	 * 验证Byte类型字符串的合法性<p>
	 * @param str Byte字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkByte(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.BYTE_STRING_RULER) && DataCheckUtils.checkNumberRange(str, "-128", "127");
	}
	
	/**
	 * 验证Short类型字符串的合法性<p>
	 * @param str Short字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkShort(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.SHORT_STRING_RULER) && DataCheckUtils.checkNumberRange(str, "-32768", "32767");
	}
	
	/**
	 * 验证Integer类型字符串的合法性<p>
	 * @param str Integer字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkInteger(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.INTEGER_STRING_RULER) && DataCheckUtils.checkNumberRange(str, "-2147483648", "2147483647");
	}
	
	/**
	 * 验证Long类型字符串的合法性<p>
	 * @param str Long字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkLong(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.LONG_STRING_RULER) && DataCheckUtils.checkNumberRange(str, "-9223372036854775808", "9223372036854775807");
	}
	
	/**
	 * 验证数量区间的合法性<p>
	 * @param mid 数量<br>
	 * @param min 最小值<br>
	 * @param max 最大值<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkNumberRange(String mid, String min, String max) {
		boolean rtnB = false;
		if (DataCheckUtils.checkNumeral(mid) && DataCheckUtils.checkNumeral(min) && DataCheckUtils.checkNumeral(max)) {
			java.math.BigDecimal midB = new java.math.BigDecimal(mid);
			java.math.BigDecimal minB = new java.math.BigDecimal(min);
			java.math.BigDecimal maxB = new java.math.BigDecimal(max);
			rtnB = midB.compareTo(minB) >= 0 && midB.compareTo(maxB) <= 0;
		}
		return rtnB;
	}
	
	/**
	 * 验证QQ号码的合法性<p>
	 * @param str QQ<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkQQ(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.QQ_STRING_RULER);
	}
	
	/**
	 * 验证IP地址字符串的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkIP(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.IP_STRING_RULER);
	}
	
	/**
	 * 验证MAC地址的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkMAC(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.MAC_STRING_RULER);
	}
	
	/**
	 * 验证邮件地址的合法性<p>
	 * @param str 邮件地址<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkEmail(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.EMAIL_ADDRESS_RULER);
	}
	
	/**
	 * 验证邮政编码的合法性<p>
	 * @param str 邮政编码<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkPostCode(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.POSTCODE_STRING_RULER);
	}
	
	/**
	 * 验证金额类型数据的合法性<p>
	 * @param str 金额<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkCurrency(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.CURRENCY_STRING_RULE);
	}
	
	/**
	 * 验证手机号码的合法性<p>
	 * @param str 手机号码字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkMobileNo(String str) {
		return StringUtils.isNotEmpty(str) ? str.matches(RegularExpressionStatics.MOBILE_NUM_RULER) : true;
	}
	
	/**
	 * 验证电话号码的合法性<p>
	 * @param str 电话号码<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkTelephoneNo(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.TELEPHONE_NUM_RULER);
	}
	
	
	/**
	 * 验证数字字符串的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkNumberString(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.NUMBER_STR_RULER);
	}
	
	/**
	 * 验证英文字符串的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkEnglishString(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.ENGLISH_CHARACTER_RULER);
	}
	
	/**
	 * 验证英文字符串的合法性:【针对英文姓名】<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkEnglishStringForName(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.ENGLISH_CHARACTER_FOR_NAME_RULER);
	}
	
	/**
	 * 验证中文字符串的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkChineseString(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.CHINESE_CHARACTER_RULER);
	}
	
	/**
	 * 验证数字与英文字母字符串的合法性<p>
	 * @param str 字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkNumberAndCharacter(String str) {
		return StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.ENGLISH_NUMBER_RULER);
	}
	
	/**
	 * 验证字符串的长度区间<p>
	 * @param str 字符串<br>
	 * @param min  最小字符长度<br>
	 * @param max  最大字符长度<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkStringLengthRange(String str, int min, int max) {
		int len = 0;
		return StringUtils.isNotEmpty(str) && (len = str.length()) >= min && len <= max;
	}
	
	/**
	 * 验证字符串的字节长度区间<p>
	 * @param str 字符串<br>
	 * @param min  最小字符长度<br>
	 * @param max  最大字符长度<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkStringByteLengthRange(String str, int min, int max) {
		int len = 0;
		return StringUtils.isNotEmpty(str) && (len = StringUtils.getStringByteLength(str)) >= min && len <= max;
	}
	
	/**
	 * 验证银行卡号的合法性<br>
	 * @param str 信用卡号<br>
	 * @return Boolean true:合法 false:非法<br>
	 */
	public static boolean checkBankCardNo(String str) {
		// 验证信用卡号不为空,且符合信用卡验证格式
		if (StringUtils.isNotEmpty(str) && str.matches(RegularExpressionStatics.CREDIT_CARD_NUMBER_RULER)) {
			// 银行卡号的权重编码
			int weight = str.length() > 0 ? str.length() % 2 > 0 ? 1 : 2 : 0;
			// 银行卡号的权重合计
			int weightSum = 0;
			// 循环计算银行卡号的权重合计
			for (int i = 0; i < str.length(); i ++) {
				int value = Integer.valueOf(str.substring(i, i + 1));
				weightSum += value * weight > 9 ? value * weight - 9 : value * weight;
				// 重新估算银行卡号的权重编码
				weight = weight > 0 ? weight % 2 > 0 ? 2:1:0;
			}
			return weightSum > 0 ? weightSum % 10 > 0 ? false:true:false;
		}
		return false;
	}
	
	/**
	 * 验证身份证号码的合法性<br>
	 * @param str 字符串<br>
	 * @return boolean true:合法 false:非法<br>
	 */
	public static boolean checkIDCard(String str) {
		boolean rtnB = false;
		if (StringUtils.isNotEmpty(str)) {
			if (str.matches(RegularExpressionStatics.IDCARD_NO_RULER) && CommonConstants.IDCardCity.containsKey(str.substring(0, 2))) {
				if (str.length() == 15) {
					rtnB = DateUtils.isLegalDateString("19".concat(str.substring(6, 12)));
				} else {
					rtnB = DateUtils.isLegalDateString(str.substring(6, 14)) && str.substring(17, 18).equals(generateIDCardVerifyBit(str));
				}
			}
		}
		return rtnB;
	}
	
	/**
	 * 将15位身份证号码变为18位身份证号码<p>
	 * @param IDCard 15位身份证号码<br>
	 * @return String 18位身份证号码<br>
	 */
	public static String changeIDCardNumber15To18 (String IDCard) {
		return StringUtils.isNotEmpty(IDCard) && IDCard.matches(RegularExpressionStatics.IDCARD_NO_RULER) && CommonConstants.IDCardCity.containsKey(IDCard.substring(0, 2)) ? 
				changeIDCard15To17(IDCard).concat(generateIDCardVerifyBit(IDCard)) : "";
	}
	
	/**
	 * 比较两日期对象的合法性<p>
	 * @param begin 开始日期<br>
	 * @param end   结束日期<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkDateRangForString(String begin, String end) {
		return DataCheckUtils.checkDateRange(DateUtils.getDateFromSpecifiedString(begin), DateUtils.getDateFromSpecifiedString(end));
	}
	
	/**
	 * 比较两日期对象的合法性<p>
	 * @param begin 开始日期<br>
	 * @param end   结束日期<br>
	 * @return true:合法 false:非法<br>
	 */
	public static boolean checkDateRange(java.util.Date begin, java.util.Date end) {
		return CommonUtils.isNotEmptyObject(begin) && CommonUtils.isNotEmptyObject(end) && end.getTime() - begin.getTime() >= 0;
	}
	
	/**
	 * 将15位身份证号码转换为17位身份证<p>
	 * @param IDCard 15位身份证号码<br>
	 * @return String 17位身份证号码<br>
	 */
	private static String changeIDCard15To17(String IDCard) {
		return IDCard.substring(0, 6).concat("19").concat(IDCard.substring(6, 15));
	}
	
	/**
	 * 根据身份证号码前17位生成校验位<p>
	 * @param IdCard 身份证号码<br>
	 * @return String 身份证号码的校验位<br>
	 */
	private static String generateIDCardVerifyBit(String IdCard) {
		// 待验证的身份证号码17位
		String tempIDCard = null;
		// 根据身份证号码长度进行操作
		if (IdCard.trim().length() == 15) {
			tempIDCard = changeIDCard15To17(IdCard);
		} else {
			tempIDCard = IdCard.substring(0, 17);
		}
		int sum = 0;
		// 将身份证号码转换
		for (int i = 0; i < 17; i++) {
			sum += CommonConstants.IDCardWi[i] * Integer.parseInt(tempIDCard.substring(i, i + 1));
		}
		// 生成并返回身份证号码的验证位
		return sum % 11 == 2 ? "X" : String.valueOf(CommonConstants.IDCardVi[sum % 11]);
	}
}
