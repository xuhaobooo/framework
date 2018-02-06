package com.sz91online.bgms.module.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.user.domain.SimpleUser;
import com.sz91online.common.db.service.ISearchableDAO;

public interface UserMapperExt extends ISearchableDAO {

	Long findOneUserRole(@Param(value = "user_code") String userCode, @Param(value = "role_code") String roleCode);

	void addUserRole(@Param(value = "user_code") String userCode, @Param(value = "role_code") String roleCode);

	void removeUserRole(@Param(value = "user_code") String userCode, @Param(value = "role_code") String roleCode);

	Long findOneUserAuth(@Param(value = "user_code") String userCode, @Param(value = "auth_code") String authCode);

	void addUserAuth(@Param(value = "user_code") String userCode, @Param(value = "auth_code")String authCode);

	void removeUserAuth(@Param(value = "user_code") String userCode, @Param(value = "auth_code")String authCode);

	List<SimpleUser> getAllUserWithRole();

	List<SimpleUser> getUserByRoleCode(@Param(value = "role_code") String roleCode);
}