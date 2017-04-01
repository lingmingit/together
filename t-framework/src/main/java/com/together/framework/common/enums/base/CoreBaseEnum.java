package com.together.framework.common.enums.base;


import javax.faces.model.SelectItem;

/**
 * 枚举类型通用接口类<p>
 * @author LingMin @date 2012-03-13<br>
 * @version 1.0<br>
 */
public interface CoreBaseEnum <E,V> {
	/**
	 * 获取枚举对象的真实值<p>
	 * @return 枚举对象真实值<br>
	 */
	public V getValue();
	
	/**
	 * 重写父类方法<p>
	 */
	public String toString();
	
	/**
	 * 获取枚举对象的显示值<p>
	 * @return 枚举对象显示值<br>
	 */
	public String getAlias();
	
	/**
	 * 获取枚举对象数组<p>
	 * @return 枚举对象数组<br>
	 */
	public Enum<?>[] getEnums();
	
	/**
	 * 根据真实值获取枚举对象<p>
	 * @param value 真实值<br>
	 * @return 枚举对象<br>
	 */
	public Enum<?> getEnum(V value);
	
	/**
	 * 将枚举对象转换为下拉列表对象数组<p>
	 * @return 下拉列表对象数组<br>
	 */
	public SelectItem[] getEnumSelectItem();
	
	/**
	 * 将枚举类型转换为HASHMAP对象<p>
	 * @return HASHMAP对象<br>
	 */
	public java.util.HashMap<String, String> getHashMap();
	
}
