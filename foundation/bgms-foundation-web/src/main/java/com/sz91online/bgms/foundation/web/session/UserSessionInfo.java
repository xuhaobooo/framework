package com.sz91online.bgms.foundation.web.session;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "登录用户信息对象", description = "注意此用户不是数据相关的对象，是一个通用的，脱离用户模块的对象！")
public class UserSessionInfo {

	private Long id;

	@ApiModelProperty(value = "用户账号", name = "loginName")
	private String loginName;

	@ApiModelProperty(value = "用户名称", name = "userName")
	private String userName;

	@ApiModelProperty(value = "用户代码，系统里用来表示用户的唯一ID，所有业务关联都使用此字段", name = "code")
	private String code;

	@ApiModelProperty(value = "用户身份标识字符串，用于单点登录、集群、APP免密码登录。可用security/tokenLogin登录", name = "code")
	private String accessCode;

	@ApiModelProperty(value = "用户角色", name = "userRole")
	private Set<String> userRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public Set<String> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<String> userRole) {
		this.userRole = userRole;
	}

}
