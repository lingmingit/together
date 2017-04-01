package com.together.framework.web.aom.edit.impl;

import javax.faces.model.SelectItem;

import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.SaveState;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.AbstractTreeData;
import com.together.framework.web.aom.edit.IDataBasetreeEditUI;
import com.together.framework.web.enums.PageStatusEnum;

/**
 * 树型基础资料编辑界面基础实现类<p>
 * 【针对基础资料 编辑界面操作相关业务逻辑，如：用户表】
 * @author LingMin @date 2014-10-20<br>
 * @version 1.0<br>
 * @param <E> 树型基础资料对象<br>
 * @param <ID> 树型对象关键字<br>
 */
@SuppressWarnings("unchecked")
public abstract class DataBasetreeEditUIBean<TREE extends AbstractTreeData, E extends AbstractDataBase, ID extends java.io.Serializable> extends DataBaseEditUIBean<E, ID> implements IDataBasetreeEditUI<TREE, E, ID> {

	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -6057239554672603976L;
	/** 上级结点关键字 **/
	@Bind @SaveState
	protected ID parentKey;
	/** 分组字段名 **/
	protected String groupField="group";
	/** 上级结点下拉信息 **/
	@Bind @SaveState
	protected SelectItem[] parentNodeDispList;
	
	/**
	 * 构造函数:初始化相关参数<p>
	 */
	public DataBasetreeEditUIBean() {
		setModelIndex(1);
	}
	
	/**
	 * 收集页面数据<p>
	 */
	public void storeField() {
		super.storeField();
		// 分组信息
		if (CommonUtils.isNotEmptyObject(parentKey)) {
			TREE parent = getTreeGroupService().get(parentKey);
			if (CommonUtils.isNotEmptyObject(parent)) {
				ReflectionUtils.setFieldValue(model, groupField, parent);
			}
		}
	}
	
	/**
	 * 加载页面数据<p>
	 */
	public void loadField() {
		super.loadField();
		// 分组信息
		TREE parent = (TREE) ReflectionUtils.getFieldValue(model, groupField);
		if (CommonUtils.isNotEmptyObject(parent)) {
			parentKey = (ID) parent.getId();
		}
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
				TREE parent = getTreeGroupService().get(parentKey);
				if (CommonUtils.isNotEmptyObject(parent)) {
					ReflectionUtils.setFieldValue(model, groupField, parent);
					setSessionAttribute(FrameworkConstants.SELECTED_TREENODE_KEY, parentKey);
				}
			}
		}
	}
	
	/**
	 * 获取标准功能的URL路径<p>
	 * @return URL路径<br>
	 */
	public String getCommonPageURL() {
		StringBuffer BufURL = new StringBuffer(super.getCommonPageURL());
		// 拼接分组字段
		if (CommonUtils.isNotEmptyObject(parentKey)) {
			BufURL.append("&").append(FrameworkConstants.SELECTED_TREENODE_KEY).append("=").append(parentKey);
		}
		return BufURL.append("&sysRandom=").append(Math.random()).toString();
	}
	
	/**
	 * 初始化下拉列表信息<p>
	 */
	public void initSelectItemList() {
		// 初始化上级结点信息
		E parent = (E) ReflectionUtils.getFieldValue(model, groupField);
		if (CommonUtils.isNotEmptyObject(parent)) {
			parentNodeDispList = new SelectItem[] {new SelectItem(parentKey, parent.getName())};
		}
	}

	/**
	 * 设置分组字段名<p>
	 * @param groupField 分组字段名<br>
	 */
	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}
	
}
