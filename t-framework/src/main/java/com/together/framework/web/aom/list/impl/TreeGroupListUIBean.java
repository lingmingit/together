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
import com.together.common.number.NumberUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.entity.base.Core;
import com.together.framework.web.aom.list.ITreeGroupListUI;
import com.together.framework.web.enums.PageStatusEnum;

/**
 * 树型分组信息序时薄界面操作实现类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
@SuppressWarnings("unchecked")
public abstract class TreeGroupListUIBean<TREE extends AbstractTreeData, E extends AbstractDataBase, ID extends java.io.Serializable> extends DataBaseListUIBean<E, ID> implements ITreeGroupListUI<TREE, E, ID> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 7606357967367625380L;
	/** 树型控件对象 **/
	@Accessible
	protected UITree tree;
	/** 树型窗口宽度 **/
	private int treeWinWidth;
	/** 树型窗口高度 **/
	private int treeWinHeight;
	/** 数据分组字段名 **/
	private String groupField="group";
	/** 树型上级字段名 **/
	private String parentField="parent";
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
	 * 构造函数:初始化相关参数<p>
	 */
	public TreeGroupListUIBean() {
		// 弹出窗口属性
		setTreeWinWidth(NumberUtils
			.getIntegerFromString(getSystemConfigParam("modal_single_width"))
		);
		setTreeWinHeight(NumberUtils
			.getIntegerFromString(getSystemConfigParam("modal_single_height"))
		);
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
	 * 刷新当前页面列表控件<p>
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
	 * 【新增】树型信息监听函数<p>
	 */
	public void treeAddNewAction() {
		try {
			// 验证是否允许执行新增操作
			if (verifyTreeAddNew()) {
				setPageStatus(PageStatusEnum.PAGE_ADDNEW.getValue());
				openModalWindow(getStandardTreePageURL(), treeWinWidth, treeWinHeight);
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			putErrorMessage("SMS".concat(StringUtils.getRandomString()), ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【编辑】树型信息监听函数<p>
	 */
	public void treeEditAction() {
		try {
			// 操作权限判断
			if (checkPermission("treeEditAction")) {
				// 判断是否选中行
				if (checkTreeNodeSelectedForOperate("DATA_EDIT_BTN")) {
					if (verifyTreeEdit(getSelectTreeNodeData())) {
						setPageStatus(PageStatusEnum.PAGE_EDIT.getValue());
						openModalWindow(getStandardTreePageURL(), treeWinWidth, treeWinHeight);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			putErrorMessage("SMS".concat(StringUtils.getRandomString()), ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【删除】树型信息监听函数<p>
	 */
	public void treeDeleteAction() {
		try {
			// 操作权限判断
			if (checkPermission("treeDeleteAction")) {
				// 判断是否选中行
				if (checkTreeNodeSelectedForOperate("DATA_DELETE_BTN")) {
					TREE tree = getSelectTreeNodeData();
					if (verifyTreeDelete(tree)) {
						processDeleteTreeRecord(tree);
						putErrorMessage(
							"IMS0011", "IMS0011", tree.getNumbers().concat(",").concat(getCustomizeMessage("DATA_DELETE_BTN"))
						);
					}
				}
				refreshDataGridList();
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			putErrorMessage("SMS".concat(StringUtils.getRandomString()), ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【启用】树型信息监听函数<p>
	 */
	public void treeEnableAction() {
		try {
			// 操作权限判断
			if (checkPermission("treeEnableAction")) {
				// 判断是否选中数据行
				if (checkTreeNodeSelectedForOperate("DATA_ENABLE_BTN")) {
					TREE tree = getSelectTreeNodeData();
					if (verifyTreeEnable(tree)) {
						processEnableTreeRecord(tree);
						putErrorMessage(
							"IMS0011", "IMS0011", tree.getNumbers().concat(",").concat(getCustomizeMessage("DATA_ENABLE_BTN"))
						);
					}
				}
				refreshDataGridList();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【禁用】树型信息监听函数<p>
	 */
	public void treeDisableAction() {
		try {
			// 操作权限判断
			if (checkPermission("treeDisableAction")) {
				// 判断是否选中数据行
				if (checkTreeNodeSelectedForOperate("DATA_DISABLE_BTN")) {
					TREE tree = getSelectTreeNodeData();
					if (verifyTreeDisable(tree)) {
						processDisableTreeRecord(tree);
						putErrorMessage(
							"IMS0011", "IMS0011", tree.getNumbers().concat(",").concat(getCustomizeMessage("DATA_DISABLE_BTN"))
						);
					}
				}
				refreshDataGridList();
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 获取工具栏的高度<p>
	 */
	public int getCurrentToolAreaHeight() {
		return super.getCurrentToolAreaHeight() * 2;
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
			condition.put(new ConditionParameter("longNumber", treeData.getLongNumber(), CompareTypeEnum.COMPARE_RIGHT_LIKE));
			// 执行数据库查询
			String[] params = getCurrentTreeService().getEntryObjectKeys(condition);
			if (CommonUtils.isNotEmptyObjectArray(params)) {
				getCondition().put(new ConditionParameter(groupField.concat(".id"), params, CompareTypeEnum.COMPARE_IN));
			}
		}
	}
	
	/**
	 * 获取标准URL的路径<p>
	 */
	public String getStandardPageURL() {
		StringBuffer bufURL = new StringBuffer(super.getStandardPageURL());
		// 获取选中的树型结点信息
		TREE selected = getSelectTreeNodeData();
		if (CommonUtils.isNotEmptyObject(selected)) {
			bufURL.append("&").append(FrameworkConstants.SELECTED_TREENODE_KEY).append("=").append(selected.getId());
		}
		return bufURL.toString();
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
	 * 获取树型编辑界面的标准URL路径<p>
	 */
	public String getStandardTreePageURL() {
		StringBuffer bufURL = new StringBuffer(getFacesContextURL(getTreeEditURL()));
		// 页面状态
		if (CommonUtils.isNotEmptyObject(getPageStatus())) {
			bufURL.append("?").append(FrameworkConstants.PAGE_STATUS).append("=").append(getPageStatus());
		}
		// 获取选中的树型结点信息
		TREE selected = getSelectTreeNodeData();
		if (CommonUtils.isNotEmptyObject(selected)) {
			if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus())) {
				bufURL.append("&").append(FrameworkConstants.SELECTED_TREENODE_KEY).append("=").append(selected.getId());
			} else {
				bufURL.append("&").append(FrameworkConstants.SELECTED_RECORD_KEY).append("=").append(selected.getId());
			}
		}
		return bufURL.append("&sysRandom=").append(Math.random()).toString();
	}
	
	/**
	 * 判断是否允许进行新增操作<p>
	 */
	public boolean verifyTreeAddNew() {
		boolean rtnB = checkPermission("treeAddNewAction");
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
	 * 判断是否允许执行编辑操作<p>
	 */
	public boolean verifyTreeEdit(TREE tree) {
		return checkSystemRecordRight(tree, "DATA_EDIT_BTN") && checkStatusRecordRight(tree, Boolean.TRUE, "DATA_EDIT_BTN");
	}
	
	/**
	 * 判断是否允许进行删除操作<p>
	 */
	public boolean verifyTreeDelete(TREE tree) {
		boolean rtnB = checkSystemRecordRight(tree, "DATA_DELETE_BTN") && checkStatusRecordRight(tree, Boolean.TRUE, "DATA_DELETE_BTN");
		// 判断结点下是否包含子结点
		java.util.List<TREE> rtnList = getCurrentTreeService().getDeepestStatusChildrenNodeList((ID)tree.getId(), null, false);
		if (CommonUtils.isNotEmptyList(rtnList)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"EMS0006", "EMS0006", tree.getNumbers().concat(",").concat(getCustomizeMessage("DATA_DELETE_BTN"))
			);
		}
		// 判断结点下是否包含数据信息
		if (getCurrentService().isExistenceRecord(groupField, tree.getId())) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"IMS0010", "IMS0010", tree.getNumbers().concat(",").concat(getCustomizeMessage("DATA_DELETE_BTN"))
			);
		}
		return rtnB;
	}
	
	/**
	 * 【启用】树型信息监听函数<p>
	 */
	public boolean verifyTreeEnable(TREE tree) {
		return dataValidateForEnable(tree) && checkStatusRecordRight(tree, Boolean.TRUE, "DATA_ENABLE_BTN");
	}
	
	/**
	 * 判断是否允许进行启用操作<p>
	 */
	public boolean verifyTreeDisable(TREE tree) {
		boolean rtnB = checkStatusRecordRight(tree, Boolean.FALSE, "DATA_DISABLE_BTN");
		if (rtnB) {
			// 判断结点下是否包含启用结点
			java.util.List<TREE> rtnList = getCurrentTreeService().getDeepestStatusChildrenNodeList((ID) tree.getId(), Boolean.TRUE, Boolean.FALSE);
			if (CommonUtils.isNotEmptyList(rtnList)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage(
					"EMS0005", "EMS0005", tree.getNumbers().concat(",").concat(getCustomizeMessage(
					"BD_STATUS_ENABLE")).concat(",").concat(getCustomizeMessage("DATA_DISABLE_BTN"))
				);
			}
		}
		return rtnB;
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
			java.util.List<TREE> rtnList = getCurrentTreeService().getChildrenNodeList(parentKey, parentField);
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
	 * 执行业务逻辑层删除树型数据操作<p>
	 */
	public void processDeleteTreeRecord(TREE tree) {
		getCurrentTreeService().delete((ID)tree.getId());
		logger.warn("the tree node which named 【".concat(tree.getNumbers()).concat("】 has been deleted!"));
	}
	
	/**
	 * 执行业务逻辑层启用树型数据操作<p>
	 */
	public void processEnableTreeRecord(TREE tree) {
		getCurrentTreeService().enable(tree, getCurrentSessionUser());
		logger.warn("the tree node which named 【".concat(tree.getNumbers()).concat("】 has been enabled!"));
	}
	
	/**
	 * 执行业务逻辑层禁用树型数据操作<p>
	 */
	public void processDisableTreeRecord(TREE tree) {
		getCurrentTreeService().disable(tree, getCurrentSessionUser());
		logger.warn("the tree node which named 【".concat(tree.getNumbers()).concat("】 has been disabled!"));
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
	 * 启用数据记录之前的数据合法性验证<p>
	 */
	public boolean dataValidateForEnable(TREE tree) {
		boolean rtnB = true;
		// 编    码
		String numbers = tree.getNumbers();
		if (StringUtils.isEmpty(numbers)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS0001", "IMS0001", getAomMessage("system.number"));
		}
		// 名    称
		String name = tree.getName();
		if (StringUtils.isEmpty(name)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS0001", "IMS0001", getAomMessage("system.name"));
		}
		return rtnB;
	}
	
	/**
	 * 执行操作之前判断是否已经选中树型结点<p>
	 * @return
	 */
	public boolean checkTreeNodeSelectedForOperate(String oprtName) {
		boolean rtnB = checkTreeNodeSelected();
		if (!rtnB) {
			putErrorMessage("IMS0009", "IMS0009", getCustomizeMessage(oprtName));
		}
		return rtnB;
	}
	
	/**
	 * 根据数据记录的系统标识判断是否具有操作权限<p>
	 */
	public boolean checkSystemRecordRight(TREE tree, String oprtName) {
		boolean rtnB = true;
		// 是否系统数据
		Boolean flag = (Boolean) ReflectionUtils.getFieldValue(tree, "isSystem");
		if (CommonUtils.isNotEmptyObject(flag) && flag) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getRandomString()), "EMS0003",
				tree.getNumbers().concat(",").concat(getCustomizeMessage(oprtName))
			);
		}
		return rtnB;
	}
	
	/**
	 * 根据数据记录的状态判断是否具有操作权限<p>
	 */
	public boolean checkStatusRecordRight(TREE tree, Boolean status, String oprtName) {
		boolean rtnB = true;
		// 是否启用
		Boolean flag = (Boolean) ReflectionUtils.getFieldValue(tree, "isEnable");
		if (flag == status) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("EMS".concat(StringUtils.getRandomString()), "EMS0002", 
				tree.getNumbers().concat(",").concat(status ? getCustomizeMessage("BD_STATUS_ENABLE")
				:getCustomizeMessage("BD_STATUS_DISABLE")).concat(",").concat(getCustomizeMessage(oprtName))
			);
		}
		return rtnB;
	}

	/**
	 * 设置树型窗口宽度<p>
	 * @param treeWinWidth 树型窗口宽度<br>
	 */
	public void setTreeWinWidth(int treeWinWidth) {
		this.treeWinWidth = treeWinWidth;
	}

	/**
	 * 设置树型窗口高度<p>
	 * @param treeWinHeight 树型窗口高度<br>
	 */
	public void setTreeWinHeight(int treeWinHeight) {
		this.treeWinHeight = treeWinHeight;
	}

	/**
	 * 设置数据分组字段名<p>
	 * @param groupField 数据分组字段名<br>
	 */
	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

	/**
	 * 设置树型上级字段名<p>
	 * @param parentField 树型上级字段名<br>
	 */
	public void setParentField(String parentField) {
		this.parentField = parentField;
	}
}
