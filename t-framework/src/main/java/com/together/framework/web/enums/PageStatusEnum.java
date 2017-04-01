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
 * 前台页面状态枚举对象类<p>
 * @author LingMin 
 * @date 2014-7-21<br>
 * @version 1.0<br>
 */
public enum PageStatusEnum implements CoreBaseEnum<PageStatusEnum, String> {
	PAGE_ADDNEW("ADDNEW", "新增"), PAGE_EDIT("EDIT", "编辑"), PAGE_VIEW("VIEW", "查看"),
	PAGE_AUDIT("AUDIT", "审核"), PAGE_EXCEPTION("PAGE_EXCEPTION", "异常"), COPY_ADD("COPY_ADD", "复制新增");
	/** 真实值 **/
	private String value;
	/** 显示值 **/
	private String alias;
	
	/**
	 * 构造函数<p>
	 * @param value 真实值<br>
	 * @param alias 显示值<br>
	 */
	private PageStatusEnum(String value, String alias) {
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
	public PageStatusEnum[] getEnums() {
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
	public PageStatusEnum getEnum(String value) {
		return (PageStatusEnum) EnumUtils.getValueEnum(this, value);
	}

	/**
	 * 将枚举类型转换为HASHMAP对象<p>
	 */
	public java.util.HashMap<String, String> getHashMap() {
		return EnumUtils.getHashMapFromEnums(this);
	}
}