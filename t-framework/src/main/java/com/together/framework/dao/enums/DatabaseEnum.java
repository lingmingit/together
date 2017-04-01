package com.together.framework.dao.enums;


import javax.faces.model.SelectItem;

import com.together.framework.common.enums.EnumUtils;
import com.together.framework.common.enums.base.CoreBaseEnum;


/**
 * 数据库类型枚举对象类<>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0 <br>
 */
public enum DatabaseEnum implements CoreBaseEnum<DatabaseEnum, String> {
	DATABASE_ORACLE("ORACLE", "ORACLE"), DATABASE_SQLSERVER("SQL SERVER", "SQL SERVER")
	,DATABASE_DB2("DB2", "DB2"), DATABASE_MYSQL("MYSQL", "MYSQL");
	/** 真实值 **/
	private String value;
	/** 显示值 **/
	private String alias;
	
	/**
	 * 构造函数:初始化枚举对象参数<p>
	 * @param value 真实值<br>
	 * @param alias 显示值<br>
	 */
	private DatabaseEnum(String value, String alias) {
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
		return DatabaseEnum.values();
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
	public DatabaseEnum getEnum(String value) {
		return (DatabaseEnum)EnumUtils.getValueEnum(this, value);
	}
}

