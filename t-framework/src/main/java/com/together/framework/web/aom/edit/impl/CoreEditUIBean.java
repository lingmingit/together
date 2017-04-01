/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.web.aom.edit.impl;

import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.component.widget.UIForm;
import org.operamasks.faces.component.widget.UIToolBar;
import org.operamasks.faces.user.util.Browser;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.web.aom.base.impl.CoreBaseUIBean;
import com.together.framework.web.aom.edit.ICoreEditUI;
import com.together.framework.web.enums.ActionNameDefineEnum;
import com.together.framework.web.enums.PageStatusEnum;
import com.together.framework.web.utils.OperamaskUtils;

/** 
 * 编辑界面后台核心litBean 抽象实现基类<p>
 * @author LingMin 
 * @date 2014-7-21<br>
 * @version 1.0<br>
 */
public abstract class CoreEditUIBean<E extends java.io.Serializable , ID extends java.io.Serializable> extends CoreBaseUIBean implements ICoreEditUI<E, ID> {

	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -4469107270185351654L;

	/** 关键字 **/
	@Bind
	protected ID id;
	/** 编    码 **/
	@Bind
	protected String number;
	/** 页面对应实体对象 **/
	@Accessible
	protected E model = null;
	/** 信息实体对象的序列 **/
	private int modelIndex = 0;
	/** 工具栏控件 **/
	@Bind(id="toolbar")  
    protected UIToolBar toolbar;
	/** 备注信息 **/
	@Bind
	protected String description;
	/** 界面FORM绑定 **/
	@Accessible @Bind(id="editForm")
	protected UIForm editForm;
	/** 是否自动编码 **/
	private boolean hasUsedNumRule = false;
	/** 编码规则信息 **/
   // private NumberRuler numberRuler = null;
	/** 编码允许重复标识 **/
	private boolean isDuplicateNumber = false;
	/** 列表控件的JS变量名 **/
	private String dataGridName = "listDataGrid";
	
	
	/**
	 * 页面渲染之前调用的函数<p>
	 */
	public void beforePageOnLoad() {
		super.beforePageOnLoad();
		// 根据页面状态初始化
		if (!(PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus()) || PageStatusEnum.PAGE_EXCEPTION.equals(getPageStatus()))) {
			id = (ID) getRequestParameter(FrameworkConstants.SELECTED_RECORD_KEY);
			if (StringUtils.isNotEmpty((String) id)) {
				//setSessionAttribute(FrameworkConstants.SELECTED_RECORD_KEY, id);
			}
		}
		// 根据页面状态初始化页面
		if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus())) {
			// 初始化页面绑定对象
			createNewModel();
			// 初始化页面数据
			initalPageData();
		} else if (PageStatusEnum.PAGE_EXCEPTION.equals(getPageStatus())) {
			// 初始化页面绑定对象
			createNewModel();
		} else {
			Object record = getCurrentService().get(id);
			// 加载页面数据
			if (record.getClass() == getEntityModelClass(modelIndex)) {
				model = (E) record;
				// 复制新增
				if (PageStatusEnum.COPY_ADD.equals(getPageStatus())) {
					String oldPk = (String) ReflectionUtils.getFieldValue(record, "id");
					//setSessionAttribute(FrameworkConstants.OLD_KEY_FOR_COPYADD, oldPk);
					initalPageDataForCopyAdd(model);
				}
			}
		}
	}
	
	/**
	 * 初始化页面<p>
	 */
	public void pageOnLoad() {
		// 加载页面数据
		loadField();
		// 初始化页面
		super.pageOnLoad();
	}
	
	/**
	 * 【保存】按钮监听函数<p>
	 */
	public void saveAction() {
		try {
			// 操作权限判断
			if (checkPermission(ActionNameDefineEnum.SaveAction.getValue())) {
				// 验证输入数据的合法性
				if (verifyInput()) {
					// 验证编码合法性
					checkNumber();
					// 收集页面数据
					storeField();
					// 执行保存
					processSaveRecord(model);
					// 重载页面数据
					pageOnLoad();
					// 设置页面状态
					setPageStatus(PageStatusEnum.PAGE_EDIT.getValue());
					// 执行页面跳转
					forwardURL(getCommonPageURL());
				}
			}
			// 刷新当前页面
			refreshParentDataGrid();
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			//putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		//displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 【退出】按钮监听函数<p>
	 */
	public void exitAction() {
		Browser.execClientScript("window.close();");
	}
	
	/**
	 * 【编辑】按钮监听函数<p>
	 */
	public void editAction() {
		try {
			// 操作权限判断
			if (checkPermission(ActionNameDefineEnum.EditAction.getValue())) {
				// 收集页面数据
				storeField();
				// 当ID不为空时，进行合法性判断
				if (verifyEdit(model)) {
					// 设置页面状态
					setPageStatus(PageStatusEnum.PAGE_EDIT.getValue());
					//removeSessionAttribute(FrameworkConstants.SELECTED_RECORD_KEY);
					forwardURL(getCommonPageURL());
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
	 * 【新增】按钮监听函数<p>
	 */
	public void addNewAction() {
		try {
			// 操作权限判断
			if (verifyAddNew()) {
				setPageStatus(PageStatusEnum.PAGE_ADDNEW.getValue());
				//removeSessionAttribute(FrameworkConstants.SELECTED_RECORD_KEY);
				forwardURL(getCommonPageURL());
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
	 * 判断是否允许执行删除操作<p>
	 */
	public void deleteAction() {
		try {
			// 操作权限判断
			if (checkPermission(ActionNameDefineEnum.DeleteAction.getValue())) {
				// 收集页面数据
				storeField();
				if (verifyDelete(model)) {
					processDeleteRecord(model);
					//putErrorMessage("SMS".concat(StringUtils.getRandomString()), "SMS0001", StringUtils.getLegalString(number).concat(",").concat(getCustomizeMessage("DATA_BTN_DELETE")));
					// 刷新当前页面
					refreshParentDataGrid();
					// 执行新增操作
					addNewAction();
				}
			}
		} catch(Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			//putErrorMessage("EMS9999", ex.getMessage());
		}
		// 显示提示信息
		//displayClientMessageTime("tipMessage");		
	}
	
	/**
	 * 加载页面数据<p>
	 */
	public void loadField() {
		// 关  键  字
		id = (ID) ReflectionUtils.getFieldValue(model, "id");
		// 编        码
		number = (String) ReflectionUtils.getFieldValue(model, "numbers");
		// 备注信息
		description = (String) ReflectionUtils.getFieldValue(model, "description");
	}
	
	/**
	 * 收集页面数据<p>
	 */
	public void storeField() {
		// 页面状态【新增】、【复制新增】
		if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus()) || PageStatusEnum.COPY_ADD.equals(getPageStatus())) {
			createNewModel();
		} else {
			if (StringUtils.isNotEmpty((String)id)) {
				Object record = getCurrentService().get(id);
				if (CommonUtils.isNotEmptyObject(record)) {
					// 加载页面数据
					if (record.getClass() == getEntityModelClass(modelIndex)) {
						model = (E) record;
					}
				}
			}
		}
		// 编        码
		ReflectionUtils.setFieldValue(model, "numbers", number);
		// 备注信息
		ReflectionUtils.setFieldValue(model, "description", description);
	}
	
	/**
	 * 判断输入数据的合法性<p>
	 */
	public boolean verifyInput() {
		boolean rtnB = true;
		// 验证编码
		if (StringUtils.isEmpty(number)) {
			rtnB = rtnB ? false : rtnB;
			//putErrorMessage("IMS0001", "IMS0001", getAomMessage("system.number"));
		}
		return rtnB;
	}
	
	/**
	 * 判断是否允许进行新增操作<p>
	 */
	public boolean verifyAddNew() {
		return checkPermission("addNewAction");
	}
	
	/**
	 * 页面状态【新增】时初始化页面数据<p>
	 */
	public void initalPageData() {
		// 启用编码规则时对编码进行初始化
		if(hasUsedNumRule) {
			ReflectionUtils.setFieldValue(model, "numbers", getNextRulerNumber());
		}
	}
	
	/**
	 * 初始化页面绑定对象<p>
	 * @return 实体对象<br>
	 */
	public void createNewModel() {
		try {
			model = (E)getEntityModelClass(modelIndex).newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 页面渲染滞后调用的函数<p>
	 */
	public void afterPageOnLoad() { }
	
	/**
	 * 初始化页面控件<p>
	 */
	public void initPageComponent() {
		// 当页面状态为 查看|审核
		if (PageStatusEnum.PAGE_EXCEPTION.equals(getPageStatus()) || PageStatusEnum.PAGE_VIEW.equals(getPageStatus())
				|| PageStatusEnum.PAGE_AUDIT.equals(getPageStatus())) {
			lockComponentForView();
		}
		// 当页面状态为新增|编辑时，如启用编码规则，则编码规则控件锁定
		if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus()) || PageStatusEnum.PAGE_EDIT.equals(getPageStatus())) {
			if (hasUsedNumRule || PageStatusEnum.PAGE_EDIT.equals(getPageStatus())) {
				OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.findUIComponent(editForm, "number"), false, true);
			} else {
				OperamaskUtils.lockOrUnLockComponent(OperamaskUtils.findUIComponent(editForm, "number"), false, false);
			}
		}
	}
	
	/**
	 * 获取标准功能的URL路径<p>
	 * @return URL路径<br>
	 */
	public String getCommonPageURL() {
		StringBuffer BufURL = new StringBuffer(getFacesContextURL(getEditURL()));
		// 拼接页面状态
		if (CommonUtils.isNotEmptyObject(getPageStatus())) {
			BufURL.append("?").append(FrameworkConstants.PAGE_STATUS).append("=").append(getPageStatus());
		}
		// 拼接关键字
		if (CommonUtils.isNotEmptyObject(id)) {
			BufURL.append("&").append(FrameworkConstants.SELECTED_RECORD_KEY).append("=").append((String) id);
		}
		return BufURL.toString();
	}
	
	/**
	 * 当页面状态为查看时,锁定制定控件<p>
	 */
	public void lockComponentForView() {
		if (CommonUtils.isNotEmptyObject(editForm)) {
			OperamaskUtils.lockOrUnLockRootComponent(editForm, false, true);
			// 页面状态为审核时解锁页面部分控件
			if (PageStatusEnum.PAGE_AUDIT.equals(getPageStatus())) {
				unLockComponentForAudit();
			}
		}
	}
	
	/**
	 * 页面状态为审核时解锁页面部分控件<p>
	 */
	public void unLockComponentForAudit() { }
	
	/**
	 * 执行业务逻辑层保存数据操作<p>
	 */
	public void processSaveRecord(E record) {
		// 基础资料
		if (record instanceof AbstractDataBase) {
			// 新增
			if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus()) || PageStatusEnum.COPY_ADD.equals(getPageStatus())) {
				ReflectionUtils.setFieldValue(record, "creator", getCurrentSessionUser());
				ReflectionUtils.setFieldValue(record, "createTime", new java.util.Date());
				ReflectionUtils.setFieldValue(record, "modifier", getCurrentSessionUser());
				ReflectionUtils.setFieldValue(record, "modifyTime", new java.util.Date());
			}
			// 编辑
			else if (PageStatusEnum.PAGE_EDIT.equals(getPageStatus())) {
				ReflectionUtils.setFieldValue(record, "modifier", getCurrentSessionUser());
				ReflectionUtils.setFieldValue(record, "modifyTime", new java.util.Date());
			}
		}
		// 执行数据库操作
		id = getCurrentService().save(record);
		if (PageStatusEnum.COPY_ADD.equals(getPageStatus())) {
			processChangeForCopyAdd(record);
		}
		// 执行分录数据业务逻辑层保存操作
		processSaveEntryRecord(record);
		//putErrorMessage("SMS0001", "SMS0001",((String) ReflectionUtils.getFieldValue(model, "numbers")).concat(",").concat(getCustomizeMessage("DATA_SAVE_BTN")));
	}
	
	/**
	 * 执行业务逻辑层删除数据操作<p>
	 */
	public void processDeleteRecord(E record) {
		this.getCurrentService().delete((ID) ReflectionUtils.getFieldValue(record, "id"));
	}
	
	/**
	 * 执行分录数据业务逻辑层保存操作<p>
	 */
	public void processSaveEntryRecord(E parent) {}
	
	/**
	 * 设置编码规则信息<p>
	 * @param numberRuler 编码规则信息<br>
	 */
//	public void setNumberRuler(NumberRuler numberRuler) {
//		this.numberRuler = numberRuler;
//	}
	
	/**
	 * 设置自动编码标识<br>
	 * @param hasUsedNumRule true:自动 false:手动<br>
	 */
	public void setHasUsedNumRule(boolean hasUsedNumRule) {
		this.hasUsedNumRule = hasUsedNumRule;
	}

	/**
	 * 设置是否允许编码重复的标识<p>
	 * @param isDuplicateNumber 编码重复的标识【true:允许 false:禁止】<br>
	 */
	public void setDuplicateNumber(boolean isDuplicateNumber) {
		this.isDuplicateNumber = isDuplicateNumber;
	}

	/**
	 * 初始化复制新增状态下的页面数据<p>
	 */
	public void initalPageDataForCopyAdd(E record){
		ReflectionUtils.setFieldValue(record, "id", null);
		// 创建者
		if (ReflectionUtils.isExistFieldForDeep(getEntityModelClass(modelIndex), "creator")) {
			ReflectionUtils.setFieldValue(record, "creator", null);
		}
		// 创建时间
		if (ReflectionUtils.isExistFieldForDeep(getEntityModelClass(modelIndex), "createTime")) {
			ReflectionUtils.setFieldValue(record, "createTime", null);
		}
		// 修改者
		if (ReflectionUtils.isExistFieldForDeep(getEntityModelClass(modelIndex), "modifier")) {
			ReflectionUtils.setFieldValue(record, "modifier", null);
		}
		// 修改时间
		if (ReflectionUtils.isExistFieldForDeep(getEntityModelClass(modelIndex), "modifyTime")) {
			ReflectionUtils.setFieldValue(record, "modifyTime", null);
		}
	}
	
	/**
	 * 刷新父窗口的指定名称的列表对象<p>
	 */
	public void refreshParentDataGrid() {
		StringBuffer jscriptBuffer = new StringBuffer(" var parent = window.opener; ")
			.append(" if(typeof(parent) == 'object' && typeof(parent.").append(dataGridName).append(")")
			.append(" == 'object') {  parent.").append(dataGridName).append(".getStore().reload();").append(" }");
		Browser.execClientScript(jscriptBuffer.toString());
	}
	
	/**
	 * 验证编码的合法性<p>
	 */
	protected void checkNumber() {
		// 编码合法性判断
		if (StringUtils.isNotEmpty(number)) {
			String oldNum = number;
			if (!isDuplicateNumber) {
				boolean duplicate = false;
				if (PageStatusEnum.PAGE_ADDNEW.equals(getPageStatus())) {
					duplicate = getCurrentService().isExistenceNumber(null, number);
				} else if (PageStatusEnum.PAGE_EDIT.equals(getPageStatus())) {
					model = getCurrentService().get(id);
					// 当前数据记录编码等于原编码时，继续执行
					oldNum = (String) ReflectionUtils.getFieldValue(model, "numbers");
					if (oldNum.equalsIgnoreCase(number)) {
						duplicate = duplicate ? duplicate : false;
					} else {
						duplicate = getCurrentService().isExistenceNumber(id, number);
					}
				}
				// 当编码重复时,如果自动生成标识为真时，自动生成并保存,否则报错
				if (duplicate) {
					this.number = getNextRulerNumber();
					//putErrorMessage("IMS0099", "IMS0006", StringUtils.getLegalString(oldNum).concat(",").concat(number));
				}
			}
		}
	}
	
	/**
	 * 设置信息实体对象的序列<p>
	 * @param modelIndex 信息实体对象的序列<br>
	 */
	public void setModelIndex(int modelIndex) {
		this.modelIndex = modelIndex;
	}
	
	/**
	 * 设置列表控件的JS变量名<p>
	 * @param dataGridName 列表控件的JS变量名<br>
	 */
	public void setDataGridName(String dataGridName) {
		this.dataGridName = dataGridName;
	}
	
	/**
	 * 复制新增数据保存后相关操作函数<p>
	 */
	public void processChangeForCopyAdd(E record) {}

	/**
	 * 根据编码规则获取新的编码<p>
	 * @return 编码<br>
	 */
	private String getNextRulerNumber() {
		//return getCurrentService().generateNextNumber(getCurrentDatabase(), numberRuler);
		return null;
	}
}
