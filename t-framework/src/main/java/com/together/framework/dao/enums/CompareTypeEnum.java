package com.together.framework.dao.enums;

import javax.faces.model.SelectItem;
import com.together.framework.common.enums.EnumUtils;
import com.together.framework.common.enums.base.CoreBaseEnum;


/**
 * 基于SQL语句的条件类型<p>
 * @author LingMin @date 2014-06-17<br>
 * @version 1.0<br>
 */
public enum CompareTypeEnum implements CoreBaseEnum<CompareTypeEnum, String> {
	COMPARE_EQUEAL("=", "等于"), COMPARE_LESS("<", "小于"), COMPARE_MORE(">", "大于"),
	COMPARE_LESS_EQUEAL("<=", "小于等于"), COMPARE_MORE_EQUEAL(">=", "大于等于"), COMPARE_IS_NULL("IS NULL", "等于空"),
	COMPARE_IN("IN", "包含"), COMPARE_NOT_IN("NOT IN", "不包含"),COMPARE_NOT_EQUEAL("!=", "不等于"),
	COMPARE_LIKE("ALL_LIKE", "全匹配"),COMPARE_LEFT_LIKE("LEFT_LIKE", "左匹配"),COMPARE_RIGHT_LIKE("RIGHT_LIKE", "右匹配");
	/** 真实值 **/
	private String value;
	/** 显示值 **/
	private String alias;
	
	/**
	 * 构造函数:初始化枚举对象参数<p>
	 * @param value 真实值<br>
	 * @param alias 显示值<br>
	 */
	private CompareTypeEnum(String value, String alias) {
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
		return CompareTypeEnum.values();
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
	public CompareTypeEnum getEnum(String value) {
		return (CompareTypeEnum)EnumUtils.getValueEnum(this, value);
	}
}
