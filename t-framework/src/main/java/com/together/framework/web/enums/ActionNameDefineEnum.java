/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.web.enums;

import javax.faces.model.SelectItem;

import com.together.framework.common.enums.EnumUtils;
import com.together.framework.common.enums.base.CoreBaseEnum;

/** 
 * <p>
 * @author LingMin 
 * @date 2014-7-22<br>
 * @version 1.0<br>
 */
public enum ActionNameDefineEnum implements CoreBaseEnum<ActionNameDefineEnum, String> {

	AddNewAction("AddNewAction", "新增") , SaveAction("SaveAction", "保存"),  EditAction("EditAction", "编辑"),
	ViewAction("ViewAction", "查看"),SubmitAction("SubmitAction", "提交"),
	DeleteAction("DeleteAction", "删除") , AuditAction("AuditAction", "审核"),  UnAuditAction("UnAuditAction", "反审核"),
	PreviewAction("PreviewAction", "打印预览") , PrintAction("PrintAction", "预览");
	/** 真实值 **/
	private String value;
	/** 显示值 **/
	private String alias;
	
	/**
	 * 构造函数<p>
	 * @param value 真实值<br>
	 * @param alias 显示值<br>
	 */
	private ActionNameDefineEnum(String value, String alias) {
		this.value = value;
		this.alias = alias;
	}

	/**
	 * 获取枚举对象的真实值<p>
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取枚举对象的显示值<p>
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * 获取枚举对象数组<p>
	 */
	public ActionNameDefineEnum[] getEnums() {
		return values();
	}

	/**
	 * 将枚举对象转换为下拉列表对象数组<p>
	 */
	public SelectItem[] getEnumSelectItem() {
		return EnumUtils.getSelectItemList(this);
	}
	
	/**
	 * 根据真实值获取枚举对象<p>
	 */
	public ActionNameDefineEnum getEnum(String value) {
		return (ActionNameDefineEnum) EnumUtils.getValueEnum(this, value);
	}

	/**
	 * 将枚举类型转换为HASHMAP对象<p>
	 */
	public java.util.HashMap<String, String> getHashMap() {
		return EnumUtils.getHashMapFromEnums(this);
	}
}
