package com.sz91online.bgms.module.user.dao;

import java.util.List;

import com.sz91online.bgms.module.user.domain.Auth;
import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.common.db.service.ISearchableDAO;

public interface AuthMapperExt extends ISearchableDAO {

	List<SimpleAuth> findAuthByUserCode(String userCode);

	List<SimpleAuth> findAuthByUserCodeWithRole(String userCode);

	List<Auth> getAuthByRoleCode(String roleCode);

	void addRoleAuth(String roleCode, String authCode);

	void removeRoleAuth(String roleCode, String authCode);

	List<SimpleAuth> findManagementAuthByUserCode(String userCode);

}