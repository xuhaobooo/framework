package com.sz91online.bgms.module.user.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_u_user
 */
public class User extends RootBean {
	/**
	 *
	 * 此属性对应数据库字段: t_u_user.id
	 */
	private Long id;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.last_name
	 */
	private String lastName;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.login_name
	 */
	private String loginName;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.password
	 */
	private String password;

	/**
	 * N：正常使用状态 S：状态为禁用状态，需要管理员恢复 T：未认证状态，用户需通过页面认证 L：被锁定状态，可通过页面或管理员解锁
	 *
	 * 
	 * 此属性对应数据库字段: t_u_user.status
	 */
	private String status;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.create_time
	 */
	private Date createTime;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.user_code
	 */
	private String userCode;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.user_desc
	 */
	private String userDesc;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.dept_code
	 */
	private String deptCode;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.email
	 */
	private String email;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.first_name
	 */
	private String firstName;

	private String cathcha;

	/**
	 *
	 * 此属性对应数据库字段: t_u_user.access_code
	 */
	private String accessCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getCathcha() {
		return cathcha;
	}

	public void setCathcha(String cathcha) {
		this.cathcha = cathcha;
	}

}