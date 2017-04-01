package com.together.framework.entity;

import javax.persistence.Column;

/**
 * 树型实体核心抽象类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
@javax.persistence.MappedSuperclass
public abstract class AbstractTreeData extends AbstractDataBase {
	/** 系统生成版本编号 **/
	private static final long serialVersionUID = -705039093796156974L;
	/** 所属层级 **/
	@Column(length=2)
	protected Integer levels;
	/** 长编码 **/
	@Column(length=200)
	protected String longNumber;
	/** 是否子节点 **/
	@Column(length=1, nullable=false)
	protected java.lang.Boolean isLeaf = false;
	
	/**
	 * 获取长编码<p>
	 * @return longNumber 长编码 <br>
	 */
	public String getLongNumber() {
		return longNumber;
	}
	
	/**
	 * 设置长编码<p>
	 * @param longNumber 长编码<br>
	 */
	public void setLongNumber(String longNumber) {
		this.longNumber = longNumber;
	}
	
	/**
	 * 获取所属层级<p>
	 * @return level 所属层级<br>
	 */
	public Integer getLevels() {
		return levels;
	}
	
	/**
	 * 设置所属层级<p>
	 * @param level 所属层级<br>
	 */
	public void setLevels(Integer levels) {
		this.levels = levels;
	}
	
	/**
	 * 查看是否为子节点(是 true,否 false)<p>
	 * @return isLeaf 是否子节点<br>
	 */
	public java.lang.Boolean getIsLeaf() {
		return isLeaf;
	}
	
	/**
	 * 设置是否子节点(是 true,否 false)<p>
	 * @param isLeaf 是否子节点<br>
	 */
	public void setIsLeaf(java.lang.Boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
}
