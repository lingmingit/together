package com.together.framework.web.aom.list.impl;

import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.component.widget.UIToolBar;
import org.operamasks.faces.user.util.Browser;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.dao.common.OrderParameter;
import com.together.framework.dao.enums.OrderTypeEnum;
import com.together.framework.web.aom.list.IBaseListUI;
import com.together.framework.web.enums.ActionNameDefineEnum;
import com.together.framework.web.enums.PageStatusEnum;


/**
 * 基于序时薄界面的基本实现接口实现类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
@SuppressWarnings("unchecked")
public abstract class BaseListUIBean<E extends java.io.Serializable , ID extends java.io.Serializable> extends CoreListUIBean<E, ID> implements IBaseListUI<E, ID> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 1367338059043486594L;
	/** 弹出窗口高度 **/
	private int winHeight=300;
	/** 弹出窗口宽度 **/
	private int  winWidth=500;
	/** 工具栏控件 **/
	@Bind(id="listOprtArea")  
    protected UIToolBar toolbar;
	/** 是否滚动条 **/
	private boolean isScroll = false;
	
	/**
	 * 【新增】按钮监听函数<p>
	 */
	public void addNewAction() {
		try {
			// 操作权限判断
			if (verifyAddNew()) {
				setPageStatus(PageStatusEnum.PAGE_ADDNEW.getValue());
				removeSessionAttribute(FrameworkConstants.SELECTED_RECORD_KEY);
				openModalWindow(getStandardPageURL(), winWidth, winHeight);
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			//putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		//displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【编辑】按钮监听函数<p>
	 */
	public void editAction() {
		try {
			// 操作权限判断
			if (checkPermission(ActionNameDefineEnum.EditAction.getValue())) {
				// 判断是否选中行
				if (checkRowSelectedForOperate("DATA_EDIT_BTN")) {
					if (verifyEdit(getSelectedRowsData()[0])) {
						setPageStatus(PageStatusEnum.PAGE_EDIT.getValue());
						openModalWindow(getStandardPageURL(), winWidth, winHeight);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			//putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		//displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【查看】按钮监听函数<p>
	 */
	public void viewAction() {
		try {
			// 操作权限判断
			if (checkPermission(ActionNameDefineEnum.ViewAction.getValue())) {
				// 判断是否选中行
				if (checkRowSelectedForOperate("DATA_VIEW_BTN")) {
					setPageStatus(PageStatusEnum.PAGE_VIEW.getValue());
					openModalWindow(getStandardPageURL(), winWidth, winHeight);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			//putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		//displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【删除】按钮监听函数<p>
	 */
	public void deleteAction() {
		try {
			// 操作权限判断
			if (checkPermission(ActionNameDefineEnum.DeleteAction.getValue())) {
				// 判断是否选中行
				if (checkRowSelectedForOperate("DATA_DELETE_BTN")) {
					E[] selected = getSelectedRowsData();
					// 循环删除指定数据记录
					for (E record : selected) {
						// 判断是否允许删除
						if (verifyDelete(record)) {
							processDeleteRecord(record);
//							putErrorMessage("SMS".concat(StringUtils.getRandomString()), "SMS0001",
//								((String) ReflectionUtils.getFieldValue(record,"numbers")).concat(",").concat(getCustomizeMessage("DATA_DELETE_BTN"))
//							);
						}
					}
					// 刷新列表控件
					refreshDataGridList();
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			//putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		//displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 验证是否允许执行新增操作<p>
	 */
	public boolean verifyAddNew() {
		return checkPermission(ActionNameDefineEnum.AddNewAction.getValue());
	}
	
	/**
	 * 设置默认的过滤条件<p>
	 */
	public void setDefaultCondition() {
		super.setDefaultCondition();
		// 设置默认过滤条件
		java.lang.reflect.Type[] paramClass = ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		if (CommonUtils.isNotEmptyObjectArray(paramClass)) {
			// 排序字段-创建时间
			if (ReflectionUtils.isExistFieldForDeep(getEntityModelClass(paramClass.length - 2), "modifyTime")) {
				getDefaultCondition().put(new OrderParameter("modifyTime", OrderTypeEnum.ORDER_DESC));
			}
		}
	}
	
	/**
	 * 获取标准URL的路径<p>
	 */
	public String getStandardPageURL() {
		StringBuffer bufURL = new StringBuffer(getFacesContextURL(getEditURL()));
		// 页面状态
		if (CommonUtils.isNotEmptyObject(getPageStatus())) {
			bufURL.append("?").append(FrameworkConstants.PAGE_STATUS).append("=").append(getPageStatus());
		}
		// 组装URL路径
		if (!(PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus()) || PageStatusEnum.PAGE_EXCEPTION.equals(getPageStatus()))) {
			// 拼接关键字
			E selected = getSelectedRowsData()[0];
			if (CommonUtils.isNotEmptyObject(selected)) {
				bufURL.append("&").append(FrameworkConstants.SELECTED_RECORD_KEY).append("=").append(ReflectionUtils.getFieldValue(selected, "id"));
			}
		}
		return bufURL.append("&sysRandom=").append(Math.random()).toString();
	}
	
	/**
	 * 以MODEL方式弹出指定的URL<p>
	 * @param url 窗口的URL<br>
	 * @param width 窗口宽度<br>
	 * @param height 矿口高度<br>
	 */
	public void openModalWindow(String url, int width, int height) {
		StringBuffer script = new StringBuffer("openWindow('").append(url).append("','")
			.append(getEditUIDisplayName()).append("',").append(width).append(",")
			.append(height).append(",").append("0, 0, ").append(isScroll).append(");");
		Browser.execClientScript(script.toString());
	}
	
	/**
	 * 执行业务逻辑层删除数据操作<p>
	 */
	public void processDeleteRecord(E record) {
		getCurrentService().delete((ID) ReflectionUtils.getFieldValue(record, "id"));
	}
	
	/**
	 * 设置滚动条标识<p>
	 * @param isScroll 滚动条标识<br>
	 */
	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}
	
	/**
	 * 获取弹出窗口高度<p>
	 * @return 弹出窗口高度<br>
	 */
	public int getWinHeight() {
		return winHeight;
	}
	
	/**
	 * 设置弹出窗口高度<p>
	 * @param winHeight 弹出窗口高度<br>
	 */
	public void setWinHeight(int winHeight) {
		this.winHeight = winHeight;
	}

	/**
	 * 获取弹出窗口宽度<p>
	 * @return 弹出窗口宽度<br>
	 */
	public int getWinWidth() {
		return winWidth;
	}

	/**
	 * 设置弹出窗口宽度<p>
	 * @param winWidth 弹出窗口宽度<br>
	 */
	public void setWinWidth(int winWidth) {
		this.winWidth = winWidth;
	}
}
