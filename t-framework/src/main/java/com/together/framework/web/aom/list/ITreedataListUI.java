package com.together.framework.web.aom.list;

import org.operamasks.faces.annotation.Action;

import com.together.framework.entity.AbstractTreeData;
import com.together.framework.service.ITreeDataService;

/**
 * 树型基础资料序时薄界面接口类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 * @param <Tree> 树型信息对象<br>
 * @param <ID>   树型信息关键字<br>
 */
public interface ITreedataListUI<TREE extends AbstractTreeData, ID extends java.io.Serializable> extends IBaseListUI<TREE, ID> {
	/**
	 * 树型信息【选择】事件监听函数<p>
	 */
	@Action
	public void tree_onselect();
	
	/**
	 * 刷新树型控件<p>
	 */
	public void refreashUITree();
	
	/**
	 * 获取树型初始化根结点的显示名称<p>
	 */
	public String getTreeRootName();
	
	/**
	 * 监听树型控件选中事件<p>
	 */
	public void tree_select_listener();
	
	/**
	 * 获取选中树型结点的数据信息<p>
	 * @return 树型结点的数据信息<br>
	 */
	public TREE getSelectTreeNodeData();
	
	/**
	 * 判断是否选中树型结点<p>
	 * @return true:选中 false:未选中<br>
	 */
	public boolean checkTreeNodeSelected();
	
	/**
	 * 获取树型结点对应的所有子结点信息<p>
	 * @return 树型结点对应的所有子结点信息<br>
	 */
	public Object[] getTreeChildren(Object userdata);
	
	/**
	 * 获取树型信息业务逻辑层操作对象<p>
	 * @return 树型信息业务逻辑层操作对象<br>
	 */
	public ITreeDataService<TREE, ID> getCurrentTreeService();
}
