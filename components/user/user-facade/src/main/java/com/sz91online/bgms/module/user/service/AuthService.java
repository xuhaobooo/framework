package com.sz91online.bgms.module.user.service;

import java.util.List;

import com.sz91online.bgms.module.user.domain.Auth;
import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.common.db.service.IDefaultService;

public interface AuthService extends IDefaultService<Auth> {

	/**
	 * 查询用户所有被直接赋予的权限(公共权限)，不包含Role里的权限
	 * 
	 * @param userCode
	 * @return
	 */
	List<SimpleAuth> findAuthByUserCode(String userCode);

	/**
	 * 查询用户通过角色被赋予的权限
	 * 
	 * @param userCode
	 * @return
	 */
	List<SimpleAuth> findAuthByUserCodeWithRole(String userCode);

	/**
	 * 查询用户所有权限，包括直接和角色赋予的权限(公共权限)
	 * 
	 * @param userCode
	 * @return
	 */
	List<SimpleAuth> findAllAuthByUserCode(String userCode);

	/**
	 * 通过roleCode查询被赋予的权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<Auth> getAuthByRoleCode(String roleCode);

	/**
	 * 修改角色权限
	 * 
	 * @param roleId
	 * @param flag
	 * @param auths
	 * @return
	 */
	void modifyRoleAuth(String roleId, String flag, List<String> auths);

}
