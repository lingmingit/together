package com.together.console.system.bean;

import org.operamasks.faces.annotation.Action;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.number.NumberUtils;
import com.together.console.system.service.IUserGroupService;
import com.together.console.system.service.IUserService;
import com.together.framework.common.annotation.IServiceCtrl;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.entity.sys.User;
import com.together.framework.entity.sys.UserGroup;
import com.together.framework.service.ITreeDataService;
import com.together.framework.service.base.ICoreBaseService;
import com.together.framework.web.aom.list.impl.TreeGroupListUIBean;

/**
 * 用户信息序时薄界面实现类<p>
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 */
@ManagedBean(name="console.system.userListUIBean", scope=ManagedBeanScope.REQUEST)
public class UserListUIBean extends TreeGroupListUIBean<UserGroup, User, String> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -3026001744798277976L;
	/** 用户信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userService")
	protected IUserService userService;
	/** 用户分组信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userGroupService")
	protected IUserGroupService userGroupService;
	
	/**
	 * 构造函数:初始化相关参数<p>
	 */
	public UserListUIBean() {
		setGroupField("group");
		setWinWidth(NumberUtils
			.getIntegerFromString(getSystemConfigParam("modal_double_width"))
		);
		setWinHeight(NumberUtils
			.getIntegerFromString(getSystemConfigParam("modal_double_height"))
		);
		setTreeWinWidth(NumberUtils
			.getIntegerFromString(getSystemConfigParam("modal_single_width"))
		);
		setTreeWinHeight(NumberUtils
			.getIntegerFromString(getSystemConfigParam("modal_single_height"))
		);
	}
	
	/**
	 * 设置默认的过滤条件<p>
	 */
	public void setDefaultCondition() {
		super.setDefaultCondition();
		// 删除标志
		getDefaultCondition().put(new ConditionParameter("isDelete", "0", CompareTypeEnum.COMPARE_EQUEAL));
	}
	
	/**
	 * 封存指定的用户信息<p>
	 */
	public void archiveAction() {
		// 操作权限判定
		if (checkPermission("archiveAction")) {
			// 判断是否选中行
			if (checkRowSelectedForOperate("DATA_ARCHIVE_BTN")) {
				User[] selected = getSelectedRowsData();
				for (User record : selected) {
					// 判断是否允许封存
					if (verifyArchive(record)) {
						userService.archiveUser(record, getCurrentSessionUser());
						putErrorMessage("SMS".concat(StringUtils.getUUIDString()), "SMS0001", record.getName().concat(",").concat(getCustomizeMessage("DATA_ARCHIVE_BTN")));
					}
				}
				refreshDataGridList();
			}
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 锁定指定的用户信息<p>
	 */
	@Action
	public void lockAction() {
		// 操作权限判定
		if (checkPermission("lockUserAction")) {
			// 判断是否选中行
			if (checkRowSelectedForOperate("DATA_LOCK_BTN")) {
				User[] selected = getSelectedRowsData();
				for (User record : selected) {
					// 判断是否允许锁定
					if (verifyLock(record)) {
						userService.lockOrUnlockUser(record, getCurrentSessionUser(), Boolean.TRUE);
						putErrorMessage("SMS".concat(StringUtils.getUUIDString()), "SMS0001", record.getName().concat(",").concat(getCustomizeMessage("DATA_LOCK_BTN")));
					}
				}
				refreshDataGridList();
			}
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}
	
	/**
	 * 解锁指定的用户信息<p>
	 */
	@Action
	public void unlockAction() {
		// 操作权限判定
		if (checkPermission("unlockUserAction")) {
			// 判断是否选中行
			if (checkRowSelectedForOperate("DATA_UNLOCK_BTN")) {
				User[] selected = getSelectedRowsData();
				for (User record : selected) {
					// 判断是否允许锁定
					if (verifyUnlock(record)) {
						userService.lockOrUnlockUser(record, getCurrentSessionUser(), Boolean.FALSE);
						putErrorMessage("SMS".concat(StringUtils.getUUIDString()), "SMS0001", record.getName().concat(",").concat(getCustomizeMessage("DATA_UNLOCK_BTN")));
					}
				}
				refreshDataGridList();
			}
		}
		// 显示提示信息
		displayClientMessageTime("tipMessage");
	}

	/**
	 * 获取编辑界面的URL路径<p>
	 */
	public String getEditURL() {
		return "/console/system/userEditUI.faces";
	}

	/**
	 * 获取树型信息编辑界面URL<p>
	 */
	public String getTreeEditURL() {
		return "/console/system/userGroupEditUI.faces";
	}
	
	/**
	 * 获取绑定实体对象数组<p>
	 */
	public User[] getEntity_Array() {
		return new User[0];
	}

	/**
	 * 获取功能名称<p>
	 */
	public String getFunctionName() {
		return "console.system.user";
	}
	
	/**
	 * 获取树型初始化根结点的显示名称<p>
	 */
	public String getTreeRootName() {
		return getAomMessage("usergroup.grouproot");
	}
	
	/**
	 * 判断是否允许锁定用户<p>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyLock(User user) {
		boolean rtnB = true;
		// 启用
		boolean result = user.getIsEnable();
		if (CommonUtils.isEmptyObject(result) || !result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("BD_STATUS_DISABLE")).concat(",").concat(getCustomizeMessage("DATA_LOCK_BTN")));
		}
		// 锁定
		result = user.getIsLocked();
		if (CommonUtils.isNotEmptyObject(result) && result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("BD_STATUS_LOCKER")).concat(",").concat(getCustomizeMessage("DATA_LOCK_BTN")));
		}
		// 封存
		result = user.getIsArchive();
		if (CommonUtils.isNotEmptyObject(result) && result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("BD_STATUS_ARCHIVE")).concat(",").concat(getCustomizeMessage("DATA_LOCK_BTN")));
		}
		// 删除
		result = user.getIsDelete();
		if (CommonUtils.isNotEmptyObject(result) && result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("BD_STATUS_DELETE")).concat(",").concat(getCustomizeMessage("DATA_LOCK_BTN")));
		}
		return rtnB;
	}
	
	/**
	 * 判断是否允许解锁用户<p>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyUnlock(User user) {
		boolean rtnB = true;
		// 锁定
		boolean result = user.getIsLocked();
		if (CommonUtils.isNotEmptyObject(result) && !result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("BD_STATUS_UNLOCKER")).concat(",").concat(getCustomizeMessage("DATA_UNLOCK_BTN")));
		}
		// 封存
		result = user.getIsArchive();
		if (CommonUtils.isNotEmptyObject(result) && result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("BD_STATUS_ARCHIVE")).concat(",").concat(getCustomizeMessage("DATA_UNLOCK_BTN")));
		}
		return rtnB;
	}
	
	/**
	 * 判断是否允许封存用户<p>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyArchive(User user) {
		boolean rtnB = true;
		// 封存
		boolean result = user.getIsArchive();
		if (CommonUtils.isNotEmptyObject(result) && result) {
			rtnB = rtnB ? false : rtnB;
			putErrorMessage("IMS".concat(StringUtils.getUUIDString()), "IMS0098", user.getName().concat(",").concat(getCustomizeMessage("DB_STATUS_ARCHIVE")).concat(",").concat(getCustomizeMessage("DATA_ARCHIVE_BTN")));
		}
		return rtnB;
	}
	
	/**
	 * 获取编辑界面的显示名称<p>
	 */
	public String getEditUIDisplayName() {
		return getAomMessage("user_edit_label");
	}
	
	/**
	 * 获取树型维护界面的显示名称<p>
	 */
	public String getTreeEditUIDisplayName() {
		return getAomMessage("usergroup_edit_label");
	}
	
	/**
	 * 执行业务逻辑层删除数据操作<p>
	 */
	public void processDeleteRecord(User record) {
		userService.deleteUser(record.getId(), getCurrentSessionUser());
	}
	
	/**
	 * 获取业务逻辑层操作对象<p>
	 */
	public ICoreBaseService<User, String> getCurrentService() {
		return userService;
	}
	
	/**
	 * 获取树型信息业务逻辑层操作对象<p>
	 */
	public ITreeDataService<UserGroup, String> getCurrentTreeService() {
		return userGroupService;
	}
}
