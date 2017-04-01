package com.together.framework.web.aom.edit.impl;

import javax.faces.model.SelectItem;

import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.SaveState;
import org.operamasks.faces.user.util.Browser;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.common.number.NumberUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.service.ITreeDataService;
import com.together.framework.web.aom.edit.ITreeGroupEditUI;
import com.together.framework.web.enums.PageStatusEnum;



/**
 * 针对树表弹出窗口，只针对树操作编辑界面，如：用户分组 <p>
 * @author LingMin 
 * @date 2014-10-30<br>
 * @version 1.0<br>
 * @param <E> 树型基础资料对象<br>
 * @param <ID> 树型对象关键字<br>
 */
@SuppressWarnings("unchecked")
public abstract class TreeGroupEditUIBean <E extends AbstractTreeData, ID extends java.io.Serializable> extends DataBaseEditUIBean<E, ID> implements ITreeGroupEditUI<E , ID> {

	
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 7097476200048619894L;
	/** 上级结点关键字 **/
	@Bind @SaveState
	protected ID parentKey;
	/** 是否子结点 **/
	@Bind
	protected Boolean isLeaf;
	/** 树型控件名称 **/
	protected String treeName = "tree";
	/** 上级结点属性名 **/
	protected String parentField = "parent";
	/** 上级结点下拉信息 **/
	@Bind @SaveState
	protected SelectItem[] parentNodeDispList;
	
	/**
	 * 收集页面数据<p>
	 */
	public void storeField() {
		super.storeField();
		// 根据上级结点关键字初始化相关数据
		if (StringUtils.isNotEmpty((String) parentKey)) {
			//查询 父节点对象
			E parent = this.getCurrentService().get(parentKey);
			// 上级结点信息
			ReflectionUtils.setFieldValue(model, parentField, parent);
			// 长编码信息
			String longNum = (String) parent.getLongNumber();
			if (StringUtils.isEmpty(longNum)) {
				// 长编码
				model.setLongNumber(number);
				// 所属层级
				model.setLevels(new Integer(1));
			} else {
				// 所属层级
				model.setLevels(NumberUtils.getLegalInteger(parent.getLevels()) + 1);
				// 长编码
				model.setLongNumber(longNum.concat(FrameworkConstants.TREE_LONGNUM_SEPARATOR).concat(number));
			}
			// 设置上级结点
			ReflectionUtils.setFieldValue(model, "parent", parent);
		} else {
			// 长编码
			model.setLongNumber(number);
			// 所属层级
			model.setLevels(new Integer(1));
		}
		// 是否子结点
		ReflectionUtils.setFieldValue(model, "isLeaf", isLeaf);
	}
	
	/**
	 * 加载页面数据<p>
	 */
	public void loadField() {
		super.loadField();
		// 是否子结点
		isLeaf = model.getIsLeaf();
		// 上级结点关键字
		E parent = (E) ReflectionUtils.getFieldValue(model, parentField);
		if (CommonUtils.isNotEmptyObject(parent)) {
			parentKey = (ID) parent.getId();
		}
	}
	
	/**
	 * 判断是否允许执行禁用操作<p>
	 */
	public boolean verifyDisable(E record) {
		boolean rtnB = super.verifyDisable(record);
		// 判断当前结点下是否包含已启用结点
		java.util.List<E> result = getTreeGroupService().getDeepestStatusChildrenNodeList((ID) record.getId(), Boolean.TRUE, Boolean.FALSE);
		if (CommonUtils.isNotEmptyList(result)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"EMS".concat(StringUtils.getRandomString()), "EMS0005",StringUtils.getLegalString(record.getNumbers())
				.concat(",").concat(getAomMessage("bd_record_status")).concat(",").concat(getCustomizeMessage("DATA_DISABLE_BTN"))
			);
		}
		return rtnB;
	}
	
	/**
	 * 判断是否允许执行删除操作<p>
	 */
	public boolean verifyDelete(E record) {
		boolean rtnB = super.verifyDelete(record);
		// 判断当前结点下是否包含子节点
		java.util.List<E> result = getTreeGroupService().getChildrenNodeList((ID) record.getId(), parentField);
		if (CommonUtils.isNotEmptyList(result)) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"EMS".concat(StringUtils.getRandomString()), "EMS0005",StringUtils.getLegalString(record.getNumbers())
				.concat(",").concat(getAomMessage("menu.isLeaf")).concat(",").concat(getCustomizeMessage("DATA_DELETE_BTN"))
			);
		}
		return rtnB;
	}
	
	/**
	 * 页面渲染之前调用的函数<p>
	 */
	public void beforePageOnLoad() {
		// 调用父类的相关方法
		super.beforePageOnLoad();
		// 根据页面菜单初始化上级菜单
		if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus())) {
			// 获取上级结点关键字
			if (StringUtils.isEmpty((String) parentKey)) {
				parentKey = (ID) getRequestParameter(FrameworkConstants.SELECTED_TREENODE_KEY);
				if (StringUtils.isEmpty((String) parentKey)) {
					removeSessionAttribute(FrameworkConstants.SELECTED_TREENODE_KEY);
				}
			}
			// 初始化上级结点信息
			if (StringUtils.isNotEmpty((String) parentKey)) {
				E parent = getTreeGroupService().get(parentKey);
				if (CommonUtils.isNotEmptyObject(parent)) {
					ReflectionUtils.setFieldValue(model, parentField, parent);
					setSessionAttribute(FrameworkConstants.SELECTED_TREENODE_KEY, parentKey);
				}
			}
		}
	}
	
	/**
	 * 初始化下拉列表信息<p>
	 */
	public void initSelectItemList() {
		// 初始化上级结点信息
		E parent = (E) ReflectionUtils.getFieldValue(model, parentField);
		if (CommonUtils.isNotEmptyObject(parent)) {
			parentNodeDispList = new SelectItem[] {new SelectItem(parentKey, parent.getName())};
		}
	}
	
	/**
	 * 刷新父窗口的树型信息<p>
	 */
	public void refreashParentTree() {
		StringBuffer jscript = new StringBuffer(" var parent = window.opener; ")
			.append(" if(typeof(parent) == 'object' && typeof(parent.")
			.append(treeName).append(") == 'object') { ")
			.append(" parent.").append(treeName).append(".root.reload();}");
		Browser.execClientScript(jscript.toString());
	}
	
	/**
	 * 刷新父窗口的指定名称的列表对象<p>
	 */
	public void refreshParentDataGrid() {
		refreashParentTree();
		super.refreshParentDataGrid();
	}
	
	/**
	 * 设置树型控件名称<p>
	 * @param treeName 树型控件名称<br>
	 */
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	
	/**
	 * 设置上级结点属性名<p>
	 * @param parentField 上级结点属性名<br>
	 */
	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	
}
