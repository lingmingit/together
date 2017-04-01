package com.together.console.system.bean;

import javax.faces.model.SelectItem;

import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.annotation.SaveState;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.date.DataCheckUtils;
import com.together.common.number.NumberUtils;
import com.together.console.system.service.IUserGroupService;
import com.together.console.system.service.IUserService;
import com.together.framework.common.annotation.IServiceCtrl;
import com.together.framework.entity.sys.User;
import com.together.framework.entity.sys.UserGroup;
import com.together.framework.service.ITreeDataService;
import com.together.framework.service.base.ICoreBaseService;
import com.together.framework.web.aom.edit.impl.DataBasetreeEditUIBean;

/**
 * 用户信息编辑界面实现类<p>
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 */
@ManagedBean(name="console.system.userEditUIBean", scope=ManagedBeanScope.REQUEST)
public class UserEditUIBean extends DataBasetreeEditUIBean<UserGroup, User, String> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 2508489046061024731L;
	/** 年        龄 **/
	@Bind
	protected Integer age;
	/** 性        别 **/
	@Bind
	protected  String sex;
	/** 电子邮箱 **/
	@Bind
	protected String email;
	/** 用户密码 **/
	@Bind
	protected String password;
	/** 电话号码 **/
	@Bind
	protected String telephone;
	/** 手机号码 **/
	@Bind
	protected String mobilePhone;
	/** QQ **/
	@Bind
	protected String qq;
	/** 用户类型 **/
	@Bind
	protected String userType;
	/** 家庭地址 **/
	@Bind
	protected String homeAddress;
	/** 性别下拉容器 **/
	@Bind @SaveState
	protected SelectItem[] sexDispList;
	/** 用户类型下拉容器 **/
	@Bind @SaveState
	protected SelectItem[] userTypeDispList;
	/** 用户信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userService")
	protected IUserService userService;
	/** 用户分组信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userGroupService")
	protected IUserGroupService userGroupService;

	/**
	 * 收集页面数据<p>
	 */
	public void storeField() {
		super.storeField();
		// 年        龄
		model.setAge(age);
		// 性        别
		model.setSex(sex);
		// 电子邮件
		model.setEmail(email);
		// 电话号码
		model.setTelephone(telephone);
		// 手机号码
		model.setMobilePhone(mobilePhone);
		//qq
		model.setQq(qq);
		//用户类型
		model.setUserType(userType);
		// 家庭地址
		model.setHomeAddress(homeAddress);
		// 密        码
		if (userService.isPasswordChanged(id, password)) {
			//model.setPassword(EncryptUtils.md5Encode(password));
		}
	}
	
	/**
	 * 加载页面数据<p>
	 */
	public void loadField() {
		super.loadField();
		// 年        龄
		age = model.getAge();
		// 性        别
		sex = model.getSex();
		// 电子邮件
		email = model.getEmail();
		// 密        码
		password = model.getPassword();
		// 电话号码
		telephone = model.getTelephone();
		// 手机号码
		mobilePhone = model.getMobilePhone();
		// 家庭地址
		homeAddress = model.getHomeAddress();
		//qq
		qq = model.getQq();
		//用户类型
		userType = model.getUserType();
	}
	
	/**
	 * 判断输入数据的合法性<p>
	 */
	public boolean verifyInput() {
		boolean rtnB = super.verifyInput();
		if (rtnB) {
			// 用户密码
			if (StringUtils.isEmpty(password)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0002", "IMS0001", getAomMessage("user.password"));
			}
			// 用户名称
			if (StringUtils.isEmpty(name)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0003", "IMS0001", getAomMessage("user.name"));
			}
			// 用户年龄
			if (NumberUtils.isEmptyInteger(age)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0004", "IMS0001", getAomMessage("user.age"));
			}
			// 性别
			if (StringUtils.isEmpty(sex)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0005", "IMS0001", getAomMessage("user.sex"));
			}
			// 移动电话
			if (StringUtils.isEmpty(mobilePhone)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0006", "IMS0001", getAomMessage("user.mobilePhone"));
			}
			// 固定电话
			if (StringUtils.isNotEmpty(telephone) && !DataCheckUtils.checkTelephoneNo(telephone)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0007", "IMS0014", getAomMessage("user.telephone"));
			}
			// 移动号码
			if (StringUtils.isNotEmpty(mobilePhone) && !DataCheckUtils.checkMobileNo(mobilePhone)) {
				rtnB = rtnB ? false : rtnB;
				putErrorMessage("IMS0008", "IMS0014", getAomMessage("user.mobilePhone"));
			}
		}
		return rtnB;
	}
	
	/**
	 * 获取编辑界面的URL路径<p>
	 */
	public String getEditURL() {
		return "/console/system/userEditUI.faces";
	}

	/**
	 * 获取功能名称<p>
	 */
	public String getFunctionName() {
		return "console.system.user";
	}
	
	/**
	 * 初始化下拉列表信息<p>
	 */
	public void initSelectItemList() {
		super.initSelectItemList();
		// 性别下拉容器
		if (CommonUtils.isEmptyObjectArray(sexDispList)) {
			//sexDispList = getSelectItemLogicBean().getSelectItemList("PERSON_SEX_ENUMS");
		}
		//用户类型
		if(CommonUtils.isEmptyObjectArray(userTypeDispList)){
			//userTypeDispList = getSelectItemLogicBean().getSelectItemList("USER_TYPE");
		}
	}

	/**
	 * 获取业务逻辑层操作对象<p>
	 */
	public ICoreBaseService<User, String> getCurrentService() {
		return userService;
	}

	/**
	 *  获取分组信息业务逻辑层操作对象<p>
	 */
	public ITreeDataService<UserGroup, String> getTreeGroupService() {
		return userGroupService;
	}
}
