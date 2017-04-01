/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.service;

import com.together.framework.entity.AbstractTreeData;

/** 
 * 树型基础资料业务逻辑层操作接口类<p>
 * @author LingMin 
 * @date 2014-7-22<br>
 * @version 1.0<br>
 */
public interface ITreeDataService<E extends AbstractTreeData, ID extends java.io.Serializable> extends IDataBaseService<E, ID> {

	/**
	 * 根据结点关键字查询该结点下叶子结点信息<p>
	 * @param parentKey 指定结点关键字<br>
	 * @param mappedField 父结点关联字段名<br>
	 * @return 叶子结点信息集<br>
	 */
	public java.util.List<E> getChildrenNodeList(ID parentKey, String mappedField);
	
	/**
	 * 根据结点关键字与状态信息查询该结点下所有叶子结点信息，包含叶子结点下的叶子结点<p>
	 * @param parentKey 结点关键字<br>
	 * @param status    结点状态 true:启用 false:禁用<br>
	 * @param hasCurrentNode 是否包含当前结点 true:包含 false:不包含<br>
	 * @return 结点信息集合<br>
	 */
	public java.util.List<E> getDeepestStatusChildrenNodeList(ID parentKey, Boolean status, boolean hasCurrentNode);
}
