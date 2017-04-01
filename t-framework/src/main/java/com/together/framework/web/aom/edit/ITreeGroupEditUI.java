package com.together.framework.web.aom.edit;

import com.together.framework.entity.AbstractTreeData;
import com.together.framework.service.ITreeDataService;


/**
 * 树型基础资料分组编辑界面操作接口类<p>
 * 【针对树表弹出窗口，只针对树操作编辑界面，如：用户分组】
 * @author LingMin @date 2014-10-20<br>
 * @version 1.0<br>
 * @param <E> 基础资料实体对象<br>
 * @param <TREE> 树型分组信息对象<br>
 * @param <ID> 基础资料关键字<br>
 */
public interface ITreeGroupEditUI<E extends AbstractTreeData, ID extends java.io.Serializable> extends IDataBaseEditUI<E, ID> {
	/**
	 * 获取分组信息业务逻辑层操作对象<p>
	 * @return 分组信息业务逻辑层操作对象<br>
	 */
	public ITreeDataService<E, ID> getTreeGroupService();
}
