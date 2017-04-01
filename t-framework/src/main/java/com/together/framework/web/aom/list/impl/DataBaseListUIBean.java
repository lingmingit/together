package com.together.framework.web.aom.list.impl;

import org.operamasks.faces.annotation.Bind;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.service.IDataBaseService;
import com.together.framework.web.aom.list.IDataBaseListUI;
import com.together.framework.web.utils.OperamaskUtils;



/**
 * 基础资料序时薄界面基础接口litBean实现类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 * @param <E> 基础资料实体对象<br>
 * @param <ID> 实体对象关键字<br>
 */
public abstract class DataBaseListUIBean<E extends AbstractDataBase, ID extends java.io.Serializable> extends BaseListUIBean<E, ID> implements IDataBaseListUI<E, ID> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 8599345866499061243L;
	/** 名    称 **/
	@Bind
	protected String name;
	/** 编    码 **/
	@Bind
	protected String number;
	/** 启用标识 **/
	@Bind
	protected Boolean isEnable;
	
	/**
	 * 构造函数:初始化相关参数<p>
	 */
	public DataBaseListUIBean() {
		// 弹出窗口属性
//		setWinWidth(NumberUtils
//			.getIntegerFromString(getSystemConfigParam("modal_single_width"))
//		);
//		setWinHeight(NumberUtils
//			.getIntegerFromString(getSystemConfigParam("modal_single_height"))
//		);
	}
	
	/**
	 * 【启用】按钮监听函数<p>
	 */
	public void enableAction() {
		try {
			// 操作权限判断
			if (checkPermission("enableAction")) {
				// 判断是否选中数据行
				if (checkRowSelectedForOperate("DATA_ENABLE_BTN")) {
					E[] selected = getSelectedRowsData();
					for (E record : selected) {
						if (verifyEnable(record)) {
							processEnableRecord(record);
							putErrorMessage("SMS".concat(StringUtils.getRandomString()), "SMS0001",
								record.getNumbers().concat(",").concat(getCustomizeMessage("DATA_ENABLE_BTN"))
							);
						}
					}
					// 刷新列表控件
					refreshDataGridList();
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
	 * 【禁用】按钮监听函数<p>
	 */
	public void disableAction() {
		try {
			// 操作权限判断
			if (checkPermission("disableAction")) {
				// 判断是否选中数据行
				if (checkRowSelectedForOperate("DATA_DISABLE_BTN")) {
					E[] selected = getSelectedRowsData();
					for (E record : selected) {
						if (verifyDisable(record)) {
							processDisableRecord(record);
							putErrorMessage("SMS".concat(StringUtils.getRandomString()), "SMS0001",
								record.getNumbers().concat(",").concat(getCustomizeMessage("DATA_DISABLE_BTN"))
							);
						}
					}
					// 刷新列表控件
					refreshDataGridList();
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
	 * 重置页面查询条件<p>
	 */
	public void resetCondition() {
		name = null;
		number = null;
		isEnable = false;
	}
	
	/**
	 * 收集页面查询条件<p>
	 */
	public void collectionCondition() {
		// 名称
		if (StringUtils.isNotEmpty(name)) {
			getCondition().put(
				new ConditionParameter("name", name,CompareTypeEnum.COMPARE_LIKE)
			);
		}
		// 编码
		if (StringUtils.isNotEmpty(number)) {
			getCondition().put(
				new ConditionParameter("numbers", number,CompareTypeEnum.COMPARE_LIKE)
			);
		}
		// 启用
		if (CommonUtils.isNotEmptyObject(isEnable)) {
			getCondition().put(
				new ConditionParameter(
					"isEnable", CommonUtils.getStringFromBoolean(isEnable),CompareTypeEnum.COMPARE_EQUEAL
				)
			);
		}
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
		return dataValidateForEnable(record)
				&& checkStatusRecordRight(record, Boolean.TRUE, "DATA_ENABLE_BTN");
	}
	
	/**
	 * 判断是否允许执行禁用操作<p>
	 */
	public boolean verifyDisable(E record) {
		return checkStatusRecordRight(record, Boolean.FALSE, "DATA_DISABLE_BTN");
	}
	
	/**
	 * 执行业务逻辑层启用数据操作<p>
	 */
	public void processEnableRecord(E record) {
		getBasedataLogicBean().enable(record, getCurrentSessionUser());
	}
	
	/**
	 * 执行业务逻辑层禁用数据操作<p>
	 */
	public void processDisableRecord(E record) {
		getBasedataLogicBean().disable(record, getCurrentSessionUser());
	}
	
	/**
	 * 获取分录行高度<p>
	 */
	public int getCurrentDataGridRowHeight() {
		return super.getCurrentDataGridRowHeight() + 3;
	}
	
	/**
	 * 启用数据记录之前的数据合法性验证<p>
	 */
	public boolean dataValidateForEnable(E record) {
		boolean rtnB = true;
		// 编    码
		if (StringUtils.isEmpty(record.getNumbers())) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS0001", "IMS0001", "数据编码");
		}
		// 名    称
		if (StringUtils.isEmpty(record.getName())) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS0001", "IMS0001", "数据名称");
		}
		return rtnB;
	}
	
	/**
	 * 获取基础资料信息业务逻辑层操作对象<p>
	 */
	private IDataBaseService<E, ID> getBasedataLogicBean() {
		return (IDataBaseService<E, ID>)getCurrentService();
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
			putErrorMessage("IMS".concat(StringUtils.getRandomString()), "EMS0003",
				record.getNumbers().concat(",").concat(getCustomizeMessage(oprtName))
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
			putErrorMessage(
				"EMS".concat(StringUtils.getRandomString()), "EMS0002", record.getNumbers().concat(",").concat(
					status ? getCustomizeMessage("BD_STATUS_ENABLE") : getCustomizeMessage("BD_STATUS_DISABLE")
				).concat(",").concat(getCustomizeMessage(oprtName))
			);
		}
		return rtnB;
	}
	
	/**
	 * 分录行单击事件监听函数<p>
	 */
	public void datagrid_row_single_onclick() {
		// 获取选中行信息
		E[] records = getSelectedRowsData();
		if (CommonUtils.isNotEmptyObjectArray(records)) {
			E record = records[0];
			// 根据选中行状态初始化工具栏按钮
			if (CommonUtils.isNotEmptyObject(toolbar)) {
				// 启用状态
				if (record.getIsEnable()) {
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEdit"), false, true);
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDelete"), false, true);
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDisable"), false, false);
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEnable"), false, true);
				} else {
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEdit"), false, false);
					// 系统数据不允许编辑
					if (!record.getIsSystem()) {
						OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDelete"), false, false);
					}
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnDisable"), false, true);
					OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.getUIToolButtonFromComponent(toolbar, "btnEnable"), false, false);
				}
			}
		}
	}
}
