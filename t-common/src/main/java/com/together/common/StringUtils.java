package com.together.common;


/****
 * 基于字符串操作的通用工具类<p>
 * @author LingMin 
 * @date 2014-6-17<br>
 * @version 1.0<br>
 */
public final class StringUtils {

	
	/**
	 * 构造函数<p>
	 */
	private StringUtils() {}
	
	/**
	 * 随机产生UUID的字符串<p>
	 * @return UUID字符串<br>
	 */
	public static String getUUIDString() {
		return java.util.UUID.randomUUID().toString();
	}
	
	/**
	 * 获取随机字符串<p>
	 * @return 随机字符串<br>
	 */
	public static String getRandomString() {
		return String.valueOf(Math.random());
	}
	
	/**
	 * 判断字符串的值为TRUE<p>
	 * @param str 字符串<br>
	 * @return boolean true:是 false:非<br>
	 */
	public static boolean isTrue(String str) {
		return CommonUtils.getBooleanFromString(str);
	}
	
	/**
	 * 判断字符串的值为FALSE<p>
	 * @param str 字符串<br>
	 * @return boolean true:非 false:是<br>
	 */
	public static boolean isFalse(String str) {
		return ! StringUtils.isTrue(str);
	}
	
	/**
	 * 判断字符串为空<p>
	 * @param str 字符串<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0
				|| str.equalsIgnoreCase("undefined")
				|| str.equalsIgnoreCase("unknown");
	}
	
	/**
	 * 判断字符串为非空<p>
	 * @param str 字符串<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}
	
	/**
	 * 判断字符串缓冲对象为空<p>
	 * @param sb 字符串缓冲对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmpty(StringBuffer sb) {
		return CommonUtils.isEmptyObject(sb)
				|| StringUtils.isEmpty(sb.toString());
	}
	
	/**
	 * 判断字符串缓冲对象为非空<p>
	 * @param sb 字符串缓冲对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isNotEmpty(StringBuffer sb) {
		return !StringUtils.isEmpty(sb);
	}
	
	/**
	 * 判断字符串是为空格键<p>
	 * @param str 字符串<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isBlank(String str) {
		if (StringUtils.isEmpty(str)) {
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串为非空格键<p>
	 * @param str 字符串<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}
	
	/**
	 * 获取合法的字符串：当字符串为NULL时，返回""字符串，否则返回原字符串<p>
	 * @param str 字符串<p>
	 * @return 合法字符串<br>
	 */
	public static String getLegalString(String str) {
		return StringUtils.isEmpty(str) ? "" : str;
	}
	
	/**
	 * 判断字符串中是否含有中文<p>
	 * @param str 字符串<br>
	 * @return true:包含 false:未包含<br>
	 */
	public static boolean hasChineseCharacter(String str) {
		boolean rtnB = false;
		for(int i = 0; i < str.length(); i ++) {
			int charCode = str.codePointAt(i);
			if (charCode >=0x4e00 && charCode <= 0x9fbb) {
				rtnB = true;
				break;
			}
		}
		return rtnB;
	}
	
	/**
	 * 将UUID字符串转换为UUID对象<p>
	 * @return UUID对象<br>
	 */
	public static java.util.UUID getUUIDObject(String uuid) {
		return java.util.UUID.fromString(uuid);
	}
	
	/**
	 * 获取字符串的字节长度<p>
	 * @param str 字符串<br>
	 * @return 字节长度<br>
	 */
	public static int getStringByteLength(String str) {
		int rtnLen = 0;
		if (StringUtils.isNotBlank(str)) {
			for(int i = 0; i < str.length(); i ++) {
				rtnLen += str.codePointAt(i) > 255 ? 2 : 1;
			}
		}
		return rtnLen;
	}
	
	/**
	 * 将字符串数组以指定的分隔符组装成字符串<p>
	 * @param array 字符串数组<br>
	 * @param delimiter 分隔符<br>
	 * @return 组装后的字符串<br>
	 */
	public static String getDelimiterString(String[] array, String delimiter) {
		String rtnS = "";
		// 合法性判断
		if (CommonUtils.isNotEmptyObjectArray(array) && StringUtils.isNotEmpty(delimiter)) {
			for (int i = 0; i < array.length; i ++) {
				if (i == 0) {
					rtnS = rtnS.concat(array[i]);
				} else {
					rtnS = rtnS.concat(delimiter).concat(array[i]);
				}
			}
		}
		return rtnS;
	}
	
	/**
	 * 将字符串的首字母变为大写<p>
	 * @param str 字符串<br>
	 * @return 字符串<br>
	 */
	public static String firstCharToUpperCase(String str) {
		if (StringUtils.isNotBlank(str) && StringUtils.isFirstCharAlpha(str)) {
			return str.substring(0, 1).toUpperCase().concat(str.substring(1));
		}
		return str;
	}
	
	/**
	 * 将字符串进行左补位操作<p>
	 * @param str 字符串<br>
	 * @param len 总长度<br>
	 * @param pad 补位字符<br>
	 * @return 字符串<br>
	 */
	public static String stringLeftPadding(String str, int len, String pad) {
		boolean flag = StringUtils.isEmpty(str);
		int strLen = flag ? 0  : str.length();
			str    = flag ? "" : str;
		for (int i = 0; i < len - strLen; i ++) {
			str = pad.concat(str);
		}
		return str;
	}
	
	/**
	 * 将字符串进行右补位操作<p>
	 * @param str 字符串<br>
	 * @param len 总长度<br>
	 * @param pad 补位字符<br>
	 * @return 字符串<br>
	 */
	public static String stringRightPadding(String str, int len, String pad) {
		boolean flag = StringUtils.isEmpty(str);
		int strLen = flag ? 0  : str.length();
			str    = flag ? "" : str;
		for (int i = 0; i < len - strLen; i ++) {
			str = str.concat(pad);
		}
		return str;
	}
	
	/**
	 * 利用字符串类的split方法对字符串进行分割<p>
	 * @param str 字符串<br>
	 * @param delimiter 分割符<br>
	 * @return 字符串数组<br>
	 */
	public static String[] splitString(String str, String delimiter) {
		return StringUtils.isNotEmpty(str) ? str.split(delimiter) : null;
	}
	
	/**
	 * 利用StringTokenizer对象对字符串进行分割<p>
	 * @param str 字符串<br>
	 * @param delimiter 分割符<br>
	 * @return 字符串数组<br>
	 */
	public static String[] splitStringTokenizer(String str, String delimiter) {
		String[] rtnSA = null;
		if (StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(delimiter)) {
			java.util.StringTokenizer st = new java.util.StringTokenizer(str, delimiter);
			rtnSA = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				rtnSA[i++] = st.nextToken().trim();
			}
		}
		return rtnSA;
	}
	
	/**
	 * 查询字符串在指定字符串中出现的次数<p>
	 * @param str 指定字符串<br>
	 * @param find 字符串<br>
	 * @return 次数<br>
	 */
	public static int findStringTimes(String str, String find) {
		int index = -1, times = 0;
		if (StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(find)) {
			while((index = str.indexOf(find, index)) > -1) {
				++ index; ++ times;
			}
		}
		return times;
	}
	
	/**
	 * 判断字符串数组中包含指定的字符串<p>
	 * @param strs 字符串数组<br>
	 * @param inStr 指定的字符串<br>
	 * @return true:包含 false:不包含<br>
	 */
	public static boolean isExistenceInArray(String[] strs, String inStr) {
		boolean rtnB = false;
		if (CommonUtils.isNotEmptyObjectArray(strs) && StringUtils.isNotEmpty(inStr)) {
			for (int i = 0; i < strs.length; i ++) {
				if (inStr.equalsIgnoreCase(strs[i])) {
					rtnB = true;
					break;
				}
			}
		}
		return rtnB;
	}
	
	/**
	 * 数组中的字符串存在于指定字符串中<p>
	 * @param str 指定字符串<br>
	 * @param strs 字符串数组<br>
	 * @return true:包含 false:不包含<br>
	 */
	public static boolean isExistenceInString(String str, String[] strs) {
		boolean rtnB = false;
		if (CommonUtils.isNotEmptyObjectArray(strs) && StringUtils.isNotEmpty(str)) {
			for (int i = 0; i < strs.length; i ++) {
				if (str.indexOf(strs[i]) >= 0) {
					rtnB = true;
					break;
				}
			}
		}
		return rtnB;
	}
	
	/**
	 * 替换字符串中的指定的字符串<p>
	 * @param src 源字符串<br>
	 * @param delimiter 分隔符<br>
	 * @param rs 移除字符串<br>
	 * @return 替换后的字符串<br>
	 */
	public static String replace(String src, String delimiter, String rs) {
		String rtnS = "";
		// 合法性判断
		if (StringUtils.isNotEmpty(src) && StringUtils.isNotEmpty(delimiter) && StringUtils.isNotEmpty(rs)) {
			// 当字符串居中时
			if (src.indexOf(delimiter.concat(rs).concat(delimiter)) > 0) {
				rtnS = src.replaceAll(delimiter.concat(rs), "");
			}
			// 当字符串居尾时
			else if (src.indexOf(delimiter.concat(rs)) > 0) {
				rtnS = src.replaceAll(delimiter.concat(rs), "");
			}
			// 当字符串居首时
			else if (src.indexOf(rs.concat(delimiter)) >= 0) {
				rtnS = src.replaceAll(rs.concat(delimiter), "");
			}
		}
		return rtnS;
	}
	
	/**
	 * 数组中的字符串不存在于指定字符串中<p>
	 * @param str 指定字符串<br>
	 * @param strs 字符串数组<br>
	 * @return true:不包含 false:包含<br>
	 */
	public static boolean isNotExistenceInString(String str, String[] strs) {
		return !StringUtils.isExistenceInString(str, strs);
	}
	
	/**
	 * 判断字符串数组中不包含指定的字符串<p>
	 * @param strs 字符串数组<br>
	 * @param inStr 指定的字符串<br>
	 * @return true:包含 false:不包含<br>
	 */
	public static boolean isNotExistenceInArray(String[] strs, String inStr) {
		return ! StringUtils.isExistenceInArray(strs, inStr);
	}
	
	/**
	 * 字符串替换<p>
	 * @param str 源字符串<br>
	 * @param replaced 被替换字符串<br>
	 * @param replace 替换为字符串<br>
	 * @return 字符串<br>
	 */
	public static String replaceAll(String str, String replaced, String replace) {
		String dest = "";
		if (StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(replaced)) {
			int replacedLen = replaced.length(), i;
			while((i = str.indexOf(replaced)) != -1) {
				dest = dest.concat(str.substring(0, i));
				dest = dest.concat(replace);
				str = str.substring(i + replacedLen);
			}
			dest = dest.concat(str);
		}
		return dest;
	}
	
	/**
	 * 字符串的字符编码格式转换<p>
	 * @param srcEncoding  字符编码格式<br>
	 * @param destEncoding 字符编码格式<br>
	 * @param str 待转换字符串<br>
	 * @return 转换后的字符串<br>
	 */
	public static String convertStringEncoding(String srcEncoding, String destEncoding, String str) {
		if (StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(srcEncoding)) {
			try {
				destEncoding = StringUtils.isEmpty(destEncoding) ? java.nio.charset.Charset.defaultCharset().name() : destEncoding;
				return StringUtils.convertByteArrayToString(str.getBytes(srcEncoding), destEncoding);
			} catch (java.io.UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * 根据规则对字符串进行替换<p>
	 * @param pattern 替换规则<br>
	 * @param replacement 替换字符<br>
	 * @param str 字符串<br>
	 * @return 替换后的字符串<br>
	 */
	public static String regexReplace(final java.util.regex.Pattern pattern, final String replacement, final String str) {
		return pattern.matcher(str).replaceAll(replacement).trim();
	}
	
	/**
	 * 判断字符串的首字符为字母<p>
	 * @param str 字符串<br>
	 * @return true:是 false:非<br>
	 */
	private static boolean isFirstCharAlpha(String str) {
		int temp = str.charAt(0);
		return ('a' <= temp && 'z' >= temp) || ('A' <= temp && 'Z' >= temp);
	}
	
	/**
	 * 根据某种编码格式将字节数组转换为字符串<p> 
	 * @param barray 字节数组<br>
	 * @param encoding 编码格式<br>
	 * @return 字符串<br>
	 */
	private static String convertByteArrayToString(byte[] barray, String encoding)throws java.io.UnsupportedEncodingException {
		return CommonUtils.isNotEmptyByteArray(barray) ? StringUtils.isNotEmpty(encoding) ? new String(barray, encoding) : new String(barray) : "";
	}
}
