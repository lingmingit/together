package com.together.framework.dao.enums;

import javax.faces.model.SelectItem;

import com.together.framework.common.enums.EnumUtils;
import com.together.framework.common.enums.base.CoreBaseEnum;


/**
 * 标准单据的单据状态枚举类<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public enum BillStatusEnum implements CoreBaseEnum<BillStatusEnum, String> {
	ADD_NEW_STATUS("AN", "新增中"), SAVE_STATUS("SE", "已保存"), SUBMIT_STATUS("ST", "审核中"),
	AUDIT_STATUS("AT", "已审核"), CLOSE_STATUS("CE", "已关闭"), INVALID_STATUS("ID", "已作废"),
	FREEZE_STATUS("FE", "已冻结");
	/** 真实值 **/
	private String value;
	/** 显示值 **/
	private String alias;
	
	/**
	 * 构造函数:初始化枚举对象参数<p>
	 * @param value 真实值<br>
	 * @param alias 显示值<br>
	 */
	private BillStatusEnum(String value, String alias) {
		this.value = value;
		this.alias = alias;
	}
	
	/**
	 * 重写 toString()方法<p>
	 */
	public String toString() {
		return value;
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
		return this.alias;
	}
	
	/**
	 * 获取枚举对象数组<p>
	 */
	public Enum[] getEnums() {
		return BillStatusEnum.values();
	}

	/**
	 * 根据真实值获取枚举对象<p>
	 */
	public BillStatusEnum getEnum(String value) {
		return (BillStatusEnum)EnumUtils.getValueEnum(this, value);
	}

	/**
	 * 将枚举对象转换为下拉列表对象数组<p>
	 */
	public SelectItem[] getEnumSelectItem() {
		return EnumUtils.getSelectItemList(this);
	}
	
	/***
	 * 将枚举 转换map 集合对象<p>
	 */
	public java.util.HashMap<String, String> getHashMap() {
		return EnumUtils.getHashMapFromEnums(this);
	}
}
