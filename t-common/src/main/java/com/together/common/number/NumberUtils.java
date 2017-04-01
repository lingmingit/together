package com.together.common.number;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.base.CommonConstants;
import com.together.common.date.DataCheckUtils;


/**
 * 基于数字类型操作的通用工具类<p>
 * @author LingMin 
 * @date 2014-07-22<br><br>
 * @version 1.0<br>
 */
public final class NumberUtils {
	/** 基本常量定义 **/
	public final static java.math.BigDecimal ZERO_BIGDECIMAL = new java.math.BigDecimal("0.0");
	public final static java.math.BigDecimal TAX_RATE_BIGDECIMAL = new java.math.BigDecimal("17");
	
	/**
	 * 构造函数<p>
	 */
	private NumberUtils() {}
	
	/**
	 * 货币字符串进行格式化<p>
	 * @param str 数字字符串<br>
	 * @return 货币字符串<br>
	 */
	public static String currencyFortmat(String str) {
		return DataCheckUtils.checkNumeral(str) ? java.text.NumberFormat
				.getCurrencyInstance().format(Double.parseDouble(str)) : "0.00";
	}
	
	/**
	 * 将字符串转换为Byte数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return Byte类型<br>
	 */
	public static byte getByteFromString(String str) {
		return DataCheckUtils.checkByte(str) ? Byte.parseByte(str) : 0;
	}
	
	/**
	 * 将字符串转换为Short数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return Short类型<br>
	 */
	public static short getShortFromString(String str) {
		return DataCheckUtils.checkShort(str) ? Short.parseShort(str) : 0;
	}
	
	/**
	 * 获取合法的INTEGER型数据<p>
	 * @param integer INTEGER对象<br>
	 * @return 合法的INTEGER型数据<br>
	 */
	public static Integer getLegalInteger(Integer integer) {
		return NumberUtils.isNotEmptyInteger(integer) ? integer : 0;
	}
	
	/**
	 * 将字符串转换为整型数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return 整型类型<br>
	 */
	public static int getIntegerFromString(String str) {
		return DataCheckUtils.checkInteger(str) ? Integer.parseInt(str) : 0;
	}
	
	/**
	 * 将字符串转换为长整型数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return 长整型类型<br>
	 */
	public static long getLongFromString(String str) {
		return DataCheckUtils.checkLong(str) ? Long.parseLong(str) : 0;
	}
	
	/**
	 * 将字符串转换为浮点型数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return 浮点型类型<br>
	 */
	public static float getFloatFromString(String str) {
		return NumberUtils.getBigDecimalFromString(str).floatValue();
	}


	/**
	 * 将Boolean类型转换为Integer类型<p>
	 * @param bool Boolean类型<br>
	 * @return int类型<br>
	 */
	public static int getIntegerFromBoolean(boolean bool) {
		return bool ? 1 : 0;
	}
	
	/**
	 * 判断Long数据类型是否为空<p>
	 * @param longNum Long对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyLong(java.lang.Long longNum) {
		return longNum == null ? true : longNum.longValue() == 0.0;
	}
	
	/**
	 * 判断Integer数据类型是否为非空<p>
	 * @param longNum Long对象<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyLong(java.lang.Long longNum) {
		return !NumberUtils.isEmptyLong(longNum);
	}
	
	/**
	 * 判断Integer数据类型是否为空<p>
	 * @param intNum Integer对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyInteger(java.lang.Integer intNum) {
		return intNum == null ? true : intNum.intValue() == 0;
	}
	
	/**
	 * 判断Integer数据类型是否为非空<p>
	 * @param intNum Integer对象<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyInteger(java.lang.Integer intNum) {
		return !NumberUtils.isEmptyInteger(intNum);
	}
	
	/**
	 * 判断BigDecimal数据类型是否为空<p>
	 * @param dbnum BigDecimal对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyBigDecimal(java.math.BigDecimal dbnum) {
		return dbnum == null ? true : dbnum.floatValue() == 0.0f;
	}
	
	/**
	 * 判断BigDecimal数据类型是否为非空<p>
	 * @param dbnum BigDecimal对象<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyBigDecimal(java.math.BigDecimal dbnum) {
		return !NumberUtils.isEmptyBigDecimal(dbnum);
	}
	
	/**
	 * 将字符串转换为Byte数据类型:当输入不合法或溢出时返回默认值<p>
	 * @param str 字符串<br>
	 * @param def 默认值<br>
	 * @return Byte类型<br>
	 */
	public static byte getByteFromStringByDefault(String str, byte def) {
		byte temp = NumberUtils.getByteFromString(str);
		return temp == 0 ? def : temp;
	}
	
	/**
	 * 将字符串转换为Short数据类型:当输入不合法或溢出时返回默认值<p>
	 * @param str 字符串<br>
	 * @param def 默认值<br>
	 * @return Short类型<br>
	 */
	public static short getShortFromStringByDefault(String str, short def) {
		short temp = NumberUtils.getShortFromString(str);
		return temp == 0 ? def : temp;
	}
	
	/**
	 * 将字符串转换为整型数据类型:当输入不合法或溢出时返回默认值<p>
	 * @param str 字符串<br>
	 * @param def 默认值<br>
	 * @return 整型类型<br>
	 */
	public static int getIntegerFromStringByDefault(String str, int def) {
		int temp = NumberUtils.getIntegerFromString(str);
		return temp == 0 ? def : temp;
	}
	
	/**
	 * 将字符串转换为长整型数据类型:当输入不合法或溢出时返回默认值<p>
	 * @param str 字符串<br>
	 * @param def 默认值<br>
	 * @return 长整型类型<br>
	 */
	public static long getLongFromStringByDefault(String str, long def) {
		long temp = NumberUtils.getLongFromString(str);
		return temp == 0l ? def : temp;
	}
	
	/**
	 * 将字符串转换为浮点型数据类型:当输入不合法或溢出时返回默认值<p>
	 * @param str 字符串<br>
	 * @param def 默认值<br>
	 * @return 浮点型类型<br>
	 */
	public static float getFloatFromStringByDefault(String str, float def) {
		float temp = NumberUtils.getFloatFromString(str);
		return temp == 0.0f ? def : temp;
	}
	
	/**
	 * 将字符串转换为Double型数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return Double型类型<br>
	 */
	public static double getDoubleFromString(String str) {
		return NumberUtils.getBigDecimalFromString(str).doubleValue();
	}
	
	/**
	 * 将字符串转换为Double型数据类型:当输入不合法或溢出时返回默认值<p>
	 * @param str 字符串<br>
	 * @param def 默认值<br>
	 * @return Double型类型<br>
	 */
	public static double getDoubleFromStringByDefault(String str, double def) {
		double temp = NumberUtils.getDoubleFromString(str);
		return temp == 0.0d ? def : temp;
	}
	
	/**
	 * 将字符串转换为BigInteger数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return BigInteger类型<br>
	 */
	public static java.math.BigInteger getBigIntegerFromString(String str) {
		return NumberUtils.getBigDecimalFromString(str).toBigInteger();
	}
	
	/**
	 * 将字符串转换为BigDecimal数据类型:当输入不合法或溢出时返回为零<p>
	 * @param str 字符串<br>
	 * @return BigDecimal类型<br>
	 */
	public static java.math.BigDecimal getBigDecimalFromString(String str) {
		return DataCheckUtils.checkNumeral(str) ? new java.math.BigDecimal(str) : new java.math.BigDecimal("0.0");
	}
	
	/**
	 * 将DOUBLE类型数据转换为BigDecimal数据类型<p>
	 * @param number DOUBLE类型数据<br>
	 * @return BigDecimal类型<br>
	 */
	public static java.math.BigDecimal getBigDecimalFromDouble(double number) {
		return new java.math.BigDecimal(number);
	}
	
	/**
	 * 将指定的字符串转换为合法的数字字符串，包含金额信息<p>
	 * @param number 字符串<br>
	 * @return 数字字符串<br>
	 */
	public static java.lang.String getLegalNumeralString(java.lang.String number) {
		return StringUtils.isNotEmpty(number) ? number.replaceAll("￥|\\$|,", "") : "0.00";
	}
	
	/**
	 * 根据指定的地理、政治和文化地区环境对货币字符串进行格式化<p>
	 * @param str 数字字符串<br>
	 * @param local 指定的地理、政治和文化地区环境<br>
	 * @return 货币字符串<br>
	 */
	public static String currencyLocaleFormat(String str, java.util.Locale local) {
		if (CommonUtils.isEmptyObject(local))
			local = java.util.Locale.getDefault();
		return DataCheckUtils.checkNumeral(str) ? java.text.NumberFormat.getCurrencyInstance(local).format(Double.parseDouble(str)) : "0.00";
	}
	
	/**
	 * 格式化BigDecimal对象并返回<p>
	 * @param value BigDecimal对象<br>
	 * @param precision 保留小数位<br>
	 * @return 格式化后对象<br>
	 */
	public static java.math.BigDecimal getFormatBigDecimal(java.math.BigDecimal value, int precision) {
		return convertNullBigDecimal(value).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 根据自定义样式格式化数字字符串<p>
	 * @param pattern 自定义样式<br>
	 * @param str 数字字符串<br>
	 * @return 格式化后的字符串<br>
	 */
	public static String customizeNumberFormatForString(String pattern, String str) {
		return StringUtils.isNotEmpty(pattern) && DataCheckUtils.checkNumeral(str) ? NumberUtils.customizeNumberFormatForDouble(pattern, Double.parseDouble(str)) : "";
	}
	
	/**
	 * 根据自定义样式格式化BIGDECIMAL数据对象<p>
	 * @param pattern 自定义样式<br>
	 * @param number BIGDECIMAL对象<br>
	 * @return 字符串<br>
	 */
	public static String customizeNumberFormatForBigDecimal(String pattern, java.math.BigDecimal number) {
		return StringUtils.isNotEmpty(pattern) && CommonUtils.isNotEmptyObject(number) ? new java.text.DecimalFormat(pattern).format(number) : "0.00";
	}
	
	/**
	 * 根据自定义样式格式化DOUBLE数据对象<p>
	 * @param pattern 自定义样式<br>
	 * @param number DOUBLE数据<br>
	 * @return 格式化后的字符串<br>
	 */
	public static String customizeNumberFormatForDouble(String pattern, double number) {
		return StringUtils.isNotEmpty(pattern) ? new java.text.DecimalFormat(pattern).format(number) : "";
	}
	
	/**
	 * 转换NULL型BIGDECIMAL数据<p>
	 * @param data BIGDECIMAL<br>
	 * @return BIGDECIMAL<br>
	 */
	public static java.math.BigDecimal convertNullBigDecimal(java.math.BigDecimal data) {
		return CommonUtils.isEmptyObject(data) ? new java.math.BigDecimal("0.0") : data;
	}
	
	/**
	 * 根据自定义样式格式化数字字符串并转换为BigDecimal对象<p>
	 * @param pattern 自定义样式<br>
	 * @param number 数字字符串<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal getBigDecimalForCustomizePattern(String pattern, String number) {
		java.math.BigDecimal rtnBD = new java.math.BigDecimal("0.0");
		try {
			rtnBD = StringUtils.isNotEmpty(pattern) && StringUtils.isNotEmpty(number) ? new java.math.BigDecimal(new java.text.DecimalFormat(pattern).parseObject(number).toString()) : rtnBD;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rtnBD;
	}
	
	/**
	 * 精确计算两个数量值的和并指定保留小数的位数<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForAddDouble(double value01, double value02, int precision) {
		return new java.math.BigDecimal(Double.toString(value01)).add(new java.math.BigDecimal(Double.toString(value02))).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 精确计算两个数量值的和<p>
	 * @param value01 数量值字符串<br>
	 * @param value02 数量值字符串<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForAddString(String value01, String value02, int precision) {
		return DataCheckUtils.checkNumeral(value01) && DataCheckUtils.checkNumeral(value02) ? NumberUtils.accurateCalculateForAddBigDecimal(new java.math.BigDecimal(value01), new java.math.BigDecimal(value02), precision) : ZERO_BIGDECIMAL;
	}
	
	/**
	 * 精确计算两个数量值的和<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForAddBigDecimal(java.math.BigDecimal value01, java.math.BigDecimal value02, int precision) {
		return  NumberUtils.convertNullBigDecimal(value01).add(NumberUtils.convertNullBigDecimal(value02)).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 精确计算两个数量值的差值【VALUE01 - VALUE02】<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForSubtractDouble(double value01, double value02, int precision) {
		return new java.math.BigDecimal(Double.toString(value01)).subtract(new java.math.BigDecimal(Double.toString(value02))).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 精确计算两个数量值的差值【VALUE01 - VALUE02】<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForSubtractBigDecimal(java.math.BigDecimal value01, java.math.BigDecimal value02, int precision) {
		return  NumberUtils.convertNullBigDecimal(value01).subtract(NumberUtils.convertNullBigDecimal(value02)).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 精确计算两个数量值的差值【VALUE01 - VALUE02】<p>
	 * @param value01 数量值字符串<br>
	 * @param value02 数量值字符串<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForSubtractString(String value01, String value02, int precision) {
		return DataCheckUtils.checkNumeral(value01) && DataCheckUtils.checkNumeral(value02) ? NumberUtils.accurateCalculateForSubtractBigDecimal(new java.math.BigDecimal(value01), new java.math.BigDecimal(value02), precision) : ZERO_BIGDECIMAL;
	}
	
	/**
	 * 精确计算两个数量值的乘积<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForMultiplyDouble(double value01, double value02, int precision) {
		return new java.math.BigDecimal(Double.toString(value01)).multiply(new java.math.BigDecimal(Double.toString(value02))).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 精确计算两个数量值的乘积<p>
	 * @param value01 数量值字符串<br>
	 * @param value02 数量值字符串<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForMultiplyString(String value01, String value02, int precision) {
		return DataCheckUtils.checkNumeral(value01) && DataCheckUtils.checkNumeral(value02) ? NumberUtils.accurateCalculateForMultiplyBigDecimal(new java.math.BigDecimal(value01), new java.math.BigDecimal(value02), precision) : ZERO_BIGDECIMAL;
	}
	
	/**
	 * 精确计算两个数量值的乘积<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForMultiplyBigDecimal(java.math.BigDecimal value01, java.math.BigDecimal value02, int precision) {
		return  NumberUtils.convertNullBigDecimal(value01).multiply(NumberUtils.convertNullBigDecimal(value02)).setScale(precision, java.math.BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 精确计算两个数量值的商值【VALUE01 / VALUE02】<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForDivideDouble(double value01, double value02, int precision) {
		return NumberUtils.isNotZeroForDouble(value02) ? new java.math.BigDecimal(Double.toString(value01)).divide(new java.math.BigDecimal(Double.toString(value02)), precision, java.math.BigDecimal.ROUND_HALF_UP) : ZERO_BIGDECIMAL;
	}
	
	/**
	 * 精确计算两个数量值的商值【VALUE01 / VALUE02】<p>
	 * @param value01 数量值<br>
	 * @param value02 数量值<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForDivideBigDecimal(java.math.BigDecimal value01, java.math.BigDecimal value02, int precision) {
		return NumberUtils.isNotZeroForBigDecimal(value02) ? NumberUtils.convertNullBigDecimal(value01).divide(value02, precision, java.math.BigDecimal.ROUND_HALF_UP) : ZERO_BIGDECIMAL;
	}
	
	/**
	 * 精确计算两个数量值的商值【VALUE01 / VALUE02】<p>
	 * @param value01 数量值字符串<br>
	 * @param value02 数量值字符串<br>
	 * @param precision 保留小数位数<br>
	 * @return {@link java.math.BigDecimal} 数据对象<br>
	 */
	public static java.math.BigDecimal accurateCalculateForDivideString(String value01, String value02, int precision) {
		return DataCheckUtils.checkNumeral(value01) && DataCheckUtils.checkNumeral(value02) && NumberUtils.isNotZeroForBigDecimal(new java.math.BigDecimal(value01)) ? NumberUtils.accurateCalculateForDivideBigDecimal(new java.math.BigDecimal(value01), new java.math.BigDecimal(value02), precision) : ZERO_BIGDECIMAL;
	}
	
	/**
	 * 将金额数字变为财务中的大写<p>
	 * @param numStr 数字字符串<br>
	 * @return 大写财务数字<br>
	 */
	public static String getFinanceNumber(String numStr) {
		// 初始化返回值
		StringBuffer rtnSB = new StringBuffer("");
		
		// 判断数字字符串是否为空|合法
		if (DataCheckUtils.checkNumeral(numStr)) {
			// 将数字字符串转换为数字类型
			double number = new java.math.BigDecimal(numStr).multiply(new java.math.BigDecimal("100")).doubleValue();
			if (number < 0)
				rtnSB.append("负");
			long temp = java.lang.Math.round(java.lang.Math.abs(number));
			// 获取整数部分
			long integer = temp / 100;
			// 获取小数部分
			long decimal = temp % 100;
			
			// 当只有小数部分时
			if (integer == 0) {
				rtnSB = rtnSB.append(dealDecimalPart(integer,decimal));
			} else {
				rtnSB = rtnSB.append(dealIntegerPart(integer)).append("元").append(dealDecimalPart(integer,decimal));
			}
		}
		return rtnSB.toString();
	}
	
	/**
	 * 处理整数部分<p>
	 * @param integer 整数部分字符串<br>
	 * @param decimal 小数部分字符串<br>
	 * @return 大写财务数字<br>
	 */
	private static StringBuffer dealIntegerPart(long integer) {
		// 初始化返回值
		StringBuffer rtnSB = new StringBuffer();
		// 转化为数字字符串
		String strInt = String.valueOf(integer);
		boolean lastZero = false ,hasValue = false;
		// 获取字符串长度
		int len = strInt.length();
		for (int i = len -1; i >= 0; i --) {
			int n = strInt.charAt(len - i - 1) - '0';
			// 判断当前数字是否为零
			if (n != 0) {
				if (lastZero)
					rtnSB = rtnSB.append(CommonConstants.HanDigiStr[0]);
				if (!(n == 1 && (i % 4) == 1 && i == len - 1))
					rtnSB = rtnSB.append(CommonConstants.HanDigiStr[n]);
				rtnSB = rtnSB.append(CommonConstants.HanDiviStr[i]);
				hasValue = true;
			} else {
				if ( (i % 8) == 0 || ( (i % 8) == 4 && hasValue))
					rtnSB = rtnSB.append(CommonConstants.HanDiviStr[i]);
			}
			if (i % 8 == 0)
				hasValue = false;
			lastZero = (n == 0) && (i % 4 != 0);
		}
		return rtnSB;
	}
	
	/**
	 * 处理小数部分<p>
	 * @param integer 整数部分字符串<br>
	 * @param decimal 小数部分字符串<br>
	 * @return 大写财务数字<br>
	 */
	private static StringBuffer dealDecimalPart(long integer, long decimal) {
		// 初始化返回值
		StringBuffer rtnSB = new StringBuffer();
		// 获取角位数值
		int jiao = (int) decimal / 10;
		// 获取分位数值
		int fen = (int) decimal % 10;
		
		if (jiao == 0 && fen == 0) {
			return rtnSB.append("整");
		} else {
			if (jiao != 0)
				rtnSB = rtnSB.append(CommonConstants.HanDigiStr[jiao]).append("角");
			if (integer != 0 && jiao == 0)
				rtnSB.append("零");
			if (fen != 0)
				rtnSB = rtnSB.append(CommonConstants.HanDigiStr[fen]).append("分");
		}
		return rtnSB;
	}
	
	/**
	 * 判断BigDecimal类型数据不为空<p>
	 * @param decimal BigDecimal数据类型<br>
	 * @return true:非空 false:空<br>
	 */
	private static boolean isNotZeroForDouble(double value) {
		return value > 0.0d || value < 0.0d;
	}
	
	/**
	 * 判断BigDecimal类型数据不为空<p>
	 * @param decimal BigDecimal数据类型<br>
	 * @return true:非空 false:空<br>
	 */
	private static boolean isNotZeroForBigDecimal(java.math.BigDecimal decimal) {
		decimal = NumberUtils.convertNullBigDecimal(decimal);
		return decimal.doubleValue() > 0.0d || decimal.doubleValue() < 0.0d;
	}
}
