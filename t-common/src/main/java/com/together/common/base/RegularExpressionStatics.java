package com.together.common.base;

/**
 * 通用工具常量定义类<p>
 * @author LingMin
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public final class RegularExpressionStatics {
	/**
	 * 构造函数<p>
	 */
	private RegularExpressionStatics(){}
	
	/** 数字字符串的正则表达式验证法则 **/
	public static final String NUMBER_STR_RULER = "^\\d+$";
	
	/** 邮政编码数据类型的正则表达式验证法则 **/
	public static final String POSTCODE_STRING_RULER = "^[1-9]\\d{5}$";
	
	/** Byte数据类型字符串的正则表达式验证法则 **/
	public static final String BYTE_STRING_RULER = "^[-\\+]?\\d{1,3}$";
	
	/** Short数据类型字符串的正则表达式验证法则 **/
	public static final String SHORT_STRING_RULER = "^[-\\+]?\\d{1,5}$";
	
	/** Integer数据类型字符串的正则表达式验证法则 **/
	public static final String INTEGER_STRING_RULER = "^[-\\+]?\\d{1,10}$";
	
	/** Long数据类型字符串的正则表达式验证法则 **/
	public static final String LONG_STRING_RULER = "^[-\\+]?\\d{1,19}$";
	
	/** 数字类型字符串的正则表达式验证法则 **/
	public static final String NUMERAL_STRING_RULER = "^[-\\+]?\\d+(\\.\\d+)?$";
	
	/** 百分数的正则表达式验证法则 **/
	public static final String PERCENT_NUMBER_RULER = "[1][0]{2}|(^\\d{1,2}([.]\\d{1,2})?)$";
	
	/** QQ数据类型的正则表达式验证法则 **/
	public static final String QQ_STRING_RULER = "^[1-9]\\d{4,9}$";
	
	/**银行类型的正则表达式验证法则**/
	public static final String CREDIT_CARD_NUMBER_RULER = "^\\d{16,19}$";
	
	/** 时间字符串的正则表达式验证法则 **/
	public static final String TIME_STRING_RULER = "^[0-2]\\d{1}(:|时)?[0-5]\\d{1}(:|分)?([0-5]\\d{1}(秒)?)?$";
	
	/** 金额的合法性验证 **/
	public static final String CURRENCY_STRING_RULE = "^[-]?\\d{0,16}[\\.]?\\d{0,4}$";
	
	/** 英文字符串的正则表达式验证法则 **/
	public static final String ENGLISH_CHARACTER_RULER = "^[A-Za-z]+$";
	public static final String ENGLISH_CHARACTER_FOR_NAME_RULER = "^[A-Za-z ]+$";
	
	/**汉字字符串的正则表达式验证法则**/
	public static final String CHINESE_CHARACTER_RULER = "^[\\u4e00-\\u9fa5]+$";
	
	/**英文与数字字符串的正则表达式验证法则**/
	public static final String ENGLISH_NUMBER_RULER = "^[A-Za-z0-9]+$";
	
	/**身份证号码的正则表达式验证法则**/
	public static final String IDCARD_NO_RULER = "(^\\d{17}(\\d|X)$)|(^\\d{15}$)";
	
	/** 手机号码的正则表达式验证法则 **/
	public static final String MOBILE_NUM_RULER = "^[0]?[1]([3]\\d{1}|[4][7]|[5][0-3]|[5][5-9]|[8][0]|[8][2]|[8][5-9])\\d{8}$";
	
	/** IP地址的验证法则 **/
	public static final String IP_STRING_RULER = "^[1-9](\\d{0,2})\\.[0-9](\\d{0,2})\\.[0-9](\\d{0,2})\\.[1-9](\\d{0,2})$";
	
	/** MAC地址的验证法则 **/
	public static final String MAC_STRING_RULER = "^[A-Z0-9]{2}\\-[A-Z0-9]{2}\\-[A-Z0-9]{2}\\-[A-Z0-9]{2}\\-[A-Z0-9]{2}\\-[A-Z0-9]{2}$";
	
	/** 邮件地址验证正则表达式 **/
	public final static String EMAIL_ADDRESS_RULER = "^([A-Za-z0-9]*[-|_]?[A-Za-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$";
	
	/** 电话号码的正则表达式验证法则 **/
	public static final String TELEPHONE_NUM_RULER = "(^([0][1-9][0-9]{1,2}[-]*)?[1-9]\\d{6,7}(-\\d{1,4})?$)|(^\\([0][1-9][0-9]{1,2}\\)[1-9]\\d{6,7}(\\(\\d{1,4}\\))?$)|(^[1-9]\\d{6,7}$)";
	
	/**日期格式化字符串表达式验证法则**/
	public static final String DATE_FORMATER_RULER = "^([E][ ])?[y]{4}(-|/|年)?[M]{2}(-|/|月)?[d]{2}(日)?([ ])?([E])?([H]{2}(:|时)?[m]{2}(:|分)?[s]{2}(秒)?)?([ ][E])?$";
}
