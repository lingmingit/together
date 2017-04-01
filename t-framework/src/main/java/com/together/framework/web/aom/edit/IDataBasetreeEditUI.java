package com.together.framework.web.aom.edit;

import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.service.ITreeDataService;

/**
 * 树型基础资料编辑界面基础接口类<p>
 * 【针对基础资料 编辑界面操作相关业务逻辑，如：用户表】
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 * @param <E> 基础资料实体对象<br>
 * @param <TREE> 树型分组信息对象<br>
 * @param <ID> 基础资料关键字<br>
 */
public interface IDataBasetreeEditUI<TREE extends AbstractTreeData, E extends AbstractDataBase, ID extends java.io.Serializable> extends IDataBaseEditUI<E, ID> {
	/**
	 * 获取分组信息业务逻辑层操作对象<p>
	 * @return 分组信息业务逻辑层操作对象<br>
	 */
	public ITreeDataService<TREE, ID> getTreeGroupService();
}
