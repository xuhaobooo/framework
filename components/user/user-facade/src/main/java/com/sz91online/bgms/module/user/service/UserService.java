package com.sz91online.bgms.module.user.service;

import java.util.List;
import java.util.Map;

import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.bgms.module.user.domain.SimpleUser;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.common.db.service.IDefaultService;


public interface UserService extends IDefaultService<User> {

	/**
	 * 通过用户Code查询用户
	 * @param userCode
	 * @return
	 */
	public User findUserByCode(String userCode);
	
	/**
	 * 通过登录账号查询用户信息，包括密码
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName);
	
	/**
	 * 验证码修改密码
	 * @param loginName
	 * @param password
	 * @param captcha
	 */
	public void forgetChangePassword(String loginName, String password, String captcha);
	
	/**
	 * 通过用户查询用户角色
	 * @param userCode
	 * @return
	 */
	public List<SimpleRole> findRoleByUserCode(String userCode);

	/**
	 * 验证用户是否是某个角色
	 * @param userCode
	 * @param roleCode
	 * @return
	 */
	public boolean checkUserRole(String userCode, String roleCode);

	/**
	 * 添加用户角色 
	 * @param userCode
	 * @param roleCode
	 */
	public void addUserRole(String userCode, String roleCode);

	/**
	 * 移除用户角色
	 * @param userCode
	 * @param deptCode
	 * @param roleCode
	 */
	public void removeUserRole(String userCode, String roleCode);

	/**
	 * 验证某人是否有权限，包括角色的权限
	 * @param userCode
	 * @param authCode
	 * @return
	 */
	public boolean checkAuthority(String userCode, String authCode);
	
	/**
	 * 用户是否有单独设置的权限
	 * @param userCode
	 * @param authCode
	 * @return
	 */
	public boolean checkUserAuth(String userCode, String authCode);

	/**
	 * 查询用户的所有权限
	 * @param userCode
	 * @return
	 */
	public List<SimpleAuth> findAuthByUserCode(String userCode);
	
	/**
	 * 添加用户权限
	 * @param userCode
	 * @param deptCode
	 * @param authCode
	 */
	public void addUserAuth(String userCode, String authCode);

	/**
	 * 移除用户权限
	 * @param userCode
	 * @param deptCode
	 * @param authCode
	 */
	public void removeUserAuth(String userCode, String authCode);

	/**
	 * 通过角色分类用户
	 */
	public Map<String,Map<String,String>> getUserByRole();

	/**
	 * 批量修改用户角色
	 * @param roleId
	 * @param flag
	 * @param auths
	 */
	public void modifyRoleUser(String roleId, String flag, List<String> UserCodes);

	/**
	 * 根据用户角色查询用户
	 * @param roleId
	 * @return
	 */
	public List<SimpleUser> getUserByRoleCode(String roleId);
	
	/**
	 * 保存验证码
	 * @param code
	 * @param mobile
	 */
	public void saveCaptcha(String code, String mobile);
}
