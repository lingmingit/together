package com.together.framework.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.together.framework.entity.AbstractDataBase;




/**
 * 用户实体对象
 * <p>
 * @author LingMin 
 * @date 2014-06-18<br>
 * @version 1.0<br>
 */
@Entity @Table(name = "t_sys_user")
public class User extends AbstractDataBase{
	
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 74013334601208534L;
	/** 性        别 **/
	@Column(length=6)
	private String sex;
	/** 年        龄 **/
	@Column
	private Integer age;
	/** 邮件地址 **/
	@Column(length=50)
	private String email;
	/** 用户密码  **/
	@Column(length=32)
	private String password;
	/** 电话号码 **/
	@Column(length=20)
	private String telephone;
	/** 手机号码 **/
	@Column(length=20)
	private String mobilePhone;
	/** QQ **/
	@Column(length=20)
	private String qq;
	/** 用户类型 **/
	@Column(length=6)
	private String userType;
	/** 家庭地址 **/
	@Column(length=100)
	private String homeAddress;
	/** 所属分组 **/
	@OneToOne(fetch=FetchType.LAZY) @JoinColumn
	private UserGroup group;
	/** 删除标识  **/
	@Column(length=1)
	private java.lang.Boolean isDelete=false;
	/** 锁定标识 **/
	@Column(length=1)
	private java.lang.Boolean isLocked=false;
	/** 封存标识 **/
	@Column(length=1)
	private java.lang.Boolean isArchive=false;

	/**
	 * 获取QQ号码<p>
	 * @return QQ号码<br>
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * 设置QQ号码<p>
	 * @param QQ号码<br>
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 获取用户类型<p>
	 * @return  userType  用户类型<br>
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * 设置用户类型<p>
	 * @param  userType  用户类型<br>
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 获取性别<p>
	 * @return 性别<br>
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置性别<p>
	 * @param sex 性别<br>
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取年龄<p>
	 * @return 年龄<br>
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * 设置年龄<p>
	 * @param age 年龄<br>
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	
	/**
	 * 获取邮件地址<p>
	 * @return 邮件地址<br>
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮件地址<p>
	 * @param email 邮件地址<br>
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取用户所属分组<p>
	 * @return 用户所属分组<br>
	 */
	public UserGroup getGroup() {
		return group;
	}

	/**
	 * 设置用户所属分组<p>
	 * @param group 用户所属分组<br>
	 */
	public void setGroup(UserGroup group) {
		this.group = group;
	}
	
	/**
	 * 获取用户密码<p>
	 * @return 用户密码<br>
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置用户密码<p>
	 * @param password 用户密码<br>
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	

	/**
	 * 获取删除标识<p>
	 * @return 删除标识<br>
	 */
	public java.lang.Boolean getIsDelete() {
		return isDelete;
	}
	
	/**
	 * 设置删除标识<p>
	 * @param isDelete 删除标识<br>
	 */
	public void setIsDelete(java.lang.Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	/**
	 * 获取锁定标识<p>
	 * @return 锁定标识<br>
	 */
	public java.lang.Boolean getIsLocked() {
		return isLocked;
	}
	
	/**
	 * 设置锁定标识<p>
	 * @param isLocked 锁定标识<br>
	 */
	public void setIsLocked(java.lang.Boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	/**
	 * 获取封存标识<p>
	 * @return 封存标识<br>
	 */
	public java.lang.Boolean getIsArchive() {
		return isArchive;
	}
	
	/**
	 * 设置封存标识<p>
	 * @param isArchive 封存标识<br>
	 */
	public void setIsArchive(java.lang.Boolean isArchive) {
		this.isArchive = isArchive;
	}

	/**
	 * 获取家庭号码<p>
	 * @return 家庭号码<br>
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置家庭号码<p>
	 * @param telephone 家庭号码<br>
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取手机号码<p>
	 * @return 手机号码<br>
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * 设置手机号码<p>
	 * @param mobilePhone 手机号码<br>
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * 获取家庭地址<p>
	 * @return 家庭地址<br>
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * 设置家庭地址<p>
	 * @param homeAddress 家庭地址<br>
	 */
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
}
