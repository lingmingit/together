package com.together.framework.web.aom.list.impl;

import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.component.tree.base.TreeDataProvider3;
import org.operamasks.faces.component.tree.base.TreeDataProviderAdapter;
import org.operamasks.faces.component.tree.impl.UITree;
import org.operamasks.faces.component.tree.impl.UITreeNode;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.entity.base.Core;
import com.together.framework.service.ITreeDataService;
import com.together.framework.web.aom.list.ITreedataListUI;

/**
 * 树型基础资料序时薄界面接口实现类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 * @param <Tree> 树型信息对象<br>
 * @param <ID>   树型信息关键字<br>
 */
@SuppressWarnings({ "unchecked"})
public abstract class TreedataListUIBean<TREE extends AbstractTreeData, ID extends java.io.Serializable> extends DataBaseListUIBean<TREE, ID> implements ITreedataListUI<TREE, ID> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 3753582582394922783L;
	/** 树型控件对象 **/
	@Accessible
	protected UITree tree;
	/** 数据分组字段名 **/
	private String groupField="group";
	/** 树型控件数据适配器 **/
	@Accessible
	protected TreeDataProviderAdapter treeDataProvider = new TreeDataProviderAdapter() {
		/**
		 * 获取树型结点的ICON图标名称<p>
		 */
		public String getIcon(Object userData) {
			return getTreeNodeIcon(userData);
		}
		
		/**
		 * 获取树型结点的连接URL地址<p>
		 */
		public String getHref(Object userData) {
			return getTreeNodeHref(userData);
		}
		
		/**
		 * 获取结点的显示名称<p>
		 */
		public String getText(Object userData) {
			return getTreeNodeDisplayText(userData);
		}
		
		/**
		 * 是否叶子结点标志<p>
		 */
		public boolean isLeaf(Object userData) {
			return isLeafTreeNode(userData);
		}
		
		/**
		 * 当结点为复选框时，判断结点是否勾选<p>
		 */
		public Boolean isChecked(Object userData) {
			return super.isChecked(userData);
		}
		
		/**
		 * 判断树型结点是否拥有缓存<p>
		 */
		public boolean isCascade(Object userData) {
			return super.isCascade(userData);
		}
		
		/**
		 * 判断结点是否自动展开<p>
		 */
		public boolean isExpanded(Object userData) {
			return userData instanceof String;
		}
		
		/**
		 * 获取选中结点下的所有子结点信息<p>
		 */
		public Object[] getChildren(Object userData) {
			return getTreeChildren(userData);
		}
		
		/**
		 * 获取结点超链接打开的TARGET目标名<p>
		 */
		public String getHrefTarget(Object userData) {
			return getTreeNodeHrefTarget(userData);
		}
	};
	
	/**
	 * 【启用】按钮监听函数<p>
	 */
	public void enableAction() {
		super.enableAction();
		refreashUITree();
	}
	
	/**
	 * 结点选择事件监听函数<p>
	 */
	public void tree_onselect() {
		getCondition().clear();
		// 监听结点事件
		tree_select_listener();
		// 刷新列表控件
		super.refreshDataGridList();
	}
	
	/**
	 * 刷新页面控件<p>
	 */
	public void refreshDataGridList() {
		refreashUITree();
		super.refreshDataGridList();
	}
	
	/**
	 * 刷新树型控件<p>
	 */
	public void refreashUITree() {
		// 刷新树型控件
		if (CommonUtils.isNotEmptyObject(tree)) {
			tree.getChildren().clear();
			tree.setValue(treeDataProvider);
			tree.repaint();
		}
	}
	
	/**
	 * 结点选中事件监听函数<p>
	 */
	public void tree_select_listener() {
		// 判断是否选中结点
		if (checkTreeNodeSelected()) {
			// 获取树型结点关键字
			TREE treeData = getSelectTreeNodeData();
			// 获取结点下所有子结点
			SQLCondition condition = new SQLCondition();
			condition.put(
				new ConditionParameter("longNumber", treeData.getLongNumber(), CompareTypeEnum.COMPARE_RIGHT_LIKE)
			);
			// 执行数据库查询
			String[] params = getCurrentService().getEntryObjectKeys(condition);
			if (CommonUtils.isNotEmptyObjectArray(params)) {
				getCondition().put(
					new ConditionParameter(getGroupField().concat(".id"), params, CompareTypeEnum.COMPARE_IN)
				);
			}
		} else {
			// 移除SESSION中保存的选中结点值
			//removeSessionAttribute(FrameworkConstants.SELECTED_TREENODE_KEY);
		}
	}
	
	/**
	 * 获取选中树型结点的数据信息<p>
	 */
	public TREE getSelectTreeNodeData() {
		TREE teedata = null;
		// 判断是否选中结点
		if (checkTreeNodeSelected()) {
			teedata = (TREE)  tree.getSelectedNode().getUserData();
		}
		return teedata;
	}
	
	/**
	 * 获取标准URL的路径<p>
	 */
	public String getStandardPageURL() {
		StringBuffer bufURL = new StringBuffer(super.getStandardPageURL());
		// 获取选中的树型结点信息
		TREE selected = getSelectTreeNodeData();
		if (CommonUtils.isNotEmptyObject(selected) && selected.getClass() == getEntityModelClass(0)) {
			bufURL.append("&").append(FrameworkConstants.SELECTED_TREENODE_KEY).append("=").append(selected.getId());
		}
		return bufURL.toString();
	}
	
	/**
	 * 判断是否选中树型结点<p>
	 * @return true:已选中 false:未选中<br>
	 */
	public boolean checkTreeNodeSelected() {
		boolean rtnB = false;
		// 获取选中结点信息
		UITreeNode selectNode = tree.getSelectedNode();
		if (CommonUtils.isNotEmptyObject(selectNode)) {
			Object treedata = selectNode.getUserData();
			rtnB = CommonUtils.isNotEmptyObject(treedata) && treedata instanceof Core;
		}
		return rtnB;
	}
	
	/**
	 * 验证是否允许执行新增操作<p>
	 */
	public boolean verifyAddNew() {
		boolean rtnB = super.verifyAddNew();
		if (rtnB) {
			// 判断选中结点是否启用
			TREE selected = getSelectTreeNodeData();
			if (CommonUtils.isNotEmptyObject(selected)) {
				// 启用标志
				if (!CommonUtils.getLegalBoolean(selected.getIsEnable())) {
					rtnB = rtnB ? false : rtnB;
					putErrorMessage(
						"IMS0012", "IMS0012", selected.getNumbers().concat(",").concat(getCustomizeMessage(
						"BD_STATUS_DISABLE")).concat(",").concat(getCustomizeMessage("DATA_ADDNEW_BTN"))
					);
				}
				// 子结点标志
				if (CommonUtils.getLegalBoolean(selected.getIsLeaf())) {
					rtnB = rtnB ? false : rtnB;
					putErrorMessage(
						"IMS0013", "IMS0013", selected.getNumbers().concat(",").concat(getCustomizeMessage("DATA_ADDNEW_BTN"))
					);
				}
			}
		}
		return rtnB;
	}
	
	/**
	 * 判断是否允许执行禁用操作<p>
	 */
	public boolean verifyDisable(TREE record) {
		boolean rtnB = super.verifyDisable(record);
		// 判断结点下是否包含启用结点
		java.util.List<TREE> rtnList = getCurrentTreeLogicBean().getDeepestStatusChildrenNodeList((ID) record.getId(), Boolean.TRUE, Boolean.FALSE);
		if (CommonUtils.isNotEmptyList(rtnList)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"EMS".concat(StringUtils.getRandomString()), "EMS0005", 
				record.getNumbers().concat(",").concat(getCustomizeMessage("BD_STATUS_ENABLE")).concat(",").concat(getCustomizeMessage("DATA_DISABLE_BTN"))
			);
		}
		return rtnB;
	}
	
	/**
	 * 判断是否允许执行删除操作<p>
	 */
	public boolean verifyDelete(TREE record) {
		boolean rtnB = super.verifyDelete(record);
		// 判断结点下是否包含子结点
		java.util.List<TREE> rtnList = getCurrentTreeLogicBean().getDeepestStatusChildrenNodeList((ID)record.getId(), null, false);
		if (CommonUtils.isNotEmptyList(rtnList)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"EMS".concat(StringUtils.getRandomString()), "EMS0006", record.getNumbers().concat(",").concat(getCustomizeMessage("DATA_DELETE_BTN"))
			);
		}
		return rtnB;
	}
	
	/**
	 * 获取工具栏的高度<p>
	 */
	public int getCurrentToolAreaHeight() {
		return super.getCurrentToolAreaHeight() * 2;
	}
	
	/**
	 * 获取树型结点显示的图片名称<p>
	 * @param 树型结点对象<br>
	 * @return 显示的图片名称<br>
	 */
	public String getTreeNodeIcon(Object userData) {
		return "";
	}
	
	/**
	 * 获取树型结点链接的URL地址<p>
	 * @param userData 树型结点对象<br>
	 * @return 树型结点链接的URL地址<br>
	 */
	public String getTreeNodeHref(Object userData) {
		return "";
	}
	
	/**
	 * 判断选中结点是否为子结点<p>
	 * @param userdata 树型结点对象<br>
	 * @return 是否子结点标志<br>
	 */
	public boolean isLeafTreeNode(Object userdata) {
		boolean rtnB = true;
		if (CommonUtils.isNotEmptyObject(userdata) && userdata.getClass() == getEntityModelClass(0)) {
			rtnB = CommonUtils.getLegalBoolean((Boolean) ReflectionUtils.getFieldValue(userdata, "isLeaf"));
		}
		return rtnB;
	}
	
	/**
	 * 获取树型结点的子节点信息<p>
	 */
	public Object[] getTreeChildren(Object userdata) {
		Object[] rtnOA = null;
		// 根据结点信息进行树构造
		if (CommonUtils.isEmptyObject(userdata)) {
			rtnOA = new Object[] { getTreeRootName() };
		} else {
			ID parentKey = null;
			if (CommonUtils.isNotEmptyObject(userdata)) {
				// 初始化查询条件
				if (userdata.getClass() == getEntityModelClass(0)) {
					parentKey = (ID) ReflectionUtils.getFieldValue(userdata, "id");
				}
			}
			// 执行数据库查询
			java.util.List<TREE> rtnList = getCurrentTreeLogicBean().getChildrenNodeList(parentKey, getGroupField());
			if (CommonUtils.isNotEmptyList(rtnList)) {
				rtnOA = rtnList.toArray();
			}
		}
		return rtnOA;
	}
	
	/**
	 * 获取结点超链接打开的TARGET目标名<p>
	 * @param userData 树型结点对象<br>
	 * @return 结点超链接打开的TARGET目标名<br>
	 */
	public String getTreeNodeHrefTarget(Object userData) {
		return "";
	}
	
	/**
	 * 根据结点对象获取结点的显示名称<p>
	 * @param userData 结点对象<br>
	 * @return 显示名称<br>
	 */
	public String getTreeNodeDisplayText(Object userData) {
		String rtnS = "";
		if (CommonUtils.isNotEmptyObject(userData)) {
			if (userData instanceof String) {
				rtnS = (String) userData;
			} else if (userData.getClass() == getEntityModelClass(0)) {
				rtnS = (String) ReflectionUtils.getFieldValue(userData, "name");
			}
		}
		return rtnS;
	}
	
	/**
	 * 获取树型控件的适配器<p>
	 * @return 树型控件的适配器<br>
	 */
	@Bind(id="tree")
	protected TreeDataProvider3 getTreeDataProviderAdapter() {
		return treeDataProvider;
	}
	
	/**
	 * 获取树型信息业务逻辑层操作对象<p>
	 */
	public ITreeDataService<TREE, ID> getCurrentTreeLogicBean() {
		return (ITreeDataService<TREE, ID>) getCurrentService();
	}
	
	/**
	 * 获取数据分组字段名<p>
	 * @return 数据分组字段名<br>
	 */
	public String getGroupField() {
		return groupField;
	}
	
	/**
	 * 设置数据分组字段名<p>
	 * @param groupField 数据分组字段名<br>
	 */
	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}
}
