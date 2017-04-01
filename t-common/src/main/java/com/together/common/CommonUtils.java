package com.together.common;


/***
 * 通用工具操作类<p>
 * @author LingMin 
 * @date 2014-6-17<br>
 * @version 1.0<br>
 */
public final class CommonUtils {

	/**
	 * 构造函数<p>
	 */
	private CommonUtils() {}
	
	/**
	 * 判断对象为空<p>
	 * @param obj 对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyObject(Object obj) {
		return obj == null;
	}
	
	/**
	 * 判断对象为非空<p>
	 * @param obj 对象<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyObject(Object obj) {
		return !CommonUtils.isEmptyObject(obj);
	}
	
	/**
	 * 判断字节数组为空<p>
	 * @param bytes 字节数组<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyByteArray(byte[] bytes) {
		return bytes == null ? true : bytes.length == 0;
	}
	
	/**
	 * 判断字节数组不为空<p>
	 * @param bytes 字节数组<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyByteArray(byte[] bytes) {
		return !CommonUtils.isEmptyByteArray(bytes);
	}
	
	/**
	 * 判断List集合为空<p>
	 * @param list List集合<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyList(java.util.List<?> list) {
		return list == null || list.size() == 0;
	}
	
	/**
	 * 判断List集合为非空<p>
	 * @param list List集合<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyList(java.util.List<?> list) {
		return !CommonUtils.isEmptyList(list);
	}
	
	/**
	 * 判断HASHMAP对象为空<p>
	 * @param map HASHMAP对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyHashMap(java.util.Map<?, ?> map) {
		return CommonUtils.isEmptyObject(map) || map.size() == 0;
	}
	
	/**
	 * 判断HASHMAP对象为非空<p>
	 * @param map HASHMAP对象<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyHashMap(java.util.Map<?, ?> map) {
		return ! CommonUtils.isEmptyHashMap(map);
	}
	
	/**
	 * 判断对象数组为空<p>
	 * @param array 对象数组<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyObjectArray(Object[] array) {
		return CommonUtils.isEmptyObject(array) || array.length == 0;
	}
	
	/**
	 * 判断对象数组为非空<p>
	 * @param array 对象数组<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyObjectArray(Object[] array) {
		return !CommonUtils.isEmptyObjectArray(array);
	}
	
	/**
	 * 判断INT数组为空<p>
	 * @param array INT数组<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptyIntArray(int[] array) {
		return CommonUtils.isEmptyObject(array) || array.length == 0;
	}
	
	/**
	 * 判断INT数组位非空<p>
	 * @param array INT数组<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptyIntArray(int[] array) {
		return !CommonUtils.isEmptyIntArray(array);
	}
	
	/**
	 * 判断SET容器为空<p>
	 * @param sets SET对象<br>
	 * @return true:空 false:非空<br>
	 */
	public static boolean isEmptySet(java.util.Set<?> sets) {
		return CommonUtils.isEmptyObject(sets) || sets.size() == 0;
	}
	
	/**
	 * 判断SET容器为非空<p>
	 * @param sets SET对象<br>
	 * @return true:非空 false:空<br>
	 */
	public static boolean isNotEmptySet(java.util.Set<?> sets) {
		return !CommonUtils.isEmptySet(sets);
	}
	
	/**
	 * 将字符串转换为Boolean类型<p>
	 * @param str 字符串<br>
	 * @return Boolean类型<br>
	 */
	public static boolean getBooleanFromString(String str) {
		return "是".equals(str) || "true".equalsIgnoreCase(str) || "1".equals(str);
	}
	
	/**
	 * 将boolean类型转换为字符串:true:"1" false:"0"<p>
	 * @param bool boolean类型<br>
	 * @return 字符串<br>
	 */
	public static String getStringFromBoolean(boolean bool) {
		return bool ? "1" : "0";
	}
	
	/**
	 * 将Boolean对象转换为boolean信息<p>
	 * @param bool Boolean对象<br>
	 * @return boolean信息<br>
	 */
	public static boolean getLegalBoolean(Boolean bool) {
		return isNotEmptyObject(bool) ? bool.booleanValue() : false;
	}
	
	/**
	 * 判断指定对象数组中是否含有相同的对象<p>
	 * @param list 对象数组<br>
	 * @param obj 指定的对象<br>
	 * @return true:包含 false:不包含<br>
	 */
	public static boolean isExistenceList(java.util.List<?> list, Object obj) {
		boolean rtnB = false;
		if (CommonUtils.isNotEmptyList(list)) {
			for (Object temp : list) {
				rtnB = temp.equals(obj) ? true : rtnB;
			}
		}
		return rtnB;
	}
	
	/**
	 * 判断指定对象数组中是否含有相同的对象<p>
	 * @param list 对象数组<br>
	 * @param obj 指定的对象<br>
	 * @return true:包含 false:不包含<br>
	 */
	public static boolean isNotExistenceList(java.util.List<?> list, Object obj) {
		return !CommonUtils.isExistenceList(list, obj);
	}
}
