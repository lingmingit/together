package com.together.framework.entity;

import javax.persistence.Column;

import com.together.framework.entity.base.BaseCore;



/**
 * 基础资料实体核心抽象类【编码、名称、简称、启用、系统】<p>
 * @author LingMin 
 * @date 2014-06-18<br>
 * @version 1.0<br>
 */
@javax.persistence.MappedSuperclass
public abstract class AbstractDataBase extends BaseCore{

	/** 系统生成版本编号 **/
	private static final long serialVersionUID = -3400686208730851006L;
	/** 编码 **/
	@Column(length=80, nullable=false)
	protected java.lang.String numbers;
	/** 名称 **/
	@Column(length=100)
	protected java.lang.String name;
	/** 简称 */
	@Column(length=30)
	protected java.lang.String simpleName;
	/** 启用标志 **/
	@Column(length=1)
	protected java.lang.Boolean isEnable = false;
	/** 系统标志 **/
	@Column(length=1)
	protected java.lang.Boolean isSystem = false;
	
	/**
	 * 获取名称<p>
	 * @return 名称<br>
	 */
	public java.lang.String getName() {
		return name;
	}
	
	/**
	 * 设置名称<p>
	 * @param name 名称<br>
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	/**
	 * 获取编码<p>
	 * @return 编码<br>
	 */
	public java.lang.String getNumbers() {
		return numbers;
	}
	/**
	 * 设置编码<br>
	 * @param number 编码<br>
	 */
	public void setNumbers(java.lang.String numbers) {
		this.numbers = numbers;
	}
	
	/**
	 * 获取简称<p>
	 * @return 简称<br>
	 */
	public java.lang.String getSimpleName() {
		return simpleName;
	}
	
	/**
	 * 设置简称<p>
	 * @param simpleName 简称<br>
	 */
	public void setSimpleName(java.lang.String simpleName) {
		this.simpleName = simpleName;
	}
	
	/**
	 * 获取启用标志<p>
	 * @return 启用标志<br>
	 */
	public java.lang.Boolean getIsEnable() {
		return isEnable;
	}
	
	/**
	 * 设置启用标志<p>
	 * @param isEnable 启用标志<br>
	 */
	public void setIsEnable(java.lang.Boolean isEnable) {
		this.isEnable = isEnable;
	}
	
	/**
	 * 获取系统标志<p>
	 * @return 系统标志<br>
	 */
	public java.lang.Boolean getIsSystem() {
		return isSystem;
	}
	
	/**
	 * 设置系统标志<p>
	 * @param isSystem 系统标志<br>
	 */
	public void setIsSystem(java.lang.Boolean isSystem) {
		this.isSystem = isSystem;
	}
}
