package com.together.framework.web.aom.list;

import org.operamasks.faces.annotation.Action;

import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.service.ITreeDataService;

/**
 * 树型分组信息序时薄界面操作接口类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public interface ITreeGroupListUI<TREE extends AbstractTreeData, E extends AbstractDataBase, ID extends java.io.Serializable> extends IBaseListUI<E, ID> {
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
	 * 【编辑】树型信息监听函数<p>
	 */
	@Action
	public void treeEditAction();
	
	/**
	 * 获取树型信息编辑界面URL<p>
	 * @return 树型信息编辑界面URL<br>
	 */
	public String getTreeEditURL();
	
	/**
	 * 【删除】树型信息监听函数<p>
	 */
	@Action
	public void treeDeleteAction();
	
	/**
	 * 【新增】树型信息监听函数<p>
	 */
	@Action
	public void treeAddNewAction();
	
	/**
	 * 【启用】树型信息监听函数<p>
	 */
	public void treeEnableAction();
	
	/**
	 * 【禁用】树型信息监听函数<p>
	 */
	public void treeDisableAction();
	
	/**
	 * 获取树型初始化根结点的显示名称<p>
	 */
	public String getTreeRootName();
	
	/**
	 * 判断是否允许进行新增操作<p>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyTreeAddNew();
	
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
	 * 获取树型编辑界面的URL<p>
	 * @return 树型编辑界面的URL<br>
	 */
	public String getStandardTreePageURL();
	
	/**
	 * 获取树型维护界面的显示名称<p>
	 * @return 树型维护界面的显示名称<br>
	 */
	public String getTreeEditUIDisplayName();
	
	/**
	 * 判断是否允许进行编辑操作<p>
	 * @param tree 树型信息<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyTreeEdit(TREE tree);
	
	/**
	 * 判断是否允许进行删除操作<p>
	 * @param tree 树型信息<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyTreeDelete(TREE tree);
	
	/**
	 * 判断是否允许进行启用操作<p>
	 * @param tree 树型信息<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyTreeEnable(TREE tree);
	
	/**
	 * 判断是否允许进行启用操作<p>
	 * @param tree 树型信息<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyTreeDisable(TREE tree);
	
	/**
	 * 执行业务逻辑层删除树型数据操作<p>
	 * @param tree 树型数据记录<br>
	 */
	public void processDeleteTreeRecord(TREE tree);
	
	/**
	 * 执行业务逻辑层启用树型数据操作<p>
	 * @param tree 树型数据记录<br>
	 */
	public void processEnableTreeRecord(TREE tree);
	
	/**
	 * 执行业务逻辑层禁用树型数据操作<p>
	 * @param tree 树型数据记录<br>
	 */
	public void processDisableTreeRecord(TREE tree);
	
	/**
	 * 获取树型结点对应的所有子结点信息<p>
	 * @param userdata 树型数据信息<br>
	 * @return 树型结点对应的所有子结点信息<br>
	 */
	public Object[] getTreeChildren(Object userdata);
	
	/**
	 * 获取树型信息业务逻辑层操作对象<p>
	 * @return 树型信息业务逻辑层操作对象<br>
	 */
	public ITreeDataService<TREE, ID> getCurrentTreeService();
}
