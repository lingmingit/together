/*
 * t-framework com.together.framework.entity.Core
 * together 2013, all rights reserved.
 * created on 2013-9-8 下午5:38:22
 */
package com.together.framework.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/** 
 * JPA实体基类【id主键】<p>
 * @author LingMin 
 * @date 2013-9-8<br>
 * @version 1.0<br>
 */
@javax.persistence.MappedSuperclass
public abstract class Core implements java.io.Serializable {
	/** 系统生成版本编号 **/
	private static final long serialVersionUID = 6801636169153045886L;
	/** 关键字 **/
	@Id @Column(length=32, nullable=false)
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	protected java.lang.String id;
	
	/**
	 * 获取关键字信息<p>
	 * @return 关键字<br>
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * 设置关键字信息<p>
	 * @param id 关键字<br>
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
}

