package com.together.framework.dao.enums;


import javax.faces.model.SelectItem;

import com.together.framework.common.enums.EnumUtils;
import com.together.framework.common.enums.base.CoreBaseEnum;

/**
 * SQL排序类型:升序|降序枚举<p>
 * @author LingMin @date 2012-03-14<br>
 * @version 1.0<br>
 */
public enum OrderTypeEnum implements CoreBaseEnum<OrderTypeEnum, String> {
	ORDER_ASC("ASC", "升序"), ORDER_DESC("DESC", "降序");
	/** 真实值 **/
	private String value;
	/** 显示值 **/
	private String alias;
	
	/**
	 * 构造函数:初始化枚举对象参数<p>
	 * @param value 真实值<br>
	 * @param alias 显示值<br>
	 */
	private OrderTypeEnum(String value, String alias) {
		this.value = value;
		this.alias = alias;
	}
	
	/**
	 * 获取枚举对象的真实值<p>
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * 获取枚举对象的显示值<p>
	 */
	public String getAlias() {
		return this.alias;
	}
	
	/**
	 * 获取枚举对象数组<p>
	 */
	public Enum<?>[] getEnums() {
		return OrderTypeEnum.values();
	}

	/**
	 * 将枚举对象转换为下拉列表对象数组<p>
	 */
	public SelectItem[] getEnumSelectItem() {
		return EnumUtils.getSelectItemList(this);
	}
	
	/***
	 * 将枚举 转换map 集合对象
	 */
	public java.util.HashMap<String, String> getHashMap() {
		return EnumUtils.getHashMapFromEnums(this);
	}
	
	/**
	 * 根据真实值获取枚举对象<p>
	 */
	public OrderTypeEnum getEnum(String value) {
		return (OrderTypeEnum)EnumUtils.getValueEnum(this, value);
	}
}
