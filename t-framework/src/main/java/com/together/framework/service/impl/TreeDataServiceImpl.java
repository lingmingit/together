package com.together.framework.service.impl;


import com.together.framework.dao.jpa.ITreeDataDao;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.service.ITreeDataService;

/**
 * 树型基础资料业务逻辑层操作接口实现类<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
public class TreeDataServiceImpl<E extends AbstractTreeData, ID extends java.io.Serializable> extends DataBaseServiceImpl<E, ID> implements ITreeDataService<E, ID> {
	/** 树型基础资料数据库层操作对象 **/
	protected ITreeDataDao<E, ID> treeDataDao;
	
	/**
	 * 构造函数:初始化树型基础资料数据库层操作对象<p>
	 * @param treeDataDao 树型基础资料数据库层操作对象<br>
	 */
	public TreeDataServiceImpl(ITreeDataDao<E, ID> treeDataDao) {
		super(treeDataDao);
		this.treeDataDao = treeDataDao;
	}
	
	/**
	 * 根据结点关键字查询该结点下叶子结点信息<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public java.util.List<E> getChildrenNodeList(ID parentKey, String mappedField) {
		return treeDataDao.getChildrenNodeList(parentKey, mappedField);
	}
	
	/**
	 * 根据结点关键字与状态信息查询该结点下所有叶子结点信息，包含叶子结点下的叶子结点<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public java.util.List<E> getDeepestStatusChildrenNodeList(ID parentKey, Boolean status, boolean hasCurrentNode) {
		return treeDataDao.getDeepestStatusChildrenNodeList(parentKey, status, hasCurrentNode);
	}
}
