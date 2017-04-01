package com.together.framework.web.aom.edit.impl;

import org.operamasks.faces.annotation.Bind;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.service.IDataBaseService;
import com.together.framework.web.aom.edit.IDataBaseEditUI;
import com.together.framework.web.enums.PageStatusEnum;
import com.together.framework.web.utils.OperamaskUtils;

/**
 * 基础资料信息编辑界面实现接口实现类<p>
 * @author LingMin 
 * @date 2014-7-22<br>
 * @version 1.0<br>
 * @param <E> 基础资料信息对象<br>
 * @param <ID> 基础资料关键字<br>
 */
public abstract class DataBaseEditUIBean<E extends AbstractDataBase, ID extends java.io.Serializable> extends CoreEditUIBean<E, ID> implements IDataBaseEditUI<E, ID> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -5758659487941524471L;
	/** 名    称 **/
	@Bind
	protected String name;
	/** 启用标识 **/
	@Bind
	protected Boolean isEnable;
	
	/**
	 * 加载页面数据<p>
	 */
	public void loadField() {
		super.loadField();
		// 名    称
		name = model.getName();
		// 状    态
		isEnable = model.getIsEnable();
	}
	
	/**
	 * 收集页面数据<p>
	 */
	public void storeField() {
		super.storeField();
		// 名    称
		model.setName(name);
		// 状    态
		if (CommonUtils.isNotEmptyObject(isEnable)) {
			model.setIsEnable(isEnable);
		}
	}
	
	/**
	 * 【启用】按钮监听函数<p>
	 */
	public void enableAction() {
		try {
			// 操作权限判断
			if (checkPermission("enableAction")) {
				// 收集页面数据
				storeField();
				// 合法性验证
				if (verifyEnable(model)) {
					processEnableRecord(model);
					// 设置页面状态
					setPageStatus(PageStatusEnum.PAGE_VIEW.getValue());
					// 页面跳转
					forwardURL(getCommonPageURL());
					// 操作提示信息
					putErrorMessage(
						"SMS0001", "SMS0001", StringUtils.getLegalString(number)
						.concat(",").concat(getCustomizeMessage("DATA_ENABLE_BTN"))
					);
				}
			}
			// 刷新当前页面
			refreshParentDataGrid();
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【禁用】按钮监听函数<p>
	 */
	public void disableAction() {
		try {
			// 操作权限判断
			if (checkPermission("disableAction")) {
				// 收集页面数据
				storeField();
				// 合法性验证
				if (verifyDisable(model)) {
					processDisableRecord(model);
					// 设置页面状态
					setPageStatus(PageStatusEnum.PAGE_VIEW.getValue());
					// 页面跳转
					forwardURL(getCommonPageURL());
					// 操作提示信息
					putErrorMessage(
						"SMS0001", "SMS0001", StringUtils.getLegalString(number)
						.concat(",").concat(getCustomizeMessage("DATA_DISABLE_BTN"))
					);
				}
			}
			// 刷新当前页面
			refreshParentDataGrid();
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 判断是否允许执行编辑操作<p>
	 */
	public boolean verifyEdit(E record) {
		return checkSystemRecordRight(record, "DATA_EDIT_BTN")
			&& checkStatusRecordRight(record, Boolean.TRUE, "DATA_EDIT_BTN");
	}
	
	/**
	 * 判断是否允许执行删除操作<p>
	 */
	public boolean verifyDelete(E record) {
		return checkSystemRecordRight(record, "DATA_DELETE_BTN")
			&& checkStatusRecordRight(record, Boolean.TRUE, "DATA_DELETE_BTN");
	}
	
	/**
	 * 判断是否允许执行启用操作<p>
	 */
	public boolean verifyEnable(E record) {
		return checkStatusRecordRight(record, Boolean.TRUE, "DATA_ENABLE_BTN");
	}
	
	/**
	 * 判断是否允许执行禁用操作<p>
	 */
	public boolean verifyDisable(E record) {
		return checkStatusRecordRight(record, Boolean.FALSE, "DATA_DISABLE_BTN");
	}
	
	/**
	 * 初始化页面控件<p>
	 */
	public void initPageComponent() {
		super.initPageComponent();
		// 根据页面状态初始化
		if (PageStatusEnum.PAGE_VIEW.equals(getPageStatus())) {
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnExit"), false, false);
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnAddNew"), false, false);
			// 根据数据状态初始化按钮
			if (!model.getIsEnable()) {
				OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEdit"), false, false);
				if (!model.getIsSystem()) {
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDelete"), false, false);
				}
				OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEnable"), false, false);
			} else {
				OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDisable"), false, false);
			}
		} else if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus())) {
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEdit"), false, true);
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDelete"), false, true);
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEnable"), false, true);
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDisable"), false, true);
		} else if (PageStatusEnum.PAGE_EDIT.equals(getPageStatus())) {
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEdit"), false, true);
			OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDisable"), false, true);
		}
	}
	
	/**
	 * 执行业务逻辑层启用数据操作<p>
	 */
	public void processEnableRecord(E record) {
		getDataBaseService().enable(record, getCurrentSessionUser());
	}
	
	/**
	 * 执行业务逻辑层禁用数据操作<p>
	 */
	public void processDisableRecord(E record) {
		getDataBaseService().disable(record, getCurrentSessionUser());
	}
	
	/**
	 * 获取基础资料信息业务逻辑层操作对象<p>
	 */
	private IDataBaseService<E, ID> getDataBaseService() {
		return (IDataBaseService<E, ID>)getDataBaseService();
	}
	
	/**
	 * 根据数据记录的系统标识判断是否具有操作权限<p>
	 */
	public boolean checkSystemRecordRight(E record, String oprtName) {
		boolean rtnB = true;
		// 是否系统数据
		Boolean flag = (Boolean) record.getIsSystem();
		if (CommonUtils.isNotEmptyObject(flag) && flag) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage(
				"EMS".concat(StringUtils.getRandomString()), "EMS0003",
				StringUtils.getLegalString(record.getNumbers()).concat(",").concat(getCustomizeMessage(oprtName))
			);
		}
		return rtnB;
	}
	
	/**
	 * 根据数据记录的状态判断是否具有操作权限<p>
	 */
	public boolean checkStatusRecordRight(E record, Boolean status, String oprtName) {
		boolean rtnB = true;
		// 是否启用
		Boolean flag = (Boolean) record.getIsEnable();
		if (flag == status) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("EMS".concat(StringUtils.getRandomString()), "EMS0002",
				(StringUtils.getLegalString(record.getNumbers()).concat(",").concat(
				status ? getCustomizeMessage("BD_STATUS_ENABLE") : getCustomizeMessage("BD_STATUS_DISABLE")
				).concat(",").concat(getCustomizeMessage(oprtName)))
			);
		}
		return rtnB;
	}
}
